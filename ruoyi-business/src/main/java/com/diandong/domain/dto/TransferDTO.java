package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Data
@ApiModel("DTO实体类")
public class TransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号id
     */
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

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 原材料类别名称
     */
    @ApiModelProperty(value = "原材料类别名称")
    private String categoryName;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 调拨数量
     */
    @ApiModelProperty(value = "调拨数量")
    private Long number;

    /**
     * 库存
     * */
    @TableField(exist = false)
    @ApiModelProperty(value = "库存")
    private Long repertory;

}