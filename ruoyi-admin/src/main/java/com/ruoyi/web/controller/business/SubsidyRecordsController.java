package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.domain.vo.SysUserVO;
import com.diandong.mapstruct.CanteenMsMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.SubsidyRecordsMpService;
import com.diandong.domain.po.SubsidyRecordsPO;
import com.diandong.domain.dto.SubsidyRecordsDTO;
import com.diandong.domain.vo.SubsidyRecordsVO;
import com.diandong.mapstruct.SubsidyRecordsMsMapper;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 补贴记录Controller
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Validated
@RestController
@Api(value = "/subsidy_records", tags = {"补贴记录模块"})
@RequestMapping(value = "/subsidy_records")
public class SubsidyRecordsController extends BaseController {

    @Resource
    private SubsidyRecordsMpService subsidyRecordsMpService;
    @Resource
    private ISysUserService userService;

    /**
     * 补贴记录分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SubsidyRecordsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "补贴记录分页查询", notes = "补贴记录分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(SubsidyRecordsVO vo) {

        Page<SubsidyRecordsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 补贴记录根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "补贴记录根据id查询", notes = "补贴记录根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<SubsidyRecordsDTO> getById(@PathVariable("id") Long id) {
        SubsidyRecordsDTO dto = SubsidyRecordsMsMapper.INSTANCE
                .po2dto(subsidyRecordsMpService.getById(id));
        return BaseResult.success(dto);
    }


    @ApiOperation(value = "新增补贴发放获取用户列表")
    @GetMapping("/userList")
    public BaseResult getUserList(SysUserVO sysUserVO) {

        return subsidyRecordsMpService.getUserList(sysUserVO);
    }


    /**
     * 补贴记录保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SubsidyRecordsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "补贴记录保存", notes = "补贴记录保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) SubsidyRecordsVO vo) {
        return subsidyRecordsMpService.saveSubsidy(vo);
    }


    /**
     * 补贴记录删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "补贴记录删除", notes = "补贴记录删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") String ids) {

        boolean result = subsidyRecordsMpService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Log(title = "", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubsidyRecordsVO vo) {
        List<Long> ids = vo.getIds();

        List<SubsidyRecordsPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = subsidyRecordsMpService.lambdaQuery().in(SubsidyRecordsPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<SubsidyRecordsDTO> subsidyRecordsList = new ArrayList<>();

        for (SubsidyRecordsPO subsidyRecordsPO : list) {
            SubsidyRecordsDTO subsidyRecordsDTO = SubsidyRecordsMsMapper.INSTANCE.po2dto(subsidyRecordsPO);

            SysUser sysUser = userService.selectUserById(subsidyRecordsDTO.getCreateBy());
            subsidyRecordsDTO.setCreateName(sysUser.getNickName());
            subsidyRecordsList.add(subsidyRecordsDTO);
        }

        ExcelUtil<SubsidyRecordsDTO> util = new ExcelUtil<SubsidyRecordsDTO>(SubsidyRecordsDTO.class);
        util.exportExcel(response, subsidyRecordsList, "补贴发放");
    }


    private LambdaQueryChainWrapper<SubsidyRecordsPO> onSelectWhere(SubsidyRecordsVO vo) {
        LambdaQueryChainWrapper<SubsidyRecordsPO> queryWrapper = subsidyRecordsMpService.lambdaQuery()
                .orderByDesc(SubsidyRecordsPO::getId);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), SubsidyRecordsPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserType()), SubsidyRecordsPO::getUserType, vo.getUserType());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getUserIds()), SubsidyRecordsPO::getUserIds, vo.getUserIds());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAmount()), SubsidyRecordsPO::getAmount, vo.getAmount());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getType()), SubsidyRecordsPO::getType, vo.getType());
        return queryWrapper;
    }


}
