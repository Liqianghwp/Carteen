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
import com.diandong.service.RechargeTimesRecordsMpService;
import com.diandong.domain.po.RechargeTimesRecordsPO;
import com.diandong.domain.dto.RechargeTimesRecordsDTO;
import com.diandong.domain.vo.RechargeTimesRecordsVO;
import com.diandong.mapstruct.RechargeTimesRecordsMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 充次记录Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/recharge_times_records", tags = {"充次记录模块"})
@RequestMapping(value = "/recharge_times_records")
public class RechargeTimesRecordsController extends BaseController {

    @Resource
    private RechargeTimesRecordsMpService rechargeTimesRecordsMpService;

    /**
     * 充次记录分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesRecordsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "充次记录分页查询", notes = "充次记录分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RechargeTimesRecordsVO vo) {

        Page<RechargeTimesRecordsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 充次记录根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充次记录根据id查询", notes = "充次记录根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RechargeTimesRecordsDTO> getById(@PathVariable("id") Long id) {
        RechargeTimesRecordsDTO dto = RechargeTimesRecordsMsMapper.INSTANCE
                .po2dto(rechargeTimesRecordsMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 充次记录保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充次记录保存", notes = "充次记录保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) RechargeTimesRecordsVO vo) {
        RechargeTimesRecordsPO po = RechargeTimesRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeTimesRecordsMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 充次记录更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充次记录更新", notes = "充次记录更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) RechargeTimesRecordsVO vo) {
        RechargeTimesRecordsPO po = RechargeTimesRecordsMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeTimesRecordsMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 充次记录删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充次记录删除", notes = "充次记录删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = rechargeTimesRecordsMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 充次记录批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "充次记录批量删除", notes = "充次记录批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = rechargeTimesRecordsMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<RechargeTimesRecordsPO> onSelectWhere(RechargeTimesRecordsVO vo) {
        LambdaQueryChainWrapper<RechargeTimesRecordsPO> queryWrapper = rechargeTimesRecordsMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeTimesRecordsPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSerialNumber()), RechargeTimesRecordsPO::getSerialNumber, vo.getSerialNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserId()), RechargeTimesRecordsPO::getUserId, vo.getUserId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRechargeMethod()), RechargeTimesRecordsPO::getRechargeMethod, vo.getRechargeMethod());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTime()), RechargeTimesRecordsPO::getTime, vo.getTime());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAmount()), RechargeTimesRecordsPO::getAmount, vo.getAmount());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCount()), RechargeTimesRecordsPO::getCount, vo.getCount());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRemainTimes()), RechargeTimesRecordsPO::getRemainTimes, vo.getRemainTimes());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getStartTime()), RechargeTimesRecordsPO::getStartTime, vo.getStartTime());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getEndTime()), RechargeTimesRecordsPO::getEndTime, vo.getEndTime());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), RechargeTimesRecordsPO::getState, vo.getState());
           return queryWrapper;
    }


}
