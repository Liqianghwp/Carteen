package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.domain.vo.DishesRawMaterialVO;
import com.diandong.mapstruct.DishesRawMaterialMsMapper;
import com.diandong.service.DishesRawMaterialMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Slf4j
@Validated
@RestController
@Api(value = "/dishes_raw_material", tags = {"菜品原材料模块"})
@RequestMapping(value = "/dishes_raw_material")
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
    public BaseResult getList(DishesRawMaterialVO vo) {
        Page<DishesRawMaterialPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<DishesRawMaterialDTO> getById(@PathVariable("id") Long id) {
        DishesRawMaterialDTO dto = DishesRawMaterialMsMapper.INSTANCE.po2dto(dishesRawMaterialMpService.getById(id));

        return BaseResult.success(dto);
    }

//    /**
//     * 保存
//     *
//     * @param vo 参数对象
//     * @return 返回结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "DishesRawMaterialVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
//    @PostMapping
//    public BaseResult save(@RequestBody @Validated(Insert.class) DishesRawMaterialVO vo) {
//        DishesRawMaterialPO po = DishesRawMaterialMsMapper.INSTANCE.vo2po(vo);
//        boolean result = dishesRawMaterialMpService.save(po);
//        if (result) {
//            return BaseResult.successMsg("添加成功！");
//        } else {
//            return BaseResult.error("添加失败！");
//        }
//    }

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
    @PostMapping
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
    public BaseResult update(@RequestBody @Validated(Update.class) DishesRawMaterialVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        DishesRawMaterialPO po = DishesRawMaterialMsMapper.INSTANCE.vo2po(vo);
//        设置更新人状态
        po.setUpdateBy(loginUser.getUserId());
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
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = dishesRawMaterialMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<DishesRawMaterialPO> onSelectWhere(DishesRawMaterialVO vo) {

        LambdaQueryChainWrapper<DishesRawMaterialPO> queryWrapper = dishesRawMaterialMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), DishesRawMaterialPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getDishesId()), DishesRawMaterialPO::getDishesId, vo.getDishesId())
                .eq(StringUtils.isNotBlank(vo.getDishesName()), DishesRawMaterialPO::getDishesName, vo.getDishesName())
                .eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), DishesRawMaterialPO::getRawMaterialId, vo.getRawMaterialId())
                .eq(StringUtils.isNotBlank(vo.getRawMaterialName()), DishesRawMaterialPO::getRawMaterialName, vo.getRawMaterialName())
                .eq(ObjectUtils.isNotEmpty(vo.getNumber()), DishesRawMaterialPO::getNumber, vo.getNumber());

        return queryWrapper;
    }

}
