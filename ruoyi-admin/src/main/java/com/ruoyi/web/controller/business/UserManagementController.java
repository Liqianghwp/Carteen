package com.ruoyi.web.controller.business;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.vo.BackstageRechargeVO;
import com.diandong.domain.vo.PhysicalCardVO;
import com.diandong.domain.vo.SysUserVO;
import com.diandong.service.IUserManagementService;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Classname UserManagementController
 * @Description 用户管理
 * @Date 2022/5/31 11:35
 * @Created by YuLiu
 */

@Validated
@RestController
@Api(value = "/user_management", tags = {"用户管理模块模块"})
@RequestMapping(value = "/user_management")
public class UserManagementController {

    @Resource
    private IUserManagementService userManagementService;

    /**
     * 用户列表
     *
     * @return
     */
    @ApiOperation("用户列表")
    @GetMapping
    public BaseResult pageList(SysUserVO vo) {
        return userManagementService.pageList(vo);
    }

    @ApiOperation(value = "根据用户id查询用户信息")
    @GetMapping("/{userId}")
    public BaseResult getById(@PathVariable String userId) {
        return userManagementService.getById(userId);
    }


    @ApiOperation(value = "保存用户信息")
    @PostMapping
    public BaseResult saveUser(@RequestBody @Validated(Insert.class) SysUserVO vo) {
        Boolean result = userManagementService.saveUser(vo);
        if (!result) {
            return BaseResult.error("新增用户失败");
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "编辑修改用户信息")
    @PutMapping
    public BaseResult updateUser(@RequestBody @Validated(Update.class) SysUserVO vo) {


        Boolean result = userManagementService.updateUser(vo);
        if (!result) {
            return BaseResult.error("更新用户失败");
        }

        return BaseResult.success();
    }


    /**
     * 绑定卡片
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "绑定卡号")
    @PostMapping("/bindCard")
    public BaseResult bindCard(@RequestBody @Validated(Insert.class) PhysicalCardVO vo) {
        return userManagementService.bindCard(vo);
    }

    /**
     * 后台充值
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "充值")
    @PostMapping("/recharge")
    public BaseResult recharge(@RequestBody @Validated(Insert.class) BackstageRechargeVO vo) {

        if (Objects.isNull(vo.getAmount()) && Objects.isNull(vo.getTimes())) {
            return BaseResult.error("充值失败");
        }
        return userManagementService.recharge(vo);
    }


}
