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
import com.diandong.service.PurchasePlanDetailMpService;
import com.diandong.domain.po.PurchasePlanDetailPO;
import com.diandong.domain.dto.PurchasePlanDetailDTO;
import com.diandong.domain.vo.PurchasePlanDetailVO;
import com.diandong.mapstruct.PurchasePlanDetailMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 采购计划单详情Controller
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Validated
@RestController
@Api(value = "/purchase_plan_detail", tags = {"采购计划单详情模块"})
@RequestMapping(value = "/purchase_plan_detail")
public class PurchasePlanDetailController extends BaseController {

    @Resource
    private PurchasePlanDetailMpService purchasePlanDetailMpService;

    /**
     * 采购计划单详情分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasePlanDetailVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "采购计划单详情分页查询", notes = "采购计划单详情分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(PurchasePlanDetailVO vo) {

        Page<PurchasePlanDetailPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 采购计划单详情根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购计划单详情根据id查询", notes = "采购计划单详情根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<PurchasePlanDetailDTO> getById(@PathVariable("id") Long id) {
        PurchasePlanDetailDTO dto = PurchasePlanDetailMsMapper.INSTANCE
                .po2dto(purchasePlanDetailMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 采购计划单详情保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasePlanDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "采购计划单详情保存", notes = "采购计划单详情保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) PurchasePlanDetailVO vo) {
        PurchasePlanDetailPO po = PurchasePlanDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = purchasePlanDetailMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 采购计划单详情更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasePlanDetailVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "采购计划单详情更新", notes = "采购计划单详情更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) PurchasePlanDetailVO vo) {
        PurchasePlanDetailPO po = PurchasePlanDetailMsMapper.INSTANCE.vo2po(vo);
        boolean result = purchasePlanDetailMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 采购计划单详情删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购计划单详情删除", notes = "采购计划单详情删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = purchasePlanDetailMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 采购计划单详情批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "采购计划单详情批量删除", notes = "采购计划单详情批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = purchasePlanDetailMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<PurchasePlanDetailPO> onSelectWhere(PurchasePlanDetailVO vo) {
        LambdaQueryChainWrapper<PurchasePlanDetailPO> queryWrapper = purchasePlanDetailMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), PurchasePlanDetailPO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getPlanId()), PurchasePlanDetailPO::getPlanId, vo.getPlanId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), PurchasePlanDetailPO::getCategoryId, vo.getCategoryId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCategoryName()), PurchasePlanDetailPO::getCategoryName, vo.getCategoryName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), PurchasePlanDetailPO::getRawMaterialId, vo.getRawMaterialId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialName()), PurchasePlanDetailPO::getRawMaterialName, vo.getRawMaterialName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUnitId()), PurchasePlanDetailPO::getUnitId, vo.getUnitId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getUnitName()), PurchasePlanDetailPO::getUnitName, vo.getUnitName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getLastPurchase()), PurchasePlanDetailPO::getLastPurchase, vo.getLastPurchase());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), PurchasePlanDetailPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCurrentPrice()), PurchasePlanDetailPO::getCurrentPrice, vo.getCurrentPrice());
           return queryWrapper;
    }


}
