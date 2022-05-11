package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.ReserveSampleMpService;
import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.vo.ReserveSampleVO;
import com.diandong.mapstruct.ReserveSampleMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

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
    public TableDataInfo<ReserveSampleDTO> getList(ReserveSampleVO vo) {
        startPage();
        List<ReserveSamplePO> dataList = reserveSampleMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), ReserveSamplePO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getReserveDate()), ReserveSamplePO::getReserveDate, vo.getReserveDate())
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimes()), ReserveSamplePO::getMealTimes, vo.getMealTimes())
                .eq(StringUtils.isNotBlank(vo.getFoodName()), ReserveSamplePO::getFoodName, vo.getFoodName())
                .eq(StringUtils.isNotBlank(vo.getReserveNum()), ReserveSamplePO::getReserveNum, vo.getReserveNum())
                .eq(ObjectUtils.isNotEmpty(vo.getStorageLocation()), ReserveSamplePO::getStorageLocation, vo.getStorageLocation())
                .eq(StringUtils.isNotBlank(vo.getReserveName()), ReserveSamplePO::getReserveName, vo.getReserveName())
                .eq(ObjectUtils.isNotEmpty(vo.getWarningDay()), ReserveSamplePO::getWarningDay, vo.getWarningDay())
                .eq(StringUtils.isNotBlank(vo.getRemark()), ReserveSamplePO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), ReserveSamplePO::getState, vo.getState())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(ReserveSampleMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
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
    public BaseResult save(@Validated(Insert.class) ReserveSampleVO vo) {
        ReserveSamplePO po = ReserveSampleMsMapper.INSTANCE.vo2po(vo);
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
    public BaseResult update(@Validated(Update.class) ReserveSampleVO vo) {
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
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "预留样品删除", notes = "预留样品删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = reserveSampleMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 预留样品批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "预留样品批量删除", notes = "预留样品批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = reserveSampleMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
