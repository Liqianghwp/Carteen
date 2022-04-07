package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.CanteenMpService;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.mapstruct.CanteenMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Validated
@RestController
@Api(value = "/canteen", tags = {"食堂设置模块"})
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
                .eq(ObjectUtils.isNotEmpty(vo.getContentName()), CanteenPO::getContentName, vo.getContentName())
                .eq(StringUtils.isNotBlank(vo.getContentPhone()), CanteenPO::getContentPhone, vo.getContentPhone())
                .eq(StringUtils.isNotBlank(vo.getCanteenAddress()), CanteenPO::getCanteenAddress, vo.getCanteenAddress())
                .eq(StringUtils.isNotBlank(vo.getBusinessLicense()), CanteenPO::getBusinessLicense, vo.getBusinessLicense())
                .eq(StringUtils.isNotBlank(vo.getQrCode()), CanteenPO::getQrCode, vo.getQrCode())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), CanteenPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getCanteenPicture()), CanteenPO::getCanteenPicture, vo.getCanteenPicture())
                .eq(StringUtils.isNotBlank(vo.getCanteenIntroduce()), CanteenPO::getCanteenIntroduce, vo.getCanteenIntroduce())
                .eq(ObjectUtils.isNotEmpty(vo.getPId()), CanteenPO::getPId, vo.getPId())
                .eq(StringUtils.isNotBlank(vo.getPName()), CanteenPO::getPName, vo.getPName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), CanteenPO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), CanteenPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), CanteenPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), CanteenPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), CanteenPO::getUpdateName, vo.getUpdateName())
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
    @ApiOperation(value = "新增食堂", notes = "新增食堂", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) CanteenVO vo) {

        LoginUser loginUser = getLoginUser();
        if(Objects.isNull(loginUser)){
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        return canteenMpService.addCanteen(vo,loginUser);
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
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error("用户未登录，无法进行操作。请您重新登录");
        }

        CanteenPO po = CanteenMsMapper.INSTANCE.vo2po(vo);

//        设置修改人信息
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());

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
