package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RecipeDTO;
import com.diandong.domain.dto.RecipeDetailDTO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.domain.po.RecipePO;
import com.diandong.domain.vo.DishesVO;
import com.diandong.domain.vo.RecipeDetailVO;
import com.diandong.domain.vo.RecipeVO;
import com.diandong.mapstruct.RecipeDetailMsMapper;
import com.diandong.mapstruct.RecipeMsMapper;
import com.diandong.service.DishesMpService;
import com.diandong.service.RecipeDetailMpService;
import com.diandong.service.RecipeMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Slf4j
@Validated
@RestController
@Api(value = "/recipe", tags = {"食谱模块"})
@RequestMapping(value = "/recipe")
public class RecipeController extends BaseController {

    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private DishesMpService dishesMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<RecipeDTO> getList(RecipeVO vo) {
        startPage();
        List<RecipePO> dataList = recipeMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RecipePO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getRecipeName()), RecipePO::getRecipeName, vo.getRecipeName())
                .eq(ObjectUtils.isNotEmpty(vo.getRecipeDate()), RecipePO::getRecipeDate, vo.getRecipeDate())
                .eq(ObjectUtils.isNotEmpty(vo.getAddWayId()), RecipePO::getAddWayId, vo.getAddWayId())
                .eq(StringUtils.isNotBlank(vo.getAddWayName()), RecipePO::getAddWayName, vo.getAddWayName())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), RecipePO::getStatus, vo.getStatus())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(RecipeMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<RecipeDTO> getById(@PathVariable("id") Long id) {


        RecipeDTO dto = RecipeMsMapper.INSTANCE.po2dto(recipeMpService.getById(id));

        List<RecipeDetailPO> recipeDetailList = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getRecipeId, id)
                .eq(RecipeDetailPO::getDelFlag, false)
                .list();

        List<RecipeDetailDTO> detailDTOList = new ArrayList<>();
        recipeDetailList.forEach(recipeDetailPO -> {
            RecipeDetailDTO recipeDetailDTO = RecipeDetailMsMapper.INSTANCE.po2dto(recipeDetailPO);

            DishesPO dishes = dishesMpService.getById(recipeDetailDTO.getDishesId());
            recipeDetailDTO.setDishesPicture(dishes.getDishesPicture());
            recipeDetailDTO.setDishesType(dishes.getDishesTypeName());
            detailDTOList.add(recipeDetailDTO);
        });

        dto.setRecipeDetailDTOList(detailDTOList);

        return BaseResult.success(dto);
    }

    /**
     * 食谱发布
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "食谱发布", notes = "食谱发布", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RecipeVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        try {
            return recipeMpService.recipePost(vo, loginUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
        }

    }


    /**
     * 根据id复制菜谱
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id"),
            @ApiImplicitParam(paramType = "path", dataType = "LocalDate", name = "vo", value = "菜谱日期")
    })
    @ApiOperation(value = "根据id复制菜谱", notes = "根据id复制菜谱", httpMethod = "GET")
    @GetMapping("/copy/{id}")
    public BaseResult copyRecipe(@PathVariable("id") Long id, LocalDate recipeDate) {

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        if (Objects.isNull(id) || Objects.isNull(recipeDate)) {
            return BaseResult.error("参数不完整");
        }

        try {
            return recipeMpService.copyRecipe(id, recipeDate, loginUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
        }

    }


    /**
     * 根据id复制菜谱
     * <p>
     * Q：这个地方有个疑问，是先有了食谱才有原材料清单还是再未创建食谱之前就可以通过传参查询原材料清单
     *
     * @param voList 菜谱菜品信息
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "List<RecipeDetailVO>", name = "voList", value = "菜谱信息"),
    })
    @ApiOperation(value = "原材料清单", notes = "原材料清单", httpMethod = "POST")
    @PostMapping("list/raw_materials")
    public BaseResult rawMaterialsList(@RequestBody List<RecipeDetailVO> voList) {
        return recipeMpService.rawMaterialsList(voList);
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RecipeVO vo) {
        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
        boolean result = recipeMpService.updateById(po);
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
        boolean result = recipeMpService.removeByIds(Arrays.asList(ids));
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
                .eq(StringUtils.isNotBlank(vo.getDishesUnit()), DishesPO::getDishesUnit, vo.getDishesUnit())
                .eq(StringUtils.isNotBlank(vo.getSpecification()), DishesPO::getSpecification, vo.getSpecification())
                .eq(ObjectUtils.isNotEmpty(vo.getPrePrice()), DishesPO::getPrePrice, vo.getPrePrice())
                .eq(StringUtils.isNotBlank(vo.getOrigin()), DishesPO::getOrigin, vo.getOrigin())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesAttrId()), DishesPO::getDishesAttrId, vo.getDishesAttrId())
                .eq(StringUtils.isNotBlank(vo.getDishesAttrName()), DishesPO::getDishesAttrName, vo.getDishesAttrName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), DishesPO::getRemark, vo.getRemark())
                .eq(StringUtils.isNotBlank(vo.getDishesPicture()), DishesPO::getDishesPicture, vo.getDishesPicture())
                .eq(StringUtils.isNotBlank(vo.getDishesIntroduction()), DishesPO::getDishesIntroduction, vo.getDishesIntroduction())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), DishesPO::getState, vo.getState());

        return queryWrapper;
    }

}
