package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.ShopCartDTO;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.mapstruct.ShopCartMsMapper;
import com.diandong.service.ShopCartMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * 购物车Controller
 *
 * @author YuLiu
 * @date 2022-06-14
 */
@Validated
@RestController
@Api(value = "/shop_cart", tags = {"购物车模块"})
@RequestMapping(value = "/shop_cart")
public class ShopCartController extends BaseController {

    @Resource
    private ShopCartMpService shopCartMpService;

    /**
     * 购物车分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "购物车分页查询", notes = "购物车分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(ShopCartVO vo) {
        return shopCartMpService.getList(vo);
    }

    /**
     * 购物车根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "购物车根据id查询", notes = "购物车根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<ShopCartDTO> getById(@PathVariable("id") Long id) {
        ShopCartDTO dto = ShopCartMsMapper.INSTANCE
                .po2dto(shopCartMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 购物车保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "购物车保存&更新 数量加减", notes = "购物车保存&更新 数量加减", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) ShopCartVO vo) {
        return shopCartMpService.save(vo);
    }

//    /**
//     * 购物车更新
//     *
//     * @param vo 参数对象
//     * @return 返回结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "购物车更新", notes = "购物车更新", httpMethod = "PUT")
//    @PutMapping
//    public BaseResult update(@Validated(Update.class) ShopCartVO vo) {
//        ShopCartPO po = ShopCartMsMapper.INSTANCE.vo2po(vo);
//        boolean result = shopCartMpService.updateById(po);
//        if (result) {
//            return BaseResult.successMsg("修改成功");
//        } else {
//            return BaseResult.error("修改失败");
//        }
//    }

    /**
     * 购物车删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long[]", name = "id", value = "编号id")
    })
    @ApiOperation(value = "购物车删除", notes = "购物车删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = shopCartMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<ShopCartPO> onSelectWhere(ShopCartVO vo) {
        LambdaQueryChainWrapper<ShopCartPO> queryWrapper = shopCartMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), ShopCartPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), ShopCartPO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getCanteenName()), ShopCartPO::getCanteenName, vo.getCanteenName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishesId()), ShopCartPO::getDishesId, vo.getDishesId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishesName()), ShopCartPO::getDishesName, vo.getDishesName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishesSpecification()), ShopCartPO::getDishesSpecification, vo.getDishesSpecification());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), ShopCartPO::getNumber, vo.getNumber());
        return queryWrapper;
    }


}
