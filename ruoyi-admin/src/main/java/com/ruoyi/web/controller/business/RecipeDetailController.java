package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.RecipeDetailDTO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.domain.vo.DishesVO;
import com.diandong.domain.vo.RecipeDetailVO;
import com.diandong.domain.vo.RecipePieSituationVO;
import com.diandong.mapstruct.RecipeDetailMsMapper;
import com.diandong.service.RecipeDetailMpService;
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
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Validated
@RestController
@Api(value = "/recipe_detail", tags = {"食谱详情模块"})
@RequestMapping(value = "/recipe_detail")
public class RecipeDetailController extends BaseController {

    @Resource
    private RecipeDetailMpService recipeDetailMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeDetailVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RecipeDetailVO vo) {

        Page<RecipeDetailPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<RecipeDetailDTO> getById(@PathVariable("id") Long id) {
        RecipeDetailDTO dto = RecipeDetailMsMapper.INSTANCE
                .po2dto(recipeDetailMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RecipeDetailVO vo) {
        RecipeDetailPO po = RecipeDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = recipeDetailMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "RecipeDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RecipeDetailVO vo) {
        RecipeDetailPO po = RecipeDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = recipeDetailMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 编号id数组
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = recipeDetailMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 派菜情况
     *
     * @param pieSituationVO
     * @return
     */
    @ApiOperation(value = "派菜情况", notes = "派菜情况", httpMethod = "GET")
    @GetMapping("/pie_situation")
    public BaseResult pieSituation(@Validated RecipePieSituationVO pieSituationVO) {
        return BaseResult.success(recipeDetailMpService.pieSituation(pieSituationVO));
    }


    private LambdaQueryChainWrapper<RecipeDetailPO> onSelectWhere(RecipeDetailVO vo) {

        LambdaQueryChainWrapper<RecipeDetailPO> queryWrapper = recipeDetailMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RecipeDetailPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getRecipeId()), RecipeDetailPO::getRecipeId, vo.getRecipeId())
                .eq(StringUtils.isNotBlank(vo.getRecipeName()), RecipeDetailPO::getRecipeName, vo.getRecipeName())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), RecipeDetailPO::getMealTimesId, vo.getMealTimesId())
                .eq(StringUtils.isNotBlank(vo.getMealTimesName()), RecipeDetailPO::getMealTimesName, vo.getMealTimesName())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), RecipeDetailPO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), RecipeDetailPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getChefId()), RecipeDetailPO::getChefId, vo.getChefId())
                .eq(StringUtils.isNotBlank(vo.getChefName()), RecipeDetailPO::getChefName, vo.getChefName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), RecipeDetailPO::getNumber, vo.getNumber());

        return queryWrapper;
    }

}
