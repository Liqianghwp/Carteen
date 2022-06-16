package com.ruoyi.web.controller.business;

import com.diandong.constant.Constants;
import com.diandong.constant.RichTextConstants;
import com.diandong.domain.dto.BusinessConfigDTO;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.diandong.mapstruct.BusinessConfigMsMapper;
import com.diandong.service.BusinessConfigMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
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


    @ApiOperation(value = "启动页", notes = "启动页", httpMethod = "GET")
    @GetMapping("/start_page")
    public BaseResult getStartPage() {
        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.START_PAGE));
    }


    /**
     //     * 查询关于我们的信息
     //     *
     //     * @return
     //     */
//    @ApiOperation(value = "关于我们", notes = "关于我们", httpMethod = "GET")
//    @GetMapping("/health_certificate")
//    public BaseResult getHealthCertificate() {
//        return BaseResult.success(businessConfigMpService.searchBusinessConfig(RichTextConstants.HEALTH_CERTIFICATE));
//    }


    /**
     * 保存更新关于我们富文本设置
     *
     * @return
     */
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
     * 保存更新启动页
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "保存&更新(启动页)")
    @PostMapping("/start_page")
    public BaseResult saveAndUpdateStartPage(@RequestBody BusinessConfigVO vo) {
        vo.setConfigName(RichTextConstants.START_PAGE);
        return businessConfigMpService.saveAndUpdateStartPage(vo);
    }

    /**
     * 删除启动页配置
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除(启动页)")
    @DeleteMapping("/start_page/{id}")
    public BaseResult deleteStartPage(@PathVariable String id) {
        BusinessConfigPO businessConfigPO = businessConfigMpService.getById(id);

        if (Constants.DEFAULT_YES == businessConfigPO.getDataState()) {
            return BaseResult.error("启用状态下不能删除");
        }
        boolean result = businessConfigMpService.removeById(id);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    /**
     * 开启&关闭启动页
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "开启&关闭(启动页)")
    @PutMapping("/start_page/onOff/{id}")
    public BaseResult onOffStartPageState(@PathVariable String id) {
        return businessConfigMpService.onOffStartPageState(id);
    }


}
