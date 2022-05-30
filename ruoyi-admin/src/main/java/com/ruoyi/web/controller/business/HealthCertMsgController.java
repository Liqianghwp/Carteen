package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.domain.vo.HealthCertMsgVO;
import com.diandong.mapstruct.HealthCertMsgMsMapper;
import com.diandong.mapstruct.HealthCertificateMsMapper;
import com.diandong.service.HealthCertMsgMpService;
import com.diandong.service.HealthCertificateMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
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
 * 健康证到期消息Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/health_cert_msg", tags = {"健康证到期消息模块"})
@RequestMapping(value = "/health_cert_msg")
public class HealthCertMsgController extends BaseController {

    @Resource
    private HealthCertMsgMpService healthCertMsgMpService;
    @Resource
    private HealthCertificateMpService healthCertificateMpService;

    /**
     * 健康证到期消息分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "健康证到期消息分页查询", notes = "健康证到期消息分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(HealthCertMsgVO vo) {
        Page<HealthCertMsgPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 健康证到期消息根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证到期消息根据id查询", notes = "健康证到期消息根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult getById(@PathVariable("id") Long id) {

        HealthCertMsgPO healthCertMsg = healthCertMsgMpService.getById(id);
        HealthCertificateDTO healthCertificateDTO = HealthCertificateMsMapper.INSTANCE.po2dto(healthCertificateMpService.getById(healthCertMsg.getHealthCertId()));
        healthCertMsg.setState(Constants.DEFAULT_YES);
        healthCertMsgMpService.updateById(healthCertMsg);
        return BaseResult.success(healthCertificateDTO);
    }

    /**
     * 健康证到期消息保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证到期消息保存", notes = "健康证到期消息保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) HealthCertMsgVO vo) {
        HealthCertMsgPO po = HealthCertMsgMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertMsgMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 健康证到期消息更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证到期消息更新", notes = "健康证到期消息更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) HealthCertMsgVO vo) {
        HealthCertMsgPO po = HealthCertMsgMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertMsgMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 健康证到期消息删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证到期消息删除", notes = "健康证到期消息删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = healthCertMsgMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<HealthCertMsgPO> onSelectWhere(HealthCertMsgVO vo) {

        LambdaQueryChainWrapper<HealthCertMsgPO> queryWrapper = healthCertMsgMpService.lambdaQuery().orderByDesc(HealthCertMsgPO::getId);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthCertMsgPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getHealthCertId()), HealthCertMsgPO::getHealthCertId, vo.getHealthCertId())
                .eq(StringUtils.isNotBlank(vo.getName()), HealthCertMsgPO::getName, vo.getName())
                .eq(StringUtils.isNotBlank(vo.getPhone()), HealthCertMsgPO::getPhone, vo.getPhone())
                .eq(ObjectUtils.isNotEmpty(vo.getWarningTime()), HealthCertMsgPO::getWarningTime, vo.getWarningTime())
                .eq(StringUtils.isNotBlank(vo.getRemindDay()), HealthCertMsgPO::getRemindDay, vo.getRemindDay())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), HealthCertMsgPO::getState, vo.getState());

        return queryWrapper;
    }

}
