package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.mapstruct.CanteenMsMapper;
import com.diandong.service.CanteenMpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
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
    public BaseResult getList(CanteenVO vo) {
        Page<CanteenPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
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
        return canteenMpService.addCanteen(vo);
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
    public BaseResult update(@RequestBody @Validated(Update.class) CanteenVO vo) {
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
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long[]", name = "ids", value = "编号id数组")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = canteenMpService.removeByIds(Arrays.asList(ids));
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
    @Log(title = "食堂管理导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CanteenVO vo) {
        List<Long> ids = vo.getIds();

        List<CanteenPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = canteenMpService.lambdaQuery().in(CanteenPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<CanteenDTO> canteenList = new ArrayList<>();

        list.forEach(canteenPO -> {
            canteenList.add(CanteenMsMapper.INSTANCE.po2dto(canteenPO));
        });

        ExcelUtil<CanteenDTO> util = new ExcelUtil<CanteenDTO>(CanteenDTO.class);
        util.exportExcel(response, canteenList, "食堂管理");
    }

    /**
     * 根据集团id查询集团下的所有食堂信息
     *
     * @param groupId 集团id
     * @return
     */
    @ApiOperation(value = "根据集团id查询集团下的所有食堂信息")
    @GetMapping("/group/{groupId}")
    public BaseResult getCanteenByGroupId(@PathVariable("groupId") Long groupId) {

        List<CanteenPO> list = canteenMpService.lambdaQuery()
                .eq(CanteenPO::getPId, groupId)
                .eq(CanteenPO::getDelFlag, Constants.DEL_NO)
                .list();

        return BaseResult.success(list);
    }


    private LambdaQueryChainWrapper<CanteenPO> onSelectWhere(CanteenVO vo) {

        LambdaQueryChainWrapper<CanteenPO> queryWrapper = canteenMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), CanteenPO::getId, vo.getId())
                .like(StringUtils.isNotBlank(vo.getCanteenName()), CanteenPO::getCanteenName, vo.getCanteenName())
                .like(ObjectUtils.isNotEmpty(vo.getContentName()), CanteenPO::getContentName, vo.getContentName())
                .like(StringUtils.isNotBlank(vo.getContentPhone()), CanteenPO::getContentPhone, vo.getContentPhone())
                .eq(StringUtils.isNotBlank(vo.getCanteenAddress()), CanteenPO::getCanteenAddress, vo.getCanteenAddress())
                .eq(StringUtils.isNotBlank(vo.getBusinessLicense()), CanteenPO::getBusinessLicense, vo.getBusinessLicense())
                .eq(StringUtils.isNotBlank(vo.getQrCode()), CanteenPO::getQrCode, vo.getQrCode())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), CanteenPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getCanteenPicture()), CanteenPO::getCanteenPicture, vo.getCanteenPicture())
                .eq(StringUtils.isNotBlank(vo.getCanteenIntroduce()), CanteenPO::getCanteenIntroduce, vo.getCanteenIntroduce())
                .eq(ObjectUtils.isNotEmpty(vo.getPId()), CanteenPO::getPId, vo.getPId())
                .eq(StringUtils.isNotBlank(vo.getPName()), CanteenPO::getPName, vo.getPName())
                .eq(StringUtils.isNotBlank(vo.getRemark()), CanteenPO::getRemark, vo.getRemark());

        return queryWrapper;
    }

}
