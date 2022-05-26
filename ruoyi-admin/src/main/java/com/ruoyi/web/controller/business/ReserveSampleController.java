package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.ChefManagementDTO;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.dto.ReserveSampleGroupExportDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.ChefManagementPO;
import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.domain.vo.ChefManagementVO;
import com.diandong.domain.vo.ReserveSampleVO;
import com.diandong.mapstruct.ChefManagementMsMapper;
import com.diandong.mapstruct.ReserveSampleMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.ReserveSampleMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 预留样品Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/reserve_sample", tags = {"预留样品模块"})
@RequestMapping(value = "/reserve_sample")
public class ReserveSampleController extends BaseController {

    @Resource
    private ReserveSampleMpService reserveSampleMpService;
    @Resource
    private CanteenMpService canteenMpService;

    /**
     * 预留样品分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "预留样品分页查询", notes = "预留样品分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(ReserveSampleVO vo) {

        CanteenPO canteen = canteenMpService.lambdaQuery().eq(CanteenPO::getDeptId, SecurityUtils.getDeptId()).one();

        vo.setReserveCanteenId(canteen.getId());
        Page<ReserveSamplePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));


        return BaseResult.success(page);
    }

    /**
     * 预留样品根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "预留样品根据id查询", notes = "预留样品根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<ReserveSampleDTO> getById(@PathVariable("id") Long id) {
        ReserveSampleDTO dto = ReserveSampleMsMapper.INSTANCE
                .po2dto(reserveSampleMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 预留样品保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "预留样品保存", notes = "预留样品保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) ReserveSampleVO vo) {


        CanteenPO canteen = canteenMpService.lambdaQuery().eq(CanteenPO::getDeptId, SecurityUtils.getDeptId()).one();
        if (Objects.isNull(canteen)) {
            return BaseResult.error("您不是食堂管理人员，请勿操作");
        }

        ReserveSamplePO po = ReserveSampleMsMapper.INSTANCE.vo2po(vo);
        po.setReserveCanteenId(canteen.getId());
        boolean result = reserveSampleMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 预留样品更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "预留样品更新", notes = "预留样品更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) ReserveSampleVO vo) {
        ReserveSamplePO po = ReserveSampleMsMapper.INSTANCE.vo2po(vo);
        boolean result = reserveSampleMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 预留样品删除
     *
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "预留样品删除", notes = "预留样品删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = reserveSampleMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 预留样品分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "预留样品分页查询", notes = "预留样品分页查询方法", httpMethod = "GET")
    @GetMapping("/group")
    public BaseResult getGroupList(ReserveSampleVO vo) {
        Page<ReserveSamplePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        Page<ReserveSampleDTO> result = reserveSampleMpService.resetPage(page);
        return BaseResult.success(result);
    }

    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @ApiOperation(value = "食堂留样登记导出")
    @GetMapping("/export/canteen")
    public void exportCanteen(HttpServletResponse response, ReserveSampleVO vo) {

        List<Long> ids = vo.getIds();
        List<ReserveSamplePO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = reserveSampleMpService.lambdaQuery().in(ReserveSamplePO::getId, ids).list();
        } else {
            CanteenPO canteen = canteenMpService.lambdaQuery().eq(CanteenPO::getDeptId, SecurityUtils.getDeptId()).one();
            vo.setReserveCanteenId(canteen.getId());
            list = onSelectWhere(vo).list();
        }

        List<ReserveSampleDTO> exportList = reserveSampleMpService.changeDTO(list);

        ExcelUtil<ReserveSampleDTO> util = new ExcelUtil<ReserveSampleDTO>(ReserveSampleDTO.class);
        util.exportExcel(response, exportList, "留样登记");

    }

    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @ApiOperation(value = "集团留样登记导出")
    @GetMapping("/export/group")
    public void exportGroup(HttpServletResponse response, ReserveSampleVO vo) {

        List<Long> ids = vo.getIds();

        List<ReserveSamplePO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = reserveSampleMpService.lambdaQuery().in(ReserveSamplePO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }

        List<ReserveSampleDTO> exportList = reserveSampleMpService.changeDTO(list);
        List<ReserveSampleGroupExportDTO> resultList = new ArrayList<>();

        exportList.forEach(reserveSampleDTO -> {
            ReserveSampleGroupExportDTO exportDTO = new ReserveSampleGroupExportDTO();
            BeanUtils.copyProperties(reserveSampleDTO, exportDTO);
            resultList.add(exportDTO);
        });

        ExcelUtil<ReserveSampleGroupExportDTO> util = new ExcelUtil<ReserveSampleGroupExportDTO>(ReserveSampleGroupExportDTO.class);
        util.exportExcel(response, resultList, "留样登记");

    }


    private LambdaQueryChainWrapper<ReserveSamplePO> onSelectWhere(ReserveSampleVO vo) {

        LambdaQueryChainWrapper<ReserveSamplePO> queryWrapper = reserveSampleMpService.lambdaQuery().orderByDesc(ReserveSamplePO::getId);

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), ReserveSamplePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getReserveCanteenId()), ReserveSamplePO::getReserveCanteenId, vo.getReserveCanteenId())
                .eq(ObjectUtils.isNotEmpty(vo.getReserveDate()), ReserveSamplePO::getReserveDate, vo.getReserveDate())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimes()), ReserveSamplePO::getMealTimes, vo.getMealTimes())
                .eq(StringUtils.isNotBlank(vo.getFoodName()), ReserveSamplePO::getFoodName, vo.getFoodName())
                .eq(StringUtils.isNotBlank(vo.getReserveNum()), ReserveSamplePO::getReserveNum, vo.getReserveNum())
                .eq(ObjectUtils.isNotEmpty(vo.getStorageLocation()), ReserveSamplePO::getStorageLocation, vo.getStorageLocation())
                .eq(StringUtils.isNotBlank(vo.getReserveName()), ReserveSamplePO::getReserveName, vo.getReserveName())
                .eq(ObjectUtils.isNotEmpty(vo.getWarningDay()), ReserveSamplePO::getWarningDay, vo.getWarningDay())
                .eq(StringUtils.isNotBlank(vo.getRemark()), ReserveSamplePO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), ReserveSamplePO::getState, vo.getState());
        if (Objects.nonNull(vo.getStartTime()) && Objects.nonNull(vo.getEndTime())) {
            queryWrapper.between(ReserveSamplePO::getReserveDate, vo.getStartTime(), vo.getEndTime());
        }

        return queryWrapper;
    }


}
