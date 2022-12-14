package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.NutritionAdviceDTO;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.vo.IntakeAnalysisVO;
import com.diandong.domain.vo.NutritionAdviceVO;
import com.diandong.mapstruct.NutritionAdviceMsMapper;
import com.diandong.service.NutritionAdviceMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2022-03-31
 */
@Slf4j
@Validated
@RestController
@Api(value = "/nutrition_advice", tags = {"营养建议模块"})
@RequestMapping(value = "/nutrition_advice")
public class NutritionAdviceController extends BaseController {

    @Resource
    private NutritionAdviceMpService nutritionAdviceMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "NutritionAdviceVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(NutritionAdviceVO vo) {
        Page<NutritionAdvicePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }


    /**
     * 分页查询
     *
     * @param mealTimesId 餐次id
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "mealTimesId", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping("/{mealTimesId}")
    public BaseResult getNutritionAdvice(@PathVariable("mealTimesId") Long mealTimesId) {
        return nutritionAdviceMpService.getNutritionAdvice(mealTimesId);
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
    public BaseResult<NutritionAdviceDTO> getById(@PathVariable("id") Long id) {
        NutritionAdviceDTO dto = NutritionAdviceMsMapper.INSTANCE
                .po2dto(nutritionAdviceMpService.getById(id));
        return BaseResult.success(dto);
    }


    /**
     * 营养摄入分析
     *
     * @param vo 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "IntakeAnalysisVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "营养摄入分析", notes = "营养摄入分析")
    @PostMapping(value = "/intake_analysis")
    public BaseResult intakeAnalysis(@RequestBody @Validated IntakeAnalysisVO vo) {
        return nutritionAdviceMpService.intakeAnalysis(vo);
    }


    /**
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<NutritionAdviceVO>", name = "naList", value = "参数对象")
    })
    @ApiOperation(value = "批量保存营养建议", notes = "批量保存营养建议", httpMethod = "POST")
    @PostMapping
    public BaseResult inputNutritionAdvice(@RequestBody @Validated(Insert.class) List<NutritionAdviceVO> naList) {

        if (CollectionUtils.isEmpty(naList)) {
            return BaseResult.error("没有要录入的营养参数");
        }
        return nutritionAdviceMpService.inputNutritionAdvice(naList);
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
        boolean result = nutritionAdviceMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<NutritionAdvicePO> onSelectWhere(NutritionAdviceVO vo) {

        LambdaQueryChainWrapper<NutritionAdvicePO> queryWrapper = nutritionAdviceMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), NutritionAdvicePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), NutritionAdvicePO::getMealTimesId, vo.getMealTimesId())
                .eq(StringUtils.isNotBlank(vo.getMealTimesName()), NutritionAdvicePO::getMealTimesName, vo.getMealTimesName())
                .eq(ObjectUtils.isNotEmpty(vo.getNutritionalId()), NutritionAdvicePO::getNutritionalId, vo.getNutritionalId())
                .eq(StringUtils.isNotBlank(vo.getNutritionalName()), NutritionAdvicePO::getNutritionalName, vo.getNutritionalName())
                .eq(StringUtils.isNotBlank(vo.getUnit()), NutritionAdvicePO::getUnit, vo.getUnit())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), NutritionAdvicePO::getNumber, vo.getNumber())
                .eq(ObjectUtils.isNotEmpty(vo.getUserId()), NutritionAdvicePO::getUserId, vo.getUserId());

        return queryWrapper;
    }
}
