package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.RechargeCardCarouselDTO;
import com.diandong.domain.po.RechargeCardCarouselPO;
import com.diandong.domain.vo.RechargeCardCarouselVO;
import com.diandong.mapstruct.RechargeCardCarouselMsMapper;
import com.diandong.service.RechargeCardCarouselMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 充值卡触摸屏轮播图Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/recharge_card_carousel", tags = {"充值卡触摸屏轮播图模块"})
@RequestMapping(value = "/recharge_card_carousel")
public class RechargeCardCarouselController extends BaseController {

    @Resource
    private RechargeCardCarouselMpService rechargeCardCarouselMpService;

    /**
     * 充值卡触摸屏轮播图分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeCardCarouselVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图分页查询", notes = "充值卡触摸屏轮播图分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(RechargeCardCarouselVO vo) {
        LambdaQueryChainWrapper<RechargeCardCarouselPO> queryWrapper = onSelectWhere(vo);
        Page<RechargeCardCarouselPO> page = queryWrapper.page(new Page<>(vo.getPageNum(), vo.getPageSize()));

        List<RechargeCardCarouselPO> records = page.getRecords();

//        设置显示顺序
        if (CollectionUtils.isNotEmpty(records)) {
            Integer sort = (vo.getPageNum() - 1) * vo.getPageSize() + 1;
            for (RechargeCardCarouselPO record : records) {
                record.setSort(sort);
                sort += 1;
            }
        }
        return BaseResult.success(page);
    }

    /**
     * 充值卡触摸屏轮播图根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图根据id查询", notes = "充值卡触摸屏轮播图根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<RechargeCardCarouselDTO> getById(@PathVariable("id") Long id) {
        RechargeCardCarouselDTO dto = RechargeCardCarouselMsMapper.INSTANCE
                .po2dto(rechargeCardCarouselMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 充值卡触摸屏轮播图保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeCardCarouselVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图保存", notes = "充值卡触摸屏轮播图保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) RechargeCardCarouselVO vo) {

        RechargeCardCarouselPO po = RechargeCardCarouselMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeCardCarouselMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 充值卡触摸屏轮播图更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "RechargeCardCarouselVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图更新", notes = "充值卡触摸屏轮播图更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) RechargeCardCarouselVO vo) {
        RechargeCardCarouselPO po = RechargeCardCarouselMsMapper.INSTANCE.vo2po(vo);
        boolean result = rechargeCardCarouselMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 充值卡触摸屏轮播图删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "充值卡触摸屏轮播图删除", notes = "充值卡触摸屏轮播图删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = rechargeCardCarouselMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<RechargeCardCarouselPO> onSelectWhere(RechargeCardCarouselVO vo) {
        LambdaQueryChainWrapper<RechargeCardCarouselPO> queryWrapper = rechargeCardCarouselMpService.lambdaQuery()
                .orderByAsc(RechargeCardCarouselPO::getCreateTime);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        rechargeCardCarouselMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeCardCarouselPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getCarouselPic()), RechargeCardCarouselPO::getCarouselPic, vo.getCarouselPic())
                .eq(StringUtils.isNotBlank(vo.getCarouselName()), RechargeCardCarouselPO::getCarouselName, vo.getCarouselName());

        return queryWrapper;
    }


}
