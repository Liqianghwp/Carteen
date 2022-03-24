package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.BusinessConfigDTO;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.diandong.mapstruct.BusinessConfigMsMapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.mp.BusinessConfigMpService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 系统配置Controller
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Validated
@RestController
@Api(value = "/business_config", tags = {"系统配置模块"})
@RequestMapping(value = "/business_config")
public class BusinessConfigController extends BaseController {

    @Resource
    private BusinessConfigMpService businessConfigMpService;

    /**
     * 系统配置分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "系统配置分页查询", notes = "系统配置分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<BusinessConfigDTO> getList(BusinessConfigVO vo) {
        startPage();
        List<BusinessConfigPO> dataList = businessConfigMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), BusinessConfigPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getConfigName()), BusinessConfigPO::getConfigName, vo.getConfigName())
                .eq(StringUtils.isNotBlank(vo.getConfigValue()), BusinessConfigPO::getConfigValue, vo.getConfigValue())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), BusinessConfigPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), BusinessConfigPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), BusinessConfigPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), BusinessConfigPO::getUpdateName, vo.getUpdateName())
                .le(ObjectUtils.isNotEmpty(vo.getEndTime()), BusinessConfigPO::getCreateTime, vo.getEndTime())
                .ge(ObjectUtils.isNotEmpty(vo.getBeginTime()), BusinessConfigPO::getCreateTime, vo.getBeginTime())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(BusinessConfigMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
    }

    /**
     * 系统配置根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "系统配置根据id查询", notes = "系统配置根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<BusinessConfigDTO> getById(@PathVariable("id") Long id) {
        BusinessConfigDTO dto = BusinessConfigMsMapper.INSTANCE
                .po2dto(businessConfigMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 系统配置保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "系统配置保存", notes = "系统配置保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) BusinessConfigVO vo) {
        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = businessConfigMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 系统配置更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "系统配置更新", notes = "系统配置更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) BusinessConfigVO vo) {
        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = businessConfigMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 系统配置删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "系统配置删除", notes = "系统配置删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = businessConfigMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 系统配置批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "系统配置批量删除", notes = "系统配置批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = businessConfigMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
