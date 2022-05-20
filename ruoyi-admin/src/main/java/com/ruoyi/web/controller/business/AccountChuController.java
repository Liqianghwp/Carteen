package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.diandong.service.AccountChuMpService;
import com.diandong.domain.po.AccountChuPO;
import com.diandong.domain.dto.AccountChuDTO;
import com.diandong.domain.vo.AccountChuVO;
import com.diandong.mapstruct.AccountChuMsMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * Controller
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@Validated
@RestController
@Api(value = "/account_chu", tags = {"模块"})
@RequestMapping(value = "/account_chu")
public class AccountChuController extends BaseController {

    @Resource
    private AccountChuMpService accountChuMpService;

    /**
     * 分页查询
     *
     * @param vo 参数对象
     * @return 分页数据结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountChuVO", name = "vo", value = "查询参数")
    })
    @ApiOperation(value = "分页查询", notes = "分页查询方法", httpMethod = "GET")
    @GetMapping
    public BaseResult getList(AccountChuVO vo) {

        Page<AccountChuPO> page = onSelectWhere(vo).page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public BaseResult<AccountChuDTO> getById(@PathVariable("id") Long id) {
        AccountChuDTO dto = AccountChuMsMapper.INSTANCE
                .po2dto(accountChuMpService.getById(id));
        return BaseResult.success(dto);
    }

    /**
     * 保存
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountChuVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping
    public BaseResult save(@Validated(Insert.class) AccountChuVO vo) {
        AccountChuPO po = AccountChuMsMapper.INSTANCE.vo2po(vo);
        boolean result = accountChuMpService.save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param vo 参数对象
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "AccountChuVO", name = "vo", value = "参数对象")
    })
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT")
    @PutMapping
    public BaseResult update(@Validated(Update.class) AccountChuVO vo) {
        AccountChuPO po = AccountChuMsMapper.INSTANCE.vo2po(vo);
        boolean result = accountChuMpService.updateById(po);
        if (result) {
            return BaseResult.successMsg("修改成功");
        } else {
            return BaseResult.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param id 编号id
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "编号id")
    })
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/{id}")
    public BaseResult delete(@PathVariable("id") Long id) {
        boolean result = accountChuMpService.removeById(id);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param idList 编号id集合
     * @return 返回结果
    */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "编号id集合")
    })
    @ApiOperation(value = "批量删除", notes = "批量删除", httpMethod = "DELETE")
    @DeleteMapping
    public BaseResult deleteByIdList(@RequestParam("idList") List<Long> idList) {
        boolean result = accountChuMpService.removeByIds(idList);
        if (result) {
            return BaseResult.successMsg("删除成功");
        } else {
            return BaseResult.error("删除失败");
        }
    }
    private LambdaQueryChainWrapper<AccountChuPO> onSelectWhere(AccountChuVO vo) {
        LambdaQueryChainWrapper<AccountChuPO> queryWrapper = accountChuMpService.lambdaQuery();

           if (Objects.isNull(vo)) {
               return queryWrapper;
           }
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), AccountChuPO::getId, vo.getId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getStockRemoval()), AccountChuPO::getStockRemoval, vo.getStockRemoval());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getOutboundAmoun()), AccountChuPO::getOutboundAmoun, vo.getOutboundAmoun());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getDifference()), AccountChuPO::getDifference, vo.getDifference());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAccountId()), AccountChuPO::getAccountId, vo.getAccountId());
           queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getAccountRu()), AccountChuPO::getAccountRu, vo.getAccountRu());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getPicture()), AccountChuPO::getPicture, vo.getPicture());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getReceiptor()), AccountChuPO::getReceiptor, vo.getReceiptor());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getOutboundway()), AccountChuPO::getOutboundway, vo.getOutboundway());
           queryWrapper.eq(StringUtils.isNotBlank(vo.getTally()), AccountChuPO::getTally, vo.getTally());
           return queryWrapper;
    }


}
