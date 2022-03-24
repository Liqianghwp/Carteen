package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 部门信息
 *
 * @author ruoyi
 */
@Api(value = "/system/dept", tags = {"部门信息"})
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDept", name = "dept", value = "")
    })
    @ApiOperation(value = "获取部门列表", notes = "获取部门列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public BaseResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return BaseResult.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "deptId", value = "")
    })
    @ApiOperation(value = "查询部门列表（排除节点）", notes = "查询部门列表（排除节点）", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public BaseResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return BaseResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "deptId", value = "")
    })
    @ApiOperation(value = "根据部门编号获取详细信息", notes = "根据部门编号获取详细信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public BaseResult getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return BaseResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDept", name = "dept", value = "")
    })
    @ApiOperation(value = "获取部门下拉树列表", notes = "获取部门下拉树列表", httpMethod = "GET")
    @GetMapping("/treeselect")
    public BaseResult treeselect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return BaseResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "roleId", value = "")
    })
    @ApiOperation(value = "加载对应角色部门列表树", notes = "加载对应角色部门列表树", httpMethod = "GET")
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public BaseResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        HashMap<String, Object> data = new HashMap<>();
        data.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        data.put("depts", deptService.buildDeptTreeSelect(depts));
        return BaseResult.success(data);
    }

    /**
     * 新增部门
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDept", name = "dept", value = "")
    })
    @ApiOperation(value = "新增部门", notes = "新增部门", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return BaseResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDept", name = "dept", value = "")
    })
    @ApiOperation(value = "修改部门", notes = "修改部门", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return BaseResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return BaseResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return BaseResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "deptId", value = "")
    })
    @ApiOperation(value = "删除部门", notes = "删除部门", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public BaseResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return BaseResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return BaseResult.error("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
