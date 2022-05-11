package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.pinyin.ChineseCharacterUtil;
import com.ruoyi.system.constant.SysConstants;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
    @Resource
    private SysDictDataMapper dictDataMapper;


    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型 字典标签获取查询数量
     *
     * @param dictData 字典数据信息
     * @return
     */
    @Override
    public Integer countDictData(SysDictData dictData) {
        return dictDataMapper.countDictData(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictData data = selectDictDataById(dictCode);

//            TODO 改造 被占用的无法进行删除操作
            if (selectUsedBizDictData(data.getDictType(), dictCodes) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", data.getDictLabel()));
            }

            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    @Override
    public void deleteBizDictDataByIds(Long[] dictCodes) {

        for (Long dictCode : dictCodes) {
            SysDictData data = selectDictDataById(dictCode);

//            TODO 改造 被占用的无法进行删除操作
            int result = selectUsedBizDictData(data.getDictType(), dictCodes);

            if (result > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", data.getDictLabel()));
            }
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }


    }

    /**
     * 新增保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData data) {

        String dictValue = data.getDictValue();
        if (StringUtils.isBlank(dictValue)) {
//            将标签转换为汉字
            String hanZiDictValue = ChineseCharacterUtil.convertHanZi2PinyinFull(data.getDictLabel());
            List<SysDictData> dictDataList = dictDataMapper.selectDictDateByTypeAndValue(data.getDictType(), hanZiDictValue + "%");
//            判断是否有以转换的汉字开头的字典数据
//            重新设置value值
            if (CollectionUtils.isNotEmpty(dictDataList)) {
                data.setDictValue(hanZiDictValue + "_" + dictDataList.size());
            } else {
                data.setDictValue(hanZiDictValue);
            }
        }
        data.setListClass(StringUtils.isBlank(data.getListClass()) ? "default" : data.getListClass());

        int row = dictDataMapper.insertDictData(data);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData data) {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0) {
            List<SysDictData> dictDataList = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDataList);
        }
        return row;
    }

    @Override
    public int selectUsedDictData(Long[] dictCodes) {
        return dictDataMapper.deleteDictDataByIds(dictCodes);
    }

    @Override
    public int selectUsedBizDictData(String dictType, Long[] dictCodes) {
        int result = 0;

        switch (dictType) {
            case SysConstants.HEALTH_INDICATORS:
//                    健康指标 (1)
                result = dictDataMapper.selectHealthIndicatorsDictDataByIds(dictCodes);

                break;
            case SysConstants.MEAL_SETTING:
//                    餐次设置
                result = dictDataMapper.selectMealSettingDictDataByIds(dictCodes);

                break;
            case SysConstants.NUTRITION_ADVICE:
//                    营养建议（应该是需要做到别的地方）
                result = dictDataMapper.selectNutritionAdviceDictDataByIds(dictCodes);
                break;
            case SysConstants.OUTBOUND_METHOD:
//                    出库方式
                result = dictDataMapper.selectOutboundMethodDictDataByIds(dictCodes);
                break;
            case SysConstants.RECHARGE_SETTING:
//                    充值设置
                result = dictDataMapper.selectRechargeSettingDictDataByIds(dictCodes);
                break;
            case SysConstants.SAMPLE_STORAGE:
//                    留样存储设置
                result = dictDataMapper.selectSampleStorageDictDataByIds(dictCodes);
                break;
            case SysConstants.STORAGE_METHOD:
//                    入库方式
                result = dictDataMapper.selectStorageMethodDictDataByIds(dictCodes);
                break;
            case SysConstants.TEST_RESULTS:
//                    检测结果
                result = dictDataMapper.selectTestResultsDictDataByIds(dictCodes);
                break;
            case SysConstants.UNIT_SETTING:
//                    单位设置
                result = dictDataMapper.selectUnitSettingDictDataByIds(dictCodes);
                break;
            case SysConstants.WAREHOUSE_MANAGEMENT:
//                    仓储管理
                result = dictDataMapper.selectWarehouseManagementDictDataByIds(dictCodes);
                break;
            case SysConstants.RAW_MATERIAL_CATEGORY:
//                    原材料类别
                result = dictDataMapper.selectRawMaterialCategoryDictDataByIds(dictCodes);
                break;
            case SysConstants.OPINION_TYPE:
//                    原材料类别
                result = dictDataMapper.selectOpinionTypeDictDataByIds(dictCodes);
                break;
            default:
                throw new ServiceException("未找到对应标签，无法删除");
        }

        return result;
    }

}
