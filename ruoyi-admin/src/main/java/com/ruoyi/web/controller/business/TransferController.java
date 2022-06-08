package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.po.TransferCommentPO;
import com.diandong.service.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.domain.po.TransferPO;
import com.diandong.domain.dto.TransferDTO;
import com.diandong.domain.vo.TransferVO;
import com.diandong.mapstruct.TransferMsMapper;
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
@Api(value = "/transfer", tags = {"模块"})
@RequestMapping(value = "/transfer")
public class TransferController extends BaseController {

    @Resource
    private TransferMpService transferMpService;
    @Resource
    private TransferCommentMpService transferCommentMpService;
    @Resource
    private InventoryLedgerMpService inventoryLedgerMpService;
    @Resource
     private   InventoryInboundMpService inventoryInboundMpService;
    @Resource
    private InventoryOutboundMpService inventoryOutboundMpService;
    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(TransferVO vo) {
        Page<TransferPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<TransferDTO> getById(@PathVariable("id") Long id) {
        TransferDTO dto = TransferMsMapper.INSTANCE
                .po2dto(transferMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "TransferVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) TransferVO vo) {
        TransferPO po = TransferMsMapper.INSTANCE.vo2po(vo);

        boolean result = transferMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "TransferVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) TransferVO vo) {
        TransferPO po = TransferMsMapper.INSTANCE.vo2po(vo);
        boolean result = transferMpService.updateById(po);
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
        boolean result = transferMpService.removeById(id);
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
        boolean result = transferMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<TransferPO> onSelectWhere(TransferVO vo) {
        LambdaQueryChainWrapper<TransferPO> queryWrapper = transferMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), TransferPO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCode()), TransferPO::getCode, vo.getCode());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getOutboundCanteen()), TransferPO::getOutboundCanteen, vo.getOutboundCanteen());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInboundCanteen()), TransferPO::getInboundCanteen, vo.getInboundCanteen());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), TransferPO::getState, vo.getState());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getReviewer()), TransferPO::getReviewer, vo.getReviewer());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getReviewTime()), TransferPO::getReviewTime, vo.getReviewTime());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), TransferPO::getRemark, vo.getRemark());
           return queryWrapper;
    }


}
