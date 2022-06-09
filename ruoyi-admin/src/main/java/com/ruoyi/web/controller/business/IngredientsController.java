package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.IngredientsDTO;
import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.domain.po.IngredientsPO;
import com.diandong.domain.vo.IngredientsDetailVO;
import com.diandong.domain.vo.IngredientsVO;
import com.diandong.mapstruct.IngredientsDetailMsMapper;
import com.diandong.mapstruct.IngredientsMsMapper;
import com.diandong.service.IngredientsDetailMpService;
import com.diandong.service.IngredientsMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 配料管理Controller
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Validated
@RestController
@Api(value = "/ingredients", tags = {"配料管理模块"})
@RequestMapping(value = "/ingredients")
public class IngredientsController extends BaseController {

    @Resource
    private IngredientsMpService ingredientsMpService;
    @Resource
    private IngredientsDetailMpService ingredientsDetailMpService;

    /**
     * 配料管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "配料管理分页查询", notes = "配料管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(IngredientsVO vo) {
        Page<IngredientsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        List<IngredientsPO> records = page.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {

            List<Long> ingredientsIds = records.stream().map(IngredientsPO::getId).collect(Collectors.toList());
            List<IngredientsDetailPO> detailList = ingredientsDetailMpService.lambdaQuery()
                    .in(IngredientsDetailPO::getIngredientsId, ingredientsIds)
                    .eq(IngredientsDetailPO::getDelFlag, false)
                    .list();


            for (IngredientsPO record : records) {

                List<IngredientsDetailPO> collect = detailList.stream().filter(ingredientsDetailPO -> ingredientsDetailPO.getIngredientsId().equals(record.getId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {

                    Map<String, List<IngredientsDetailPO>> collect1 = collect.stream().collect(Collectors.groupingBy(IngredientsDetailPO::getType));

                    for (Map.Entry<String, List<IngredientsDetailPO>> entry : collect1.entrySet()) {
                        List<IngredientsDetailPO> value = entry.getValue();
                        String val = null;
                        if (CollectionUtils.isNotEmpty(value)) {
                            val = value.stream().map(ingredientsDetailPO -> ingredientsDetailPO.getRawMaterialName() + " " + ingredientsDetailPO.getNumber() + "g").collect(Collectors.joining(","));
                        }

                        switch (entry.getKey()) {
                            case Constants.INGREDIENTS_MAIN:
                                record.setMainIngredient(val);
                                break;
                            case Constants.INGREDIENTS_SECONDARY:
                                record.setExcipients(val);
                                break;
                        }
                    }
                }
            }
        }

        return BaseResult.success(page);
    }

    /**
     * 配料管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理根据id查询", notes = "配料管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<IngredientsDTO> getById(@PathVariable("id") Long id) {
        IngredientsDTO dto = IngredientsMsMapper.INSTANCE.po2dto(ingredientsMpService.getById(id));
        List<IngredientsDetailPO> list = ingredientsDetailMpService.lambdaQuery()
                .eq(IngredientsDetailPO::getIngredientsId, dto.getId())
                .eq(IngredientsDetailPO::getDelFlag, false)
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<IngredientsDetailPO>> collect = list.stream().collect(Collectors.groupingBy(IngredientsDetailPO::getType));
            dto.setZio(CollectionUtils.isNotEmpty(collect.get(Constants.INGREDIENTS_MAIN)) ? collect.get(Constants.INGREDIENTS_MAIN) : null);
            dto.setZic(CollectionUtils.isNotEmpty(collect.get(Constants.INGREDIENTS_SECONDARY)) ? collect.get(Constants.INGREDIENTS_SECONDARY) : null);
        }
        return BaseResult.success(dto);
    }

    /**
     * 配料管理保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理保存", notes = "配料管理保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) IngredientsVO vo) {

        return ingredientsMpService.saveIngredients(vo);
    }

    /**
     * 配料管理更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理更新", notes = "配料管理更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) IngredientsVO vo) {
        return ingredientsMpService.updateIngredients(vo);
    }


    /**
     * 配料管理删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理删除", notes = "配料管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = ingredientsDetailMpService.lambdaUpdate().set(IngredientsDetailPO::getDelFlag, false).eq(IngredientsDetailPO::getIngredientsId, id).update();

        if (result) {
            result = ingredientsMpService.removeById(id);
        }
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }

    }

    private LambdaQueryChainWrapper<IngredientsPO> onSelectWhere(IngredientsVO vo) {
        LambdaQueryChainWrapper<IngredientsPO> queryWrapper = ingredientsMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), IngredientsPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishTypeId()), IngredientsPO::getDishTypeId, vo.getDishTypeId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishTypeName()), IngredientsPO::getDishTypeName, vo.getDishTypeName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishId()), IngredientsPO::getDishId, vo.getDishId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishName()), IngredientsPO::getDishName, vo.getDishName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), IngredientsPO::getRemark, vo.getRemark());
        return queryWrapper;
    }


}
