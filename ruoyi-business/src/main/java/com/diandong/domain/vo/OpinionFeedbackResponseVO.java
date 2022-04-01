package com.diandong.domain.vo;

import com.diandong.domain.po.OpinionFeedbackPO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("集团pc端意见反馈列表返回实体类")
@Data
public class OpinionFeedbackResponseVO extends OpinionFeedbackPO implements Serializable {

    //    手机号
    private String phone;

}
