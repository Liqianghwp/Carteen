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
import com.diandong.service.RechargeAmountRecordsMpService;
import com.diandong.domain.po.RechargeAmountRecordsPO;
import com.diandong.domain.dto.RechargeAmountRecordsDTO;
import com.diandong.domain.vo.RechargeAmountRecordsVO;
import com.diandong.mapstruct.RechargeAmountRecordsMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 充值记录Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/recharge_amount_records", tags = {"充值记录模块"})
@RequestMapping(value = "/recharge_amount_records")
public class RechargeAmountRecordsController extends BaseController {

    @Resource
    private RechargeAmountRecordsMpService rechargeAmountRecordsMpService;

    /**
     * 充值记录分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeAmountRecordsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "充值记录分页查询", notes = "充值记录分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RechargeAmountRecordsVO vo) {

        Page<RechargeAmountRecordsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 充值记录根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值记录根据id查询", notes = "充值记录根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RechargeAmountRecordsDTO> getById(@PathVariable("id") Long id) {
        RechargeAmountRecordsDTO dto = RechargeAmountRecordsMsMapper.INSTANCE
                .po2dto(rechargeAmountRecordsMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 充值记录保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeAmountRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值记录保存", notes = "充值记录保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) RechargeAmountRecordsVO vo) {
        RechargeAmountRecordsPO po = RechargeAmountRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeAmountRecordsMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 充值记录更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeAmountRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值记录更新", notes = "充值记录更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) RechargeAmountRecordsVO vo) {
        RechargeAmountRecordsPO po = RechargeAmountRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeAmountRecordsMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 充值记录删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值记录删除", notes = "充值记录删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = rechargeAmountRecordsMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 充值记录批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "充值记录批量删除", notes = "充值记录批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = rechargeAmountRecordsMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<RechargeAmountRecordsPO> onSelectWhere(RechargeAmountRecordsVO vo) {
        LambdaQueryChainWrapper<RechargeAmountRecordsPO> queryWrapper = rechargeAmountRecordsMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeAmountRecordsPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSerialNumber()), RechargeAmountRecordsPO::getSerialNumber, vo.getSerialNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserId()), RechargeAmountRecordsPO::getUserId, vo.getUserId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRechargeType()), RechargeAmountRecordsPO::getRechargeType, vo.getRechargeType());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCardNo()), RechargeAmountRecordsPO::getCardNo, vo.getCardNo());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAmount()), RechargeAmountRecordsPO::getAmount, vo.getAmount());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRechargeMethod()), RechargeAmountRecordsPO::getRechargeMethod, vo.getRechargeMethod());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), RechargeAmountRecordsPO::getState, vo.getState());
           return queryWrapper;
    }


}
