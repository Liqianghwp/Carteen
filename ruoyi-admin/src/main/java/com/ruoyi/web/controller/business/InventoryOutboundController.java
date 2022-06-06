package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.InventoryInboundDTO;
import com.diandong.domain.po.InventoryInboundPO;
import com.diandong.domain.po.InventoryLedgerPO;
import com.diandong.domain.vo.InventoryInboundVO;
import com.diandong.mapstruct.InventoryInboundMsMapper;
import com.diandong.service.InventoryInboundMpService;
import com.diandong.service.InventoryLedgerMpService;
import com.diandong.service.RawMaterialMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.InventoryOutboundMpService;
import com.diandong.domain.po.InventoryOutboundPO;
import com.diandong.domain.dto.InventoryOutboundDTO;
import com.diandong.domain.vo.InventoryOutboundVO;
import com.diandong.mapstruct.InventoryOutboundMsMapper;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 出库Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/inventory_outbound", tags = {"出库模块"})
@RequestMapping(value = "/inventory_outbound")
public class InventoryOutboundController extends BaseController {

    @Resource
    private InventoryOutboundMpService inventoryOutboundMpService;
    @Resource
    private InventoryLedgerMpService inventoryLedgerMpService;
    @Resource
    private ISysDictDataService iSysDictDataService;
    /**
     * 出库分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryOutboundVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "出库分页查询", notes = "出库分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(InventoryOutboundVO vo) {

        Page<InventoryOutboundPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));

        for (InventoryOutboundPO record : page.getRecords()) {
            //关联 台账
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(record.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(record.getOutboundType());

            //保存出库名称
            record.setOutboundName(sysDictData.getDictLabel());
            //保存原材料名称
            record.setRawName(byId.getRawMaterialName());
            //总金额
            record.setAmount(record.getPrice().multiply(new BigDecimal(record.getNumber())));
        }
        return BaseResult.success(page);
    }

    /**
     * 出库根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "出库根据id查询", notes = "出库根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<InventoryOutboundDTO> getById(@PathVariable("id") Long id) {
        InventoryOutboundDTO dto = InventoryOutboundMsMapper.INSTANCE
                .po2dto(inventoryOutboundMpService.getById(id));
        //关联 台账
        InventoryLedgerPO byId = inventoryLedgerMpService.getById(dto.getLedgerId());
        //关联字典
        SysDictData sysDictData = iSysDictDataService.selectDictDataById(dto.getOutboundType());
        //保存入库名称
        dto.setOutboundName(sysDictData.getDictLabel());
        //保存原材料名称

        dto.setRawName(byId.getRawMaterialName());
        //总金额
        dto.setAmount(dto.getPrice().multiply(new BigDecimal(dto.getNumber())));
        return BaseResult.success(dto);
    }

    /**
     * 出库更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryOutboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "出库更新", notes = "出库更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) InventoryOutboundVO vo) {
        InventoryOutboundPO po = InventoryOutboundMsMapper.INSTANCE.vo2po(vo);
        //判断有没有记账
        if (Constants.TALLY_NO.equals(po.getState())) {
            //总金额
            po.setAmount(po.getPrice().multiply(new BigDecimal(po.getNumber())));
            //关联账抬
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(po.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(po.getOutboundType());
            //保存入库名称
            po.setOutboundName(sysDictData.getDictLabel());
            //保存原材料名称
            po.setRawName(byId.getRawMaterialName());

        }else{
            return BaseResult.successMsg("已记账无发更改");
        }
        boolean result = inventoryOutboundMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 出库删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "出库删除", notes = "出库删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        InventoryOutboundPO byId = inventoryOutboundMpService.getById(id);
        //记账无法删除
        if (Constants.TALLY_YES.equals(byId)){
            return BaseResult.error("已记账无法删除");
        }
        boolean result = inventoryOutboundMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
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
        InventoryOutboundPO byId = inventoryOutboundMpService.getById(id);
        String state = byId.getState();
        if (Constants.TALLY_NO.equals(state)) {
            byId.setState(Constants.TALLY_YES);
        }else {
            return BaseResult.error("已记账无法更改");
        }

        boolean result = inventoryOutboundMpService.updateById(byId);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountRuVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "出库金额", notes = "出库金额", httpMethod = "GET")
    @GetMapping(value = "/warehousing")
    public BaseResult baseResult(InventoryOutboundVO vo) {
        vo.setState(Constants.TALLY_YES);
        List<InventoryOutboundPO> list = onSelectWhere(vo).list();
        //设置初始值为零
        BigDecimal zero = BigDecimal.ZERO;
        for (InventoryOutboundPO inventoryOutboundPO : list) {
            // zero= 0   +     总金额
            zero=zero.add(inventoryOutboundPO.getPrice().multiply(new BigDecimal(inventoryOutboundPO.getNumber())));
        }

        return BaseResult.success(zero);
    }
    
    @Log(title = "入库表", businessType = BusinessType.EXPORT)
    @PutMapping("/export")
    public void export(HttpServletResponse response, InventoryOutboundVO vo) {
        //实例勾选
        List<Long> ids = vo.getIds();
        //创建list集合
        List<InventoryOutboundPO> list;
        //判断他有没有勾选 如果没有勾选就打印全部
        if (CollectionUtils.isNotEmpty(ids)){
            list = inventoryOutboundMpService.lambdaQuery().in(InventoryOutboundPO::getId, ids).list();
        }else {
            //如果勾选就打印勾选
            list=onSelectWhere(vo).list();
        }
        //将要打印的保存到inventoryInboundDTOList集合里面
        List<InventoryOutboundDTO>inventoryInboundDTOList = new ArrayList<>();

        list.forEach(InventoryOutboundPO -> {
            inventoryInboundDTOList.add(InventoryOutboundMsMapper.INSTANCE.po2dto(InventoryOutboundPO));
        });
        //将表里没有的查询出来保存到里面
        for (InventoryOutboundDTO inventoryOutboundDTO : inventoryInboundDTOList) {
            //总金额
            inventoryOutboundDTO.setAmount(inventoryOutboundDTO.getPrice().multiply(new BigDecimal(inventoryOutboundDTO.getNumber())));
            //关联账抬
            InventoryLedgerPO byId = inventoryLedgerMpService.getById(inventoryOutboundDTO.getLedgerId());
            //关联字典
            SysDictData sysDictData = iSysDictDataService.selectDictDataById(inventoryOutboundDTO.getOutboundType());
            //保存入库名称
            inventoryOutboundDTO.setOutboundName(sysDictData.getDictLabel());
            //保存原材料名称
            inventoryOutboundDTO.setRawName(byId.getRawMaterialName());
        }
        ExcelUtil<InventoryOutboundDTO> util = new ExcelUtil(InventoryOutboundDTO.class);
        util.exportExcel(response, inventoryInboundDTOList, "出库表");
    }

    private LambdaQueryChainWrapper<InventoryOutboundPO> onSelectWhere(InventoryOutboundVO vo) {
        LambdaQueryChainWrapper<InventoryOutboundPO> queryWrapper = inventoryOutboundMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), InventoryOutboundPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getLedgerId()), InventoryOutboundPO::getLedgerId, vo.getLedgerId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRecipients()), InventoryOutboundPO::getRecipients, vo.getRecipients());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getWarehouseId()), InventoryOutboundPO::getWarehouseId, vo.getWarehouseId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getWarehouseName()), InventoryOutboundPO::getWarehouseName, vo.getWarehouseName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), InventoryOutboundPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getOutboundType()), InventoryOutboundPO::getOutboundType, vo.getOutboundType());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getOutboundName()), InventoryOutboundPO::getOutboundName, vo.getOutboundName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAssociation()), InventoryOutboundPO::getAssociation, vo.getAssociation());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), InventoryOutboundPO::getPrice, vo.getPrice());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialPic()), InventoryOutboundPO::getRawMaterialPic, vo.getRawMaterialPic());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), InventoryOutboundPO::getState, vo.getState());
           return queryWrapper;
    }


}
