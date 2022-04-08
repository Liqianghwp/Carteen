package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author ruoyi
 */
public interface SysDictDataMapper {
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    public int countDictDataByType(String dictType);

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    public int deleteDictDataByIds(Long[] dictCodes);

    /**
     * 批量查询字典数据信息
     *
     * @param dictCodes 需要查询的字典数据ID集合
     * @return 结果
     */
    public int selectDictDataByIds(Long[] dictCodes);

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictData dictData);

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);


    //    TODO 健康指标
    int selectHealthIndicatorsDictDataByIds(Long[] dictCodes);

    //    TODO 餐次设置
    int selectMealSettingDictDataByIds(Long[] dictCodes);

    //    TODO 营养建议
    int selectNutritionAdviceDictDataByIds(Long[] dictCodes);

    //    TODO 出库方式
    int selectOutboundMethodDictDataByIds(Long[] dictCodes);

    //    TODO 充值设置
    int selectRechargeSettingDictDataByIds(Long[] dictCodes);

    //    TODO 留样存储
    int selectSampleStorageDictDataByIds(Long[] dictCodes);

    //    TODO 入库方式
    int selectStorageMethodDictDataByIds(Long[] dictCodes);

    //    TODO 检测结果
    int selectTestResultsDictDataByIds(Long[] dictCodes);

    //    TODO 单位设置
    int selectUnitSettingDictDataByIds(Long[] dictCodes);

    //    TODO 库存管理
    int selectWarehouseManagementDictDataByIds(Long[] dictCodes);

    //    TODO 菜品类别
    int selectDishesTypeDictDataByIds(Long[] dictCodes);

    //    TODO 原材料类别
    int selectRawMaterialCategoryDictDataByIds(Long[] dictCodes);

    //    TODO 意见类型
    int selectOpinionTypeDictDataByIds(Long[] dictCodes);


}
