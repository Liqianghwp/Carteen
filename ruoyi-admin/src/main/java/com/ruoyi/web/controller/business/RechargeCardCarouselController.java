package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.RechargeCardCarouselMpService;
import com.diandong.domain.po.RechargeCardCarouselPO;
import com.diandong.domain.dto.RechargeCardCarouselDTO;
import com.diandong.domain.vo.RechargeCardCarouselVO;
import com.diandong.mapstruct.RechargeCardCarouselMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

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
    public TableDataInfo<RechargeCardCarouselDTO> getList(RechargeCardCarouselVO vo) {
        startPage();
        List<RechargeCardCarouselPO> dataList = rechargeCardCarouselMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), RechargeCardCarouselPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getCarouselPic()), RechargeCardCarouselPO::getCarouselPic, vo.getCarouselPic())
                .eq(StringUtils.isNotBlank(vo.getCarouselName()), RechargeCardCarouselPO::getCarouselName, vo.getCarouselName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(RechargeCardCarouselMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
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
    public BaseResult save(@Validated(Insert.class) RechargeCardCarouselVO vo) {
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
    public BaseResult update(@Validated(Update.class) RechargeCardCarouselVO vo) {
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图删除", notes = "充值卡触摸屏轮播图删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = rechargeCardCarouselMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 充值卡触摸屏轮播图批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "充值卡触摸屏轮播图批量删除", notes = "充值卡触摸屏轮播图批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = rechargeCardCarouselMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
