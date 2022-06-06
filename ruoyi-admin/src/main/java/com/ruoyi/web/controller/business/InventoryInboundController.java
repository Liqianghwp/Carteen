package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.InventoryInboundDTO;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.InventoryInboundVO;
import com.diandong.mapstruct.InventoryInboundMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 入库Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/inventory_inbound", tags = {"入库模块"})
@RequestMapping(value = "/inventory_inbound")
public class InventoryInboundController extends BaseController {

    @Resource
    private InventoryInboundMpService inventoryInboundMpService;
    @Resource
    private InventoryLedgerMpService inventoryLedgerMpService;
    @Resource
    private ISysDictDataService iSysDictDataService;

    /**
     * 入库分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryInboundVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "入库分页查询", notes = "入库分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(InventoryInboundVO vo) {
        //分页查询
        Page<InventoryInboundPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        //遍历数组
        for (InventoryInboundPO record : page.getRecords()) {
            //关联 台账
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(record.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(record.getInboundType());
            //保存入库名称
            record.setInboundName(sysDictData.getDictLabel());
            //保存原材料名称
            record.setRawName(byId.getRawMaterialName());
            //总金额
            record.setAmount(record.getPrice().multiply(new BigDecimal(record.getNumber())));
        }

        return BaseResult.success(page);
    }

    /**
     * 入库根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "入库根据id查询", notes = "入库根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<InventoryInboundDTO> getById(@PathVariable("id") Long id) {
        InventoryInboundDTO dto = InventoryInboundMsMapper.INSTANCE
                .po2dto(inventoryInboundMpService.getById(id));
        //关联 台账
        InventoryLedgerPO byId = inventoryLedgerMpService.getById(dto.getLedgerId());
        //关联字典
        SysDictData sysDictData = iSysDictDataService.selectDictDataById(dto.getInboundType());
        //保存入库名称
        dto.setInboundName(sysDictData.getDictLabel());
        //保存原材料名称
        dto.setRawName(byId.getRawMaterialName());
        //总金额
        dto.setAmount(dto.getPrice().multiply(new BigDecimal(dto.getNumber())));

        return BaseResult.success(dto);
    }
    /**
     * 入库更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryInboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "入库更新", notes = "入库更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) InventoryInboundVO vo) {
        InventoryInboundPO po = InventoryInboundMsMapper.INSTANCE.vo2po(vo);
        //判断有没有记账
        if (Constants.TALLY_NO.equals(po.getState())) {
            //总金额
            po.setAmount(po.getPrice().multiply(new BigDecimal(po.getNumber())));
            //关联账抬
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(po.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(po.getInboundType());
            //保存入库名称
            po.setInboundName(sysDictData.getDictLabel());
            //保存原材料名称
            po.setRawName(byId.getRawMaterialName());

        }else{
            return BaseResult.successMsg("已记账无发更改");
        }


            boolean result = inventoryInboundMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }
    /**
     * 入库根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "记账", notes = "记账", httpMethod = "GET")
    @GetMapping(value = "/user/{id}")
    public BaseResult baseResult(@PathVariable("id") Long id) {
        InventoryInboundPO byId = inventoryInboundMpService.getById(id);
        String state = byId.getState();
        if (Constants.TALLY_NO.equals(state)) {
         byId.setState(Constants.TALLY_YES);
        }else {
            return BaseResult.error("已记账无法更改");
        }

        boolean result = inventoryInboundMpService.updateById(byId);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 入库删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "入库删除", notes = "入库删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        InventoryInboundPO byId = inventoryInboundMpService.getById(id);
        //记账无法删除
        if (Constants.TALLY_YES.equals(byId)){
            return BaseResult.error("已记账无法删除");
        }
        boolean result = inventoryInboundMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }



    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountRuVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "入库金额", notes = "入库金额", httpMethod = "GET")
    @GetMapping(value = "/warehousing")
    public BaseResult baseResult(InventoryInboundVO vo) {
        vo.setState(Constants.TALLY_YES);
        List<InventoryInboundPO> list = onSelectWhere(vo).list();
        //设置初始值为零
         BigDecimal zero = BigDecimal.ZERO;
        for (InventoryInboundPO inventoryInboundPO : list) {
         // zero= 0   +     总金额
            zero=zero.add(inventoryInboundPO.getPrice().multiply(new BigDecimal(inventoryInboundPO.getNumber())));
        }

        return BaseResult.success(zero);
    }
    @Log(title = "入库表", businessType = BusinessType.EXPORT)
    @PutMapping("/export")
    public void export(HttpServletResponse response, InventoryInboundVO vo) {
        //实例勾选
        List<Long> ids = vo.getIds();
        //创建list集合
        List<InventoryInboundPO> list;
        //判断他有没有勾选 如果没有勾选就打印全部
        if (CollectionUtils.isNotEmpty(ids)){
          list = inventoryInboundMpService.lambdaQuery().in(InventoryInboundPO::getId, ids).list();
        }else {
            //如果勾选就打印勾选
            list=onSelectWhere(vo).list();
        }
        //将要打印的保存到inventoryInboundDTOList集合里面
        List<InventoryInboundDTO>inventoryInboundDTOList = new ArrayList<>();

        list.forEach(inventoryInboundPO -> {
            inventoryInboundDTOList.add(InventoryInboundMsMapper.INSTANCE.po2dto(inventoryInboundPO));
        });
            //将表里没有的查询出来保存到里面
        for (InventoryInboundDTO inventoryInboundDTO : inventoryInboundDTOList) {
            //总金额
            inventoryInboundDTO.setAmount(inventoryInboundDTO.getPrice().multiply(new BigDecimal(inventoryInboundDTO.getNumber())));
            //关联账抬
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(inventoryInboundDTO.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(inventoryInboundDTO.getInboundType());
            //保存入库名称
            inventoryInboundDTO.setInboundName(sysDictData.getDictLabel());
            //保存原材料名称
            inventoryInboundDTO.setRawName(byId.getRawMaterialName());


        }
        ExcelUtil<InventoryInboundDTO> util = new ExcelUtil(InventoryInboundDTO.class);
        util.exportExcel(response, inventoryInboundDTOList, "入库表");

    }



    private LambdaQueryChainWrapper<InventoryInboundPO> onSelectWhere(InventoryInboundVO vo) {
        LambdaQueryChainWrapper<InventoryInboundPO> queryWrapper = inventoryInboundMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), InventoryInboundPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getLedgerId()), InventoryInboundPO::getLedgerId, vo.getLedgerId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInbounder()), InventoryInboundPO::getInbounder, vo.getInbounder());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialPic()), InventoryInboundPO::getRawMaterialPic, vo.getRawMaterialPic());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), InventoryInboundPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), InventoryInboundPO::getPrice, vo.getPrice());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getInboundType()), InventoryInboundPO::getInboundType, vo.getInboundType());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInboundName()), InventoryInboundPO::getInboundName, vo.getInboundName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAssociation()), InventoryInboundPO::getAssociation, vo.getAssociation());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getWarehouseId()), InventoryInboundPO::getWarehouseId, vo.getWarehouseId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getWarehouseName()), InventoryInboundPO::getWarehouseName, vo.getWarehouseName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), InventoryInboundPO::getState, vo.getState());
           return queryWrapper;
    }


}
