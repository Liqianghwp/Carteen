package com.ruoyi.pay.model.vo;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayResultEvenVO {

    @ApiModelProperty(value = "支付类型")
    private PayWay payWay;

    @ApiModelProperty(value = "支付宝异步返回数据")
    private AliPayCallBackVO aliPayCallBackVo;

    @ApiModelProperty(value = "微信异步返回数据")
    private WxPayOrderNotifyResult wxPayOrderNotifyResult;

    @ApiModelProperty(value = "银联云闪付异步返回数据")
    private UnionPayCallBackVO unionPayCallBackVO;

}
