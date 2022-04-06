package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.vo.ShopCartDetailVO;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.enums.OrderStatusEnum;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.OrderMpService;
import com.diandong.domain.po.OrderPO;
import com.diandong.domain.dto.OrderDTO;
import com.diandong.domain.vo.OrderVO;
import com.diandong.mapstruct.OrderMsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Slf4j
@Validated
@RestController
@Api(value = "/order", tags = {"模块"})
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

    @Resource
    private OrderMpService orderMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OrderVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<OrderDTO> getList(OrderVO vo) {
        startPage();
        List<OrderPO> dataList = orderMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OrderPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), OrderPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), OrderPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), OrderPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getOrderTime()), OrderPO::getOrderTime, vo.getOrderTime())
                .eq(ObjectUtils.isNotEmpty(vo.getEvaluationStatus()), OrderPO::getEvaluationStatus, vo.getEvaluationStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getPaymentMethodId()), OrderPO::getPaymentMethodId, vo.getPaymentMethodId())
                .eq(StringUtils.isNotBlank(vo.getPaymentMethodName()), OrderPO::getPaymentMethodName, vo.getPaymentMethodName())
                .eq(ObjectUtils.isNotEmpty(vo.getPaymentTime()), OrderPO::getPaymentTime, vo.getPaymentTime())
                .eq(ObjectUtils.isNotEmpty(vo.getDataStatus()), OrderPO::getDataStatus, vo.getDataStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), OrderPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), OrderPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), OrderPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(OrderMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<OrderDTO> getById(@PathVariable("id") Long id) {
        OrderDTO dto = OrderMsMapper.INSTANCE
                .po2dto(orderMpService.getById(id));
        return BaseResult.success(dto);
    }

//    /**
//     * 保存
//     *
//     * @param vo 参数对象
//     * @return 返回结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "OrderVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "生成订单", notes = "生成订单", httpMethod = "POST")
//    @PostMapping
//    public BaseResult save(@Validated(Insert.class) OrderVO vo) {
//
//
//        OrderPO po = OrderMsMapper.INSTANCE.vo2po(vo);
//        boolean result = orderMpService.save(po);
//        if (result) {
//            return BaseResult.successMsg("添加成功！");
//        } else {
//            return BaseResult.error("添加失败！");
//        }
//    }


    /**
     * 创建订单
     *
     * @param shopCartVO 购物车集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "shopCartVO", value = "参数对象")
    })
    @ApiOperation(value = "生成订单", notes = "生成订单", httpMethod = "POST")
    @PostMapping("/createOrder")
    public BaseResult createOrder(@RequestBody ShopCartVO shopCartVO) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }


        List<ShopCartDetailVO> shopCartDetailVOList = shopCartVO.getShopCartDetailVOList();

        if (CollectionUtils.isEmpty(shopCartDetailVOList)) {
            return BaseResult.error("没有选好的菜品，请您选择菜品");
        } else {
            List<Long> collect = shopCartDetailVOList.stream().map(ShopCartDetailVO::getId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                return BaseResult.error("没有选好的菜品，请您选择菜品");
            }
        }
        try {
//            生成订单操作
            return orderMpService.createOrder(shopCartVO, loginUser);
        } catch (Exception e) {
//            当保存失败后处理的信息
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OrderVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) OrderVO vo) {
        OrderPO po = OrderMsMapper.INSTANCE.vo2po(vo);
        boolean result = orderMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 支付订单
     *
     * @param orderId 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "参数对象")
    })
    @ApiOperation(value = "支付订单", notes = "支付订单", httpMethod = "POST")
    @PostMapping("/payOrder/{id}")
    public BaseResult payOrder(@PathVariable("id") Long orderId) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

//        return orderMpService.payOrder(orderId, loginUser);
        return orderMpService.processOrders(orderId, loginUser, OrderStatusEnum.COMPLETED.value());
    }


    /**
     * 订单详情
     *
     * @param orderId 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "参数对象")
    })
    @ApiOperation(value = "订单详情", notes = "订单详情", httpMethod = "GET")
    @GetMapping("/detail/{id}")
    public BaseResult getOrderDetail(@PathVariable("id") Long orderId) {

//        返回订单详情
        return orderMpService.getOrderDetail(orderId);

//        return BaseResult.successMsg("订单详情");
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
        boolean result = orderMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 取消订单
     *
     * @param orderId 订单编号
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderId", value = "编号id")
    })
    @ApiOperation(value = "取消订单", notes = "取消订单", httpMethod = "GET")
    @GetMapping("/cancelOrder/{id}")
    public BaseResult cancelOrder(@PathVariable("id") Long orderId) {

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
//        取消订单
//        return orderMpService.cancelOrder(orderId, loginUser);
        return orderMpService.processOrders(orderId, loginUser, OrderStatusEnum.CANCELLED.value());
    }

    /**
     * 批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = orderMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
