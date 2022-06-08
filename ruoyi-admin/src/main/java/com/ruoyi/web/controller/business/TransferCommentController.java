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
import com.diandong.service.TransferCommentMpService;
import com.diandong.domain.po.TransferCommentPO;
import com.diandong.domain.dto.TransferCommentDTO;
import com.diandong.domain.vo.TransferCommentVO;
import com.diandong.mapstruct.TransferCommentMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Validated
@RestController
@Api(value = "/transfer_comment", tags = {"模块"})
@RequestMapping(value = "/transfer_comment")
public class TransferCommentController extends BaseController {

    @Resource
    private TransferCommentMpService transferCommentMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferCommentVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(TransferCommentVO vo) {

        Page<TransferCommentPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<TransferCommentDTO> getById(@PathVariable("id") Long id) {
        TransferCommentDTO dto = TransferCommentMsMapper.INSTANCE
                .po2dto(transferCommentMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferCommentVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) TransferCommentVO vo) {
        TransferCommentPO po = TransferCommentMsMapper.INSTANCE.vo2po(vo);
        boolean result = transferCommentMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "TransferCommentVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) TransferCommentVO vo) {
        TransferCommentPO po = TransferCommentMsMapper.INSTANCE.vo2po(vo);
        boolean result = transferCommentMpService.updateById(po);
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
        boolean result = transferCommentMpService.removeById(id);
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
        boolean result = transferCommentMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<TransferCommentPO> onSelectWhere(TransferCommentVO vo) {
        LambdaQueryChainWrapper<TransferCommentPO> queryWrapper = transferCommentMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), TransferCommentPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTransferId()), TransferCommentPO::getTransferId, vo.getTransferId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRid()), TransferCommentPO::getRid, vo.getRid());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), TransferCommentPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getFelFlag()), TransferCommentPO::getFelFlag, vo.getFelFlag());
           return queryWrapper;
    }


}
