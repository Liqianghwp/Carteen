package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 补贴记录VO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("补贴记录VO实体类")
public class SubsidyRecordsVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @NotBlank(groups = {Insert.class}, message = "用户集合不能为空")
    @ApiModelProperty(value = "用户集合")
    private String userIds;

    /**
     * 补贴金额
     */
    @NotNull(groups = {Insert.class}, message = "补贴金额不能为空")
    @ApiModelProperty(value = "补贴金额")
    private BigDecimal amount;

    /**
     * 类型 0：新增；1：清零
     */
    @NotNull(groups = {Insert.class, Update.class}, message = "类型不能为空")
    @ApiModelProperty(value = "类型 0：新增；1：清零")
    private Integer type;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "勾选要导出的id集合")
    private List<Long> ids;

}