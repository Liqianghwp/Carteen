package com.diandong.domain.vo;

import com.diandong.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class RawMaterialNutritionListVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8480593660028767017L;

    /**
     * 原材料ID
     */
    @NotNull(message = "原材料id不能为空")
    private Long rawMaterialId;

    private List<RawMaterialNutritionVO> rmnList;


}
