package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.BusinessConfigDTO;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.diandong.mapstruct.BusinessConfigMsMapper;
import com.diandong.service.BusinessConfigMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Controller
 * 这个是配置富文本的访问接口信息
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/business_config", tags = {"富文本设置"})
@RequestMapping(value = "/business_config")
public class BusinessConfigController extends BaseController {

    @Resource
    private BusinessConfigMpService businessConfigMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<BusinessConfigDTO> getList(BusinessConfigVO vo) {
        startPage();
        List<BusinessConfigPO> dataList = businessConfigMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), BusinessConfigPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getConfigName()), BusinessConfigPO::getConfigName, vo.getConfigName())
                .eq(StringUtils.isNotBlank(vo.getConfigValue()), BusinessConfigPO::getConfigValue, vo.getConfigValue())
                .eq(ObjectUtils.isNotEmpty(vo.getDataState()), BusinessConfigPO::getDataState, vo.getDataState())
                .eq(ObjectUtils.isNotEmpty(vo.getVersion()), BusinessConfigPO::getVersion, vo.getVersion())
                .eq(StringUtils.isNotBlank(vo.getCreateName()), BusinessConfigPO::getCreateName, vo.getCreateName())
                .eq(StringUtils.isNotBlank(vo.getUpdateName()), BusinessConfigPO::getUpdateName, vo.getUpdateName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(BusinessConfigMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<BusinessConfigDTO> getById(@PathVariable("id") Long id) {
        BusinessConfigDTO dto = BusinessConfigMsMapper.INSTANCE
                .po2dto(businessConfigMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) BusinessConfigVO vo) {

//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);
//        设置创建人信息
        po.setCreateBy(loginUser.getUserId());
        po.setCreateName(loginUser.getUsername());
        boolean result = businessConfigMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 查询关于我们的信息
     *
     * @return
     */
    @ApiOperation(value = "关于我们", notes = "关于我们", httpMethod = "GET")
    @GetMapping("/getAboutUs")
    public BaseResult getAboutUs() {

        return searchBusinessConfig("关于我们");
    }

    /**
     * 查询用户服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "用户服务协议", notes = "用户服务协议", httpMethod = "GET")
    @GetMapping("/getUserServicesAgreement")
    public BaseResult getUserServicesAgreement() {

        return searchBusinessConfig("用户服务协议");
    }

    /**
     * 查询充值服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "充值服务协议", notes = "充值服务协议", httpMethod = "GET")
    @GetMapping("/getRechargeServiceAgreement")
    public BaseResult getRechargeServiceAgreement() {

        return searchBusinessConfig("充值服务协议");
    }


    /**
     * 根据配置名称查询配置信息
     *
     * @param configName 配置名称
     * @return
     */
    private BaseResult searchBusinessConfig(String configName) {
        BusinessConfigPO businessConfigPO = new BusinessConfigPO();
        if (StringUtils.isNotBlank(configName)) {
            List<BusinessConfigPO> list = businessConfigMpService.lambdaQuery().eq(BusinessConfigPO::getConfigName, configName).list();

            if (CollectionUtils.isNotEmpty(list)) {
                businessConfigPO = list.get(0);
            }
        }
        return BaseResult.success(businessConfigPO);
    }


    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) BusinessConfigVO vo) {
//        判断登录状态
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return BaseResult.error(Constants.ERROR_MESSAGE);
        }

        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);
        po.setUpdateBy(loginUser.getUserId());
        po.setUpdateName(loginUser.getUsername());

        boolean result = businessConfigMpService.updateById(po);
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
        boolean result = businessConfigMpService.removeById(id);
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
        boolean result = businessConfigMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
