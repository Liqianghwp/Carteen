package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.HealthCertMsgMpService;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.domain.dto.HealthCertMsgDTO;
import com.diandong.domain.vo.HealthCertMsgVO;
import com.diandong.mapstruct.HealthCertMsgMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 健康证到期消息Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/health_cert_msg", tags = {"健康证到期消息模块"})
@RequestMapping(value = "/health_cert_msg")
public class HealthCertMsgController extends BaseController {

    @Resource
    private HealthCertMsgMpService healthCertMsgMpService;

    /**
     * 健康证到期消息分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "健康证到期消息分页查询", notes = "健康证到期消息分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<HealthCertMsgDTO> getList(HealthCertMsgVO vo) {
        startPage();
        List<HealthCertMsgPO> dataList = healthCertMsgMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), HealthCertMsgPO::getId, vo.getId())
                .eq(ObjectUtils.isNotEmpty(vo.getHealthCertId()), HealthCertMsgPO::getHealthCertId, vo.getHealthCertId())
                .eq(StringUtils.isNotBlank(vo.getName()), HealthCertMsgPO::getName, vo.getName())
                .eq(StringUtils.isNotBlank(vo.getPhone()), HealthCertMsgPO::getPhone, vo.getPhone())
                .eq(ObjectUtils.isNotEmpty(vo.getWarningTime()), HealthCertMsgPO::getWarningTime, vo.getWarningTime())
                .eq(StringUtils.isNotBlank(vo.getRemindDay()), HealthCertMsgPO::getRemindDay, vo.getRemindDay())
                .eq(ObjectUtils.isNotEmpty(vo.getState()), HealthCertMsgPO::getState, vo.getState())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(HealthCertMsgMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
    }

    /**
     * 健康证到期消息根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证到期消息根据id查询", notes = "健康证到期消息根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<HealthCertMsgDTO> getById(@PathVariable("id") Long id) {
        HealthCertMsgDTO dto = HealthCertMsgMsMapper.INSTANCE
                .po2dto(healthCertMsgMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 健康证到期消息保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证到期消息保存", notes = "健康证到期消息保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) HealthCertMsgVO vo) {
        HealthCertMsgPO po = HealthCertMsgMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertMsgMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 健康证到期消息更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HealthCertMsgVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "健康证到期消息更新", notes = "健康证到期消息更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) HealthCertMsgVO vo) {
        HealthCertMsgPO po = HealthCertMsgMsMapper.INSTANCE.vo2po(vo);
        boolean result = healthCertMsgMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 健康证到期消息删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "健康证到期消息删除", notes = "健康证到期消息删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = healthCertMsgMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 健康证到期消息批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "健康证到期消息批量删除", notes = "健康证到期消息批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = healthCertMsgMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
