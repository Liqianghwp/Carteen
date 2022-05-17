package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.DishesTypeDTO;
import com.diandong.domain.po.DishesTypePO;
import com.diandong.domain.vo.DishesTypeVO;
import com.diandong.mapstruct.DishesTypeMsMapper;
import com.diandong.service.DishesTypeMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
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
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/dishes_type", tags = {"菜品类型模块"})
@RequestMapping(value = "/dishes_type")
public class DishesTypeController extends BaseController {

    @Resource
    private DishesTypeMpService dishesTypeMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesTypeVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(DishesTypeVO vo) {
        Page<DishesTypePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<DishesTypeDTO> getById(@PathVariable("id") Long id) {
        DishesTypeDTO dto = DishesTypeMsMapper.INSTANCE
                .po2dto(dishesTypeMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesTypeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) DishesTypeVO vo) {

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }


        DishesTypePO po = DishesTypeMsMapper.INSTANCE.vo2po(vo);

//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());

        boolean result = dishesTypeMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "DishesTypeVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) DishesTypeVO vo) {
//        判断更新状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        DishesTypePO po = DishesTypeMsMapper.INSTANCE.vo2po(vo);
        po.setUpdateBy(loginUser.getUserId());
        boolean result = dishesTypeMpService.updateById(po);
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
        boolean result = dishesTypeMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<DishesTypePO> onSelectWhere(DishesTypeVO vo) {

        LambdaQueryChainWrapper<DishesTypePO> queryWrapper = dishesTypeMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), DishesTypePO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), DishesTypePO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getCanteenName()), DishesTypePO::getCanteenName, vo.getCanteenName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getTypeName()), DishesTypePO::getTypeName, vo.getTypeName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSort()), DishesTypePO::getSort, vo.getSort());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getIsShow()), DishesTypePO::getIsShow, vo.getIsShow());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), DishesTypePO::getRemark, vo.getRemark());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getIsPackage()), DishesTypePO::getIsPackage, vo.getIsPackage());

        return queryWrapper;
    }

}
