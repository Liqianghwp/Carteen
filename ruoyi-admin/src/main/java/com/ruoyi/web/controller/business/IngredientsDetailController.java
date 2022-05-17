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
import com.diandong.service.IngredientsDetailMpService;
import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.domain.dto.IngredientsDetailDTO;
import com.diandong.domain.vo.IngredientsDetailVO;
import com.diandong.mapstruct.IngredientsDetailMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 配料管理详情Controller
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Validated
@RestController
@Api(value = "/ingredients_detail", tags = {"配料管理详情模块"})
@RequestMapping(value = "/ingredients_detail")
public class IngredientsDetailController extends BaseController {

    @Resource
    private IngredientsDetailMpService ingredientsDetailMpService;

    /**
     * 配料管理详情分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsDetailVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "配料管理详情分页查询", notes = "配料管理详情分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(IngredientsDetailVO vo) {

        Page<IngredientsDetailPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 配料管理详情根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理详情根据id查询", notes = "配料管理详情根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<IngredientsDetailDTO> getById(@PathVariable("id") Long id) {
        IngredientsDetailDTO dto = IngredientsDetailMsMapper.INSTANCE
                .po2dto(ingredientsDetailMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 配料管理详情保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理详情保存", notes = "配料管理详情保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) IngredientsDetailVO vo) {
        IngredientsDetailPO po = IngredientsDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = ingredientsDetailMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 配料管理详情更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理详情更新", notes = "配料管理详情更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) IngredientsDetailVO vo) {
        IngredientsDetailPO po = IngredientsDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = ingredientsDetailMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 配料管理详情删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理详情删除", notes = "配料管理详情删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = ingredientsDetailMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 配料管理详情批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "配料管理详情批量删除", notes = "配料管理详情批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = ingredientsDetailMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<IngredientsDetailPO> onSelectWhere(IngredientsDetailVO vo) {
        LambdaQueryChainWrapper<IngredientsDetailPO> queryWrapper = ingredientsDetailMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), IngredientsDetailPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getIngredientsId()), IngredientsDetailPO::getIngredientsId, vo.getIngredientsId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getType()), IngredientsDetailPO::getType, vo.getType());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), IngredientsDetailPO::getRawMaterialId, vo.getRawMaterialId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialName()), IngredientsDetailPO::getRawMaterialName, vo.getRawMaterialName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), IngredientsDetailPO::getNumber, vo.getNumber());
           return queryWrapper;
    }


}
