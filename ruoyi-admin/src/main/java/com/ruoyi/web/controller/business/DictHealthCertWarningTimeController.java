package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.domain.vo.DictDateVO;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.constant.SysConstants;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Classname DictHealthCertWarningTimeController
 * @Description 健康证管理配置（主要用于设置健康证提前多少天预警）
 * @Date 2022/5/10 16:05
 * @Created by YuLiu
 */
@Api(value = "/dict/health_cert", tags = {"健康证管理配置"})
@RestController
@RequestMapping("/dict/health_cert")
public class DictHealthCertWarningTimeController extends BaseController {

    @Resource
    private ISysDictDataService dictDataService;

    /**
     * 字典数据列表
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDictData", name = "dictData", value = "")
    })
    @ApiOperation(value = "字典数据列表", notes = "字典数据列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('dict:health:list')")
    @GetMapping
    public BaseResult list() {
        SysDictData sysDictData = new SysDictData();
        resetSysDictData(sysDictData);
        List<SysDictData> dictDataList = dictDataService.selectDictDataList(sysDictData);
        if (CollectionUtils.isNotEmpty(dictDataList)) {
            return BaseResult.success(dictDataList.get(0));
        } else {
            return BaseResult.success();
        }

    }

    /**
     * 修改保存字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictData", name = "dict", value = "")
    })
    @ApiOperation(value = "修改保存字典类型", notes = "修改保存字典类型", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('dict:health:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody DictDateVO dict) {

        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(dict, sysDictData);
        resetSysDictData(sysDictData);
        sysDictData.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(sysDictData));
    }

    /**
     * 默认设置字典类型
     *
     * @param sysDictData 字典数据
     */
    private void resetSysDictData(SysDictData sysDictData) {
        if (Objects.isNull(sysDictData)) {
            sysDictData = new SysDictData();
        }
        if (StringUtils.isBlank(sysDictData.getDictType())) {
            sysDictData.setDictType(SysConstants.HEALTH_CERTIFICATE);
        }
    }

}
