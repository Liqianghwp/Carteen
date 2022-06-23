package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.dto.PurchasePlanDTO;
import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.vo.PurchasePlanVO;
import com.diandong.service.PurchasePlanMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 采购计划单Controller
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Validated
@RestController
@Api(value = "/purchase_plan", tags = {"采购计划单模块"})
@RequestMapping(value = "/purchase_plan")
public class PurchasePlanController extends BaseController {

    @Resource
    private PurchasePlanMpService purchasePlanMpService;

    /**
     * 采购计划单分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasePlanVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "采购计划单分页查询", notes = "采购计划单分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(PurchasePlanVO vo) {
        Page<PurchasePlanPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 采购计划单根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购计划单根据id查询", notes = "采购计划单根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<PurchasePlanDTO> getById(@PathVariable("id") Long id) {
        return purchasePlanMpService.findPurchasePlan(id);
    }

    /**
     * 采购计划单保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasePlanVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "采购计划单保存", notes = "采购计划单保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) PurchasePlanVO vo) {
        return purchasePlanMpService.savePurchasePlan(vo);
    }

    /**
     * 采购计划单审核
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "采购计划单审核", notes = "采购计划单审核")
    @GetMapping("audit")
    public BaseResult audit(PurchasePlanVO vo) {
        return purchasePlanMpService.audit(vo);
    }


    private LambdaQueryChainWrapper<PurchasePlanPO> onSelectWhere(PurchasePlanVO vo) {
        LambdaQueryChainWrapper<PurchasePlanPO> queryWrapper = purchasePlanMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), PurchasePlanPO::getId, vo.getId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getPlanId()), PurchasePlanPO::getPlanId, vo.getPlanId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getApplyId()), PurchasePlanPO::getApplyId, vo.getApplyId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), PurchasePlanPO::getCanteenId, vo.getCanteenId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getCanteenName()), PurchasePlanPO::getCanteenName, vo.getCanteenName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRecipeDate()), PurchasePlanPO::getRecipeDate, vo.getRecipeDate());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTotal()), PurchasePlanPO::getTotal, vo.getTotal());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), PurchasePlanPO::getState, vo.getState());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), PurchasePlanPO::getRemark, vo.getRemark());
        if (ObjectUtils.isNotEmpty(vo.getStartTime()) && ObjectUtils.isNotEmpty(vo.getEndTime())) {
            queryWrapper.between(PurchasePlanPO::getCreateTime, vo.getStartTime(), vo.getEndTime());
        }


        return queryWrapper;
    }


}
