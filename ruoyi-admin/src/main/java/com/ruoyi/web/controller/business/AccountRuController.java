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
import com.diandong.service.AccountRuMpService;
import com.diandong.domain.po.AccountRuPO;
import com.diandong.domain.dto.AccountRuDTO;
import com.diandong.domain.vo.AccountRuVO;
import com.diandong.mapstruct.AccountRuMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Validated
@RestController
@Api(value = "/account_ru", tags = {"模块"})
@RequestMapping(value = "/account_ru")
public class AccountRuController extends BaseController {

    @Resource
    private AccountRuMpService accountRuMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountRuVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(AccountRuVO vo) {

        Page<AccountRuPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<AccountRuDTO> getById(@PathVariable("id") Long id) {
        AccountRuDTO dto = AccountRuMsMapper.INSTANCE
                .po2dto(accountRuMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountRuVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) AccountRuVO vo) {
        AccountRuPO po = AccountRuMsMapper.INSTANCE.vo2po(vo);
        boolean result = accountRuMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountRuVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) AccountRuVO vo) {
        AccountRuPO po = AccountRuMsMapper.INSTANCE.vo2po(vo);
        boolean result = accountRuMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = accountRuMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = accountRuMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<AccountRuPO> onSelectWhere(AccountRuVO vo) {
        LambdaQueryChainWrapper<AccountRuPO> queryWrapper = accountRuMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAccountBookId()), AccountRuPO::getAccountBookId, vo.getAccountBookId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), AccountRuPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getStorageTime()), AccountRuPO::getStorageTime, vo.getStorageTime());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getSupplier()), AccountRuPO::getSupplier, vo.getSupplier());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getInventoryQuantity()), AccountRuPO::getInventoryQuantity, vo.getInventoryQuantity());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAggregate()), AccountRuPO::getAggregate, vo.getAggregate());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getTally()), AccountRuPO::getTally, vo.getTally());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getWarehousePeople()), AccountRuPO::getWarehousePeople, vo.getWarehousePeople());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAggregateAmount()), AccountRuPO::getAggregateAmount, vo.getAggregateAmount());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getPurchaseOrderNumber()), AccountRuPO::getPurchaseOrderNumber, vo.getPurchaseOrderNumber());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getPictureRu()), AccountRuPO::getPictureRu, vo.getPictureRu());
           return queryWrapper;
    }


}
