package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.DishesNutritionMpService;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.dto.DishesNutritionDTO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.diandong.mapstruct.DishesNutritionMsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
@Api(value = "/dishes_nutrition", tags = {"菜品营养信息模块"})
@RequestMapping(value = "/dishes_nutrition")
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
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), DishesNutritionPO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), DishesNutritionPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getNutritionId()), DishesNutritionPO::getNutritionId, vo.getNutritionId())
                .eq(StringUtils.isNotBlank(vo.getNutritionName()), DishesNutritionPO::getNutritionName, vo.getNutritionName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), DishesNutritionPO::getNumber, vo.getNumber())
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
     * @param voList 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<DishesNutritionVO>", name = "voList", value = "参数对象")
    })
    @ApiOperation(value = "批量保存菜品营养信息", notes = "批量保存菜品营养信息", httpMethod = "POST")
    @PostMapping
    public BaseResult saveList(@RequestBody @Validated(Insert.class) List<DishesNutritionVO> voList) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        try {
            return dishesNutritionMpService.saveDishesNutritionList(voList, loginUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
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

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        DishesNutritionPO po = DishesNutritionMsMapper.INSTANCE.vo2po(vo);

        po.setUpdateBy(loginUser.getUserId());
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
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = dishesNutritionMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
