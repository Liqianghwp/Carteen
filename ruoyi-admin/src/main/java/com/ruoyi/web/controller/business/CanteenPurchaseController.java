package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.dto.CanteenPurchaseDTO;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.vo.CanteenPurchaseVO;
import com.diandong.service.CanteenPurchaseMpService;
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
 * 食堂采购Controller
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Validated
@RestController
@Api(value = "/canteen_purchase", tags = {"食堂采购模块"})
@RequestMapping(value = "/canteen_purchase")
public class CanteenPurchaseController extends BaseController {

    @Resource
    private CanteenPurchaseMpService canteenPurchaseMpService;

    /**
     * 食堂采购分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CanteenPurchaseVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "食堂采购分页查询", notes = "食堂采购分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(CanteenPurchaseVO vo) {

        Page<CanteenPurchasePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 食堂采购根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "食堂采购根据id查询", notes = "食堂采购根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<CanteenPurchaseDTO> getById(@PathVariable("id") Long id) {
        return canteenPurchaseMpService.getCanteenPurchase(id);
    }

    /**
     * 食堂采购保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CanteenPurchaseVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "食堂采购保存", notes = "食堂采购保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) CanteenPurchaseVO vo) {
        return canteenPurchaseMpService.saveCanteenPurchase(vo);
    }


    /**
     * 审核
     *
     * @return
     */
    @ApiOperation(value = "审核")
    @GetMapping("audit")
    public BaseResult audit(@Validated CanteenPurchaseVO vo) {
        return canteenPurchaseMpService.audit(vo);
    }


    private LambdaQueryChainWrapper<CanteenPurchasePO> onSelectWhere(CanteenPurchaseVO vo) {
        LambdaQueryChainWrapper<CanteenPurchasePO> queryWrapper = canteenPurchaseMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), CanteenPurchasePO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), CanteenPurchasePO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getCanteenName()), CanteenPurchasePO::getCanteenName, vo.getCanteenName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRecipeStartDate()), CanteenPurchasePO::getRecipeStartDate, vo.getRecipeStartDate());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRecipeEndDate()), CanteenPurchasePO::getRecipeEndDate, vo.getRecipeEndDate());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDays()), CanteenPurchasePO::getDays, vo.getDays());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), CanteenPurchasePO::getState, vo.getState());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), CanteenPurchasePO::getRemark, vo.getRemark());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSubmitTime()), CanteenPurchasePO::getSubmitTime, vo.getSubmitTime());
        return queryWrapper;
    }


}
