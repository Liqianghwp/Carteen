package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author ruoyi
 */
@Api(value = "/system/post", tags = {"岗位信息操作处理"})
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    @Autowired
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysPost", name = "post", value = "")
    })
    @ApiOperation(value = "获取岗位列表", notes = "获取岗位列表", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    /**
     * 岗位管理
     * @param response
     * @param post
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "SysPost", name = "post", value = "")
    })
    @ApiOperation(value = "岗位管理", notes = "岗位管理", httpMethod = "POST")
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "postId", value = "")
    })
    @ApiOperation(value = "根据岗位编号获取详细信息", notes = "根据岗位编号获取详细信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public BaseResult getInfo(@PathVariable Long postId) {
        return BaseResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysPost", name = "post", value = "")
    })
    @ApiOperation(value = "新增岗位", notes = "新增岗位", httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return BaseResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return BaseResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysPost", name = "post", value = "")
    })
    @ApiOperation(value = "修改岗位", notes = "修改岗位", httpMethod = "PUT")
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return BaseResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return BaseResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "postIds", value = "")
    })
    @ApiOperation(value = "删除岗位", notes = "删除岗位", httpMethod = "DELETE")
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public BaseResult remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @ApiOperation(value = "获取岗位选择框列表", notes = "获取岗位选择框列表", httpMethod = "GET")
    @GetMapping("/optionselect")
    public BaseResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return BaseResult.success(posts);
    }
}
