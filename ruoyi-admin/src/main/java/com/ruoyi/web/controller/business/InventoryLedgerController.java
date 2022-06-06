package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.InventoryInboundVO;
import com.diandong.domain.vo.InventoryOutboundVO;
import com.diandong.mapstruct.InventoryInboundMsMapper;
import com.diandong.mapstruct.InventoryOutboundMsMapper;
import com.diandong.service.InventoryInboundMpService;
import com.diandong.service.InventoryOutboundMpService;
import com.diandong.service.RawMaterialMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.diandong.service.InventoryLedgerMpService;
import com.diandong.domain.dto.InventoryLedgerDTO;
import com.diandong.domain.vo.InventoryLedgerVO;
import com.diandong.mapstruct.InventoryLedgerMsMapper;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Resource
    private InventoryInboundMpService inventoryInboundMpService;
    @Resource
    private InventoryOutboundMpService inventoryOutboundMpService;

    @Resource
    private RawMaterialMpService rawMaterialMpService;


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
    public BaseResult getList( InventoryLedgerVO vo) {
        Page<InventoryLedgerPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        for (InventoryLedgerPO record : page.getRecords()) {
            //查询入库
            List<InventoryInboundPO> list = inventoryInboundMpService.lambdaQuery()
                    .eq(InventoryInboundPO::getLedgerId, record.getId())
                    .eq(InventoryInboundPO::getState, 1).list();
            //查询出库
            List<InventoryOutboundPO> list1 = inventoryOutboundMpService.lambdaQuery()
                    .eq(InventoryOutboundPO::getId, record.getId())
                    .eq(InventoryOutboundPO::getState, 1).list();
            //联查食堂id
            List<RawMaterialPO> list2 = rawMaterialMpService.lambdaQuery().eq(RawMaterialPO::getId, record.getCanteenId()).list();
            //实例化1
            String RawMaterialName = new String();
            //实例化2
            String unitName = new String();
            //遍历集合
            for (RawMaterialPO rawMaterialPO : list2) {
                //将原材料名称保存到实例化1
                RawMaterialName = rawMaterialPO.getRawMaterialName();
                //将单位名称保存到实例化2
                unitName = rawMaterialPO.getUnitName();
            }
            //金额设为0 ZERO
            BigDecimal totalPrice = BigDecimal.ZERO;
            //数量 为0
            Long total = 0L;
            //遍历入库集合
            for (InventoryInboundPO inventoryInboundPO : list) {
               //如果有入库则增加数量
                total += inventoryInboundPO.getNumber();
                //计算他的总金额
                totalPrice = totalPrice.add(inventoryInboundPO.getPrice().multiply(new BigDecimal(inventoryInboundPO.getNumber())));
            }
            //总金额除以总数量获得单价 保留小数点后两位 ROUND_HALF_UP    四舍五入
            totalPrice = totalPrice.divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
            //遍历出库
            for (InventoryOutboundPO inventoryOutboundPO : list1) {
                //出库 数量相减
                total -= inventoryOutboundPO.getNumber();
            }
            //保存原材料名称
            vo.setRawMaterialName(RawMaterialName);
            //保存单位名称
            vo.setUnitName(unitName);
            //保存库存
            vo.setRepertory(total);
            //保存单价
            vo.setUnitPrice(totalPrice);
        }
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

        List<InventoryInboundPO> list = inventoryInboundMpService.lambdaQuery()
                .eq(InventoryInboundPO::getLedgerId, dto.getId()).list();

        List<InventoryOutboundPO> list1 = inventoryOutboundMpService.lambdaQuery()
                .eq(InventoryOutboundPO::getLedgerId, dto.getId()).list();

        dto.setList1(list);
        dto.setList2(list1);

        return BaseResult.success(dto);
    }

    /**
     * 库存台账入库
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "InventoryLedgerVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "库存台账入库", notes = "库存台账入库", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) InventoryLedgerVO vo) {
        Boolean result = false;
        InventoryLedgerPO inventoryLedgerPO = inventoryLedgerMpService.lambdaQuery()
                .eq(InventoryLedgerPO::getCanteenId, SecurityUtils.getDeptId())
                .eq(InventoryLedgerPO::getCategoryId, vo.getCategoryId())
                .eq(InventoryLedgerPO::getRawMaterialName, vo.getRawMaterialName())
                .one();

         //判断库里是否有着条数据
        if (Objects.isNull(inventoryLedgerPO)) {
            inventoryLedgerPO = InventoryLedgerMsMapper.INSTANCE.vo2po(vo);
//            没有的话直接保存
            result = inventoryLedgerMpService.save(inventoryLedgerPO);
        }
        //实例化入库信息
        List<InventoryInboundVO> storage = vo.getStorage();
        //建立一个数组集合
        List<InventoryInboundPO> list = new ArrayList<>();
         //判断数据是否为空
        if (CollectionUtils.isNotEmpty(storage)) {
            //遍历集合
            for (InventoryInboundVO inventoryInboundVO : storage) {
                //关联ID
                inventoryInboundVO.setLedgerId(inventoryLedgerPO.getId());
                //计算总金额
                inventoryInboundVO.setAmount(inventoryInboundVO.getPrice().multiply(new BigDecimal(inventoryInboundVO.getNumber())));
//                vo->po
                InventoryInboundPO inventoryInboundPO = InventoryInboundMsMapper.INSTANCE.vo2po(inventoryInboundVO);
                //将数据保存到集合中
                list.add(inventoryInboundPO);
            }
            result = inventoryInboundMpService.saveBatch(list);
        }
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
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
    @ApiOperation(value = "库存台账出库", notes = "库存台账出库", httpMethod = "POST")
    @PostMapping(value = "/add")
    public BaseResult stockRemoval( @RequestBody @Validated(Insert.class) InventoryLedgerVO vo) {
        //查询原材料属性
        InventoryLedgerPO inventoryLedgerPO = inventoryLedgerMpService.lambdaQuery()
                .eq(InventoryLedgerPO::getCanteenId, SecurityUtils.getDeptId())
                .eq(InventoryLedgerPO::getCategoryId, vo.getCategoryId())
                .eq(InventoryLedgerPO::getRawMaterialName, vo.getRawMaterialName())
                .one();
        //判断库里是否有着条数据

        if (Objects.isNull(inventoryLedgerPO)) {

            //没有的话直接return
            return BaseResult.success("当前原材料不可出库");
        }
               //实例化出库
        List<InventoryOutboundVO> stockRemoval = vo.getStockRemoval();
          //建立一个数组进行保存数据
        List<InventoryOutboundPO> poList = new ArrayList<>();
         //遍历出库实例化
        for (InventoryOutboundVO inventoryOutboundVO : stockRemoval) {
            //关联id
            inventoryOutboundVO.setLedgerId(inventoryLedgerPO.getId());
            //算出总金额
            inventoryOutboundVO.setOutboundAmoun(
                    inventoryOutboundVO.getPrice() .multiply(new BigDecimal(inventoryOutboundVO.getNumber()))
            );
            //po->vo
            InventoryOutboundPO inventoryOutboundPO = InventoryOutboundMsMapper.INSTANCE.vo2po(inventoryOutboundVO);
            //将数据将数据进行保存到建立的数组中
            poList.add(inventoryOutboundPO);
        }

        //保存数据
        boolean result = inventoryOutboundMpService.saveOrUpdateBatch(poList);
        if (result) {
            return BaseResult.successMsg("出库成功！");
        } else {
            return BaseResult.error("出库失败！");
        }
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
    @ApiOperation(value = "库存台账入库出库", notes = "库存台账入库出库", httpMethod = "POST")
    @PostMapping(value = "/enterAddLeave")
    public BaseResult stock(@RequestBody @Validated(Insert.class) InventoryLedgerVO vo) {
         //入库
        InventoryLedgerPO inventoryLedgerPO = inventoryLedgerMpService.lambdaQuery()
                //查询部门id
                .eq(InventoryLedgerPO::getCanteenId, SecurityUtils.getDeptId())
                //查询原材料id
                .eq(InventoryLedgerPO::getCategoryId, vo.getCategoryId())
                //查询原材料
                .eq(InventoryLedgerPO::getRawMaterialName, vo.getRawMaterialName())

                .one();
        //判断这条数据是否为空
        if (Objects.isNull(inventoryLedgerPO)) {
            inventoryLedgerPO = InventoryLedgerMsMapper.INSTANCE.vo2po(vo);
             inventoryLedgerMpService.save(inventoryLedgerPO);
        }
        List<InventoryInboundVO> storage = vo.getStorage();
        if (CollectionUtils.isNotEmpty(storage)) {
            List<InventoryInboundPO> list = new ArrayList<>();
            for (InventoryInboundVO inventoryInboundVO : storage) {
                inventoryInboundVO.setLedgerId(inventoryLedgerPO.getId());
                inventoryInboundVO.setAmount(inventoryInboundVO.getPrice().multiply(new BigDecimal(inventoryInboundVO.getNumber())));
                InventoryInboundPO inventoryInboundPO = InventoryInboundMsMapper.INSTANCE.vo2po(inventoryInboundVO);
                list.add(inventoryInboundPO);
            }
             inventoryInboundMpService.saveBatch(list);
        }
        //出库
        List<InventoryOutboundVO> stockRemoval = vo.getStockRemoval();

        List<InventoryOutboundPO> poList = new ArrayList<>();

        for (InventoryOutboundVO inventoryOutboundVO : stockRemoval) {
            inventoryOutboundVO.setLedgerId(inventoryLedgerPO.getId());
            inventoryOutboundVO.setOutboundAmoun(
                    inventoryOutboundVO.getPrice() .multiply(new BigDecimal(inventoryOutboundVO.getNumber()))
            );

            InventoryOutboundPO inventoryOutboundPO = InventoryOutboundMsMapper.INSTANCE.vo2po(inventoryOutboundVO);

            poList.add(inventoryOutboundPO);
        }
        boolean result = inventoryOutboundMpService.saveOrUpdateBatch(poList);



        if (result) {
            return BaseResult.successMsg("直进直出成功！");
        } else {
            return BaseResult.error("直进直出失败");
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
