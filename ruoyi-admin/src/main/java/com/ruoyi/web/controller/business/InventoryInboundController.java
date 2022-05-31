package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.InventoryInboundDTO;
import com.diandong.domain.po.InventoryInboundPO;
import com.diandong.domain.vo.InventoryInboundVO;
import com.diandong.mapstruct.InventoryInboundMsMapper;
import com.diandong.service.InventoryInboundMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 入库Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/inventory_inbound", tags = {"入库模块"})
@RequestMapping(value = "/inventory_inbound")
public class InventoryInboundController extends BaseController {

    @Resource
    private InventoryInboundMpService inventoryInboundMpService;

    /**
     * 入库分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryInboundVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "入库分页查询", notes = "入库分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(InventoryInboundVO vo) {

        Page<InventoryInboundPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 入库根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "入库根据id查询", notes = "入库根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<InventoryInboundDTO> getById(@PathVariable("id") Long id) {
        InventoryInboundDTO dto = InventoryInboundMsMapper.INSTANCE
                .po2dto(inventoryInboundMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 入库保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryInboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "入库保存", notes = "入库保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) InventoryInboundVO vo) {
        InventoryInboundPO po = InventoryInboundMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryInboundMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 入库更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryInboundVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "入库更新", notes = "入库更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) InventoryInboundVO vo) {
        InventoryInboundPO po = InventoryInboundMsMapper.INSTANCE.vo2po(vo);
        boolean result = inventoryInboundMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 入库删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "入库删除", notes = "入库删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = inventoryInboundMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 入库批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "入库批量删除", notes = "入库批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = inventoryInboundMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<InventoryInboundPO> onSelectWhere(InventoryInboundVO vo) {
        LambdaQueryChainWrapper<InventoryInboundPO> queryWrapper = inventoryInboundMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), InventoryInboundPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getLedgerId()), InventoryInboundPO::getLedgerId, vo.getLedgerId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInbounder()), InventoryInboundPO::getInbounder, vo.getInbounder());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRawMaterialPic()), InventoryInboundPO::getRawMaterialPic, vo.getRawMaterialPic());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getNumber()), InventoryInboundPO::getNumber, vo.getNumber());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getPrice()), InventoryInboundPO::getPrice, vo.getPrice());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getInboundType()), InventoryInboundPO::getInboundType, vo.getInboundType());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getInboundName()), InventoryInboundPO::getInboundName, vo.getInboundName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAssociation()), InventoryInboundPO::getAssociation, vo.getAssociation());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getWarehouseId()), InventoryInboundPO::getWarehouseId, vo.getWarehouseId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getWarehouseName()), InventoryInboundPO::getWarehouseName, vo.getWarehouseName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getState()), InventoryInboundPO::getState, vo.getState());
           return queryWrapper;
    }


}
