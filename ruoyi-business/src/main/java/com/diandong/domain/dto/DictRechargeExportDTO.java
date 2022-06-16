package com.diandong.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务字典DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@Data
@ApiModel("业务字典DTO实体类")
public class DictRechargeExportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典名称
     */
    @Excel()
    @ApiModelProperty(value = "字典名称")
    private String dictLabel;

    /**
     * 字典值
     */
    @Excel(name = "充值金额")
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    /**
     * 停用启用状态 0：正常；1：停用
     */
    @ApiModelProperty(value = "停用启用状态 0：正常；1：停用")
    private String status;

    /**
     * 集团id
     */
    @ApiModelProperty(value = "集团id")
    private Long groupId;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 餐次开始时间
     */
    @ApiModelProperty(value = "餐次开始时间")
    private String beginTime;

    /**
     * 餐次结束时间
     */
    @ApiModelProperty(value = "餐次结束时间")
    private String endTime;

    /**
     * 使用状态 0：未使用；1：使用
     */
    @ApiModelProperty(value = "使用状态 0：未使用；1：使用")
    private Integer used;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @Excel(name = "创建时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
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

}