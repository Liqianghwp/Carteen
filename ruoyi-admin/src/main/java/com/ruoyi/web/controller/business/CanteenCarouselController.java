package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.CanteenCarouselDTO;
import com.diandong.domain.po.CanteenCarouselPO;
import com.diandong.domain.vo.CanteenCarouselVO;
import com.diandong.mapstruct.CanteenCarouselMsMapper;
import com.diandong.service.CanteenCarouselMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 食堂轮播图Controller
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Validated
@RestController
@Api(value = "/canteen_carousel", tags = {"食堂轮播图模块"})
@RequestMapping(value = "/canteen_carousel")
public class CanteenCarouselController extends BaseController {

    @Resource
    private CanteenCarouselMpService canteenCarouselMpService;

    /**
     * 食堂轮播图分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiOperation(value = "食堂轮播图分页查询", notes = "食堂轮播图分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(CanteenCarouselVO vo) {

        Page<CanteenCarouselPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 食堂轮播图根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "食堂轮播图根据id查询", notes = "食堂轮播图根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<CanteenCarouselDTO> getById(@PathVariable("id") Long id) {
        CanteenCarouselDTO dto = CanteenCarouselMsMapper.INSTANCE
                .po2dto(canteenCarouselMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 食堂轮播图保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiOperation(value = "食堂轮播图保存", notes = "食堂轮播图保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) CanteenCarouselVO vo) {
        vo.setCanteenId(SecurityUtils.getCanteenId());

        CanteenCarouselPO po = CanteenCarouselMsMapper.INSTANCE.vo2po(vo);
        boolean result = canteenCarouselMpService.save(po);
        if (result) {
            return BaseResult.success(po);
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 食堂轮播图更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiOperation(value = "食堂轮播图更新", notes = "食堂轮播图更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) CanteenCarouselVO vo) {
        CanteenCarouselPO po = CanteenCarouselMsMapper.INSTANCE.vo2po(vo);
        boolean result = canteenCarouselMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 食堂轮播图删除
     *
     * @param id 编号id
     * @return 返回结果
     */

    @ApiOperation(value = "食堂轮播图删除", notes = "食堂轮播图删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = canteenCarouselMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<CanteenCarouselPO> onSelectWhere(CanteenCarouselVO vo) {
        LambdaQueryChainWrapper<CanteenCarouselPO> queryWrapper = canteenCarouselMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), CanteenCarouselPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), CanteenCarouselPO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getCarouselPic()), CanteenCarouselPO::getCarouselPic, vo.getCarouselPic());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSort()), CanteenCarouselPO::getSort, vo.getSort());
        return queryWrapper;
    }


}
