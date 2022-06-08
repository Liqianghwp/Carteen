package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Data
@ApiModel("VO实体类")
public class TransferVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号id
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "序号id")
    private Long id;

    /**
     * 单据编号
     */
    @ApiModelProperty(value = "单据编号")
    private String code;

    /**
     * 出库食堂
     */
    @ApiModelProperty(value = "出库食堂")
    private String outboundCanteen;

    /**
     * 入库食堂
     */
    @ApiModelProperty(value = "入库食堂")
    private String inboundCanteen;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String reviewer;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}