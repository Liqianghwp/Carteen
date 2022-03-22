package com.diandong.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandong.dao.mp.SysUserMpMapper;
import com.diandong.pojo.po.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lingzhi
 * @date 2022/2/8
 */
@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {

    @Resource
    private SysUserMpMapper sysUserMpMapper;

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }

    @RequestMapping("/hello_world/mybatis_plus")
    public String testMybatisPlus(@RequestParam("userName") String userName) {
        QueryWrapper<SysUser>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        SysUser user = sysUserMpMapper.selectOne(queryWrapper);
        return "user is " + user.toString();
    }
}
