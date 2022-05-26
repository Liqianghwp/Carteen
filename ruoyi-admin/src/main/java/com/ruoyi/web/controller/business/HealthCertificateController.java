package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.domain.vo.HealthCertificateVO;
import com.diandong.mapstruct.HealthCertificateMsMapper;
import com.diandong.service.HealthCertificateMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 健康证Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/health_certificate", tags = {"健康证模块"})
@RequestMapping(value = "/health_certificate")
public class HealthCertificateController extends BaseController {

    @Resource
    private HealthCertificateMpService healthCertificateMpService;

    /**
     * 健康证分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertificateVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "健康证分页查询", notes = "健康证分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(HealthCertificateVO vo) {
        Page<HealthCertificatePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 健康证根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证根据id查询", notes = "健康证根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<HealthCertificateDTO> getById(@PathVariable("id") Long id) {
        HealthCertificateDTO dto = HealthCertificateMsMapper.INSTANCE
                .po2dto(healthCertificateMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 健康证保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertificateVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证保存", notes = "健康证保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) HealthCertificateVO vo) {
        HealthCertificatePO po = HealthCertificateMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertificateMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 健康证更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertificateVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证更新", notes = "健康证更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) HealthCertificateVO vo) {
        HealthCertificatePO po = HealthCertificateMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertificateMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 健康证删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long[]", name = "ids", value = "编号id数组")
    })
    @ApiOperation(value = "健康证删除", notes = "健康证删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = healthCertificateMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @Log(title = "健康证导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthCertificateVO vo) {
        List<Long> ids = vo.getIds();

        List<HealthCertificatePO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = healthCertificateMpService.lambdaQuery().in(HealthCertificatePO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<HealthCertificateDTO> healthCertificateList = new ArrayList<>();

        list.forEach(healthCertificatePO -> {
            healthCertificateList.add(HealthCertificateMsMapper.INSTANCE.po2dto(healthCertificatePO));
        });

        ExcelUtil<HealthCertificateDTO> util = new ExcelUtil<HealthCertificateDTO>(HealthCertificateDTO.class);
        util.exportExcel(response, healthCertificateList, "健康证管理");
    }


    private LambdaQueryChainWrapper<HealthCertificatePO> onSelectWhere(HealthCertificateVO vo) {

        LambdaQueryChainWrapper<HealthCertificatePO> queryWrapper = healthCertificateMpService.lambdaQuery().orderByDesc(HealthCertificatePO::getId);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthCertificatePO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getHealthCertPic()), HealthCertificatePO::getHealthCertPic, vo.getHealthCertPic())
                .like(StringUtils.isNotBlank(vo.getName()), HealthCertificatePO::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getPhone()), HealthCertificatePO::getPhone, vo.getPhone())
                .like(StringUtils.isNotBlank(vo.getCode()), HealthCertificatePO::getCode, vo.getCode())
                .eq(ObjectUtils.isNotEmpty(vo.getSex()), HealthCertificatePO::getSex, vo.getSex())
                .eq(ObjectUtils.isNotEmpty(vo.getBirthday()), HealthCertificatePO::getBirthday, vo.getBirthday())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), HealthCertificatePO::getState, vo.getState());

        if (Objects.nonNull(vo.getStartTime()) && Objects.nonNull(vo.getEndTime())) {
            queryWrapper.between(HealthCertificatePO::getValidityEndTime, vo.getStartTime(), vo.getEndTime());
        }

        return queryWrapper;
    }

}
