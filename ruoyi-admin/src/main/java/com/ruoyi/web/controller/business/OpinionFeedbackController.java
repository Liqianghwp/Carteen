package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.OpinionFeedbackMpService;
import com.diandong.domain.po.OpinionFeedbackPO;
import com.diandong.domain.dto.OpinionFeedbackDTO;
import com.diandong.domain.vo.OpinionFeedbackVO;
import com.diandong.mapstruct.OpinionFeedbackMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/opinion_feedback", tags = {"模块"})
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
    public TableDataInfo<OpinionFeedbackDTO> getList(OpinionFeedbackVO vo) {
        startPage();
        List<OpinionFeedbackPO> dataList = opinionFeedbackMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), OpinionFeedbackPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getOpinionId()), OpinionFeedbackPO::getOpinionId, vo.getOpinionId())
                .eq(StringUtils.isNotBlank(vo.getOpinionType()), OpinionFeedbackPO::getOpinionType, vo.getOpinionType())
                .eq(StringUtils.isNotBlank(vo.getOpinionContent()), OpinionFeedbackPO::getOpinionContent, vo.getOpinionContent())
                .eq(StringUtils.isNotBlank(vo.getOpinionPicture()), OpinionFeedbackPO::getOpinionPicture, vo.getOpinionPicture())
                .eq(StringUtils.isNotBlank(vo.getProcessInformation()), OpinionFeedbackPO::getProcessInformation, vo.getProcessInformation())
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
        OpinionFeedbackDTO dto = OpinionFeedbackMsMapper.INSTANCE
                .po2dto(opinionFeedbackMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) OpinionFeedbackVO vo) {
        OpinionFeedbackPO po = OpinionFeedbackMsMapper.INSTANCE.vo2po(vo);
        boolean result = opinionFeedbackMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "OpinionFeedbackVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) OpinionFeedbackVO vo) {
        OpinionFeedbackPO po = OpinionFeedbackMsMapper.INSTANCE.vo2po(vo);
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

}
