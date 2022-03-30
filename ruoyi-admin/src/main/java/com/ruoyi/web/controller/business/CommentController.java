package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.CommentMpService;
import com.diandong.domain.po.CommentPO;
import com.diandong.domain.dto.CommentDTO;
import com.diandong.domain.vo.CommentVO;
import com.diandong.mapstruct.CommentMsMapper;
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
@Api(value = "/comment", tags = {"模块"})
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentMpService commentMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CommentVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<CommentDTO> getList(CommentVO vo) {
        startPage();
        List<CommentPO> dataList = commentMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), CommentPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getOrderId()), CommentPO::getOrderId, vo.getOrderId())
                .eq(StringUtils.isNotBlank(vo.getCommentContent()), CommentPO::getCommentContent, vo.getCommentContent())
                .eq(StringUtils.isNotBlank(vo.getCommentPicture()), CommentPO::getCommentPicture, vo.getCommentPicture())
                .eq(StringUtils.isNotBlank(vo.getDeliciousDishes()), CommentPO::getDeliciousDishes, vo.getDeliciousDishes())
                .eq(StringUtils.isNotBlank(vo.getUndeliciousDishes()), CommentPO::getUndeliciousDishes, vo.getUndeliciousDishes())
                .eq(StringUtils.isNotBlank(vo.getProcessDescription()), CommentPO::getProcessDescription, vo.getProcessDescription())
                .eq(ObjectUtils.isNotEmpty(vo.getProcessTime()), CommentPO::getProcessTime, vo.getProcessTime())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), CommentPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), CommentPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), CommentPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), CommentPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(CommentMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<CommentDTO> getById(@PathVariable("id") Long id) {
        CommentDTO dto = CommentMsMapper.INSTANCE
                .po2dto(commentMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CommentVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) CommentVO vo) {
        CommentPO po = CommentMsMapper.INSTANCE.vo2po(vo);
        boolean result = commentMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "CommentVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) CommentVO vo) {
        CommentPO po = CommentMsMapper.INSTANCE.vo2po(vo);
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = commentMpService.removeById(id);
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
        boolean result = commentMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
