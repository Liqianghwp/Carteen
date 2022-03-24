package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 系统配置VO实体类
 *
 * @author YuLiu
 * @date 2022-03-23
 */
@Data
@ApiModel("系统配置VO实体类")
public class BusinessConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /**
     * 配置内容
     */
    @ApiModelProperty(value = "配置内容")
    private String configValue;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

}