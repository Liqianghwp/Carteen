package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.NutritionParamsMpService;
import com.diandong.domain.po.NutritionParamsPO;
import com.diandong.domain.dto.NutritionParamsDTO;
import com.diandong.domain.vo.NutritionParamsVO;
import com.diandong.mapstruct.NutritionParamsMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.net.ContentHandler;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Validated
@RestController
@Api(value = "/nutrition_params", tags = {"营养信息模块"})
@RequestMapping(value = "/nutrition_params")
public class NutritionParamsController extends BaseController {

    @Resource
    private NutritionParamsMpService nutritionParamsMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "NutritionParamsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<NutritionParamsDTO> getList(NutritionParamsVO vo) {
        startPage();
        List<NutritionParamsPO> dataList = nutritionParamsMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), NutritionParamsPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), NutritionParamsPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), NutritionParamsPO::getCanteenName, vo.getCanteenName())
                .eq(StringUtils.isNotBlank(vo.getNutritionName()), NutritionParamsPO::getNutritionName, vo.getNutritionName())
                .eq(StringUtils.isNotBlank(vo.getUnit()), NutritionParamsPO::getUnit, vo.getUnit())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), NutritionParamsPO::getVersion, vo.getVersion())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), NutritionParamsPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getBeUsed()), NutritionParamsPO::getBeUsed, vo.getBeUsed())
                .eq(ObjectUtils.isNotEmpty(vo.getCreateName()), NutritionParamsPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), NutritionParamsPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(NutritionParamsMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<NutritionParamsDTO> getById(@PathVariable("id") Long id) {
        NutritionParamsDTO dto = NutritionParamsMsMapper.INSTANCE
                .po2dto(nutritionParamsMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "NutritionParamsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "添加营养信息", notes = "添加营养信息", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) NutritionParamsVO vo) {

//        获取登录信息
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        NutritionParamsPO po = NutritionParamsMsMapper.INSTANCE.vo2po(vo);

//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());
        po.setCreateName(loginUser.getUsername());

        boolean result = nutritionParamsMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "NutritionParamsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) NutritionParamsVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        NutritionParamsPO po = NutritionParamsMsMapper.INSTANCE.vo2po(vo);
//        设置更新人信息
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());
        boolean result = nutritionParamsMpService.updateById(po);
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
        boolean result = nutritionParamsMpService.removeById(id);
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
        boolean result = nutritionParamsMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
