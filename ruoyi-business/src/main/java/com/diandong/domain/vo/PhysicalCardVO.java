package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 实物卡VO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("实物卡VO实体类")
public class PhysicalCardVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @NotNull(groups = {Insert.class},message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 卡号
     */
    @NotBlank(groups = {Insert.class},message = "卡号不能为空")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 停用启用 0：停用；1：启用
     */
    @ApiModelProperty(value = "停用启用 0：停用；1：启用")
    private Integer state;

    /**
     * 是否挂失 0：否；1：是
     */
    @ApiModelProperty(value = "是否挂失 0：否；1：是")
    private Integer reportLoss;


}