package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.po.*;
import com.diandong.constant.Constants;
import com.diandong.service.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.diandong.domain.po.TransferPO;
import com.diandong.domain.dto.TransferDTO;
import com.diandong.domain.vo.TransferVO;
import com.diandong.mapstruct.TransferMsMapper;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Validated
@RestController
@Api(value = "/transfer", tags = {"模块"})
@RequestMapping(value = "/transfer")
public class TransferController extends BaseController {

    @Resource
    private TransferMpService transferMpService;
    @Resource
    private TransferCommentMpService transferCommentMpService;
    @Resource
    private InventoryLedgerMpService inventoryLedgerMpService;
    @Resource
     private   InventoryInboundMpService inventoryInboundMpService;
    @Resource
    private InventoryOutboundMpService inventoryOutboundMpService;
    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(TransferVO vo) {
        Page<TransferPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     * @author Yangchao
     * @param id 编号id
     * @return 返回结果
     *
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<TransferDTO> getById(@PathVariable("id") Long id) {
        Long repertory=0L;
        TransferDTO dto = TransferMsMapper.INSTANCE
                .po2dto(transferMpService.getById(id));
        TransferCommentPO transferCommentPO = transferCommentMpService.lambdaQuery().eq(TransferCommentPO::getTransferId, dto.getId()).one();
        InventoryLedgerPO inventoryLedgerPo = inventoryLedgerMpService.getById(transferCommentPO.getRid());
        //查询入库
        List<InventoryInboundPO> list = inventoryInboundMpService.lambdaQuery()
                .eq(InventoryInboundPO::getLedgerId, inventoryLedgerPo.getId())
                .eq(InventoryInboundPO::getState, 1).list();
        //查询出库
        List<InventoryOutboundPO> list1 = inventoryOutboundMpService.lambdaQuery()
                .eq(InventoryOutboundPO::getId, inventoryLedgerPo.getId())
                .eq(InventoryOutboundPO::getState, 1).list();
        for (InventoryInboundPO inventoryInboundPO : list) {
            //如果有入库则增加数量
            repertory += inventoryInboundPO.getNumber();
            //计算他的总金额
        }
        //遍历出库
        for (InventoryOutboundPO inventoryOutboundPO : list1) {
            //出库 数量相减
            repertory -= inventoryOutboundPO.getNumber();
        }
        dto.setCategoryName(inventoryLedgerPo.getCategoryName());
        dto.setRawMaterialName(inventoryLedgerPo.getRawMaterialName());
        dto.setUnitName(inventoryLedgerPo.getUnitName());
        dto.setNumber(transferCommentPO.getNumber());
        dto.setRepertory(repertory); //库存量 经过计算
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) TransferVO vo) {
        TransferPO po = TransferMsMapper.INSTANCE.vo2po(vo);

        List<TransferCommentPO> storage = vo.getStorage();


        //获取当前时间
        po.setCreateTime(LocalDateTime.now());
        //获取时间的数字数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String times = sdf.format(new Date());
        //单号
        po.setCode("DB" + times + Math.round((Math.random() + 1) * 1000));
        //状态
        po.setState("1");
        //创建人 getUsername当前账号归属人
        po.setCreateBy(getUsername());

        boolean result =  transferMpService.save(po);
        for (TransferCommentPO transferCommentPO : storage) {
            transferCommentPO.setTransferId(po.getId());

        }

        result = transferCommentMpService.saveBatch(storage);

        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 根据更新查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "状态", notes = "状态", httpMethod = "GET")
    @PutMapping(value = "/{id}")
    public BaseResult<TransferDTO> update(@PathVariable("id") Long id,@RequestBody @Validated(Insert.class) TransferVO vo) {
        TransferPO byId = transferMpService.getById(id);


        if (Constants.REVIEW_THE_REJECTED.equals(vo.getState())){
             byId.setState("2");
             transferMpService.updateById(byId);
            return BaseResult.successMsg("审核失败");
        }
             byId.setReviewer(getUsername());
             byId.setReviewTime(LocalDateTime.now());
             byId.setState("3");
             transferMpService.updateById(byId);
            return BaseResult.successMsg("审核成功");


    }

    /**
     * 删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        TransferPO byId = transferMpService.getById(id);
        if (Constants.REVIEW_THE_REJECTED.equals(byId.getState()) || Constants.TO_AUDIT.equals(byId.getState())) {
            boolean result = transferMpService.removeById(id);
            if (result) {
                return BaseResult.successMsg("删除成功");
            } else {
                return BaseResult.error("审批通过无法删除");
            }
        }
        return BaseResult.error("审批通过无法删除");
        }



    @Log(title = "调拨管理", businessType = BusinessType.EXPORT)
    @PutMapping("/export")
    public void export(HttpServletResponse response, TransferVO vo) {
        //实例勾选
        List<Long> ids = vo.getIds();
        //创建list集合
        List<TransferPO> list;
        //判断他有没有勾选 如果没有勾选就打印全部
        if (CollectionUtils.isNotEmpty(ids)) {
            list = transferMpService.lambdaQuery().in(TransferPO::getId, ids).list();
        } else {
            //如果勾选就打印勾选
            list = onSelectWhere(vo).list();
        }
        //将要打印的保存到inventoryInboundDTOList集合里面
        List<TransferDTO> transferDTOS = new ArrayList<>();

        list.forEach(TransferPO -> {
            transferDTOS.add(TransferMsMapper.INSTANCE.po2dto(TransferPO));

        });
        ExcelUtil<TransferDTO> util = new ExcelUtil(TransferDTO.class);
        util.exportExcel(response, transferDTOS, "调拨管理表");
    }


    private LambdaQueryChainWrapper<TransferPO> onSelectWhere(TransferVO vo) {
        LambdaQueryChainWrapper<TransferPO> queryWrapper = transferMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), TransferPO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCode()), TransferPO::getCode, vo.getCode());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getOutboundCanteen()), TransferPO::getOutboundCanteen, vo.getOutboundCanteen());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInboundCanteen()), TransferPO::getInboundCanteen, vo.getInboundCanteen());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), TransferPO::getState, vo.getState());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getReviewer()), TransferPO::getReviewer, vo.getReviewer());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getReviewTime()), TransferPO::getReviewTime, vo.getReviewTime());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), TransferPO::getRemark, vo.getRemark());
           return queryWrapper;
    }


}


