package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RawMaterialDTO;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.RawMaterialPO;
import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.domain.vo.RawMaterialVO;
import com.diandong.domain.vo.ReserveSampleVO;
import com.diandong.mapstruct.RawMaterialMsMapper;
import com.diandong.service.RawMaterialMpService;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/raw_material", tags = {"原材料设置"})
@RequestMapping(value = "/raw_material")
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
    public BaseResult getList(RawMaterialVO vo) {

        Page<RawMaterialPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
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
        RawMaterialDTO dto = RawMaterialMsMapper.INSTANCE.po2dto(rawMaterialMpService.getById(id));

        rawMaterialMpService.resetRawMaterialDTO(dto);

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
        boolean result = rawMaterialMpService.saveRawMaterial(vo);
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
    public BaseResult update(@RequestBody @Validated(Update.class) RawMaterialVO vo) {

        boolean result = rawMaterialMpService.updateRawMaterial(vo);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = rawMaterialMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    @ApiOperation(value = "根据Type查询原材料信息")
    @GetMapping("/type")
    public BaseResult getListByType(RawMaterialVO vo) {

        Page<RawMaterialPO> page = rawMaterialMpService.lambdaQuery()
                .eq(RawMaterialPO::getDelFlag, Constants.DEL_NO)
                .eq(Objects.nonNull(vo.getCategoryId()), RawMaterialPO::getCategoryId, vo.getCategoryId())
                .like(StringUtils.isNotBlank(vo.getRawMaterialName()), RawMaterialPO::getRawMaterialName, vo.getRawMaterialName())
                .page(new Page<>(vo.getPageNum(), vo.getPageSize()));

        return BaseResult.success(page);
    }

    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @ApiOperation(value = "原材料导出")
    @PostMapping("/export")
    public void exportCanteen(HttpServletResponse response, RawMaterialVO vo) {

        List<Long> ids = vo.getIds();
        List<RawMaterialPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = rawMaterialMpService.lambdaQuery().in(RawMaterialPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<RawMaterialDTO> exportList = new ArrayList<>();

        list.forEach(rawMaterialPO -> {

            RawMaterialDTO rawMaterialDTO = RawMaterialMsMapper.INSTANCE.po2dto(rawMaterialPO);
//            设置库存信息
            rawMaterialDTO.setStock(1);
            exportList.add(rawMaterialDTO);
        });


        ExcelUtil<RawMaterialDTO> util = new ExcelUtil<RawMaterialDTO>(RawMaterialDTO.class);
        util.exportExcel(response, exportList, "原材料设置");

    }


    private LambdaQueryChainWrapper<RawMaterialPO> onSelectWhere(RawMaterialVO vo) {

        LambdaQueryChainWrapper<RawMaterialPO> queryWrapper = rawMaterialMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RawMaterialPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), RawMaterialPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), RawMaterialPO::getCanteenName, vo.getCanteenName())
                .eq(StringUtils.isNotBlank(vo.getRawMaterialName()), RawMaterialPO::getRawMaterialName, vo.getRawMaterialName())
                .eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), RawMaterialPO::getCategoryId, vo.getCategoryId())
                .eq(StringUtils.isNotBlank(vo.getCategoryName()), RawMaterialPO::getCategoryName, vo.getCategoryName())
                .eq(ObjectUtils.isNotEmpty(vo.getUnitId()), RawMaterialPO::getUnitId, vo.getUnitId())
                .eq(StringUtils.isNotBlank(vo.getUnitName()), RawMaterialPO::getUnitName, vo.getUnitName())
                .eq(ObjectUtils.isNotEmpty(vo.getPurchaseType()), RawMaterialPO::getPurchaseType, vo.getPurchaseType())
                .eq(ObjectUtils.isNotEmpty(vo.getPrePrice()), RawMaterialPO::getPrePrice, vo.getPrePrice())
                .eq(ObjectUtils.isNotEmpty(vo.getStorehouseId()), RawMaterialPO::getStorehouseId, vo.getStorehouseId())
                .eq(StringUtils.isNotBlank(vo.getStorehouseName()), RawMaterialPO::getStorehouseName, vo.getStorehouseName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), RawMaterialPO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), RawMaterialPO::getStatus, vo.getStatus());

        return queryWrapper;
    }
}
