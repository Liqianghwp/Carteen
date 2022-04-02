package com.diandong.domain.vo;

import com.diandong.domain.po.OpinionFeedbackPO;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("集团pc端意见反馈列表返回实体类")
@Data
public class OpinionFeedbackResponseVO extends OpinionFeedbackPO implements Serializable {

    private static final long serialVersionUID = 7373974414344296531L;
    //    手机号

    @Excel(sort = 5, name = "手机号", prompt = "手机号")
    private String phone;

}
