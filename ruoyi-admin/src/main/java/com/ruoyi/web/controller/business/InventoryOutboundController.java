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
import com.diandong.service.InventoryOutboundMpService;
import com.diandong.domain.po.InventoryOutboundPO;
import com.diandong.domain.dto.InventoryOutboundDTO;
import com.diandong.domain.vo.InventoryOutboundVO;
import com.diandong.mapstruct.InventoryOutboundMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 出库Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/inventory_outbound", tags = {"出库模块"})
@RequestMapping(value = "/inventory_outbound")
public class InventoryOutboundController extends BaseController {

    @Resource
    private InventoryOutboundMpService inventoryOutboundMpService;

    /**
     * 出库分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryOutboundVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "出库分页查询", notes = "出库分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(InventoryOutboundVO vo) {

        Page<InventoryOutboundPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 出库根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "出库根据id查询", notes = "出库根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<InventoryOutboundDTO> getById(@PathVariable("id") Long id) {
        InventoryOutboundDTO dto = InventoryOutboundMsMapper.INSTANCE
                .po2dto(inventoryOutboundMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 出库保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryOutboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "出库保存", notes = "出库保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) InventoryOutboundVO vo) {
        InventoryOutboundPO po = InventoryOutboundMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryOutboundMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 出库更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryOutboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "出库更新", notes = "出库更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) InventoryOutboundVO vo) {
        InventoryOutboundPO po = InventoryOutboundMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryOutboundMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 出库删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "出库删除", notes = "出库删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = inventoryOutboundMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 出库批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "出库批量删除", notes = "出库批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = inventoryOutboundMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<InventoryOutboundPO> onSelectWhere(InventoryOutboundVO vo) {
        LambdaQueryChainWrapper<InventoryOutboundPO> queryWrapper = inventoryOutboundMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), InventoryOutboundPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getLedgerId()), InventoryOutboundPO::getLedgerId, vo.getLedgerId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRecipients()), InventoryOutboundPO::getRecipients, vo.getRecipients());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getWarehouseId()), InventoryOutboundPO::getWarehouseId, vo.getWarehouseId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getWarehouseName()), InventoryOutboundPO::getWarehouseName, vo.getWarehouseName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), InventoryOutboundPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getOutboundType()), InventoryOutboundPO::getOutboundType, vo.getOutboundType());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getOutboundName()), InventoryOutboundPO::getOutboundName, vo.getOutboundName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAssociation()), InventoryOutboundPO::getAssociation, vo.getAssociation());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), InventoryOutboundPO::getPrice, vo.getPrice());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialPic()), InventoryOutboundPO::getRawMaterialPic, vo.getRawMaterialPic());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), InventoryOutboundPO::getState, vo.getState());
           return queryWrapper;
    }


}
