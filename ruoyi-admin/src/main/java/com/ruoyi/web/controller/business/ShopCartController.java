package com.ruoyi.web.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.vo.ShopCartDetailVO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.ShopCartMpService;
import com.diandong.domain.po.ShopCartPO;
import com.diandong.domain.dto.ShopCartDTO;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.mapstruct.ShopCartMsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Slf4j
@Validated
@RestController
@Api(value = "/shop_cart", tags = {"购物车模块"})
@RequestMapping(value = "/shop_cart")
public class ShopCartController extends BaseController {

    @Resource
    private ShopCartMpService shopCartMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<ShopCartDTO> getList(ShopCartVO vo) {
        startPage();
        List<ShopCartPO> dataList = shopCartMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), ShopCartPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), ShopCartPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), ShopCartPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), ShopCartPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), ShopCartPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), ShopCartPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), ShopCartPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(ShopCartMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<ShopCartDTO> getById(@PathVariable("id") Long id) {
        ShopCartDTO dto = ShopCartMsMapper.INSTANCE
                .po2dto(shopCartMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) ShopCartVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        try {
            return shopCartMpService.saveShopCart(vo, loginUser);
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
            @ApiImplicitParam(paramType = "query", dataType = "ShopCartVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) ShopCartVO vo) {
        ShopCartPO po = ShopCartMsMapper.INSTANCE.vo2po(vo);
        boolean result = shopCartMpService.updateById(po);
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
        boolean result = shopCartMpService.removeById(id);
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
        boolean result = shopCartMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
