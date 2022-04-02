package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.vo.OpinionFeedbackResponseVO;
import com.diandong.enums.OpinionStatusEnum;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.OpinionFeedbackMpService;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.dto.OpinionFeedbackDTO;
import com.diandong.domain.vo.OpinionFeedbackVO;
import com.diandong.mapstruct.OpinionFeedbackMsMapper;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Validated
@RestController
@Api(value = "/opinionFeedback", tags = {"意见反馈模块"})
@RequestMapping(value = "/opinionFeedback")
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
    public TableDataInfo<OpinionFeedbackDTO> getList(OpinionFeedbackVO vo) {
        startPage();
        List<OpinionFeedbackPO> dataList = opinionFeedbackMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OpinionFeedbackPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), OpinionFeedbackPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), OpinionFeedbackPO::getCanteenName, vo.getCanteenName())
                .eq(ObjectUtils.isNotEmpty(vo.getOpinionId()), OpinionFeedbackPO::getOpinionId, vo.getOpinionId())
                .eq(StringUtils.isNotBlank(vo.getOpinionType()), OpinionFeedbackPO::getOpinionType, vo.getOpinionType())
                .eq(StringUtils.isNotBlank(vo.getOpinionContent()), OpinionFeedbackPO::getOpinionContent, vo.getOpinionContent())
                .eq(StringUtils.isNotBlank(vo.getOpinionPicture()), OpinionFeedbackPO::getOpinionPicture, vo.getOpinionPicture())
                .eq(StringUtils.isNotBlank(vo.getProcessInformation()), OpinionFeedbackPO::getProcessInformation, vo.getProcessInformation())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), OpinionFeedbackPO::getStatus, vo.getStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getAnonymous()), OpinionFeedbackPO::getAnonymous, vo.getAnonymous())
                .eq(ObjectUtils.isNotEmpty(vo.getProcessTime()), OpinionFeedbackPO::getProcessTime, vo.getProcessTime())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), OpinionFeedbackPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), OpinionFeedbackPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), OpinionFeedbackPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), OpinionFeedbackPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(OpinionFeedbackMsMapper.INSTANCE.poList2dtoList(dataList));
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
        po.setCreateName(loginUser.getUsername());
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "集团后台pc端查看列表", notes = "后台系统pc端查看列表", httpMethod = "POST")
    @GetMapping("/getPcOpinionList")
    public TableDataInfo getPcOpinionList(OpinionFeedbackVO vo) {
        startPage();
        List<OpinionFeedbackResponseVO> pcOpinionList = opinionFeedbackMpService.getPcOpinionList(vo);
        return getDataTable(pcOpinionList);
    }


    /**
     * 获取当前食堂的列表信息
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "集团后台pc端查看列表", notes = "后台系统pc端查看列表", httpMethod = "POST")
    @GetMapping("/getGroupPcOpinionList")
    public TableDataInfo getGroupPcOpinionList(Long groupId, OpinionFeedbackVO vo) {
        startPage();
        List<OpinionFeedbackResponseVO> pcOpinionList = opinionFeedbackMpService.getGroupPcOpinionList(groupId, vo);
        return getDataTable(pcOpinionList);
    }


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
    public BaseResult update(@Validated(Update.class) OpinionFeedbackVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        OpinionFeedbackPO po = OpinionFeedbackMsMapper.INSTANCE.vo2po(vo);
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = opinionFeedbackMpService.removeById(id);
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
        boolean result = opinionFeedbackMpService.removeByIds(idList);
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

        List<OpinionFeedbackResponseVO> list = opinionFeedbackMpService.getGroupPcOpinionList(vo.getGroupId(), vo);

        ExcelUtil<OpinionFeedbackResponseVO> util = new ExcelUtil<OpinionFeedbackResponseVO>(OpinionFeedbackResponseVO.class);
        util.exportExcel(response, list, "意见反馈");
    }

}
