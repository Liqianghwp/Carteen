package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.dto.PurchaseOrderDTO;
import com.diandong.domain.po.PurchaseOrderPO;
import com.diandong.domain.vo.PurchaseOrderVO;
import com.diandong.mapstruct.PurchaseOrderMsMapper;
import com.diandong.service.PurchaseOrderMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 采购订单管理Controller
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Validated
@RestController
@Api(value = "/purchase_order", tags = {"采购订单管理模块"})
@RequestMapping(value = "/purchase_order")
public class PurchaseOrderController extends BaseController {

    @Resource
    private PurchaseOrderMpService purchaseOrderMpService;
    @Resource
    private ISysUserService userService;

    /**
     * 采购订单管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchaseOrderVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "采购订单管理分页查询", notes = "采购订单管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(PurchaseOrderVO vo) {

        Page<PurchaseOrderPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 采购订单管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购订单管理根据id查询", notes = "采购订单管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<PurchaseOrderDTO> getById(@PathVariable("id") Long id) {
        return purchaseOrderMpService.selectPurchaseOrder(id);
    }

    /**
     * 采购订单管理保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchaseOrderVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "采购订单管理保存", notes = "采购订单管理保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) PurchaseOrderVO vo) {
        return purchaseOrderMpService.createPurchaseOrder(vo);
    }


    /**
     * 采购订单管理删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购订单管理删除", notes = "采购订单管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = purchaseOrderMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 采购订单管理批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "采购订单管理批量删除", notes = "采购订单管理批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = purchaseOrderMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    @ApiModelProperty(value = "采购订单导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrderVO vo) {

        List<Long> ids = vo.getIds();
        List<PurchaseOrderPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = purchaseOrderMpService.lambdaQuery().in(PurchaseOrderPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }

        List<PurchaseOrderDTO> dtoList = PurchaseOrderMsMapper.INSTANCE.poList2dtoList(list);

        for (PurchaseOrderDTO purchaseOrderDTO : dtoList) {
            SysUser user = userService.selectUserById(purchaseOrderDTO.getCreateBy());
            purchaseOrderDTO.setCreateName(user.getUserName());
        }

        ExcelUtil<PurchaseOrderDTO> util = new ExcelUtil<PurchaseOrderDTO>(PurchaseOrderDTO.class);
        util.exportExcel(response, dtoList, "采购订单");

    }

    private LambdaQueryChainWrapper<PurchaseOrderPO> onSelectWhere(PurchaseOrderVO vo) {
        LambdaQueryChainWrapper<PurchaseOrderPO> queryWrapper = purchaseOrderMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), PurchaseOrderPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getGroupId()), PurchaseOrderPO::getGroupId, vo.getGroupId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getOrderId()), PurchaseOrderPO::getOrderId, vo.getOrderId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSupplierId()), PurchaseOrderPO::getSupplierId, vo.getSupplierId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getSupplierName()), PurchaseOrderPO::getSupplierName, vo.getSupplierName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getOrderPrice()), PurchaseOrderPO::getOrderPrice, vo.getOrderPrice());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), PurchaseOrderPO::getState, vo.getState());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDetails()), PurchaseOrderPO::getDetails, vo.getDetails());

        if (Objects.nonNull(vo.getStartTime()) && Objects.nonNull(vo.getEndTime())) {
            queryWrapper.between(PurchaseOrderPO::getCreateTime, vo.getStartTime(), vo.getEndTime());
        }

        return queryWrapper;
    }


}
