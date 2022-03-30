package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.RecipeMpService;
import com.diandong.domain.po.RecipePO;
import com.diandong.domain.dto.RecipeDTO;
import com.diandong.domain.vo.RecipeVO;
import com.diandong.mapstruct.RecipeMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/recipe", tags = {"模块"})
@RequestMapping(value = "/recipe")
public class RecipeController extends BaseController {

    @Resource
    private RecipeMpService recipeMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<RecipeDTO> getList(RecipeVO vo) {
        startPage();
        List<RecipePO> dataList = recipeMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RecipePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), RecipePO::getMealTimesId, vo.getMealTimesId())
                .eq(StringUtils.isNotBlank(vo.getMealTimesName()), RecipePO::getMealTimesName, vo.getMealTimesName())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), RecipePO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), RecipePO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), RecipePO::getNumber, vo.getNumber())
                .eq(ObjectUtils.isNotEmpty(vo.getRecipeDate()), RecipePO::getRecipeDate, vo.getRecipeDate())
                .eq(StringUtils.isNotBlank(vo.getAddWay()), RecipePO::getAddWay, vo.getAddWay())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), RecipePO::getVersion, vo.getVersion())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), RecipePO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), RecipePO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), RecipePO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(RecipeMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<RecipeDTO> getById(@PathVariable("id") Long id) {
        RecipeDTO dto = RecipeMsMapper.INSTANCE
                .po2dto(recipeMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) RecipeVO vo) {
        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
        boolean result = recipeMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "RecipeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) RecipeVO vo) {
        RecipePO po = RecipeMsMapper.INSTANCE.vo2po(vo);
        boolean result = recipeMpService.updateById(po);
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
        boolean result = recipeMpService.removeById(id);
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
        boolean result = recipeMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
