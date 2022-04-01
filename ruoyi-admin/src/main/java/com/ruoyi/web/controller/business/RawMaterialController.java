package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.vo.RawMaterialVO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.RawMaterialMpService;
import com.diandong.mapstruct.RawMaterialMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/rawMaterial", tags = {"原材料设置"})
@RequestMapping(value = "/rawMaterial")
public class RawMaterialController extends BaseController {

    @Resource
    private RawMaterialMpService rawMaterialMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<RawMaterialDTO> getList(RawMaterialVO vo) {
        startPage();
        List<RawMaterialPO> dataList = rawMaterialMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RawMaterialPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), RawMaterialPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), RawMaterialPO::getCanteenName, vo.getCanteenName())
                .eq(StringUtils.isNotBlank(vo.getRawMaterialName()), RawMaterialPO::getRawMaterialName, vo.getRawMaterialName())
                .eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), RawMaterialPO::getCategoryId, vo.getCategoryId())
                .eq(StringUtils.isNotBlank(vo.getCategoryName()), RawMaterialPO::getCategoryName, vo.getCategoryName())
                .eq(ObjectUtils.isNotEmpty(vo.getUnitId()), RawMaterialPO::getUnitId, vo.getUnitId())
                .eq(StringUtils.isNotBlank(vo.getUnitName()), RawMaterialPO::getUnitName, vo.getUnitName())
                .eq(ObjectUtils.isNotEmpty(vo.getPurchaseTypeId()), RawMaterialPO::getPurchaseTypeId, vo.getPurchaseTypeId())
                .eq(StringUtils.isNotBlank(vo.getPurchaseTypeName()), RawMaterialPO::getPurchaseTypeName, vo.getPurchaseTypeName())
                .eq(ObjectUtils.isNotEmpty(vo.getPrePrice()), RawMaterialPO::getPrePrice, vo.getPrePrice())
                .eq(ObjectUtils.isNotEmpty(vo.getStorehouseId()), RawMaterialPO::getStorehouseId, vo.getStorehouseId())
                .eq(StringUtils.isNotBlank(vo.getStorehouseName()), RawMaterialPO::getStorehouseName, vo.getStorehouseName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), RawMaterialPO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), RawMaterialPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), RawMaterialPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), RawMaterialPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), RawMaterialPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), RawMaterialPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(RawMaterialMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RawMaterialDTO> getById(@PathVariable("id") Long id) {
        RawMaterialDTO dto = RawMaterialMsMapper.INSTANCE
                .po2dto(rawMaterialMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "添加原材料信息", notes = "添加原材料信息", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RawMaterialVO vo) {

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        RawMaterialPO po = RawMaterialMsMapper.INSTANCE.vo2po(vo);

        po.setCreateBy(loginUser.getUserId());
        po.setCreateName(loginUser.getUsername());

        boolean result = rawMaterialMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) RawMaterialVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if(Objects.isNull(loginUser)){
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        RawMaterialPO po = RawMaterialMsMapper.INSTANCE.vo2po(vo);
//        设置更新人信息
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());
        boolean result = rawMaterialMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = rawMaterialMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = rawMaterialMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
