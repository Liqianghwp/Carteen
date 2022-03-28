package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.DishesNutritionMpService;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.dto.DishesNutritionDTO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.diandong.mapstruct.DishesNutritionMsMapper;
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
@Validated
@RestController
@Api(value = "/dishesNutrition", tags = {"菜品营养信息模块"})
@RequestMapping(value = "/dishesNutrition")
public class DishesNutritionController extends BaseController {

    @Resource
    private DishesNutritionMpService dishesNutritionMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesNutritionVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<DishesNutritionDTO> getList(DishesNutritionVO vo) {
        startPage();
        List<DishesNutritionPO> dataList = dishesNutritionMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), DishesNutritionPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getDisherId()), DishesNutritionPO::getDisherId, vo.getDisherId())
                .eq(ObjectUtils.isNotEmpty(vo.getDisherName()), DishesNutritionPO::getDisherName, vo.getDisherName())
                .eq(ObjectUtils.isNotEmpty(vo.getNutritionId()), DishesNutritionPO::getNutritionId, vo.getNutritionId())
                .eq(StringUtils.isNotBlank(vo.getNutritionName()), DishesNutritionPO::getNutritionName, vo.getNutritionName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), DishesNutritionPO::getNumber, vo.getNumber())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), DishesNutritionPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), DishesNutritionPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(DishesNutritionMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<DishesNutritionDTO> getById(@PathVariable("id") Long id) {
        DishesNutritionDTO dto = DishesNutritionMsMapper.INSTANCE
                .po2dto(dishesNutritionMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesNutritionVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) DishesNutritionVO vo) {
        DishesNutritionPO po = DishesNutritionMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesNutritionMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "DishesNutritionVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) DishesNutritionVO vo) {
        DishesNutritionPO po = DishesNutritionMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesNutritionMpService.updateById(po);
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
        boolean result = dishesNutritionMpService.removeById(id);
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
        boolean result = dishesNutritionMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
