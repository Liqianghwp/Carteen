package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.OpinionFeedbackDTO;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.vo.OpinionFeedbackVO;
import com.diandong.mapstruct.OpinionFeedbackMsMapper;
import com.diandong.service.OpinionFeedbackMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Validated
@RestController
@Api(value = "/opinion_feedback", tags = {"意见反馈模块"})
@RequestMapping(value = "/opinion_feedback")
public class OpinionFeedbackController extends BaseController {

    @Resource
    private OpinionFeedbackMpService opinionFeedbackMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(OpinionFeedbackVO vo) {
        Page<OpinionFeedbackPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));

        List<OpinionFeedbackPO> records = page.getRecords();
        opinionFeedbackMpService.resetOpinionFeedBack(records);
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
    public BaseResult<OpinionFeedbackDTO> getById(@PathVariable("id") Long id) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        OpinionFeedbackPO opinionFeedback = opinionFeedbackMpService.getById(id);
        OpinionFeedbackDTO dto = OpinionFeedbackMsMapper.INSTANCE.po2dto(opinionFeedback);

        return BaseResult.success(dto);
    }

    /**
     * 移动端添加意见反馈
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "添加意见反馈", notes = "添加意见反馈", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) OpinionFeedbackVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        OpinionFeedbackPO po = OpinionFeedbackMsMapper.INSTANCE.vo2po(vo);
//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());
        boolean result = opinionFeedbackMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }


    /**
     * 获取当前食堂的列表信息
     *
     * @return
     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "集团后台pc端查看列表", notes = "后台系统pc端查看列表", httpMethod = "POST")
//    @GetMapping("/opinion/pc")
//    public TableDataInfo getPcOpinionList(OpinionFeedbackVO vo) {
//        startPage();
//        List<OpinionFeedbackResponseVO> pcOpinionList = opinionFeedbackMpService.getPcOpinionList(vo);
//        return getDataTable(pcOpinionList);
//    }


    /**
     * 获取当前食堂的列表信息
     *
     * @return
     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
//    })
//    @ApiOperation(value = "集团后台pc端查看列表", notes = "后台系统pc端查看列表", httpMethod = "POST")
//    @GetMapping("/opinion/group_pc")
//    public TableDataInfo getGroupPcOpinionList(Long groupId, OpinionFeedbackVO vo) {
//        startPage();
//        List<OpinionFeedbackPO> pcOpinionList = opinionFeedbackMpService.getGroupPcOpinionList(groupId, vo);
//        return getDataTable(pcOpinionList);
//    }


    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "处理意见反馈", notes = "处理意见反馈", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) OpinionFeedbackVO vo) {
        
        OpinionFeedbackPO po = OpinionFeedbackMsMapper.INSTANCE.vo2po(vo);
        po.setHandlerId(SecurityUtils.getUserId());
        boolean result = opinionFeedbackMpService.updateById(po);
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
        boolean result = opinionFeedbackMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "")
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @Log(title = "意见反馈", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpinionFeedbackVO vo) {

        List<OpinionFeedbackPO> list = onSelectWhere(vo).list();

        opinionFeedbackMpService.resetOpinionFeedBack(list);

        ExcelUtil<OpinionFeedbackPO> util = new ExcelUtil<OpinionFeedbackPO>(OpinionFeedbackPO.class);
        util.exportExcel(response, list, "意见反馈");
    }


    private LambdaQueryChainWrapper<OpinionFeedbackPO> onSelectWhere(OpinionFeedbackVO vo) {

        LambdaQueryChainWrapper<OpinionFeedbackPO> queryWrapper = opinionFeedbackMpService.lambdaQuery();
        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OpinionFeedbackPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), OpinionFeedbackPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), OpinionFeedbackPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getOpinionId()), OpinionFeedbackPO::getOpinionId, vo.getOpinionId())
                .eq(StringUtils.isNotBlank(vo.getOpinionType()), OpinionFeedbackPO::getOpinionType, vo.getOpinionType())
                .like(StringUtils.isNotBlank(vo.getOpinionContent()), OpinionFeedbackPO::getOpinionContent, vo.getOpinionContent())
                .eq(StringUtils.isNotBlank(vo.getProcessInformation()), OpinionFeedbackPO::getProcessInformation, vo.getProcessInformation())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), OpinionFeedbackPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getAnonymous()), OpinionFeedbackPO::getAnonymous, vo.getAnonymous())
                .eq(ObjectUtils.isNotEmpty(vo.getProcessTime()), OpinionFeedbackPO::getProcessTime, vo.getProcessTime());
        if (ObjectUtils.isNotEmpty(vo.getStartTime()) && ObjectUtils.isNotEmpty(vo.getEndTime())) {
            queryWrapper.between(OpinionFeedbackPO::getCreateTime, vo.getStartTime(), vo.getEndTime());
        }

        return queryWrapper;
    }

}
