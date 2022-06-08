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
public class TransferCommentVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 调拨表id
     */
    @ApiModelProperty(value = "调拨表id")
    private Long transferId;

    /**
     * 台账id
     */
    @ApiModelProperty(value = "台账id")
    private Long rid;

    /**
     * 调拨数量
     */
    @ApiModelProperty(value = "调拨数量")
    private Long number;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer felFlag;


}