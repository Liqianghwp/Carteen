package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.DishesRawMaterialMpService;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.vo.DishesRawMaterialVO;
import com.diandong.mapstruct.DishesRawMaterialMsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Slf4j
@Validated
@RestController
@Api(value = "/dishesRawMaterial", tags = {"菜品原材料模块"})
@RequestMapping(value = "/dishesRawMaterial")
public class DishesRawMaterialController extends BaseController {

    @Resource
    private DishesRawMaterialMpService dishesRawMaterialMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesRawMaterialVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<DishesRawMaterialDTO> getList(DishesRawMaterialVO vo) {
        startPage();
        List<DishesRawMaterialPO> dataList = dishesRawMaterialMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), DishesRawMaterialPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), DishesRawMaterialPO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), DishesRawMaterialPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), DishesRawMaterialPO::getRawMaterialId, vo.getRawMaterialId())
                .eq(StringUtils.isNotBlank(vo.getRawMaterialName()), DishesRawMaterialPO::getRawMaterialName, vo.getRawMaterialName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), DishesRawMaterialPO::getNumber, vo.getNumber())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), DishesRawMaterialPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), DishesRawMaterialPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(DishesRawMaterialMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<DishesRawMaterialDTO> getById(@PathVariable("id") Long id) {
        DishesRawMaterialDTO dto = DishesRawMaterialMsMapper.INSTANCE
                .po2dto(dishesRawMaterialMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesRawMaterialVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) DishesRawMaterialVO vo) {
        DishesRawMaterialPO po = DishesRawMaterialMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesRawMaterialMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 菜品原材料信息批量保存
     *
     * @param voList 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesRawMaterialVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存原材料信息", notes = "保存原材料信息", httpMethod = "POST")
    @PostMapping("/saveList")
    public BaseResult saveList(@RequestBody @Validated(Insert.class) List<DishesRawMaterialVO> voList) {

        Boolean result = false;

        try {
            result = dishesRawMaterialMpService.saveList(voList, getLoginUser());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (result) {
            return BaseResult.success("添加成功");
        } else {
            return BaseResult.error("添加失败");
        }
    }


    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesRawMaterialVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) DishesRawMaterialVO vo) {
        DishesRawMaterialPO po = DishesRawMaterialMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesRawMaterialMpService.updateById(po);
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
        boolean result = dishesRawMaterialMpService.removeById(id);
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
        boolean result = dishesRawMaterialMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
