package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.DishesAdditiveMpService;
import com.diandong.domain.po.DishesAdditivePO;
import com.diandong.domain.dto.DishesAdditiveDTO;
import com.diandong.domain.vo.DishesAdditiveVO;
import com.diandong.mapstruct.DishesAdditiveMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 菜品添加剂信息Controller
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Validated
@RestController
@Api(value = "/dishes_additive", tags = {"菜品添加剂信息模块"})
@RequestMapping(value = "/dishes_additive")
public class DishesAdditiveController extends BaseController {

    @Resource
    private DishesAdditiveMpService dishesAdditiveMpService;

    /**
     * 菜品添加剂信息分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesAdditiveVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "菜品添加剂信息分页查询", notes = "菜品添加剂信息分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(DishesAdditiveVO vo) {

        Page<DishesAdditivePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 菜品添加剂信息根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品添加剂信息根据id查询", notes = "菜品添加剂信息根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<DishesAdditiveDTO> getById(@PathVariable("id") Long id) {
        DishesAdditiveDTO dto = DishesAdditiveMsMapper.INSTANCE
                .po2dto(dishesAdditiveMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 菜品添加剂信息保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesAdditiveVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品添加剂信息保存", notes = "菜品添加剂信息保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) DishesAdditiveVO vo) {
        DishesAdditivePO po = DishesAdditiveMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesAdditiveMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 菜品添加剂信息更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesAdditiveVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品添加剂信息更新", notes = "菜品添加剂信息更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) DishesAdditiveVO vo) {
        DishesAdditivePO po = DishesAdditiveMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesAdditiveMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 菜品添加剂信息删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品添加剂信息删除", notes = "菜品添加剂信息删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = dishesAdditiveMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 菜品添加剂信息批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "菜品添加剂信息批量删除", notes = "菜品添加剂信息批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = dishesAdditiveMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<DishesAdditivePO> onSelectWhere(DishesAdditiveVO vo) {
        LambdaQueryChainWrapper<DishesAdditivePO> queryWrapper = dishesAdditiveMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), DishesAdditivePO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAdditiveName()), DishesAdditivePO::getAdditiveName, vo.getAdditiveName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getContent()), DishesAdditivePO::getContent, vo.getContent());
           return queryWrapper;
    }


}
