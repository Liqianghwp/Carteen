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
import com.diandong.service.RechargeTimesConfigMpService;
import com.diandong.domain.po.RechargeTimesConfigPO;
import com.diandong.domain.dto.RechargeTimesConfigDTO;
import com.diandong.domain.vo.RechargeTimesConfigVO;
import com.diandong.mapstruct.RechargeTimesConfigMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 充值次数设置Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/recharge_times_config", tags = {"充值次数设置模块"})
@RequestMapping(value = "/recharge_times_config")
public class RechargeTimesConfigController extends BaseController {

    @Resource
    private RechargeTimesConfigMpService rechargeTimesConfigMpService;

    /**
     * 充值次数设置分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesConfigVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "充值次数设置分页查询", notes = "充值次数设置分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RechargeTimesConfigVO vo) {

        Page<RechargeTimesConfigPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 充值次数设置根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值次数设置根据id查询", notes = "充值次数设置根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RechargeTimesConfigDTO> getById(@PathVariable("id") Long id) {
        RechargeTimesConfigDTO dto = RechargeTimesConfigMsMapper.INSTANCE
                .po2dto(rechargeTimesConfigMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 充值次数设置保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值次数设置保存", notes = "充值次数设置保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RechargeTimesConfigVO vo) {
        RechargeTimesConfigPO po = RechargeTimesConfigMsMapper.INSTANCE.vo2po(vo);



        boolean result = rechargeTimesConfigMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 充值次数设置更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值次数设置更新", notes = "充值次数设置更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RechargeTimesConfigVO vo) {
        RechargeTimesConfigPO po = RechargeTimesConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeTimesConfigMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 充值次数设置删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值次数设置删除", notes = "充值次数设置删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = rechargeTimesConfigMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 充值次数设置批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "充值次数设置批量删除", notes = "充值次数设置批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = rechargeTimesConfigMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<RechargeTimesConfigPO> onSelectWhere(RechargeTimesConfigVO vo) {
        LambdaQueryChainWrapper<RechargeTimesConfigPO> queryWrapper = rechargeTimesConfigMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeTimesConfigPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSex()), RechargeTimesConfigPO::getSex, vo.getSex());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getBeginTime()), RechargeTimesConfigPO::getBeginTime, vo.getBeginTime());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTime()), RechargeTimesConfigPO::getTime, vo.getTime());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), RechargeTimesConfigPO::getPrice, vo.getPrice());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTimes()), RechargeTimesConfigPO::getTimes, vo.getTimes());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), RechargeTimesConfigPO::getRemark, vo.getRemark());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getQrCode()), RechargeTimesConfigPO::getQrCode, vo.getQrCode());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), RechargeTimesConfigPO::getState, vo.getState());
        return queryWrapper;
    }


}
