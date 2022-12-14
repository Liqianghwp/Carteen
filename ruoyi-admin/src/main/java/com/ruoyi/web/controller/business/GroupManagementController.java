package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.GroupManagementDTO;
import com.diandong.domain.dto.HealthCertificateDTO;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.domain.vo.GroupManagementVO;
import com.diandong.domain.vo.HealthCertificateVO;
import com.diandong.mapstruct.GroupManagementMsMapper;
import com.diandong.mapstruct.HealthCertificateMsMapper;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/group_management", tags = {"集团管理模块"})
@RequestMapping(value = "/group_management")
public class GroupManagementController extends BaseController {

    @Resource
    private GroupManagementMpService groupManagementMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "GroupManagementVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(GroupManagementVO vo) {
        Page<GroupManagementPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<GroupManagementDTO> getById(@PathVariable("id") Long id) {
        GroupManagementDTO dto = GroupManagementMsMapper.INSTANCE
                .po2dto(groupManagementMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "GroupManagementVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) GroupManagementVO vo) {

        boolean result = groupManagementMpService.saveGroupManagement(vo);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "GroupManagementVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) GroupManagementVO vo) {

//        判断更新状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        GroupManagementPO po = GroupManagementMsMapper.INSTANCE.vo2po(vo);
        po.setUpdateBy(loginUser.getUserId());
        boolean result = groupManagementMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = groupManagementMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @Log(title = "集团管理导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GroupManagementVO vo) {
        List<Long> ids = vo.getIds();

        List<GroupManagementPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = groupManagementMpService.lambdaQuery().in(GroupManagementPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<GroupManagementDTO> groupManagementList = new ArrayList<>();

        list.forEach(groupManagementPO -> {
            groupManagementList.add(GroupManagementMsMapper.INSTANCE.po2dto(groupManagementPO));
        });

        ExcelUtil<GroupManagementDTO> util = new ExcelUtil<GroupManagementDTO>(GroupManagementDTO.class);
        util.exportExcel(response, groupManagementList, "集团管理");
    }


    private LambdaQueryChainWrapper<GroupManagementPO> onSelectWhere(GroupManagementVO vo) {

        LambdaQueryChainWrapper<GroupManagementPO> queryWrapper = groupManagementMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), GroupManagementPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getGroupName()), GroupManagementPO::getGroupName, vo.getGroupName())
                .eq(ObjectUtils.isNotEmpty(vo.getContentName()), GroupManagementPO::getContentName, vo.getContentName())
                .eq(StringUtils.isNotBlank(vo.getContentPhone()), GroupManagementPO::getContentPhone, vo.getContentPhone())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteensAllowed()), GroupManagementPO::getCanteensAllowed, vo.getCanteensAllowed())
                .eq(StringUtils.isNotBlank(vo.getGroupAddress()), GroupManagementPO::getGroupAddress, vo.getGroupAddress())
                .eq(StringUtils.isNotBlank(vo.getBusinessLicense()), GroupManagementPO::getBusinessLicense, vo.getBusinessLicense())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), GroupManagementPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getRemark()), GroupManagementPO::getRemark, vo.getRemark());

        return queryWrapper;
    }

}
