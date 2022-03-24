package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@Api(value = "/system/dict/type", tags = {"数据字典信息"})
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 字典列表
     * @param dictType
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDictType", name = "dictType", value = "")
    })
    @ApiOperation(value = "字典列表", notes = "字典列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 字典类型
     * @param response
     * @param dictType
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "SysDictType", name = "dictType", value = "")
    })
    @ApiOperation(value = "字典类型", notes = "字典类型", httpMethod = "POST")
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "dictId", value = "")
    })
    @ApiOperation(value = "查询字典类型详细", notes = "查询字典类型详细", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictId}")
    public BaseResult getInfo(@PathVariable Long dictId) {
        return BaseResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictType", name = "dict", value = "")
    })
    @ApiOperation(value = "新增字典类型", notes = "新增字典类型", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return BaseResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictType", name = "dict", value = "")
    })
    @ApiOperation(value = "修改字典类型", notes = "修改字典类型", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return BaseResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "dictIds", value = "")
    })
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public BaseResult remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    @ApiOperation(value = "刷新字典缓存", notes = "刷新字典缓存", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public BaseResult refreshCache() {
        dictTypeService.resetDictCache();
        return BaseResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @ApiOperation(value = "获取字典选择框列表", notes = "获取字典选择框列表", httpMethod = "GET")
    @GetMapping("/optionselect")
    public BaseResult optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return BaseResult.success(dictTypes);
    }
}
