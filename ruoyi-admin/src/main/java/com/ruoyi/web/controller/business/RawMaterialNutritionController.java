package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialNutritionDTO;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.vo.RawMaterialNutritionVO;
import com.diandong.mapstruct.RawMaterialNutritionMsMapper;
import com.diandong.service.RawMaterialNutritionMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
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
 * @date 2022-03-24
 */
@Slf4j
@Validated
@RestController
@Api(value = "/raw_material_nutrition", tags = {"原材料营养信息模块"})
@RequestMapping(value = "/raw_material_nutrition")
public class RawMaterialNutritionController extends BaseController {

    @Resource
    private RawMaterialNutritionMpService rawMaterialNutritionMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialNutritionVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<RawMaterialNutritionDTO> getList(RawMaterialNutritionVO vo) {
        startPage();
        List<RawMaterialNutritionPO> dataList = rawMaterialNutritionMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RawMaterialNutritionPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), RawMaterialNutritionPO::getRawMaterialId, vo.getRawMaterialId())
                .eq(StringUtils.isNotBlank(vo.getRawMaterialName()), RawMaterialNutritionPO::getRawMaterialName, vo.getRawMaterialName())
                .eq(ObjectUtils.isNotEmpty(vo.getNutritionParamsId()), RawMaterialNutritionPO::getNutritionParamsId, vo.getNutritionParamsId())
                .eq(StringUtils.isNotBlank(vo.getNutritionParamsName()), RawMaterialNutritionPO::getNutritionParamsName, vo.getNutritionParamsName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), RawMaterialNutritionPO::getNumber, vo.getNumber())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(RawMaterialNutritionMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<RawMaterialNutritionDTO> getById(@PathVariable("id") Long id) {
        RawMaterialNutritionDTO dto = RawMaterialNutritionMsMapper.INSTANCE
                .po2dto(rawMaterialNutritionMpService.getById(id));
        return BaseResult.success(dto);
    }

//    /**
//     * 保存
//     *
//     * @param vo 参数对象
//     * @return 返回结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialNutritionVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
//    @PostMapping
//    public BaseResult save(@Validated(Insert.class) RawMaterialNutritionVO vo) {
//        RawMaterialNutritionPO po = RawMaterialNutritionMsMapper.INSTANCE.vo2po(vo);
//        boolean result = rawMaterialNutritionMpService.save(po);
//        if (result) {
//            return BaseResult.successMsg("添加成功！");
//        } else {
//            return BaseResult.error("添加失败！");
//        }
//    }

    /**
     * 保存
     *
     * @param voList 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<RawMaterialNutritionVO>", name = "voList", value = "参数对象")
    })
    @ApiOperation(value = "保存原材料营养集合", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult saveList(@RequestBody @Validated List<RawMaterialNutritionVO> voList) {

//
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        try {
            return rawMaterialNutritionMpService.addList(voList, loginUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
        }

    }


    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialNutritionVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) RawMaterialNutritionVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        RawMaterialNutritionPO po = RawMaterialNutritionMsMapper.INSTANCE.vo2po(vo);

        po.setUpdateBy(loginUser.getUserId());
        boolean result = rawMaterialNutritionMpService.updateById(po);
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
        boolean result = rawMaterialNutritionMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
