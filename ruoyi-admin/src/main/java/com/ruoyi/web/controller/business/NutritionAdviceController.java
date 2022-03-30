package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.NutritionAdviceMpService;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.dto.NutritionAdviceDTO;
import com.diandong.domain.vo.NutritionAdviceVO;
import com.diandong.mapstruct.NutritionAdviceMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/nutrition_advice", tags = {"模块"})
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
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesName()), NutritionAdvicePO::getMealTimesName, vo.getMealTimesName())
                .eq(StringUtils.isNotBlank(vo.getNutritionalName()), NutritionAdvicePO::getNutritionalName, vo.getNutritionalName())
                .eq(StringUtils.isNotBlank(vo.getUnit()), NutritionAdvicePO::getUnit, vo.getUnit())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), NutritionAdvicePO::getNumber, vo.getNumber())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), NutritionAdvicePO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), NutritionAdvicePO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), NutritionAdvicePO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), NutritionAdvicePO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(NutritionAdviceMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<NutritionAdviceDTO> getById(@PathVariable("id") Long id) {
        NutritionAdviceDTO dto = NutritionAdviceMsMapper.INSTANCE
                .po2dto(nutritionAdviceMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "NutritionAdviceVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) NutritionAdviceVO vo) {
        NutritionAdvicePO po = NutritionAdviceMsMapper.INSTANCE.vo2po(vo);
        boolean result = nutritionAdviceMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "NutritionAdviceVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) NutritionAdviceVO vo) {
        NutritionAdvicePO po = NutritionAdviceMsMapper.INSTANCE.vo2po(vo);
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = nutritionAdviceMpService.removeById(id);
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
