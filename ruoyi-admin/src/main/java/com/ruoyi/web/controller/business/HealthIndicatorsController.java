package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.dto.HealthIndicatorsDTO;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.domain.vo.HealthIndicatorsVO;
import com.diandong.mapstruct.HealthIndicatorsMsMapper;
import com.diandong.service.HealthIndicatorsMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
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
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(HealthIndicatorsVO vo) {
        Page<HealthIndicatorsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
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
    @ApiOperation(value = "查询自己的健康指标", notes = "查询自己的健康指标", httpMethod = "GET")
    @GetMapping(value = "/own")
    public BaseResult getHealthIndicator() {


        List<HealthIndicatorsPO> list = healthIndicatorsMpService.lambdaQuery()
                .eq(HealthIndicatorsPO::getCreateBy, SecurityUtils.getUserId())
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return BaseResult.success();
        }
        return BaseResult.success(HealthIndicatorsMsMapper.INSTANCE.poList2dtoList(list));
    }


    /**
     * 保存
     *
     * @param voList 参数对象
     * @return 返回结果
     */
    @ApiOperation(value = "保存健康指标", notes = "保存健康指标", httpMethod = "POST")
    @PostMapping
    public BaseResult saveList(@RequestBody @Validated(Insert.class) List<HealthIndicatorsVO> voList) {
        return healthIndicatorsMpService.saveList(voList);
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
        boolean result = healthIndicatorsMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<HealthIndicatorsPO> onSelectWhere(HealthIndicatorsVO vo) {

        LambdaQueryChainWrapper<HealthIndicatorsPO> queryWrapper = healthIndicatorsMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthIndicatorsPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getIndicatorsId()), HealthIndicatorsPO::getIndicatorsId, vo.getIndicatorsId())
                .eq(StringUtils.isNotBlank(vo.getIndicatorsName()), HealthIndicatorsPO::getIndicatorsName, vo.getIndicatorsName())
                .eq(ObjectUtils.isNotEmpty(vo.getIndicatorValue()), HealthIndicatorsPO::getIndicatorValue, vo.getIndicatorValue())
                .eq(StringUtils.isNotBlank(vo.getIndicatorUnit()), HealthIndicatorsPO::getIndicatorUnit, vo.getIndicatorUnit())
                .eq(ObjectUtils.isNotEmpty(vo.getUserId()), HealthIndicatorsPO::getUserId, vo.getUserId())
                .eq(StringUtils.isNotBlank(vo.getUserName()), HealthIndicatorsPO::getUserName, vo.getUserName());

        return queryWrapper;
    }

}
