package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.CanteenPurchaseMpService;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.dto.CanteenPurchaseDTO;
import com.diandong.domain.vo.CanteenPurchaseVO;
import com.diandong.mapstruct.CanteenPurchaseMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

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
        CanteenPurchaseDTO dto = CanteenPurchaseMsMapper.INSTANCE
                .po2dto(canteenPurchaseMpService.getById(id));
        return BaseResult.success(dto);
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
     * 食堂采购更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CanteenPurchaseVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "食堂采购更新", notes = "食堂采购更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) CanteenPurchaseVO vo) {
        CanteenPurchasePO po = CanteenPurchaseMsMapper.INSTANCE.vo2po(vo);
        boolean result = canteenPurchaseMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 食堂采购删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "食堂采购删除", notes = "食堂采购删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = canteenPurchaseMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 食堂采购批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "食堂采购批量删除", notes = "食堂采购批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = canteenPurchaseMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
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
