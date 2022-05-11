package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.HealthCertificateMpService;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.vo.HealthCertificateVO;
import com.diandong.mapstruct.HealthCertificateMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

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
    public TableDataInfo<HealthCertificateDTO> getList(HealthCertificateVO vo) {
        startPage();
        List<HealthCertificatePO> dataList = healthCertificateMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthCertificatePO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getHealthCertPic()), HealthCertificatePO::getHealthCertPic, vo.getHealthCertPic())
                .eq(StringUtils.isNotBlank(vo.getName()), HealthCertificatePO::getName, vo.getName())
                .eq(StringUtils.isNotBlank(vo.getPhone()), HealthCertificatePO::getPhone, vo.getPhone())
                .eq(ObjectUtils.isNotEmpty(vo.getValidityStartTime()), HealthCertificatePO::getValidityStartTime, vo.getValidityStartTime())
                .eq(ObjectUtils.isNotEmpty(vo.getValidityEndTime()), HealthCertificatePO::getValidityEndTime, vo.getValidityEndTime())
                .eq(StringUtils.isNotBlank(vo.getCode()), HealthCertificatePO::getCode, vo.getCode())
                .eq(ObjectUtils.isNotEmpty(vo.getSex()), HealthCertificatePO::getSex, vo.getSex())
                .eq(ObjectUtils.isNotEmpty(vo.getBirthday()), HealthCertificatePO::getBirthday, vo.getBirthday())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), HealthCertificatePO::getState, vo.getState())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(HealthCertificateMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
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
    public BaseResult save(@Validated(Insert.class) HealthCertificateVO vo) {
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
    public BaseResult update(@Validated(Update.class) HealthCertificateVO vo) {
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证删除", notes = "健康证删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = healthCertificateMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 健康证批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "健康证批量删除", notes = "健康证批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = healthCertificateMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
