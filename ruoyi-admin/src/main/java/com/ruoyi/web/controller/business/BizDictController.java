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
import com.diandong.service.BizDictMpService;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.dto.BizDictDTO;
import com.diandong.domain.vo.BizDictVO;
import com.diandong.mapstruct.BizDictMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 业务字典Controller
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@Validated
@RestController
@Api(value = "/biz_dict", tags = {"业务字典模块"})
@RequestMapping(value = "/biz_dict}")
public class BizDictController extends BaseController {

    @Resource
    private BizDictMpService bizDictMpService;

    /**
     * 业务字典分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BizDictVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "业务字典分页查询", notes = "业务字典分页查询方法", httpMethod = "GET")
    @GetMapping("/{code}")
    public BaseResult getList(BizDictVO vo, @PathVariable String code) {

        Page<BizDictPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 业务字典根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "业务字典根据id查询", notes = "业务字典根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{code}/{id}")
    public BaseResult<BizDictDTO> getById(@PathVariable("id") Long id, @PathVariable String code) {
        BizDictDTO dto = BizDictMsMapper.INSTANCE
                .po2dto(bizDictMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 业务字典保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BizDictVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "业务字典保存", notes = "业务字典保存", httpMethod = "POST")
    @PostMapping("/{code}")
    public BaseResult save(@RequestBody @Validated(Insert.class) BizDictVO vo, @PathVariable String code) {
        return bizDictMpService.saveBizDict(vo);
    }

    /**
     * 业务字典更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BizDictVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "业务字典更新", notes = "业务字典更新", httpMethod = "PUT")
    @PutMapping("/{code}")
    public BaseResult update(@Validated(Update.class) BizDictVO vo) {
        BizDictPO po = BizDictMsMapper.INSTANCE.vo2po(vo);
        boolean result = bizDictMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 业务字典删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "业务字典删除", notes = "业务字典删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = bizDictMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    private LambdaQueryChainWrapper<BizDictPO> onSelectWhere(BizDictVO vo) {
        LambdaQueryChainWrapper<BizDictPO> queryWrapper = bizDictMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), BizDictPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDictSort()), BizDictPO::getDictSort, vo.getDictSort());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDictLabel()), BizDictPO::getDictLabel, vo.getDictLabel());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDictValue()), BizDictPO::getDictValue, vo.getDictValue());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDictType()), BizDictPO::getDictType, vo.getDictType());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getStatus()), BizDictPO::getStatus, vo.getStatus());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getGroupId()), BizDictPO::getGroupId, vo.getGroupId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), BizDictPO::getCanteenId, vo.getCanteenId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getBeginTime()), BizDictPO::getBeginTime, vo.getBeginTime());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getEndTime()), BizDictPO::getEndTime, vo.getEndTime());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUsed()), BizDictPO::getUsed, vo.getUsed());
        return queryWrapper;
    }


}
