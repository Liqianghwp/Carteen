package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
import com.ruoyi.common.core.page.TableDataInfo;
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
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/dishesType", tags = {"菜品类型模块"})
@RequestMapping(value = "/dishesType")
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
    public TableDataInfo<DishesTypeDTO> getList(DishesTypeVO vo) {
        startPage();
        List<DishesTypePO> dataList = dishesTypeMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), DishesTypePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), DishesTypePO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), DishesTypePO::getCanteenName, vo.getCanteenName())
                .eq(StringUtils.isNotBlank(vo.getTypeName()), DishesTypePO::getTypeName, vo.getTypeName())
                .eq(ObjectUtils.isNotEmpty(vo.getSort()), DishesTypePO::getSort, vo.getSort())
                .eq(ObjectUtils.isNotEmpty(vo.getIsShow()), DishesTypePO::getIsShow, vo.getIsShow())
                .eq(StringUtils.isNotBlank(vo.getTypeLabel()), DishesTypePO::getTypeLabel, vo.getTypeLabel())
                .eq(ObjectUtils.isNotEmpty(vo.getIsPackage()), DishesTypePO::getIsPackage, vo.getIsPackage())
                .eq(StringUtils.isNotBlank(vo.getUuid()), DishesTypePO::getUuid, vo.getUuid())
                .eq(StringUtils.isNotBlank(vo.getPuuid()), DishesTypePO::getPuuid, vo.getPuuid())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), DishesTypePO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), DishesTypePO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), DishesTypePO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), DishesTypePO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(DishesTypeMsMapper.INSTANCE.poList2dtoList(dataList));
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
        po.setCreateName(loginUser.getUsername());

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
    public BaseResult update(@Validated(Update.class) DishesTypeVO vo) {
        DishesTypePO po = DishesTypeMsMapper.INSTANCE.vo2po(vo);
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = dishesTypeMpService.removeById(id);
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
        boolean result = dishesTypeMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
