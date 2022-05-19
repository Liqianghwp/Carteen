package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.RawMaterialBlacklistDTO;
import com.diandong.domain.po.RawMaterialBlacklistPO;
import com.diandong.domain.vo.RawMaterialBlacklistVO;
import com.diandong.mapstruct.RawMaterialBlacklistMsMapper;
import com.diandong.service.RawMaterialBlacklistMpService;
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
 * 原材料黑名单Controller
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@Validated
@RestController
@Api(value = "/raw_material_blacklist", tags = {"原材料黑名单模块"})
@RequestMapping(value = "/raw_material_blacklist")
public class RawMaterialBlacklistController extends BaseController {

    @Resource
    private RawMaterialBlacklistMpService rawMaterialBlacklistMpService;

    /**
     * 原材料黑名单分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialBlacklistVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "原材料黑名单分页查询", notes = "原材料黑名单分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RawMaterialBlacklistVO vo) {

        Page<RawMaterialBlacklistPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 原材料黑名单根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "原材料黑名单根据id查询", notes = "原材料黑名单根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RawMaterialBlacklistDTO> getById(@PathVariable("id") Long id) {
        RawMaterialBlacklistDTO dto = RawMaterialBlacklistMsMapper.INSTANCE
                .po2dto(rawMaterialBlacklistMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 原材料黑名单保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialBlacklistVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "原材料黑名单保存", notes = "原材料黑名单保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RawMaterialBlacklistVO vo) {
        RawMaterialBlacklistPO po = RawMaterialBlacklistMsMapper.INSTANCE.vo2po(vo);
        boolean result = rawMaterialBlacklistMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 原材料黑名单更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RawMaterialBlacklistVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "原材料黑名单更新", notes = "原材料黑名单更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RawMaterialBlacklistVO vo) {
        RawMaterialBlacklistPO po = RawMaterialBlacklistMsMapper.INSTANCE.vo2po(vo);
        boolean result = rawMaterialBlacklistMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 原材料黑名单删除
     *
     * @param ids 编号id数组
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long[]", name = "ids", value = "编号id数组")
    })
    @ApiOperation(value = "原材料黑名单删除", notes = "原材料黑名单删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = rawMaterialBlacklistMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<RawMaterialBlacklistPO> onSelectWhere(RawMaterialBlacklistVO vo) {
        LambdaQueryChainWrapper<RawMaterialBlacklistPO> queryWrapper = rawMaterialBlacklistMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), RawMaterialBlacklistPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), RawMaterialBlacklistPO::getCategoryId, vo.getCategoryId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialName()), RawMaterialBlacklistPO::getRawMaterialName, vo.getRawMaterialName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUnitId()), RawMaterialBlacklistPO::getUnitId, vo.getUnitId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPurchaseTypeId()), RawMaterialBlacklistPO::getPurchaseTypeId, vo.getPurchaseTypeId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrePrice()), RawMaterialBlacklistPO::getPrePrice, vo.getPrePrice());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), RawMaterialBlacklistPO::getRemark, vo.getRemark());
        return queryWrapper;
    }


}
