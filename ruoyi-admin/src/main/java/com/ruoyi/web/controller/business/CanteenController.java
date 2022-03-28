package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.mapstruct.CanteenMsMapper;
import com.diandong.service.CanteenMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

/**
 * Controller
 *  食堂设置接口信息
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/canteen", tags = {"食堂设置"})
@RequestMapping(value = "/canteen")
public class CanteenController extends BaseController {

    @Resource
    private CanteenMpService canteenMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CanteenVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<CanteenDTO> getList(CanteenVO vo) {
        startPage();
        List<CanteenPO> dataList = canteenMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), CanteenPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), CanteenPO::getCanteenName, vo.getCanteenName())
                .eq(StringUtils.isNotBlank(vo.getStatus()), CanteenPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getCanteenPicture()), CanteenPO::getCanteenPicture, vo.getCanteenPicture())
                .eq(StringUtils.isNotBlank(vo.getCanteenIntroduce()), CanteenPO::getCanteenIntroduce, vo.getCanteenIntroduce())
                .eq(StringUtils.isNotBlank(vo.getUuid()), CanteenPO::getUuid, vo.getUuid())
                .eq(StringUtils.isNotBlank(vo.getPuuid()), CanteenPO::getPuuid, vo.getPuuid())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(CanteenMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<CanteenDTO> getById(@PathVariable("id") Long id) {
        CanteenDTO dto = CanteenMsMapper.INSTANCE
                .po2dto(canteenMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "CanteenVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) CanteenVO vo) {
        LoginUser loginUser = getLoginUser();
        if (ObjectUtils.isNull(loginUser)) {
            return BaseResult.error("用户未登陆，无法保存食堂信息");
        }

        CanteenPO po = CanteenMsMapper.INSTANCE.vo2po(vo);

        po.setUuid(UUID.randomUUID().toString());
//        设置部门id 绑定这个意味着这个食堂属于这个部门
        po.setPuuid(loginUser.getDeptId().toString());

        boolean result = canteenMpService.save(po);


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
            @ApiImplicitParam(paramType = "query", dataType = "CanteenVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) CanteenVO vo) {
        CanteenPO po = CanteenMsMapper.INSTANCE.vo2po(vo);
        boolean result = canteenMpService.updateById(po);
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
        boolean result = canteenMpService.removeById(id);
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
        boolean result = canteenMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
