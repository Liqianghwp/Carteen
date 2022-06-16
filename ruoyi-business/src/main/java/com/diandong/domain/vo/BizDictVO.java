package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 业务字典VO实体类
 *
 * @author YuLiu
 * @date 2022-06-10
 */
@Data
@ApiModel("业务字典VO实体类")
public class BizDictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @ApiModelProperty(value = "字典名称")
    private String dictLabel;

    /**
     * 字典值
     */
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
     *
     */
    @ApiModelProperty(value = "勾选导出的id集合")
    private List<Long> ids;

    /**
     * 使用状态 0：未使用；1：使用
     */
    @ApiModelProperty(value = "使用状态 0：未使用；1：使用")
    private Integer used;

    @ApiModelProperty(value = "每页个数", example = "10", dataType = "java.lang.Integer")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "页码", example = "1", dataType = "java.lang.Integer")
    private Integer pageNum = 1;
}