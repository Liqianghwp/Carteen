package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.UserCheckExportDTO;
import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.domain.vo.UserCheckVO;
import com.diandong.service.FaceRecognitionMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname UserAuditController
 * @Description 用户审核模块模块
 * @Date 2022/5/24 9:06
 * @Created by YuLiu
 */
@Validated
@RestController
@Api(value = "/user_audit", tags = {"用户审核模块模块"})
@RequestMapping(value = "/user_audit")
public class UserAuditController extends BaseController {
    @Resource
    private ISysUserService userService;
    @Resource
    private FaceRecognitionMpService faceRecognitionMpService;

    @ApiOperation(value = "用户审核分页查询")
    @GetMapping
    public BaseResult getList(SysUser vo) {
        Page<SysUser> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        List<SysUser> records = page.getRecords();

        if (CollectionUtils.isNotEmpty(records)) {
            List<Long> collect = records.stream().map(SysUser::getUserId).collect(Collectors.toList());
            List<FaceRecognitionPO> list = faceRecognitionMpService.lambdaQuery()
                    .eq(FaceRecognitionPO::getDelFlag, Constants.DEL_NO)
                    .in(FaceRecognitionPO::getCreateBy, collect)
                    .list();
            records.forEach(sysUser -> {
                List<FaceRecognitionPO> collect1 = list.stream().filter(faceRecognitionPO -> faceRecognitionPO.getCreateBy() == sysUser.getUserId()).collect(Collectors.toList());
                sysUser.setFaceImage(CollectionUtils.isNotEmpty(collect1) ? collect1.get(0).getFacePicture() : null);
            });
        }

        return BaseResult.success(page);
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/{id}")
    public BaseResult getById(@PathVariable Long id) {
        SysUser byId = userService.lambdaQuery().eq(SysUser::getUserId, id).one();
        return BaseResult.success(byId);
    }

    @ApiOperation(value = "用户审核/用户重新提交审核")
    @PostMapping("/check")
    public BaseResult checkState(@Validated @RequestBody UserCheckVO vo) {


        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();

        String checkState = vo.getCheckState();
        updateWrapper.set(SysUser::getCheckState, checkState);
        switch (checkState) {
            case Constants.USER_CHECKED:
                updateWrapper.eq(SysUser::getUserId, SecurityUtils.getUserId());
                break;
            case Constants.USER_CHECK_YES:
                updateWrapper.eq(SysUser::getUserId, vo.getUserId());
                break;
            case Constants.USER_CHECK_NO:
                updateWrapper.eq(SysUser::getUserId, vo.getUserId());
                if (StringUtils.isBlank(vo.getRejectionReason())) {
                    return BaseResult.error("驳回原因不能为空");
                }
                updateWrapper.set(SysUser::getRejectionReason, vo.getRejectionReason());
                break;
            default:
                break;
        }
        boolean update = userService.update(updateWrapper);

        if (update) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }

    }

    @ApiOperation(value = "用户审核导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser vo) {
        List<Long> ids = vo.getIds();
        List<SysUser> userList;
        if (CollectionUtils.isNotEmpty(ids)) {
            userList = userService.lambdaQuery().in(SysUser::getUserId, ids).list();
        } else {
            userList = onSelectWhere(vo).list();
        }

        List<UserCheckExportDTO> exportList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(userList)) {
            for (SysUser user : userList) {
                UserCheckExportDTO exportDTO = new UserCheckExportDTO();
                BeanUtils.copyProperties(user, exportDTO);

                exportList.add(exportDTO);
            }
        }
        ExcelUtil<UserCheckExportDTO> util = new ExcelUtil(UserCheckExportDTO.class);
        util.exportExcel(response, exportList, "用户审核");


    }


    private LambdaQueryChainWrapper<SysUser> onSelectWhere(SysUser vo) {
        LambdaQueryChainWrapper<SysUser> queryWrapper = userService.lambdaQuery();
        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(SysUser::getDelFlag, Constants.DEL_NO)
                .like(StringUtils.isNotBlank(vo.getNickName()), SysUser::getNickName, vo.getNickName())
                .like(StringUtils.isNotBlank(vo.getPhonenumber()), SysUser::getPhonenumber, vo.getPhonenumber())
                .eq(StringUtils.isNotBlank(vo.getUserType()), SysUser::getUserType, vo.getUserType())
                .eq(StringUtils.isNotBlank(vo.getSex()), SysUser::getSex, vo.getSex())
                .eq(StringUtils.isNotBlank(vo.getStatus()), SysUser::getCheckState, vo.getCheckState());

        return queryWrapper;
    }

}
