package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.dto.UserAmountDTO;
import com.diandong.domain.po.UserAmountPO;
import com.diandong.domain.vo.UserAmountVO;
import com.diandong.mapstruct.UserAmountMsMapper;
import com.diandong.service.UserAmountMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户金额Controller
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Validated
@RestController
@Api(value = "/user_amount", tags = {"用户金额模块"})
@RequestMapping(value = "/user_amount")
public class UserAmountController extends BaseController {

    @Resource
    private UserAmountMpService userAmountMpService;

    /**
     * 用户金额分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "UserAmountVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "用户金额分页查询", notes = "用户金额分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(UserAmountVO vo) {

        Page<UserAmountPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 用户金额根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "用户金额根据id查询", notes = "用户金额根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<UserAmountDTO> getById(@PathVariable("id") Long id) {
        UserAmountDTO dto = UserAmountMsMapper.INSTANCE
                .po2dto(userAmountMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 用户金额保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "UserAmountVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "用户金额保存", notes = "用户金额保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) UserAmountVO vo) {
        UserAmountPO po = UserAmountMsMapper.INSTANCE.vo2po(vo);
        boolean result = userAmountMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 用户金额更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "UserAmountVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "用户金额更新", notes = "用户金额更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) UserAmountVO vo) {
        UserAmountPO po = UserAmountMsMapper.INSTANCE.vo2po(vo);
        boolean result = userAmountMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 用户金额删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "用户金额删除", notes = "用户金额删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = userAmountMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 用户金额批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "用户金额批量删除", notes = "用户金额批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = userAmountMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<UserAmountPO> onSelectWhere(UserAmountVO vo) {
        LambdaQueryChainWrapper<UserAmountPO> queryWrapper = userAmountMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), UserAmountPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getUserId()), UserAmountPO::getUserId, vo.getUserId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAmount()), UserAmountPO::getAmount, vo.getAmount());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getSubsidy()), UserAmountPO::getSubsidy, vo.getSubsidy());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getTimes()), UserAmountPO::getTimes, vo.getTimes());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getType()), UserAmountPO::getType, vo.getType());
           return queryWrapper;
    }


}
