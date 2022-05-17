package com.ruoyi.web.controller.business;

import com.diandong.constant.RichTextConstants;
import com.diandong.domain.dto.BusinessConfigDTO;
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
        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.ABOUT_US));
    }

    /**
     * 查询用户服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "用户服务协议", notes = "用户服务协议", httpMethod = "GET")
    @GetMapping("/user_services_agreement")
    public BaseResult getUserServicesAgreement() {

        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.USER_SERVICES_AGREEMENT));
    }

    /**
     * 查询充值服务协议的信息
     *
     * @return
     */
    @ApiOperation(value = "充值服务协议", notes = "充值服务协议", httpMethod = "GET")
    @GetMapping("/recharge_service_agreement")
    public BaseResult getRechargeServiceAgreement() {
        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.RECHARGE_SERVICE_AGREEMENT));
    }


    /**
     * 查询关于我们的信息
     *
     * @return
     */
    @ApiOperation(value = "关于我们", notes = "关于我们", httpMethod = "GET")
    @GetMapping("/health_certificate")
    public BaseResult getHealthCertificate() {
        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.HEALTH_CERTIFICATE));
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
        return businessConfigMpService.saveAndUpdate(vo);
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
        return businessConfigMpService.saveAndUpdate(vo);
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
        return businessConfigMpService.saveAndUpdate(vo);
    }

    /**
     * 保存更新关于我们富文本设置
     *
     * @return
     */
    @ApiOperation(value = "保存&更新(健康证过期时间)")
    @PostMapping("/health_certificate")
    public BaseResult saveAndUpdateHealthCertificate(@RequestBody BusinessConfigVO vo) {
        vo.setConfigName(RichTextConstants.HEALTH_CERTIFICATE);
        return businessConfigMpService.saveAndUpdate(vo);
    }


}
