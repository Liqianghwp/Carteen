package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.PurchasingDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.PurchasingPO;
import com.diandong.domain.vo.PurchasingVO;
import com.diandong.mapstruct.PurchasingMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.PurchasingMpService;
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
import java.util.Objects;

/**
 * 采购清单管理Controller
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Validated
@RestController
@Api(value = "/purchasing", tags = {"采购清单管理模块"})
@RequestMapping(value = "/purchasing")
public class PurchasingController extends BaseController {

    @Resource
    private PurchasingMpService purchasingMpService;
    @Resource
    private CanteenMpService canteenMpService;

    /**
     * 采购清单管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PurchasingVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "采购清单管理分页查询", notes = "采购清单管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(PurchasingVO vo) {
        CanteenPO canteen = canteenMpService.getById(SecurityUtils.getCanteenId());
        if (Objects.isNull(canteen)) {
            return BaseResult.error("您没有权限进行采购");
        }
        vo.setGroupId(canteen.getPId());
        Page<PurchasingPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 采购清单管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "采购清单管理根据id查询", notes = "采购清单管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<PurchasingDTO> getById(@PathVariable("id") Long id) {
        PurchasingDTO dto = PurchasingMsMapper.INSTANCE
                .po2dto(purchasingMpService.getById(id));
        return BaseResult.success(dto);
    }


    private LambdaQueryChainWrapper<PurchasingPO> onSelectWhere(PurchasingVO vo) {
        LambdaQueryChainWrapper<PurchasingPO> queryWrapper = purchasingMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), PurchasingPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getGroupId()), PurchasingPO::getGroupId, vo.getGroupId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), PurchasingPO::getCategoryId, vo.getCategoryId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getCategoryName()), PurchasingPO::getCategoryName, vo.getCategoryName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), PurchasingPO::getRawMaterialId, vo.getRawMaterialId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getRawMaterialName()), PurchasingPO::getRawMaterialName, vo.getRawMaterialName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), PurchasingPO::getNumber, vo.getNumber());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUnitId()), PurchasingPO::getUnitId, vo.getUnitId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getUnitName()), PurchasingPO::getUnitName, vo.getUnitName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), PurchasingPO::getPrice, vo.getPrice());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getIsPurchase()), PurchasingPO::getIsPurchase, vo.getIsPurchase());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSubtotal()), PurchasingPO::getSubtotal, vo.getSubtotal());
        return queryWrapper;
    }


}
