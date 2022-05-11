package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.PaymentConfigDTO;
import com.diandong.domain.po.PaymentConfigPO;
import com.diandong.domain.vo.PaymentConfigVO;
import com.diandong.mapstruct.PaymentConfigMsMapper;
import com.diandong.service.PaymentConfigMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Validated
@RestController
@Api(value = "/payment_config", tags = {"支付设置模块"})
@RequestMapping(value = "/payment_config")
public class PaymentConfigController extends BaseController {

    @Resource
    private PaymentConfigMpService paymentConfigMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PaymentConfigVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<PaymentConfigDTO> getList(PaymentConfigVO vo) {
        startPage();
        List<PaymentConfigPO> dataList = paymentConfigMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), PaymentConfigPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getPaymentMethod()), PaymentConfigPO::getPaymentMethod, vo.getPaymentMethod())
                .eq(ObjectUtils.isNotEmpty(vo.getStatus()), PaymentConfigPO::getStatus, vo.getStatus())
                .eq(StringUtils.isNotBlank(vo.getRemark()), PaymentConfigPO::getRemark, vo.getRemark())
                .eq(ObjectUtils.isNotEmpty(vo.getCanteenId()), PaymentConfigPO::getCanteenId, vo.getCanteenId())
                .eq(StringUtils.isNotBlank(vo.getCanteenName()), PaymentConfigPO::getCanteenName, vo.getCanteenName())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(PaymentConfigMsMapper.INSTANCE.poList2dtoList(dataList));
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
    public BaseResult<PaymentConfigDTO> getById(@PathVariable("id") Long id) {
        PaymentConfigDTO dto = PaymentConfigMsMapper.INSTANCE
                .po2dto(paymentConfigMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "PaymentConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) PaymentConfigVO vo) {
        PaymentConfigPO po = PaymentConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = paymentConfigMpService.save(po);
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
            @ApiImplicitParam(paramType = "query", dataType = "PaymentConfigVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) PaymentConfigVO vo) {
        PaymentConfigPO po = PaymentConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = paymentConfigMpService.updateById(po);
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
        boolean result = paymentConfigMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

}
