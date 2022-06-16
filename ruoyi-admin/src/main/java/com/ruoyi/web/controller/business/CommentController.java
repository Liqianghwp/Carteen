package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.CommentDTO;
import com.diandong.domain.po.CommentPO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.vo.CommentVO;
import com.diandong.enums.CommentStatusEnum;
import com.diandong.mapstruct.CommentMsMapper;
import com.diandong.service.CommentMpService;
import com.diandong.service.DishesMpService;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Validated
@RestController
@Api(value = "/comment", tags = {"评价模块"})
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentMpService commentMpService;
    @Resource
    private DishesMpService dishesMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(CommentVO vo) {

        Page<CommentPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        if (CollectionUtils.isNotEmpty(page.getRecords())) {

            for (CommentPO commentPO : page.getRecords()) {
//        喜好菜品
                commentPO.setDeliciousDishes(resetDishesName(commentPO.getDeliciousDishes()));
//        不喜欢的菜品
                commentPO.setUndeliciousDishes(resetDishesName(commentPO.getUndeliciousDishes()));
            }
        }

        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<CommentDTO> getById(@PathVariable("id") Long id) {

//        判断登录信息
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        CommentPO comment = commentMpService.getById(id);

//        喜好菜品
        comment.setDeliciousDishes(resetDishesName(comment.getDeliciousDishes()));
//        不喜欢的菜品
        comment.setUndeliciousDishes(resetDishesName(comment.getUndeliciousDishes()));

        CommentDTO dto = CommentMsMapper.INSTANCE.po2dto(comment);

        return BaseResult.success(dto);
    }

    /**
     * 添加订单评价
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiOperation(value = "添加订单评价", notes = "添加订单评价", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) CommentVO vo) {
        return commentMpService.saveOrderEvaluation(vo);
    }

    /**
     * 处理评价信息
     *
     * @param vo 参数
     * @return 返回结果
     */
    @ApiOperation(value = "处理评价信息", notes = "处理评价信息", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) CommentVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        CommentPO po = CommentMsMapper.INSTANCE.vo2po(vo);
//        设置修改人信息
        po.setStatus(CommentStatusEnum.COMPLETED.value());
        po.setProcessTime(LocalDateTime.now());
        po.setUpdateBy(loginUser.getUserId());

        boolean result = commentMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 编号id数组
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = commentMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 根据ID重新设置展示菜品名称
     *
     * @param dishesIds
     * @return
     */
    private String resetDishesName(String dishesIds) {

        if (StringUtils.isNotBlank(dishesIds)) {
//            标记喜欢的实物
            List<String> deliciousDishesIds = Arrays.asList(dishesIds.split(","));
            List<DishesPO> deliciousDishesList = dishesMpService.lambdaQuery().in(DishesPO::getId, deliciousDishesIds).list();

            return CollectionUtils.isEmpty(deliciousDishesList) ? null : deliciousDishesList.stream().map(DishesPO::getDishesName).collect(Collectors.joining("、"));

        }
        return null;
    }


    private LambdaQueryChainWrapper<CommentPO> onSelectWhere(CommentVO vo) {

        LambdaQueryChainWrapper<CommentPO> queryWrapper = commentMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), CommentPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getOrderId()), CommentPO::getOrderId, vo.getOrderId())
                .eq(StringUtils.isNotBlank(vo.getCommentContent()), CommentPO::getCommentContent, vo.getCommentContent())
                .eq(StringUtils.isNotBlank(vo.getCommentPicture()), CommentPO::getCommentPicture, vo.getCommentPicture())
                .eq(StringUtils.isNotBlank(vo.getDeliciousDishes()), CommentPO::getDeliciousDishes, vo.getDeliciousDishes())
                .eq(StringUtils.isNotBlank(vo.getUndeliciousDishes()), CommentPO::getUndeliciousDishes, vo.getUndeliciousDishes())
                .eq(StringUtils.isNotBlank(vo.getProcessDescription()), CommentPO::getProcessDescription, vo.getProcessDescription())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), CommentPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getProcessTime()), CommentPO::getProcessTime, vo.getProcessTime());

        return queryWrapper;
    }
}
