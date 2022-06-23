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
import com.diandong.service.ReviewListMpService;
import com.diandong.domain.po.ReviewListPO;
import com.diandong.domain.dto.ReviewListDTO;
import com.diandong.domain.vo.ReviewListVO;
import com.diandong.mapstruct.ReviewListMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 审核列Controller
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Validated
@RestController
@Api(value = "/review_list", tags = {"审核列模块"})
@RequestMapping(value = "/review_list")
public class ReviewListController extends BaseController {

    @Resource
    private ReviewListMpService reviewListMpService;

    /**
     * 审核列分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReviewListVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "审核列分页查询", notes = "审核列分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(ReviewListVO vo) {

        Page<ReviewListPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 审核列根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "审核列根据id查询", notes = "审核列根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<ReviewListDTO> getById(@PathVariable("id") Long id) {
        ReviewListDTO dto = ReviewListMsMapper.INSTANCE
                .po2dto(reviewListMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 审核列保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReviewListVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "审核列保存", notes = "审核列保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) ReviewListVO vo) {
        ReviewListPO po = ReviewListMsMapper.INSTANCE.vo2po(vo);
        boolean result = reviewListMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 审核列更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReviewListVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "审核列更新", notes = "审核列更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) ReviewListVO vo) {
        ReviewListPO po = ReviewListMsMapper.INSTANCE.vo2po(vo);
        boolean result = reviewListMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 审核列删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "审核列删除", notes = "审核列删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = reviewListMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 审核列批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "审核列批量删除", notes = "审核列批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = reviewListMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<ReviewListPO> onSelectWhere(ReviewListVO vo) {
        LambdaQueryChainWrapper<ReviewListPO> queryWrapper = reviewListMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), ReviewListPO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getApplyId()), ReviewListPO::getApplyId, vo.getApplyId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getApplyStatus()), ReviewListPO::getApplyStatus, vo.getApplyStatus());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getApplyStepType()), ReviewListPO::getApplyStepType, vo.getApplyStepType());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserId()), ReviewListPO::getUserId, vo.getUserId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getUserName()), ReviewListPO::getUserName, vo.getUserName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDeptId()), ReviewListPO::getDeptId, vo.getDeptId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getDeptName()), ReviewListPO::getDeptName, vo.getDeptName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRoleId()), ReviewListPO::getRoleId, vo.getRoleId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRoleName()), ReviewListPO::getRoleName, vo.getRoleName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getType()), ReviewListPO::getType, vo.getType());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getFinishTime()), ReviewListPO::getFinishTime, vo.getFinishTime());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getApplyOpinion()), ReviewListPO::getApplyOpinion, vo.getApplyOpinion());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSort()), ReviewListPO::getSort, vo.getSort());
           return queryWrapper;
    }


}
