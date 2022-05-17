package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 原材料黑名单VO实体类
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@Data
@ApiModel("原材料黑名单VO实体类")
public class RawMaterialBlacklistVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 原材料类别id
     */
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 采购类型id
     */
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 预估进价
     */
    @ApiModelProperty(value = "预估进价")
    private BigDecimal prePrice;

    /**
     * 其他描述
     */
    @ApiModelProperty(value = "其他描述")
    private String remark;


}