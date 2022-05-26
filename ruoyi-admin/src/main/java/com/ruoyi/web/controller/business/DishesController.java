package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.DishesDTO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.vo.DishesVO;
import com.diandong.mapstruct.DishesMsMapper;
import com.diandong.service.DishesMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/dishes", tags = {"菜品设置"})
@RequestMapping(value = "/dishes")
public class DishesController extends BaseController {

    @Resource
    private DishesMpService dishesMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(DishesVO vo) {
        Page<DishesPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
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
    public BaseResult<DishesDTO> getById(@PathVariable("id") Long id) {
        DishesDTO dto = DishesMsMapper.INSTANCE.po2dto(dishesMpService.getById(id));
        dishesMpService.getAllDishMsg(dto);

        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) DishesVO vo) {

        dishesMpService.saveDishes(vo);
        return BaseResult.success("添加成功");
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) DishesVO vo) {

        dishesMpService.updateDishes(vo);
        return BaseResult.success();
    }


    @ApiOperation(value = "上下架")
    @GetMapping("/onOff/{id}")
    public BaseResult onOrOff(@PathVariable String id) {

        DishesPO dishes = dishesMpService.getById(id);
        if (Constants.SHELF_OFF == dishes.getState()) {
        }
        dishes.setState(Constants.SHELF_OFF == dishes.getState() ? Constants.SHELF_ON : Constants.SHELF_OFF);

        boolean result = dishesMpService.updateById(dishes);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    /**
     * 获取菜品更新
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取菜品更新", notes = "主要是更新检测报告，菜品原材料供应商，添加剂等信息")
    @GetMapping("/msg/{id}")
    public BaseResult getMsg(@PathVariable String id) {
        return dishesMpService.getMsg(id);
    }

    /**
     * 保存菜品更新
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "保存菜品更新", notes = "主要是更新检测报告，菜品原材料供应商，添加剂等信息")
    @PostMapping("/msg")
    public BaseResult saveOrUpdateMsg(@RequestBody @Validated(Update.class) DishesVO vo) {
        return dishesMpService.saveOrUpdateMsg(vo);
    }


    /**
     * 删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除菜品信息", notes = "删除菜品信息", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = dishesMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<DishesPO> onSelectWhere(DishesVO vo) {

        LambdaQueryChainWrapper<DishesPO> queryWrapper = dishesMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), DishesPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), DishesPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), DishesPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesTypeId()), DishesPO::getDishesTypeId, vo.getDishesTypeId())
                .eq(StringUtils.isNotBlank(vo.getDishesTypeName()), DishesPO::getDishesTypeName, vo.getDishesTypeName())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), DishesPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesPrice()), DishesPO::getDishesPrice, vo.getDishesPrice())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesUnit()), DishesPO::getDishesUnit, vo.getDishesUnit())
                .eq(StringUtils.isNotBlank(vo.getSpecification()), DishesPO::getSpecification, vo.getSpecification())
                .eq(ObjectUtils.isNotEmpty(vo.getPrePrice()), DishesPO::getPrePrice, vo.getPrePrice())
                .eq(StringUtils.isNotBlank(vo.getOrigin()), DishesPO::getOrigin, vo.getOrigin())
                .eq(StringUtils.isNotBlank(vo.getDishesAttrId()), DishesPO::getDishesAttrId, vo.getDishesAttrId())
                .eq(StringUtils.isNotBlank(vo.getDishesAttrName()), DishesPO::getDishesAttrName, vo.getDishesAttrName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), DishesPO::getRemark, vo.getRemark())
                .eq(StringUtils.isNotBlank(vo.getDishesPicture()), DishesPO::getDishesPicture, vo.getDishesPicture())
                .eq(StringUtils.isNotBlank(vo.getDishesIntroduction()), DishesPO::getDishesIntroduction, vo.getDishesIntroduction())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), DishesPO::getState, vo.getState());

        return queryWrapper;
    }

}
