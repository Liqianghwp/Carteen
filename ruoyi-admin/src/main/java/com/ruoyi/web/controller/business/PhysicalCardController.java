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
import com.diandong.service.PhysicalCardMpService;
import com.diandong.domain.po.PhysicalCardPO;
import com.diandong.domain.dto.PhysicalCardDTO;
import com.diandong.domain.vo.PhysicalCardVO;
import com.diandong.mapstruct.PhysicalCardMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 实物卡Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/physical_card", tags = {"实物卡模块"})
@RequestMapping(value = "/physical_card")
public class PhysicalCardController extends BaseController {

    @Resource
    private PhysicalCardMpService physicalCardMpService;

    /**
     * 实物卡分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PhysicalCardVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "实物卡分页查询", notes = "实物卡分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(PhysicalCardVO vo) {

        Page<PhysicalCardPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 实物卡根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "实物卡根据id查询", notes = "实物卡根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<PhysicalCardDTO> getById(@PathVariable("id") Long id) {
        PhysicalCardDTO dto = PhysicalCardMsMapper.INSTANCE
                .po2dto(physicalCardMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 实物卡保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PhysicalCardVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "实物卡保存", notes = "实物卡保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) PhysicalCardVO vo) {
        PhysicalCardPO po = PhysicalCardMsMapper.INSTANCE.vo2po(vo);
        boolean result = physicalCardMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 实物卡更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PhysicalCardVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "实物卡更新", notes = "实物卡更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) PhysicalCardVO vo) {
        PhysicalCardPO po = PhysicalCardMsMapper.INSTANCE.vo2po(vo);
        boolean result = physicalCardMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 实物卡删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "实物卡删除", notes = "实物卡删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = physicalCardMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 实物卡批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "实物卡批量删除", notes = "实物卡批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = physicalCardMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<PhysicalCardPO> onSelectWhere(PhysicalCardVO vo) {
        LambdaQueryChainWrapper<PhysicalCardPO> queryWrapper = physicalCardMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), PhysicalCardPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserId()), PhysicalCardPO::getUserId, vo.getUserId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCardNo()), PhysicalCardPO::getCardNo, vo.getCardNo());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getState()), PhysicalCardPO::getState, vo.getState());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getReportLoss()), PhysicalCardPO::getReportLoss, vo.getReportLoss());
           return queryWrapper;
    }


}
