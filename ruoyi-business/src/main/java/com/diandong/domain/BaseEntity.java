package com.diandong.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer pageSize = 10;

    @TableField(exist = false)
    @ApiModelProperty(value = "页码", example = "1", dataType = "java.lang.Integer")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "搜索-开始时间", example = "2022-05-13 00:00:00")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "搜索-结束时间", example = "2022-05-13 23:59:59")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private LocalDateTime endTime;


}
