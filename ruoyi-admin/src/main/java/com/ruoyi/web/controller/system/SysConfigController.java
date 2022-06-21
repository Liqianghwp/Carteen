package com.ruoyi.web.controller.system;

import com.diandong.domain.vo.SysConfigVO;
import com.diandong.enums.BizConfigEnum;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置 信息操作处理
 *
 * @author ruoyi
 */
@Api(value = "/system/config", tags = {"参数配置 信息操作处理"})
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    @Autowired
    private ISysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysConfig", name = "config", value = "")
    })
    @ApiOperation(value = "获取参数配置列表", notes = "获取参数配置列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "SysConfig", name = "config", value = "")
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        util.exportExcel(response, list, "参数数据");
    }

    /**
     * 根据参数编号获取详细信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "configId", value = "")
    })
    @ApiOperation(value = "根据参数编号获取详细信息", notes = "根据参数编号获取详细信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{configId}")
    public BaseResult getInfo(@PathVariable Long configId) {
        return BaseResult.success(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "configKey", value = "")
    })
    @ApiOperation(value = "根据参数键名查询参数值", notes = "根据参数键名查询参数值", httpMethod = "GET")
    @GetMapping(value = "/configKey/{configKey}")
    public BaseResult getConfigKey(@PathVariable String configKey) {
        return BaseResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysConfig", name = "config", value = "")
    })
    @ApiOperation(value = "新增参数配置", notes = "新增参数配置", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return BaseResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(getUsername());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysConfig", name = "config", value = "")
    })
    @ApiOperation(value = "修改参数配置", notes = "修改参数配置", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return BaseResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(getUsername());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "configIds", value = "")
    })
    @ApiOperation(value = "删除参数配置", notes = "删除参数配置", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public BaseResult remove(@PathVariable Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return success();
    }

    /**
     * 刷新参数缓存
     */
    @ApiOperation(value = "刷新参数缓存", notes = "刷新参数缓存", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public BaseResult refreshCache() {
        configService.resetConfigCache();
        return BaseResult.success();
    }

    @ApiOperation(value = "配置保存", notes = "配置保存", httpMethod = "POST")
//    @PreAuthorize("@ss.hasPermi('system:config:remove')")
//    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @PostMapping("/save_config")
    public BaseResult healthCertInvalid(@RequestBody SysConfigVO config) {

        String registerUser = BizConfigEnum.REGISTER_USER.key();
        String healthCertInvalid = BizConfigEnum.HEALTH_CERT_INVALID.key();
        String mealTimesInterval = BizConfigEnum.MEAL_TIMES_INTERVAL.key();
        String mealTimesShow = BizConfigEnum.MEAL_TIMES_SHOW.key();

        List<String> keys = new ArrayList<>();
        keys.add(registerUser);
        keys.add(healthCertInvalid);
        keys.add(mealTimesInterval);
        keys.add(mealTimesShow);

        List<SysConfig> listByKeys = configService.getListByKeys(keys);

        for (SysConfig sysConfig : listByKeys) {
            switch (BizConfigEnum.getConfigEnum(sysConfig.getConfigKey())) {
                case REGISTER_USER:
                    sysConfig.setConfigValue(config.getRegisterUser());
                    break;
                case HEALTH_CERT_INVALID:
                    sysConfig.setConfigValue(config.getHealthCertInvalid());
                    break;
                case MEAL_TIMES_INTERVAL:
                    sysConfig.setConfigValue(config.getMealTimesInterval());
                    break;
                case MEAL_TIMES_SHOW:
                    sysConfig.setConfigValue(config.getMeanTimesShow());
                    break;
                default:
                    break;
            }
            configService.updateConfig(sysConfig);
        }
        return BaseResult.success();
    }


}
