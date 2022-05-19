package com.ruoyi.web.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.domain.vo.IngredientsDetailVO;
import com.diandong.mapstruct.IngredientsDetailMsMapper;
import com.diandong.service.IngredientsDetailMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.IngredientsMpService;
import com.diandong.domain.po.IngredientsPO;
import com.diandong.domain.dto.IngredientsDTO;
import com.diandong.domain.vo.IngredientsVO;
import com.diandong.mapstruct.IngredientsMsMapper;
import io.swagger.models.auth.In;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;

/**
 * 配料管理Controller
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Validated
@RestController
@Api(value = "/ingredients", tags = {"配料管理模块"})
@RequestMapping(value = "/ingredients")
public class IngredientsController extends BaseController {

    @Resource
    private IngredientsMpService ingredientsMpService;
    @Resource
    private IngredientsDetailMpService ingredientsDetailMpService;

    /**
     * 配料管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "配料管理分页查询", notes = "配料管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(IngredientsVO vo) {
        Page<IngredientsPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        for (IngredientsPO record : page.getRecords()) {

            List<IngredientsDetailPO> list = ingredientsDetailMpService.lambdaQuery().eq(IngredientsDetailPO::getIngredientsId, record.getId()).list();

//            主料
            List<IngredientsDetailPO> list1 = new ArrayList<>();
//            辅料
            List<IngredientsDetailPO> list2 = new ArrayList<>();

            for (IngredientsDetailPO ingredientsDetailPO : list) {
                if ("0".equalsIgnoreCase(ingredientsDetailPO.getType())) {
                    list1.add(ingredientsDetailPO);
                }else {
                    list2.add(ingredientsDetailPO);
                }

            }

//            String collect = list1.stream().map(ingredientsDetailPO -> ingredientsDetailPO.getRawMaterialName() + " " + ingredientsDetailPO.getNumber() + "g")
//                    .collect(Collectors.joining(","));
//
//            Stream<String> stringStream = list1.stream().map(ingredientsDetailPO -> ingredientsDetailPO.getRawMaterialName() + " " + ingredientsDetailPO.getNumber() + "g");
//
//
//            stringStream.collect(Collectors.joining(","))


            StringBuilder stringBuilder=new StringBuilder();
            for (IngredientsDetailPO ingredientsDetailPO : list1) {
                if(stringBuilder.length() == 0){
                    stringBuilder.append(ingredientsDetailPO.getRawMaterialName()+" " + ingredientsDetailPO.getNumber()+"g");
                }else {
                    stringBuilder.append(",") .append(ingredientsDetailPO.getRawMaterialName()+" " + ingredientsDetailPO.getNumber()+"g");
                }
            }
            StringBuilder stringBuilder1= new StringBuilder();
            for (IngredientsDetailPO ingredientsDetailPO : list2) {
                if (stringBuilder1.length()==1){
                    stringBuilder.append(ingredientsDetailPO.getRawMaterialName()+""+ingredientsDetailPO.getNumber()+"g");
                }else {
                    stringBuilder.append(",").append(ingredientsDetailPO.getRawMaterialName()+""+ingredientsDetailPO.getNumber()+"g");

                }
            }


            record.setZic1(stringBuilder.toString());
            record.setZio2(stringBuilder1.toString());

        }

        return BaseResult.success(page);
    }

    /**
     * 配料管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理根据id查询", notes = "配料管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<IngredientsDTO> getById(@PathVariable("id") Long id) {
        IngredientsDTO dto = IngredientsMsMapper.INSTANCE
                .po2dto(ingredientsMpService.getById(id));
        List<IngredientsDetailPO> list = ingredientsDetailMpService.lambdaQuery().eq(IngredientsDetailPO::getId, dto.getId()).list();
        //    主料
        List<IngredientsDetailPO> list1 = new ArrayList<>();
//            辅料
        List<IngredientsDetailPO> list2 = new ArrayList<>();

        for (IngredientsDetailPO ingredientsDetailPO : list) {
            if ("0".equalsIgnoreCase(ingredientsDetailPO.getType())) {
                list1.add(ingredientsDetailPO);
            }else {
                list2.add(ingredientsDetailPO);
            }
        }
       dto.setList(list1);
       dto.setList1(list2);
        return BaseResult.success(dto);
    }

    /**
     * 配料管理保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理保存", notes = "配料管理保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@RequestBody @Validated(Insert.class) IngredientsVO vo) {
        IngredientsPO po = IngredientsMsMapper.INSTANCE.vo2po(vo);

        /**
         * 主料
         * */
        List<IngredientsDetailVO> zic = vo.getZic();
        List<IngredientsDetailPO> list = new ArrayList<>();
        boolean result = ingredientsMpService.save(po);
        for (IngredientsDetailVO ingredientsDetailVO : zic) {

            ingredientsDetailVO.setType("0");
            ingredientsDetailVO.setIngredientsId(po.getId());

            IngredientsDetailPO po1= IngredientsDetailMsMapper.INSTANCE.vo2po(ingredientsDetailVO);
            list.add(po1);


        }
        /**
         * 辅料
         * */
        List<IngredientsDetailVO> zio = vo.getZio();
        List<IngredientsDetailPO> objects = new ArrayList<>();
        for (IngredientsDetailVO ingredientsDetailVO : zio) {
            ingredientsDetailVO.setIngredientsId(po.getId());

            ingredientsDetailVO.setType("1");
            IngredientsDetailPO ingredientsDetailPO = IngredientsDetailMsMapper.INSTANCE.vo2po(ingredientsDetailVO);
            objects.add(ingredientsDetailPO);
        }


        ingredientsDetailMpService.saveBatch(list);
        ingredientsDetailMpService.saveBatch(objects);

        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 配料管理更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "IngredientsVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "配料管理更新", notes = "配料管理更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update( @RequestBody @Validated(Update.class) IngredientsVO vo) {
        IngredientsPO po = IngredientsMsMapper.INSTANCE.vo2po(vo);

        /**
         * 主料
         * */
        List<IngredientsDetailVO> zic = vo.getZic();
        List<IngredientsDetailPO> list = new ArrayList<>();
        for (IngredientsDetailVO ingredientsDetailVO : zic) {

            ingredientsDetailVO.setType("0");
            IngredientsDetailPO po1= IngredientsDetailMsMapper.INSTANCE.vo2po(ingredientsDetailVO);
            list.add(po1);
        }
        /**
         * 辅料
         * */
        List<IngredientsDetailVO> zio = vo.getZio();
        List<IngredientsDetailPO> objects = new ArrayList<>();
        for (IngredientsDetailVO ingredientsDetailVO : zio) {
            ingredientsDetailVO.setType("1");
            IngredientsDetailPO ingredientsDetailPO = IngredientsDetailMsMapper.INSTANCE.vo2po(ingredientsDetailVO);
            objects.add(ingredientsDetailPO);
        }

        ingredientsDetailMpService.saveOrUpdateBatch(list);
        ingredientsDetailMpService.saveOrUpdateBatch(objects);
        boolean result = ingredientsMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }





    /**
     * 配料管理删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "配料管理删除", notes = "配料管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        Long aLong = ingredientsDetailMpService.echoMessage(id);
        if (aLong<=0){
            return BaseResult.error("删除失败");
        }else {
            boolean result = ingredientsMpService.removeById(id);
            if (result) {
                return BaseResult.successMsg("删除成功");
            } else {
                return BaseResult.error("删除失败");
            }
        }

    }
    private LambdaQueryChainWrapper<IngredientsPO> onSelectWhere(IngredientsVO vo) {
        LambdaQueryChainWrapper<IngredientsPO> queryWrapper = ingredientsMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), IngredientsPO::getId, vo.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishTypeId()), IngredientsPO::getDishTypeId, vo.getDishTypeId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishTypeName()), IngredientsPO::getDishTypeName, vo.getDishTypeName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDishId()), IngredientsPO::getDishId, vo.getDishId());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getDishName()), IngredientsPO::getDishName, vo.getDishName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), IngredientsPO::getRemark, vo.getRemark());
        return queryWrapper;
    }


}
