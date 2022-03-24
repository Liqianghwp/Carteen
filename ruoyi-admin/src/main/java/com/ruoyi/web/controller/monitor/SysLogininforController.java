package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.service.ISysLogininforService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统访问记录
 *
 * @author ruoyi
 */
@Api(value = "/monitor/logininfor", tags = {"系统访问记录"})
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
    @Autowired
    private ISysLogininforService logininforService;

    /**
     * 查询登录日志
     * @param logininfor
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysLogininfor", name = "logininfor", value = "")
    })
    @ApiOperation(value = "查询登录日志", notes = "查询登录日志", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    /**
     * 导出登陆日志
     * @param response
     * @param logininfor
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "SysLogininfor", name = "logininfor", value = "")
    })
    @ApiOperation(value = "导出登陆日志", notes = "导出登陆日志", httpMethod = "POST")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    /**
     * 删除登录日志
     * @param infoIds
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "infoIds", value = "")
    })
    @ApiOperation(value = "删除登录日志", notes = "删除登录日志", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public BaseResult remove(@PathVariable Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    /**
     * 清除登录日志
     * @return
     */
    @ApiOperation(value = "清除登录日志", notes = "清除登录日志", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public BaseResult clean() {
        logininforService.cleanLogininfor();
        return BaseResult.success();
    }
}
