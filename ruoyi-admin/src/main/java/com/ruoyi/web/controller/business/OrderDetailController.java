package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.OrderDetailDTO;
import com.diandong.domain.po.OrderDetailPO;
import com.diandong.domain.vo.OrderDetailVO;
import com.diandong.mapstruct.OrderDetailMsMapper;
import com.diandong.service.OrderDetailMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Validated
@RestController
@Api(value = "/order_detail", tags = {"订单详情模块"})
@RequestMapping(value = "/order_detail")
public class OrderDetailController extends BaseController {

    @Resource
    private OrderDetailMpService orderDetailMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OrderDetailVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<OrderDetailDTO> getList(OrderDetailVO vo) {
        startPage();
        List<OrderDetailPO> dataList = orderDetailMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OrderDetailPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getOrderId()), OrderDetailPO::getOrderId, vo.getOrderId())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), OrderDetailPO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), OrderDetailPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesPrice()), OrderDetailPO::getDishesPrice, vo.getDishesPrice())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesCount()), OrderDetailPO::getDishesCount, vo.getDishesCount())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesTotalPrice()), OrderDetailPO::getDishesTotalPrice, vo.getDishesTotalPrice())
                .eq(StringUtils.isNotBlank(vo.getDishesPicture()), OrderDetailPO::getDishesPicture, vo.getDishesPicture())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(OrderDetailMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<OrderDetailDTO> getById(@PathVariable("id") Long id) {
        OrderDetailDTO dto = OrderDetailMsMapper.INSTANCE
                .po2dto(orderDetailMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OrderDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) OrderDetailVO vo) {
        OrderDetailPO po = OrderDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = orderDetailMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OrderDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) OrderDetailVO vo) {
        OrderDetailPO po = OrderDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = orderDetailMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = orderDetailMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
