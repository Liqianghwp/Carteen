package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.DishesSupplierDTO;
import com.diandong.domain.po.DishesSupplierPO;
import com.diandong.domain.vo.DishesSupplierVO;
import com.diandong.mapstruct.DishesSupplierMsMapper;
import com.diandong.service.DishesSupplierMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 菜品供应商信息Controller
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Validated
@RestController
@Api(value = "/dishes_supplier", tags = {"菜品供应商信息模块"})
@RequestMapping(value = "/dishes_supplier")
public class DishesSupplierController extends BaseController {

    @Resource
    private DishesSupplierMpService dishesSupplierMpService;

    /**
     * 菜品供应商信息分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesSupplierVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "菜品供应商信息分页查询", notes = "菜品供应商信息分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(DishesSupplierVO vo) {

        Page<DishesSupplierPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 菜品供应商信息根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品供应商信息根据id查询", notes = "菜品供应商信息根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<DishesSupplierDTO> getById(@PathVariable("id") Long id) {
        DishesSupplierDTO dto = DishesSupplierMsMapper.INSTANCE
                .po2dto(dishesSupplierMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 菜品供应商信息保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesSupplierVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品供应商信息保存", notes = "菜品供应商信息保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) DishesSupplierVO vo) {
        DishesSupplierPO po = DishesSupplierMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesSupplierMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 菜品供应商信息更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesSupplierVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品供应商信息更新", notes = "菜品供应商信息更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) DishesSupplierVO vo) {
        DishesSupplierPO po = DishesSupplierMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesSupplierMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 菜品供应商信息删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品供应商信息删除", notes = "菜品供应商信息删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = dishesSupplierMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 菜品供应商信息批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "菜品供应商信息批量删除", notes = "菜品供应商信息批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = dishesSupplierMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<DishesSupplierPO> onSelectWhere(DishesSupplierVO vo) {
        LambdaQueryChainWrapper<DishesSupplierPO> queryWrapper = dishesSupplierMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), DishesSupplierPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSupplierId()), DishesSupplierPO::getSupplierId, vo.getSupplierId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getSupplierName()), DishesSupplierPO::getSupplierName, vo.getSupplierName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), DishesSupplierPO::getRawMaterialId, vo.getRawMaterialId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialName()), DishesSupplierPO::getRawMaterialName, vo.getRawMaterialName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialOrigin()), DishesSupplierPO::getRawMaterialOrigin, vo.getRawMaterialOrigin());
           return queryWrapper;
    }


}
