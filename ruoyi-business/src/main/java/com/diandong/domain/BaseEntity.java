package com.diandong.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname BaseEntity
 * @Description 基础实体类
 * @Date 2022/5/9 10:08
 * @Created by YuLiu
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -2503666272649968138L;


    @TableField(exist = false)
    @ApiModelProperty(value = "每页个数", example = "10", dataType = "java.lang.Integer")
    private Integer pageSize;

    @TableField(exist = false)
    @ApiModelProperty(value = "页码", example = "1", dataType = "java.lang.Integer")
    private Integer pageNum;

    @ApiModelProperty(value = "搜索-开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "搜索-结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime endTime;

}
