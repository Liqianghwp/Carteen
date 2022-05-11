package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
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
import com.ruoyi.common.core.page.TableDataInfo;
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
    public TableDataInfo<NutritionAdviceDTO> getList(NutritionAdviceVO vo) {
        startPage();
        List<NutritionAdvicePO> dataList = nutritionAdviceMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), NutritionAdvicePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), NutritionAdvicePO::getMealTimesId, vo.getMealTimesId())
                .eq(StringUtils.isNotBlank(vo.getMealTimesName()), NutritionAdvicePO::getMealTimesName, vo.getMealTimesName())
                .eq(ObjectUtils.isNotEmpty(vo.getNutritionalId()), NutritionAdvicePO::getNutritionalId, vo.getNutritionalId())
                .eq(StringUtils.isNotBlank(vo.getNutritionalName()), NutritionAdvicePO::getNutritionalName, vo.getNutritionalName())
                .eq(StringUtils.isNotBlank(vo.getUnit()), NutritionAdvicePO::getUnit, vo.getUnit())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), NutritionAdvicePO::getNumber, vo.getNumber())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(NutritionAdviceMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
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

//        判断登录信息
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        return nutritionAdviceMpService.getNutritionAdvice(mealTimesId, loginUser);
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
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @PostMapping(value = "/intake_analysis")
    public BaseResult intakeAnalysis(@RequestBody @Validated IntakeAnalysisVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        return nutritionAdviceMpService.intakeAnalysis(vo, loginUser);
    }


//    /**
//     * 保存
//     *
//     * @param vo 参数对象
//     * @return 返回结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "NutritionAdviceVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
//    @PostMapping
//    public BaseResult save(@Validated(Insert.class) NutritionAdviceVO vo) {
//        NutritionAdvicePO po = NutritionAdviceMsMapper.INSTANCE.vo2po(vo);
//        boolean result = nutritionAdviceMpService.save(po);
//        if (result) {
//            return BaseResult.successMsg("添加成功！");
//        } else {
//            return BaseResult.error("添加失败！");
//        }
//    }

    /**
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<NutritionAdviceVO>", name = "naList", value = "参数对象")
    })
    @ApiOperation(value = "批量保存营养建议", notes = "批量保存营养建议", httpMethod = "POST")
    @PostMapping
    public BaseResult inputNutritionAdvice(@RequestBody @Validated(Insert.class) List<NutritionAdviceVO> naList) {

//        判断是否登录
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        if (CollectionUtils.isEmpty(naList)) {
            return BaseResult.error("没有要录入的营养参数");
        }

        try {
            return nutritionAdviceMpService.inputNutritionAdvice(naList, loginUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error("添加失败");
        }
    }


    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "NutritionAdviceVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) NutritionAdviceVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        NutritionAdvicePO po = NutritionAdviceMsMapper.INSTANCE.vo2po(vo);
//        设置更新人信息
        po.setUpdateBy(loginUser.getUserId());

        boolean result = nutritionAdviceMpService.updateById(po);
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
        boolean result = nutritionAdviceMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
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
        boolean result = nutritionAdviceMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
