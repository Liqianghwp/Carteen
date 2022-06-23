package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.configuration.Insert;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.vo.IntakeAnalysisVO;
import com.diandong.domain.vo.NutritionAdviceVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-31
 */
public interface NutritionAdviceMpService extends CommonService<NutritionAdvicePO> {

    /**
     * 批量录入营养信息
     *
     * @param naList    营养信息集合
     * @return  BaseResult
     */
    BaseResult inputNutritionAdvice(List<NutritionAdviceVO> naList) ;

    /**
     * 查询营养参数信息
     * @param vo    查询参数
     * @return  BaseResult
     */
    BaseResult intakeAnalysis(IntakeAnalysisVO vo);


    /**
     * 查询当前天营养信息记录
     * @param mealTimesId   餐次id
     * @return  BaseResult
     */
    BaseResult getNutritionAdvice(Long mealTimesId);
}
