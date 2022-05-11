package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.ChefManagementMpService;
import com.diandong.domain.po.ChefManagementPO;
import com.diandong.domain.dto.ChefManagementDTO;
import com.diandong.domain.vo.ChefManagementVO;
import com.diandong.mapstruct.ChefManagementMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 厨师管理Controller
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Validated
@RestController
@Api(value = "/chef_management", tags = {"厨师管理模块"})
@RequestMapping(value = "/chef_management")
public class ChefManagementController extends BaseController {

    @Resource
    private ChefManagementMpService chefManagementMpService;

    /**
     * 厨师管理分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ChefManagementVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "厨师管理分页查询", notes = "厨师管理分页查询方法", httpMethod = "GET")
    @GetMapping
    public TableDataInfo<ChefManagementDTO> getList(ChefManagementVO vo) {
        startPage();
        List<ChefManagementPO> dataList = chefManagementMpService.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getId()), ChefManagementPO::getId, vo.getId())
                .eq(StringUtils.isNotBlank(vo.getChefName()), ChefManagementPO::getChefName, vo.getChefName())
                .eq(ObjectUtils.isNotEmpty(vo.getSex()), ChefManagementPO::getSex, vo.getSex())
                .eq(StringUtils.isNotBlank(vo.getPhone()), ChefManagementPO::getPhone, vo.getPhone())
                .eq(StringUtils.isNotBlank(vo.getJobTitle()), ChefManagementPO::getJobTitle, vo.getJobTitle())
                .eq(StringUtils.isNotBlank(vo.getHomeAddress()), ChefManagementPO::getHomeAddress, vo.getHomeAddress())
                .eq(StringUtils.isNotBlank(vo.getChefDetails()), ChefManagementPO::getChefDetails, vo.getChefDetails())
                .list();
        TableDataInfo pageData = getDataTable(dataList);
        pageData.setRows(ChefManagementMsMapper.INSTANCE.poList2dtoList(dataList));
        return pageData;
    }

    /**
     * 厨师管理根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "厨师管理根据id查询", notes = "厨师管理根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<ChefManagementDTO> getById(@PathVariable("id") Long id) {
        ChefManagementDTO dto = ChefManagementMsMapper.INSTANCE
                .po2dto(chefManagementMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 厨师管理保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ChefManagementVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "厨师管理保存", notes = "厨师管理保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) ChefManagementVO vo) {
        ChefManagementPO po = ChefManagementMsMapper.INSTANCE.vo2po(vo);
        boolean result = chefManagementMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 厨师管理更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ChefManagementVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "厨师管理更新", notes = "厨师管理更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) ChefManagementVO vo) {
        ChefManagementPO po = ChefManagementMsMapper.INSTANCE.vo2po(vo);
        boolean result = chefManagementMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 厨师管理删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "厨师管理删除", notes = "厨师管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = chefManagementMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 厨师管理批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "厨师管理批量删除", notes = "厨师管理批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = chefManagementMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


}
