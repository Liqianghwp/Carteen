package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.FaceRecognitionDTO;
import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.domain.vo.FaceRecognitionVO;
import com.diandong.mapstruct.FaceRecognitionMsMapper;
import com.diandong.service.FaceRecognitionMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Validated
@RestController
@Api(value = "/face_recognition", tags = {"人脸识别录入模块"})
@RequestMapping(value = "/face_recognition")
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
    public BaseResult getList(FaceRecognitionVO vo) {
        Page<FaceRecognitionPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageNum()));
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
    public BaseResult save(@RequestBody @Validated(Insert.class) FaceRecognitionVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }
        boolean result = false;

        List<FaceRecognitionPO> list = faceRecognitionMpService.lambdaQuery()
                .eq(FaceRecognitionPO::getCreateBy, loginUser.getUserId())
                .eq(FaceRecognitionPO::getDelFlag, 0)
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
//            触发更新操作
            FaceRecognitionPO faceRecognitionPO = list.get(0);
            faceRecognitionPO.setFacePicture(vo.getFacePicture());
            faceRecognitionPO.setUpdateBy(loginUser.getUserId());

            result = faceRecognitionMpService.updateById(faceRecognitionPO);
        } else {
//            触发添加操作
            FaceRecognitionPO po = FaceRecognitionMsMapper.INSTANCE.vo2po(vo);
            po.setCreateBy(loginUser.getUserId());
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
    public BaseResult update(@RequestBody @Validated(Update.class) FaceRecognitionVO vo) {
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
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable Long[] ids) {
        boolean result = faceRecognitionMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    private LambdaQueryChainWrapper<FaceRecognitionPO> onSelectWhere(FaceRecognitionVO vo) {

        LambdaQueryChainWrapper<FaceRecognitionPO> queryWrapper = faceRecognitionMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), FaceRecognitionPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getFacePicture()), FaceRecognitionPO::getFacePicture, vo.getFacePicture());

        return queryWrapper;
    }


}
