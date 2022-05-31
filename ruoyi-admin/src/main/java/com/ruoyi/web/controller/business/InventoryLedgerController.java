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
import com.diandong.service.InventoryLedgerMpService;
import com.diandong.domain.po.InventoryLedgerPO;
import com.diandong.domain.dto.InventoryLedgerDTO;
import com.diandong.domain.vo.InventoryLedgerVO;
import com.diandong.mapstruct.InventoryLedgerMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 库存台账Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/inventory_ledger", tags = {"库存台账模块"})
@RequestMapping(value = "/inventory_ledger")
public class InventoryLedgerController extends BaseController {

    @Resource
    private InventoryLedgerMpService inventoryLedgerMpService;

    /**
     * 库存台账分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryLedgerVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "库存台账分页查询", notes = "库存台账分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(InventoryLedgerVO vo) {

        Page<InventoryLedgerPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 库存台账根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "库存台账根据id查询", notes = "库存台账根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<InventoryLedgerDTO> getById(@PathVariable("id") Long id) {
        InventoryLedgerDTO dto = InventoryLedgerMsMapper.INSTANCE
                .po2dto(inventoryLedgerMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 库存台账保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryLedgerVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "库存台账保存", notes = "库存台账保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) InventoryLedgerVO vo) {
        InventoryLedgerPO po = InventoryLedgerMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryLedgerMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 库存台账更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryLedgerVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "库存台账更新", notes = "库存台账更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) InventoryLedgerVO vo) {
        InventoryLedgerPO po = InventoryLedgerMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryLedgerMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 库存台账删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "库存台账删除", notes = "库存台账删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = inventoryLedgerMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 库存台账批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "库存台账批量删除", notes = "库存台账批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = inventoryLedgerMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<InventoryLedgerPO> onSelectWhere(InventoryLedgerVO vo) {
        LambdaQueryChainWrapper<InventoryLedgerPO> queryWrapper = inventoryLedgerMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), InventoryLedgerPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), InventoryLedgerPO::getCanteenId, vo.getCanteenId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCanteenName()), InventoryLedgerPO::getCanteenName, vo.getCanteenName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getCategoryId()), InventoryLedgerPO::getCategoryId, vo.getCategoryId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getCategoryName()), InventoryLedgerPO::getCategoryName, vo.getCategoryName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getRawMaterialId()), InventoryLedgerPO::getRawMaterialId, vo.getRawMaterialId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialName()), InventoryLedgerPO::getRawMaterialName, vo.getRawMaterialName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUnitId()), InventoryLedgerPO::getUnitId, vo.getUnitId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getUnitName()), InventoryLedgerPO::getUnitName, vo.getUnitName());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getMinStockWarning()), InventoryLedgerPO::getMinStockWarning, vo.getMinStockWarning());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getMaxStockWarning()), InventoryLedgerPO::getMaxStockWarning, vo.getMaxStockWarning());
           return queryWrapper;
    }


}
