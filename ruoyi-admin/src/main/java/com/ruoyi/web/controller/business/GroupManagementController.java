package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.GroupManagementDTO;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.vo.GroupManagementVO;
import com.diandong.mapstruct.GroupManagementMsMapper;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Api(value = "/groupManagement", tags = {"集团管理模块"})
@RequestMapping(value = "/groupManagement")
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
    public TableDataInfo<GroupManagementDTO> getList(GroupManagementVO vo) {
        startPage();
        List<GroupManagementPO> dataList = groupManagementMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), GroupManagementPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getGroupName()), GroupManagementPO::getGroupName, vo.getGroupName())
                .eq(ObjectUtils.isNotEmpty(vo.getContentName()), GroupManagementPO::getContentName, vo.getContentName())
                .eq(StringUtils.isNotBlank(vo.getContentPhone()), GroupManagementPO::getContentPhone, vo.getContentPhone())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteensAllowed()), GroupManagementPO::getCanteensAllowed, vo.getCanteensAllowed())
                .eq(StringUtils.isNotBlank(vo.getGroupAddress()), GroupManagementPO::getGroupAddress, vo.getGroupAddress())
                .eq(StringUtils.isNotBlank(vo.getBusinessLicense()), GroupManagementPO::getBusinessLicense, vo.getBusinessLicense())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), GroupManagementPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getRemark()), GroupManagementPO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getDataStatus()), GroupManagementPO::getDataStatus, vo.getDataStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), GroupManagementPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), GroupManagementPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), GroupManagementPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(GroupManagementMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
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

        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error("用户未登录，无法进行操作。请您重新登录");
        }


        GroupManagementPO po = GroupManagementMsMapper.INSTANCE.vo2po(vo);
//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());
        po.setCreateName(loginUser.getUsername());
        boolean result = groupManagementMpService.save(po);
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
    public BaseResult update(@Validated(Update.class) GroupManagementVO vo) {

//        判断更新状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        GroupManagementPO po = GroupManagementMsMapper.INSTANCE.vo2po(vo);
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = groupManagementMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = groupManagementMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
