package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.CanteenDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.domain.vo.HealthCertMsgVO;
import com.diandong.mapstruct.CanteenMsMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.ChefManagementMpService;
import com.diandong.domain.po.ChefManagementPO;
import com.diandong.domain.dto.ChefManagementDTO;
import com.diandong.domain.vo.ChefManagementVO;
import com.diandong.mapstruct.ChefManagementMsMapper;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    @Resource
    private ISysUserService userService;
    @Resource
    private ISysDictDataService dictDataService;

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
    public BaseResult getList(ChefManagementVO vo) {
        Page<ChefManagementPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));

        List<ChefManagementPO> records = page.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {

            records.forEach(chefManagementPO -> {
                SysUser user = userService.selectUserById(chefManagementPO.getCreateBy());
                chefManagementPO.setCreateName(Objects.isNull(user) ? null : user.getUserName());
            });
        }
        return BaseResult.success(page);
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
    public BaseResult save(@RequestBody @Validated(Insert.class) ChefManagementVO vo) {
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
    public BaseResult update(@RequestBody @Validated(Update.class) ChefManagementVO vo) {
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
     * @param ids 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long[]", name = "ids", value = "编号id")
    })
    @ApiOperation(value = "厨师管理删除", notes = "厨师管理删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{ids}")
    public BaseResult delete(@PathVariable("ids") Long[] ids) {
        boolean result = chefManagementMpService.removeByIds(Arrays.asList(ids));
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 导出
     *
     * @param response
     * @param vo
     */
    @Log(title = "厨师管理导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChefManagementVO vo) {

        List<Long> ids = vo.getIds();

        List<ChefManagementPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = chefManagementMpService.lambdaQuery().in(ChefManagementPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<ChefManagementDTO> chefManagementList = new ArrayList<>();

        list.forEach(chefManagementPO -> {
            ChefManagementDTO chefManagementDTO = ChefManagementMsMapper.INSTANCE.po2dto(chefManagementPO);
//            设置创建人姓名
            SysUser user = userService.selectUserById(chefManagementDTO.getCreateBy());
            chefManagementDTO.setCreateName(user.getUserName());
//            设置性别显示
            SysDictData sysDictData = dictDataService.selectDictDataById(chefManagementDTO.getSex());
            chefManagementDTO.setSexName(sysDictData.getDictLabel());

            chefManagementList.add(chefManagementDTO);
        });

        ExcelUtil<ChefManagementDTO> util = new ExcelUtil<ChefManagementDTO>(ChefManagementDTO.class);
        util.exportExcel(response, chefManagementList, "厨师管理");

    }


    private LambdaQueryChainWrapper<ChefManagementPO> onSelectWhere(ChefManagementVO vo) {

        LambdaQueryChainWrapper<ChefManagementPO> queryWrapper = chefManagementMpService.lambdaQuery();

        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper
                .eq(ObjectUtils.isNotEmpty(vo.getId()), ChefManagementPO::getId, vo.getId())
                .like(StringUtils.isNotBlank(vo.getChefName()), ChefManagementPO::getChefName, vo.getChefName())
                .eq(ObjectUtils.isNotEmpty(vo.getSex()), ChefManagementPO::getSex, vo.getSex())
                .eq(StringUtils.isNotBlank(vo.getPhone()), ChefManagementPO::getPhone, vo.getPhone())
                .eq(StringUtils.isNotBlank(vo.getJobTitle()), ChefManagementPO::getJobTitle, vo.getJobTitle())
                .eq(StringUtils.isNotBlank(vo.getHomeAddress()), ChefManagementPO::getHomeAddress, vo.getHomeAddress())
                .eq(StringUtils.isNotBlank(vo.getChefDetails()), ChefManagementPO::getChefDetails, vo.getChefDetails());

        return queryWrapper;
    }


}
