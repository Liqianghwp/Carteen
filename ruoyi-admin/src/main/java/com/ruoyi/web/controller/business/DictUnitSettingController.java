package com.ruoyi.web.controller.business;

import com.diandong.domain.vo.BizDictVO;
import com.diandong.service.BizDictMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.constant.SysConstants;
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
 * 数据字典信息
 *
 * @author ruoyi
 */
@Api(value = "/dict/unit", tags = {"单位设置"})
@RestController
@RequestMapping("/dict/unit")
public class DictUnitSettingController extends BaseController {





    @Resource
    private BizDictMpService dictMpService;

    /**
     * 字典数据列表
     *
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDictData", name = "dictData", value = "")
    })
    @ApiOperation(value = "字典数据列表", notes = "字典数据列表", httpMethod = "GET")
    @GetMapping
    public BaseResult list(BizDictVO vo) {
        resetSysDictData(vo);
        return dictMpService.pageList(vo);
    }


    /**
     * 查询字典数据详细
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "dictCode", value = "")
    })
    @ApiOperation(value = "查询字典数据详细", notes = "查询字典数据详细", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(dictMpService.getById(id));
    }

//    /**
//     * 根据字典类型查询字典数据信息
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "path", dataType = "string", name = "dictType", value = "")
//    })
//    @ApiOperation(value = "根据字典类型查询字典数据信息", notes = "根据字典类型查询字典数据信息", httpMethod = "GET")
//    @GetMapping(value = "/type/{dictType}")
//    public BaseResult dictType(@PathVariable String dictType) {
//        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
//        if (StringUtils.isNull(data)) {
//            data = new ArrayList<SysDictData>();
//        }
//        return BaseResult.success(data);
//    }

    /**
     * 新增字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictDateVO", name = "dict", value = "")
    })
    @ApiOperation(value = "新增字典类型", notes = "新增字典类型", httpMethod = "POST")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody BizDictVO vo) {
        resetSysDictData(vo);
        return dictMpService.saveBizDict(vo);
    }

    /**
     * 修改保存字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictData", name = "dict", value = "")
    })
    @ApiOperation(value = "修改保存字典类型", notes = "修改保存字典类型", httpMethod = "PUT")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody BizDictVO vo) {
        resetSysDictData(vo);
        return dictMpService.updateBizDict(vo);
    }


    /**
     * 删除字典类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "dictCodes", value = "")
    })
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型", httpMethod = "DELETE")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable Long[] ids) {
        dictMpService.removeByIds(Arrays.asList(ids));
        return success();
    }



    /**
     * 默认设置字典类型
     *
     * @param vo 字典数据
     */
    private void resetSysDictData(BizDictVO vo) {
        if (Objects.isNull(vo)) {
            vo = new BizDictVO();
        }
        if (StringUtils.isBlank(vo.getDictType())) {
            vo.setDictType(SysConstants.UNIT_SETTING);
        }
    }
}
