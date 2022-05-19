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
import com.diandong.service.SupplierMpService;
import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.dto.SupplierDTO;
import com.diandong.domain.vo.SupplierVO;
import com.diandong.mapstruct.SupplierMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 供应商管理Controller
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Validated
@RestController
@Api(value = "/supplier", tags = {"供应商管理模块"})
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController {

    @Resource
    private SupplierMpService supplierMpService;

    /**
     * 供应商管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SupplierVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "供应商管理分页查询", notes = "供应商管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(SupplierVO vo) {

        Page<SupplierPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 供应商管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "供应商管理根据id查询", notes = "供应商管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<SupplierDTO> getById(@PathVariable("id") Long id) {
        SupplierDTO dto = SupplierMsMapper.INSTANCE
                .po2dto(supplierMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 供应商管理保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SupplierVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "供应商管理保存", notes = "供应商管理保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) SupplierVO vo) {
        SupplierPO po = SupplierMsMapper.INSTANCE.vo2po(vo);
        boolean result = supplierMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 供应商管理更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SupplierVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "供应商管理更新", notes = "供应商管理更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) SupplierVO vo) {
        SupplierPO po = SupplierMsMapper.INSTANCE.vo2po(vo);
        boolean result = supplierMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 供应商管理删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "供应商管理删除", notes = "供应商管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = supplierMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 供应商管理批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "供应商管理批量删除", notes = "供应商管理批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = supplierMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<SupplierPO> onSelectWhere(SupplierVO vo) {
        LambdaQueryChainWrapper<SupplierPO> queryWrapper = supplierMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), SupplierPO::getId, vo.getId());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getSupplierName()), SupplierPO::getSupplierName, vo.getSupplierName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getAccount()), SupplierPO::getAccount, vo.getAccount());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getContactName()), SupplierPO::getContactName, vo.getContactName());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getContactPhone()), SupplierPO::getContactPhone, vo.getContactPhone());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getIsBlack()), SupplierPO::getIsBlack, vo.getIsBlack());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getMoveTime()), SupplierPO::getMoveTime, vo.getMoveTime());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), SupplierPO::getRemark, vo.getRemark());
           return queryWrapper;
    }


}
