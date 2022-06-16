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
import com.diandong.service.DishesEvaluationsRecordsMpService;
import com.diandong.domain.po.DishesEvaluationsRecordsPO;
import com.diandong.domain.dto.DishesEvaluationsRecordsDTO;
import com.diandong.domain.vo.DishesEvaluationsRecordsVO;
import com.diandong.mapstruct.DishesEvaluationsRecordsMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 菜品评价记录Controller
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Validated
@RestController
@Api(value = "/dishes_evaluations_records", tags = {"菜品评价记录模块"})
@RequestMapping(value = "/dishes_evaluations_records")
public class DishesEvaluationsRecordsController extends BaseController {

    @Resource
    private DishesEvaluationsRecordsMpService dishesEvaluationsRecordsMpService;

    /**
     * 菜品评价记录分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesEvaluationsRecordsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "菜品评价记录分页查询", notes = "菜品评价记录分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(DishesEvaluationsRecordsVO vo) {

        Page<DishesEvaluationsRecordsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 菜品评价记录根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品评价记录根据id查询", notes = "菜品评价记录根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<DishesEvaluationsRecordsDTO> getById(@PathVariable("id") Long id) {
        DishesEvaluationsRecordsDTO dto = DishesEvaluationsRecordsMsMapper.INSTANCE
                .po2dto(dishesEvaluationsRecordsMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 菜品评价记录保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesEvaluationsRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品评价记录保存", notes = "菜品评价记录保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) DishesEvaluationsRecordsVO vo) {
        DishesEvaluationsRecordsPO po = DishesEvaluationsRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesEvaluationsRecordsMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 菜品评价记录更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "DishesEvaluationsRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "菜品评价记录更新", notes = "菜品评价记录更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) DishesEvaluationsRecordsVO vo) {
        DishesEvaluationsRecordsPO po = DishesEvaluationsRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = dishesEvaluationsRecordsMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 菜品评价记录删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "菜品评价记录删除", notes = "菜品评价记录删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = dishesEvaluationsRecordsMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 菜品评价记录批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "菜品评价记录批量删除", notes = "菜品评价记录批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = dishesEvaluationsRecordsMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<DishesEvaluationsRecordsPO> onSelectWhere(DishesEvaluationsRecordsVO vo) {
        LambdaQueryChainWrapper<DishesEvaluationsRecordsPO> queryWrapper = dishesEvaluationsRecordsMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), DishesEvaluationsRecordsPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), DishesEvaluationsRecordsPO::getCanteenId, vo.getCanteenId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCanteenName()), DishesEvaluationsRecordsPO::getCanteenName, vo.getCanteenName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishesId()), DishesEvaluationsRecordsPO::getDishesId, vo.getDishesId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getDishesName()), DishesEvaluationsRecordsPO::getDishesName, vo.getDishesName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getGoodNum()), DishesEvaluationsRecordsPO::getGoodNum, vo.getGoodNum());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getBadNum()), DishesEvaluationsRecordsPO::getBadNum, vo.getBadNum());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPraise()), DishesEvaluationsRecordsPO::getPraise, vo.getPraise());
           return queryWrapper;
    }


}
