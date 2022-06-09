package com.diandong.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 补贴记录DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("补贴记录DTO实体类")
public class SubsidyRecordsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Long userType;

    /**
     * 用户集合
     */
    @ApiModelProperty(value = "用户集合")
    private String userIds;

    /**
     * 补贴金额
     */
    @Excel(name = "补贴金额", sort = 2)
    @ApiModelProperty(value = "补贴金额")
    private BigDecimal amount;

    /**
     * 类型 0：新增；1：清零
     */
    @ApiModelProperty(value = "类型 0：新增；1：清零")
    private Integer type;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @Excel(name = "补贴时间", sort = 1, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @Excel(name = "创建人", sort = 3)
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

}