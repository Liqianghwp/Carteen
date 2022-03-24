package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@Api(tags = {"登录验证"})
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "LoginBody", name = "loginBody", value = "登录信息")
    })
    @ApiOperation(value = "登录方法", notes = "登录方法", httpMethod = "POST")
    @PostMapping("/login")
    public BaseResult login(@RequestBody LoginBody loginBody) {
        HashMap<String, Object> data = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        data.put(Constants.TOKEN, token);
        return BaseResult.success(data);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/getInfo")
    public BaseResult getInfo() {

        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        HashMap<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("roles", roles);
        data.put("permissions", permissions);
        return BaseResult.success(data);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @ApiOperation(value = "获取路由信息", notes = "获取路由信息", httpMethod = "GET")
    @GetMapping("getRouters")
    public BaseResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return BaseResult.success(menuService.buildMenus(menus));
    }
}
