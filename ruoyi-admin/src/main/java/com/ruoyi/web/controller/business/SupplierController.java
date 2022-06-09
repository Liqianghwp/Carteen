package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.SupplierDTO;
import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.vo.SupplierVO;
import com.diandong.mapstruct.SupplierMsMapper;
import com.diandong.service.SupplierMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ApiOperation(value = "供应商管理分页查询 白名单", notes = "供应商管理分页查询方法", httpMethod = "GET")
    @GetMapping()
    public BaseResult getList(SupplierVO vo) {
        vo.setIsBlack(Constants.WHITELIST);
        Page<SupplierPO> page = supplierMpService.onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
    public BaseResult save(@RequestBody @Validated(Insert.class) SupplierVO vo) {
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
    public BaseResult update(@RequestBody @Validated(Update.class) SupplierVO vo) {
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

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "=Long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "加入黑名单", notes = "移出黑名单", httpMethod = "PUT")
    @PutMapping(value = "/user/{id}")
    public BaseResult baseResult(@PathVariable("id") Long id) {
        boolean result = supplierMpService.changeBlack(id);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    @Log(title = "供应商导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierVO vo) {
        vo.setIsBlack(Constants.WHITELIST);
        List<SupplierDTO> exportList = supplierMpService.getExportList(vo);

        ExcelUtil<SupplierDTO> util = new ExcelUtil(SupplierDTO.class);
        util.exportExcel(response, exportList, "供应商管理");
    }


}
