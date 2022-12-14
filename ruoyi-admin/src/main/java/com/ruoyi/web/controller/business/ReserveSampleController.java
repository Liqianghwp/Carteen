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
 * ????????????Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/reserve_sample", tags = {"??????????????????"})
@RequestMapping(value = "/reserve_sample")
public class ReserveSampleController extends BaseController {

    @Resource
    private ReserveSampleMpService reserveSampleMpService;
    @Resource
    private CanteenMpService canteenMpService;

    /**
     * ????????????????????????
     *
     * @param vo ????????????
     * @return ??????????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "????????????")
    })
    @ApiOperation(value = "????????????????????????", notes = "??????????????????????????????", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(ReserveSampleVO vo) {

        CanteenPO canteen = canteenMpService.lambdaQuery().eq(CanteenPO::getId, SecurityUtils.getCanteenId()).one();

        if(Objects.isNull(canteen)){
            return BaseResult.error("?????????????????????????????????");
        }
        vo.setReserveCanteenId(canteen.getId());
        Page<ReserveSamplePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));


        return BaseResult.success(page);
    }

    /**
     * ??????????????????id??????
     *
     * @param id ??????id
     * @return ????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "??????id")
    })
    @ApiOperation(value = "??????????????????id??????", notes = "??????????????????id??????", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<ReserveSampleDTO> getById(@PathVariable("id") Long id) {
        ReserveSampleDTO dto = ReserveSampleMsMapper.INSTANCE
                .po2dto(reserveSampleMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * ??????????????????
     *
     * @param vo ????????????
     * @return ????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "????????????")
    })
    @ApiOperation(value = "??????????????????", notes = "??????????????????", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) ReserveSampleVO vo) {


        CanteenPO canteen = canteenMpService.lambdaQuery().eq(CanteenPO::getId, SecurityUtils.getCanteenId()).one();
        if (Objects.isNull(canteen)) {
            return BaseResult.error("??????????????????????????????????????????");
        }

        ReserveSamplePO po = ReserveSampleMsMapper.INSTANCE.vo2po(vo);
        po.setReserveCanteenId(canteen.getId());
        boolean result = reserveSampleMpService.save(po);
        if (result) {
            return BaseResult.successMsg("???????????????");
        } else {
            return BaseResult.error("???????????????");
        }
    }

    /**
     * ??????????????????
     *
     * @param vo ????????????
     * @return ????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "????????????")
    })
    @ApiOperation(value = "??????????????????", notes = "??????????????????", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@RequestBody @Validated(Update.class) ReserveSampleVO vo) {
        ReserveSamplePO po = ReserveSampleMsMapper.INSTANCE.vo2po(vo);
        boolean result = reserveSampleMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("????????????");
        } else {
            return BaseResult.error("????????????");
        }
    }

    /**
     * ??????????????????
     *
     * @param ids ??????id
     * @return ????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "??????id")
    })
    @ApiOperation(value = "??????????????????", notes = "??????????????????", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = reserveSampleMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("????????????");
        } else {
            return BaseResult.error("????????????");
        }
    }


    /**
     * ????????????????????????
     *
     * @param vo ????????????
     * @return ??????????????????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ReserveSampleVO", name = "vo", value = "????????????")
    })
    @ApiOperation(value = "????????????????????????", notes = "??????????????????????????????", httpMethod = "GET")
    @GetMapping("/group")
    public BaseResult getGroupList(ReserveSampleVO vo) {
        Page<ReserveSamplePO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        Page<ReserveSampleDTO> result = reserveSampleMpService.resetPage(page);
        return BaseResult.success(result);
    }

    /**
     * ??????
     *
     * @param response
     * @param vo
     */
    @ApiOperation(value = "????????????????????????")
    @PostMapping("/export/canteen")
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
        util.exportExcel(response, exportList, "????????????");

    }

    /**
     * ??????
     *
     * @param response
     * @param vo
     */
    @ApiOperation(value = "????????????????????????")
    @PostMapping("/export/group")
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
        util.exportExcel(response, resultList, "????????????");

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
