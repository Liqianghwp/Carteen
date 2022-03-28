package com.ruoyi.web.controller.business;

import com.diandong.domain.vo.DictDateVO;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@Api(value = "/dict/results", tags = {"检测结果"})
@RestController
@RequestMapping("/dict/results")
public class DictTestResultsController extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 字典数据列表
     *
     * @param dictData
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDictData", name = "dictData", value = "")
    })
    @ApiOperation(value = "字典数据列表", notes = "字典数据列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('dict:results:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 字典数据导出
     *
     * @param response
     * @param dictData
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "SysDictData", name = "dictData", value = "")
    })
    @ApiOperation(value = "字典数据导出", notes = "字典数据导出", httpMethod = "POST")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('dict:results:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "dictCode", value = "")
    })
    @ApiOperation(value = "查询字典数据详细", notes = "查询字典数据详细", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('dict:results:query')")
    @GetMapping(value = "/{dictCode}")
    public BaseResult getInfo(@PathVariable Long dictCode) {
        return BaseResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "dictType", value = "")
    })
    @ApiOperation(value = "根据字典类型查询字典数据信息", notes = "根据字典类型查询字典数据信息", httpMethod = "GET")
    @GetMapping(value = "/type/{dictType}")
    public BaseResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return BaseResult.success(data);
    }

    /**
     * 新增字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictData", name = "dict", value = "")
    })
    @ApiOperation(value = "新增字典类型", notes = "新增字典类型", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('dict:results:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
//    public BaseResult add(@Validated @RequestBody SysDictData dict) {
//        dict.setCreateBy(getUsername());
//        return toAjax(dictDataService.insertDictData(dict));
//    }
    public BaseResult add(@Validated @RequestBody DictDateVO dict) {

        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(dict, sysDictData);
        sysDictData.setDictType("test_results");

        sysDictData.setCreateBy(getUsername());
        return toAjax(dictDataService.insertDictData(sysDictData));
    }

    /**
     * 修改保存字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictData", name = "dict", value = "")
    })
    @ApiOperation(value = "修改保存字典类型", notes = "修改保存字典类型", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('dict:results:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
//    public BaseResult edit(@Validated @RequestBody SysDictData dict) {
//        dict.setUpdateBy(getUsername());
//        return toAjax(dictDataService.updateDictData(dict));
//    }
    public BaseResult edit(@Validated @RequestBody DictDateVO dict) {

        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(dict, sysDictData);
        sysDictData.setDictType("test_results");

        sysDictData.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(sysDictData));
    }


    /**
     * 删除字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "dictCodes", value = "")
    })
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('dict:results:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public BaseResult remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
