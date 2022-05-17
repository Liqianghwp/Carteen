package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.RechargeTimesDTO;
import com.diandong.domain.po.RechargeTimesPO;
import com.diandong.domain.vo.RechargeTimesVO;
import com.diandong.mapstruct.RechargeTimesMsMapper;
import com.diandong.service.RechargeTimesMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * 充值次数设置Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/recharge_times", tags = {"充值次数设置模块"})
@RequestMapping(value = "/recharge_times")
public class RechargeTimesController extends BaseController {

    @Resource
    private RechargeTimesMpService rechargeTimesMpService;

    /**
     * 充值次数设置分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "充值次数设置分页查询", notes = "充值次数设置分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RechargeTimesVO vo) {
        LambdaQueryChainWrapper<RechargeTimesPO> queryWrapper = onSelectWhere(vo);
        Page<RechargeTimesPO> page = queryWrapper.page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult<RechargeTimesDTO> getById(@PathVariable("id") Long id) {
        RechargeTimesDTO dto = RechargeTimesMsMapper.INSTANCE
                .po2dto(rechargeTimesMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 充值次数设置保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值次数设置保存", notes = "充值次数设置保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RechargeTimesVO vo) {
        RechargeTimesPO po = RechargeTimesMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeTimesMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "RechargeTimesVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值次数设置更新", notes = "充值次数设置更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RechargeTimesVO vo) {

        logger.info(SecurityUtils.getUserId().toString());

        RechargeTimesPO po = RechargeTimesMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeTimesMpService.saveOrUpdate(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 充值次数设置删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值次数设置删除", notes = "充值次数设置删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = rechargeTimesMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<RechargeTimesPO> onSelectWhere(RechargeTimesVO vo) {
        LambdaQueryChainWrapper<RechargeTimesPO> queryWrapper = rechargeTimesMpService.lambdaQuery().orderByDesc(RechargeTimesPO::getCreateTime);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeTimesPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getSex()), RechargeTimesPO::getSex, vo.getSex())
                .eq(StringUtils.isNotBlank(vo.getTime()), RechargeTimesPO::getTime, vo.getTime())
                .eq(ObjectUtils.isNotEmpty(vo.getPrice()), RechargeTimesPO::getPrice, vo.getPrice())
                .eq(ObjectUtils.isNotEmpty(vo.getTimes()), RechargeTimesPO::getTimes, vo.getTimes())
                .eq(StringUtils.isNotBlank(vo.getRemark()), RechargeTimesPO::getRemark, vo.getRemark())
                .eq(StringUtils.isNotBlank(vo.getQrCode()), RechargeTimesPO::getQrCode, vo.getQrCode())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), RechargeTimesPO::getState, vo.getState());

        return queryWrapper;

    }


}
