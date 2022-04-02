package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.FaceRecognitionMpService;
import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.domain.dto.FaceRecognitionDTO;
import com.diandong.domain.vo.FaceRecognitionVO;
import com.diandong.mapstruct.FaceRecognitionMsMapper;
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
 * @date 2022-04-01
 */
@Validated
@RestController
@Api(value = "/faceRecognition", tags = {"模块"})
@RequestMapping(value = "/faceRecognition")
public class FaceRecognitionController extends BaseController {

    @Resource
    private FaceRecognitionMpService faceRecognitionMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "FaceRecognitionVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<FaceRecognitionDTO> getList(FaceRecognitionVO vo) {
        startPage();
        List<FaceRecognitionPO> dataList = faceRecognitionMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), FaceRecognitionPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getFacePicture()), FaceRecognitionPO::getFacePicture, vo.getFacePicture())
                .eq(ObjectUtils.isNotEmpty(vo.getDataStatus()), FaceRecognitionPO::getDataStatus, vo.getDataStatus())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), FaceRecognitionPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), FaceRecognitionPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), FaceRecognitionPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(FaceRecognitionMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<FaceRecognitionDTO> getById(@PathVariable("id") Long id) {
        FaceRecognitionDTO dto = FaceRecognitionMsMapper.INSTANCE
                .po2dto(faceRecognitionMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "FaceRecognitionVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "上传人脸识别图片", notes = "上传人脸识别图片", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) FaceRecognitionVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        boolean result = false;

        List<FaceRecognitionPO> list = faceRecognitionMpService.lambdaQuery()
                .eq(FaceRecognitionPO::getCreateBy, loginUser.getUserId())
                .eq(FaceRecognitionPO::getDataStatus, 0)
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
//            触发更新操作
            FaceRecognitionPO faceRecognitionPO = list.get(0);
            faceRecognitionPO.setFacePicture(vo.getFacePicture());
            faceRecognitionPO.setUpdateBy(loginUser.getUserId());
            faceRecognitionPO.setUpdateName(loginUser.getUsername());

            result = faceRecognitionMpService.updateById(faceRecognitionPO);
        } else {
//            触发添加操作
            FaceRecognitionPO po = FaceRecognitionMsMapper.INSTANCE.vo2po(vo);
            po.setCreateBy(loginUser.getUserId());
            po.setCreateName(loginUser.getUsername());
            result = faceRecognitionMpService.save(po);
        }

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
            @ApiImplicitParam(paramType = "query", dataType = "FaceRecognitionVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) FaceRecognitionVO vo) {
        FaceRecognitionPO po = FaceRecognitionMsMapper.INSTANCE.vo2po(vo);
        boolean result = faceRecognitionMpService.updateById(po);
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
        boolean result = faceRecognitionMpService.removeById(id);
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
        boolean result = faceRecognitionMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
