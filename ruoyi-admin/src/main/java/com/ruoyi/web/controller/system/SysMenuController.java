package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@Api(value = "/system/menu", tags = {"菜单信息"})
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysMenu", name = "menu", value = "")
    })
    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public BaseResult list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return BaseResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "menuId", value = "")
    })
    @ApiOperation(value = "根据菜单编号获取详细信息", notes = "根据菜单编号获取详细信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public BaseResult getInfo(@PathVariable Long menuId) {
        return BaseResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysMenu", name = "menu", value = "")
    })
    @ApiOperation(value = "获取菜单下拉树列表", notes = "获取菜单下拉树列表", httpMethod = "GET")
    @GetMapping("/treeselect")
    public BaseResult treeselect(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return BaseResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "roleId", value = "")
    })
    @ApiOperation(value = "加载对应角色菜单列表树", notes = "加载对应角色菜单列表树", httpMethod = "GET")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public BaseResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        HashMap<String, Object> data = new HashMap<>();
        data.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        data.put("menus", menuService.buildMenuTreeSelect(menus));
        return BaseResult.success(data);
    }

    /**
     * 新增菜单
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysMenu", name = "menu", value = "")
    })
    @ApiOperation(value = "新增菜单", notes = "新增菜单", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return BaseResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return BaseResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysMenu", name = "menu", value = "")
    })
    @ApiOperation(value = "修改菜单", notes = "修改菜单", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return BaseResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return BaseResult.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return BaseResult.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "menuId", value = "")
    })
    @ApiOperation(value = "删除菜单", notes = "删除菜单", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public BaseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return BaseResult.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return BaseResult.error("菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }
}