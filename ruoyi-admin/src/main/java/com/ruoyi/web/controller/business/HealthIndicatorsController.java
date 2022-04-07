package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.HealthIndicatorsMpService;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.dto.HealthIndicatorsDTO;
import com.diandong.domain.vo.HealthIndicatorsVO;
import com.diandong.mapstruct.HealthIndicatorsMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/health_indicators", tags = {"我的-健康指标模块"})
@RequestMapping(value = "/health_indicators")
public class HealthIndicatorsController extends BaseController {

    @Resource
    private HealthIndicatorsMpService healthIndicatorsMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthIndicatorsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<HealthIndicatorsDTO> getList(HealthIndicatorsVO vo) {
        startPage();
        List<HealthIndicatorsPO> dataList = healthIndicatorsMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthIndicatorsPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getWaistline()), HealthIndicatorsPO::getWaistline, vo.getWaistline())
                .eq(ObjectUtils.isNotEmpty(vo.getBodyWeight()), HealthIndicatorsPO::getBodyWeight, vo.getBodyWeight())
                .eq(ObjectUtils.isNotEmpty(vo.getBodyFat()), HealthIndicatorsPO::getBodyFat, vo.getBodyFat())
                .eq(ObjectUtils.isNotEmpty(vo.getHeartRate()), HealthIndicatorsPO::getHeartRate, vo.getHeartRate())
                .eq(ObjectUtils.isNotEmpty(vo.getBloodPressure()), HealthIndicatorsPO::getBloodPressure, vo.getBloodPressure())
                .eq(ObjectUtils.isNotEmpty(vo.getBloodSugar()), HealthIndicatorsPO::getBloodSugar, vo.getBloodSugar())
                .eq(ObjectUtils.isNotEmpty(vo.getTriglycerides()), HealthIndicatorsPO::getTriglycerides, vo.getTriglycerides())
                .eq(ObjectUtils.isNotEmpty(vo.getTotalCholesterol()), HealthIndicatorsPO::getTotalCholesterol, vo.getTotalCholesterol())
                .eq(ObjectUtils.isNotEmpty(vo.getBadCholesterol()), HealthIndicatorsPO::getBadCholesterol, vo.getBadCholesterol())
                .eq(ObjectUtils.isNotEmpty(vo.getBloodOxygen()), HealthIndicatorsPO::getBloodOxygen, vo.getBloodOxygen())
                .eq(ObjectUtils.isNotEmpty(vo.getBloodViscosity()), HealthIndicatorsPO::getBloodViscosity, vo.getBloodViscosity())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), HealthIndicatorsPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), HealthIndicatorsPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), HealthIndicatorsPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), HealthIndicatorsPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(HealthIndicatorsMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<HealthIndicatorsDTO> getById(@PathVariable("id") Long id) {
        HealthIndicatorsDTO dto = HealthIndicatorsMsMapper.INSTANCE
                .po2dto(healthIndicatorsMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 获取健康指标
     *
     * @return 返回结果
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/own")
    public BaseResult getHealthIndicator() {

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        List<HealthIndicatorsPO> list = healthIndicatorsMpService.lambdaQuery()
                .eq(HealthIndicatorsPO::getCreateBy, loginUser.getUserId())
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return BaseResult.success();
        }
        return BaseResult.success(list.get(0));
    }


    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthIndicatorsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存健康指标", notes = "保存健康指标", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) HealthIndicatorsVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        HealthIndicatorsPO po = HealthIndicatorsMsMapper.INSTANCE.vo2po(vo);

//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());
        po.setCreateName(loginUser.getUsername());

        boolean result = healthIndicatorsMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "HealthIndicatorsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) HealthIndicatorsVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        HealthIndicatorsPO po = HealthIndicatorsMsMapper.INSTANCE.vo2po(vo);
//        设置更新人信息
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());

        boolean result = healthIndicatorsMpService.updateById(po);
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
        boolean result = healthIndicatorsMpService.removeById(id);
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
        boolean result = healthIndicatorsMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
