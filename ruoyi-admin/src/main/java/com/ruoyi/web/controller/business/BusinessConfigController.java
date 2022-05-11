package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.constant.RichTextConstants;
import com.diandong.domain.dto.BusinessConfigDTO;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.diandong.mapstruct.BusinessConfigMsMapper;
import com.diandong.service.BusinessConfigMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
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
     * 查询关于我们的信息
     *
     * @return
     */
    @ApiOperation(value = "关于我们", notes = "关于我们", httpMethod = "GET")
    @GetMapping("/about_us")
    public BaseResult getAboutUs() {

        return searchBusinessConfig(RichTextConstants.ABOUT_US);
    }

    /**
     * 查询用户服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "用户服务协议", notes = "用户服务协议", httpMethod = "GET")
    @GetMapping("/user_services_agreement")
    public BaseResult getUserServicesAgreement() {

        return searchBusinessConfig(RichTextConstants.USER_SERVICES_AGREEMENT);
    }

    /**
     * 查询充值服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "充值服务协议", notes = "充值服务协议", httpMethod = "GET")
    @GetMapping("/recharge_service_agreement")
    public BaseResult getRechargeServiceAgreement() {
        return searchBusinessConfig(RichTextConstants.RECHARGE_SERVICE_AGREEMENT);
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
     * 保存更新关于我们富文本设置
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "BusinessConfigVO", name = "vo")
    })
    @ApiOperation(value = "保存&更新(关于我们)")
    @PostMapping("/about_us")
    public BaseResult saveAndUpdateAboutUs(@RequestBody BusinessConfigVO vo) {
        vo.setConfigName(RichTextConstants.ABOUT_US);
        return saveAndUpdate(vo);
    }

    /**
     * 保存更新关于我们富文本设置
     *
     * @return
     */
    @ApiOperation(value = "保存&更新(用户服务协议)")
    @PostMapping("/user_services_agreement")
    public BaseResult saveAndUpdateUserServicesAgreement(@RequestBody BusinessConfigVO vo) {
        vo.setConfigName(RichTextConstants.USER_SERVICES_AGREEMENT);
        return saveAndUpdate(vo);
    }

    /**
     * 保存更新关于我们富文本设置
     *
     * @return
     */
    @ApiOperation(value = "保存&更新(充值服务协议)")
    @PostMapping("/recharge_service_agreement")
    public BaseResult saveAndUpdateRechargeServiceAgreement(@RequestBody BusinessConfigVO vo) {
        vo.setConfigName(RichTextConstants.RECHARGE_SERVICE_AGREEMENT);
        return saveAndUpdate(vo);
    }

    private BaseResult saveAndUpdate(BusinessConfigVO vo) {
        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);

        BusinessConfigPO oldConfig = businessConfigMpService.lambdaQuery().eq(BusinessConfigPO::getConfigName, po.getConfigName()).one();
        Boolean result = false;
        if (Objects.isNull(oldConfig)) {
            result = businessConfigMpService.save(po);
        } else {
            po.setId(oldConfig.getId());
            result = businessConfigMpService.updateById(po);
        }

        if (result) {
            return BaseResult.success(po);
        } else {
            return BaseResult.error("操作失败");
        }
    }


}
