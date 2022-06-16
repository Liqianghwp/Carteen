package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.domain.dto.BizDictDTO;
import com.diandong.domain.dto.DictRechargeExportDTO;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.domain.vo.BizDictVO;
import com.diandong.mapstruct.BizDictMsMapper;
import com.diandong.mapstruct.HealthCertificateMsMapper;
import com.diandong.service.BizDictMpService;
import com.diandong.service.CanteenMpService;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.constant.SysConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@Api(value = "/dict/recharge", tags = {"充值面额设置"})
@RestController
@RequestMapping("/dict/recharge")
public class DictRechargeSettingController extends BaseController {


    @Resource
    private BizDictMpService dictMpService;
    @Resource
    private GroupManagementMpService groupManagementMpService;
    @Resource
    private CanteenMpService canteenMpService;

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

        GroupManagementPO groupManagement = groupManagementMpService.lambdaQuery().eq(GroupManagementPO::getDeptId, SecurityUtils.getDeptId()).last("limit 1").one();
        if (Objects.nonNull(groupManagement)) {
            vo.setGroupId(groupManagement.getId());
        }
        vo.setCanteenId(SecurityUtils.getCanteenId());


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

    @ApiOperation(value = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizDictVO vo) {

        List<Long> ids = vo.getIds();

        List<BizDictPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = dictMpService.lambdaQuery().in(BizDictPO::getId, ids).list();
        } else {
            list = dictMpService.onSelectWhere(vo).list();
        }
        List<DictRechargeExportDTO> dictRechargeExportList = new ArrayList<>();

        list.forEach(dictPO -> {
            BizDictDTO bizDictDTO = BizDictMsMapper.INSTANCE.po2dto(dictPO);
            DictRechargeExportDTO dto = new DictRechargeExportDTO();

            BeanUtils.copyProperties(bizDictDTO, dto);

            dictRechargeExportList.add(dto);
        });

        ExcelUtil<DictRechargeExportDTO> util = new ExcelUtil<DictRechargeExportDTO>(DictRechargeExportDTO.class);
        util.exportExcel(response, dictRechargeExportList, "健康证管理");
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
            vo.setDictType(SysConstants.RECHARGE_SETTING);
        }
    }

}
