package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.AliMessageUtil;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.*;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.*;
import com.diandong.enums.*;
import com.diandong.mapstruct.*;
import com.diandong.service.*;
import com.ruoyi.common.constant.LoginTypeConstants;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.sms.SmsCodeAuthenticationToken;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.pay.model.enums.BizTypeEnum;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.res.ResPayResultVO;
import com.ruoyi.pay.service.PayFactory;
import com.ruoyi.pay.service.PayService;
import com.ruoyi.system.constant.MealSettingConstants;
import com.ruoyi.system.constant.SysConstants;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Classname AppServiceImpl
 * @Description APP的各种常用接口
 * @Date 2022/6/13 11:20
 * @Created by YuLiu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AppServiceImpl implements AppService {

    @Resource
    private ISysUserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private SysLoginService loginService;
    @Resource
    private FaceRecognitionMpService faceRecognitionMpService;
    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private BizDictMpService bizDictMpService;
    @Resource
    private DishesMpService dishesMpService;
    @Resource
    private ChefManagementMpService chefManagementMpService;
    @Resource
    private PaymentConfigMpService paymentConfigMpService;
    @Resource
    private CanteenCarouselMpService canteenCarouselMpService;
    @Resource
    private OrderMpService orderMpService;
    @Resource
    private OrderDetailMpService orderDetailMpService;
    @Resource
    private UserAmountMpService userAmountMpService;
    @Resource
    private IPayService payService;
    @Resource
    private PhysicalCardMpService physicalCardMpService;
    @Resource
    private RechargeAmountRecordsMpService rechargeAmountRecordsMpService;
    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private PayFactory payFactory;
    @Resource
    private OpinionFeedbackMpService opinionFeedbackMpService;
    //    @Resource
//    private ISysConfigService configService;
    @Resource
    private BusinessConfigMpService businessConfigMpService;

    /**
     * 找回密码的通用key
     */
    private static final String RESET_PWD = "reset_pwd:";

    @Override
    public void getPhoneVerifyCode(String phone) {
        String phoneVerifyCode = AliMessageUtil.getPhonemsg(phone);
        stringRedisTemplate.opsForValue().set(phone, phoneVerifyCode, 120L, TimeUnit.SECONDS);
    }

    @Override
    public BaseResult phoneRegisterAndLogin(PhoneRegisterAndLoginVO phoneLogin) {

        String phone = phoneLogin.getPhone();

//        获取验证码
        String realCode = stringRedisTemplate.opsForValue().get(phone);
        if (!phoneLogin.getCode().equals(realCode)) {
            return BaseResult.error("验证码错误");
        }
        SysUser sysUser = userService.lambdaQuery()
                .eq(SysUser::getPhonenumber, phone)
                .eq(SysUser::getDelFlag, String.valueOf(com.diandong.constant.Constants.DEL_NO))
                .one();

        if (Objects.isNull(sysUser)) {
            sysUser = new SysUser();
            sysUser.setUserName(phone);
            sysUser.setPhonenumber(phone);
            sysUser.setNickName("用户" + phone);
            if (StringUtils.isNotBlank(phoneLogin.getPassword())) {
                sysUser.setPassword(SecurityUtils.encryptPassword(phoneLogin.getPassword()));
            }
            sysUser.setCreateTime(new Date());
            userService.insertUser(sysUser);
        } else {
            return BaseResult.error("该手机号已经注册，请勿重复注册");
        }

        String token = loginVerify(new AppLoginVO(phoneLogin.getPhone(), LoginTypeConstants.PHONE));
        return BaseResult.success(token);
    }

    @Override
    public BaseResult appLogin(AppLoginVO appLoginVO) {

        //                    验证码校验
        if (LoginTypeConstants.PHONE.equals(appLoginVO.getType())) {
            String realCode = stringRedisTemplate.opsForValue().get(appLoginVO.getPhone());
            if (!appLoginVO.getAuthCode().equals(realCode)) {
                return BaseResult.error("验证码错误");
            }
        }
        return BaseResult.success(loginVerify(appLoginVO));
    }

    public String loginVerify(AppLoginVO appLoginVO) {

        // 用户验证
        Authentication authentication = null;
        try {

            if (LoginTypeConstants.ACCOUNT.equals(appLoginVO.getType())) {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appLoginVO.getAccount(), appLoginVO.getAuthCode()));
            } else if (LoginTypeConstants.PHONE.equals(appLoginVO.getType())) {
                authentication = authenticationManager.authenticate(new SmsCodeAuthenticationToken(appLoginVO.getPhone()));
            }
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(StringUtils.isNotBlank(appLoginVO.getAccount()) ? appLoginVO.getAccount() : appLoginVO.getPhone(), "Error", MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(StringUtils.isNotBlank(appLoginVO.getAccount()) ? appLoginVO.getAccount() : appLoginVO.getPhone(), "Error", e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(StringUtils.isNotBlank(appLoginVO.getAccount()) ? appLoginVO.getAccount() : appLoginVO.getPhone(), "Success", MessageUtils.message("user.login.success")));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        loginService.recordLoginInfo(loginUser.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }

    @Override
    public BaseResult getChangePhoneVerifyCode(String phone, String type) {
        SysUser user = userService.lambdaQuery().eq(SysUser::getPhonenumber, phone).one();
        switch (type) {
            case Constants.OLD_PHONE_VERIFY_CODE:
                if (Objects.isNull(user)) {
                    return BaseResult.error("用户不存在");
                }
                break;
            case Constants.NEW_PHONE_VERIFY_CODE:
                if (Objects.nonNull(user)) {
                    return BaseResult.error("新手机号已注册");
                }
                break;
            default:
                return BaseResult.error("获取手机验证码失败");
        }

        String code = AliMessageUtil.getPhonemsg(phone);
        stringRedisTemplate.opsForValue().set(phone, code, 180L, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    @Override
    public BaseResult changePhone(String oldPhoneCode, String newPhone, String newPhoneCode) {

        String oldPhone = SecurityUtils.getLoginUser().getUser().getPhonenumber();

        SysUser user = userService.lambdaQuery().eq(SysUser::getPhonenumber, newPhone).one();
        if (user != null) {
            return BaseResult.error("手机号已注册");
        }
        String realCode = stringRedisTemplate.opsForValue().get(oldPhone);
        if (!oldPhoneCode.equals(realCode)) return BaseResult.error("原手机号验证码错误");
        realCode = stringRedisTemplate.opsForValue().get(newPhone);
        if (!newPhoneCode.equals(realCode)) return BaseResult.error("新手机号验证码错误");
        userService.lambdaUpdate().eq(SysUser::getPhonenumber, oldPhone).set(SysUser::getPhonenumber, newPhone).update();
        return BaseResult.success(userService.lambdaQuery().eq(SysUser::getPhonenumber, newPhone).one());
    }

    @Override
    public BaseResult resetPwdVerifyCode(ResetPwdVO vo) {
        String phone = vo.getPhone();
        String code = AliMessageUtil.getPhonemsg(phone);
        stringRedisTemplate.opsForValue().set(RESET_PWD + phone, code, 180L, TimeUnit.SECONDS);
        return BaseResult.success();
    }

    @Override
    public BaseResult resetVerifyPhoneCode(ResetPwdVO vo) {

        SysUser user = userService.lambdaQuery().eq(SysUser::getPhonenumber, vo.getPhone()).eq(SysUser::getDelFlag, Constants.DEL_NO).one();
        if (Objects.isNull(user)) {
            log.error("手机号：{}，对应的用户不存在");
            return BaseResult.error("验证码校验失败");
        }
        String code = stringRedisTemplate.opsForValue().get(RESET_PWD + vo.getPhone());
        if (StringUtils.isBlank(code) || !code.equals(vo.getVerifyCode())) {
            return BaseResult.error("验证码校验失败");
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult resetPwd(ResetPwdVO vo) {

        SysUser user = userService.lambdaQuery().eq(SysUser::getPhonenumber, vo.getPhone()).eq(SysUser::getDelFlag, Constants.DEL_NO).one();
        if (Objects.isNull(user)) {
            log.error("手机号：{}，对应的用户不存在");
            return BaseResult.error("设置密码失败");
        }

        //将密码保存到库
        userService.lambdaUpdate().set(SysUser::getPassword, SecurityUtils.encryptPassword(vo.getPassword()))
                .eq(SysUser::getUserId, user.getUserId()).update();

        return BaseResult.success();
    }

    @Override
    public BaseResult getUserInfo() {
        SysUser user = null;
        if (SecurityUtils.isLogin()) {
            user = SecurityUtils.getLoginUser().getUser();

            FaceRecognitionPO faceRecognition = faceRecognitionMpService.lambdaQuery().eq(FaceRecognitionPO::getUserId, user.getUserId()).eq(FaceRecognitionPO::getDelFlag, Constants.DEL_NO).last(Constants.limit).one();
            user.setFaceImage(faceRecognition.getFacePicture());
        }

        return BaseResult.success(user);
    }

    @Override
    public BaseResult submitReview(SysUserVO vo) {

        vo.setUserId(SecurityUtils.getUserId());


        boolean update = userService.lambdaUpdate()
                .set(StringUtils.isNotBlank(vo.getUserType()), SysUser::getUserType, vo.getUserType())
                .set(StringUtils.isNotBlank(vo.getSex()), SysUser::getSex, vo.getSex())
                .set(StringUtils.isNotBlank(vo.getNickName()), SysUser::getNickName, vo.getNickName())
                .set(SysUser::getCheckState, Constants.USER_CHECKED)
                .eq(SysUser::getUserId, vo.getUserId())
                .update();
        if (!update) {
            return BaseResult.error("提交审核失败");
        }

//        保存人脸图片
        String faceImage = vo.getFaceImage();

//        查询当前用是否有未删除的人脸图片信息 如果有则进行更新
        FaceRecognitionPO faceRecognition = faceRecognitionMpService.lambdaQuery().eq(FaceRecognitionPO::getUserId, vo.getUserId()).eq(FaceRecognitionPO::getDelFlag, Constants.DEL_NO).last("limit 1").one();
        if (Objects.nonNull(faceRecognition)) {
            faceRecognition.setFacePicture(faceImage);
        } else {
//            如果没有人脸图片 防止有异常数据 删除有逻辑删除当前用户id的人脸图片

            List<FaceRecognitionPO> list = faceRecognitionMpService.lambdaQuery().eq(FaceRecognitionPO::getUserId, vo.getUserId()).list();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> collect = list.stream().map(FaceRecognitionPO::getId).collect(Collectors.toList());
                faceRecognitionMpService.removeByIds(collect);
            }

            faceRecognition = new FaceRecognitionPO();
            faceRecognition.setFacePicture(faceImage);
            faceRecognition.setUserId(vo.getUserId());
        }
        faceRecognitionMpService.saveOrUpdate(faceRecognition);

        LoginUser loginUser = SecurityUtils.getLoginUser();

        SysUser user = loginUser.getUser();

        user.setCheckState(Constants.USER_CHECKED);
        user.setUserType(vo.getUserType());
        user.setFaceImage(vo.getFaceImage());
        user.setSex(vo.getSex());
        user.setNickName(vo.getNickName());
        /**
         * 刷新Token
         */
        tokenService.refreshToken(loginUser);
        return BaseResult.success();
    }

    @Override
    public BaseResult canteenList() {
        List<CanteenPO> list = canteenMpService.lambdaQuery().eq(CanteenPO::getDelFlag, Constants.DEL_NO).list();
        List<CanteenDTO> canteenDTOList = CanteenMsMapper.INSTANCE.poList2dtoList(list);
        return BaseResult.success(canteenDTOList);
    }

    @Override
    public BaseResult setDiningHall(Long canteenId) {

        boolean result = userService.lambdaUpdate().set(SysUser::getDiningCanteenId, canteenId)
                .eq(SysUser::getUserId, SecurityUtils.getUserId())
                .update();

        LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.getUser().setDiningCanteenId(canteenId);
        tokenService.refreshToken(loginUser);
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error("选择食堂失败");
        }
    }

    @Override
    public BaseResult getCanteenCarousel() {

        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();

        List<CanteenCarouselPO> list = canteenCarouselMpService.lambdaQuery()
                .eq(CanteenCarouselPO::getCanteenId, diningCanteenId)
                .eq(CanteenCarouselPO::getDelFlag, Constants.DEL_NO)
                .orderByAsc(CanteenCarouselPO::getSort)
                .orderByDesc(CanteenCarouselPO::getId)
                .list();
        return BaseResult.success(CanteenCarouselMsMapper.INSTANCE.poList2dtoList(list));
    }

    @Override
    public BaseResult getTodayRecipe(Long canteenId) {

        RecipePO recipe = recipeMpService.lambdaQuery().eq(RecipePO::getCanteenId, canteenId)
                .eq(RecipePO::getRecipeDate, LocalDate.now())
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .one();
        return BaseResult.success(resetRecipe(recipe));
    }

    @Override
    public BaseResult getWeekRecipe(Long canteenId) {

//        Date weekDay = instance.getTime();
//        获取当前日期
        LocalDate today = LocalDate.now();
//        获取7天后的日期
        LocalDate weekDay = today.plusDays(6L);


//        根据日期范围获取食谱列表
        List<RecipePO> list = recipeMpService.lambdaQuery().eq(RecipePO::getCanteenId, canteenId)
                .between(RecipePO::getRecipeDate, today, weekDay)
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .orderByAsc(RecipePO::getRecipeDate)
                .list();

        List<RecipeDTO> resultList = new ArrayList<>();
//        补充食谱信息
        for (RecipePO recipePO : list) {
            resultList.add(resetRecipe(recipePO));
        }

        return BaseResult.success(resultList);
    }

    @Override
    public BaseResult searchDishes(RecipeDetailVO vo) {
//        如果没有食谱id则查询出当天的食谱id
        if (Objects.isNull(vo.getRecipeId())) {
            Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();

            RecipePO recipe = recipeMpService.lambdaQuery()
                    .eq(RecipePO::getCanteenId, diningCanteenId)
                    .eq(RecipePO::getRecipeDate, LocalDate.now())
                    .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            vo.setRecipeId(recipe.getId());
        }
//        查询菜谱详情信息
        List<RecipeDetailPO> list = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getRecipeId, vo.getRecipeId())
                .eq(Objects.nonNull(vo.getMealTimesId()), RecipeDetailPO::getMealTimesId, vo.getMealTimesId())
                .eq(Objects.nonNull(vo.getDishesTypeId()), RecipeDetailPO::getDishesTypeId, vo.getDishesTypeId())
                .like(StringUtils.isNotBlank(vo.getDishesName()), RecipeDetailPO::getDishesName, vo.getDishesName())
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .list();

        List<RecipeDetailDTO> recipeDetailList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            recipeDetailList = RecipeDetailMsMapper.INSTANCE.poList2dtoList(list);

            recipeDetailList.forEach(recipeDetailDTO -> {
                recipeDetailDTO.setDishes(DishesMsMapper.INSTANCE.po2dto(dishesMpService.getById(recipeDetailDTO.getDishesId())));
            });
        }
        return BaseResult.success(recipeDetailList);
    }

    @Override
    public BaseResult getDishesMessage(Long id) {


        RecipeDetailPO recipeDetailPO = recipeDetailMpService.getById(id);

        DishesDTO dto = DishesMsMapper.INSTANCE.po2dto(dishesMpService.getById(recipeDetailPO.getDishesId()));
        dishesMpService.getAllDishMsg(dto);


        ChefManagementPO chef = chefManagementMpService.getById(recipeDetailPO.getChefId());
        dto.setChef(ChefManagementMsMapper.INSTANCE.po2dto(chef));
        return BaseResult.success(dto);
    }

    @Override
    public BaseResult getPayWay() {
        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();
        List<PaymentConfigPO> list = paymentConfigMpService.lambdaQuery()
                .eq(PaymentConfigPO::getCanteenId, diningCanteenId)
                .eq(PaymentConfigPO::getStatus, Constants.DEFAULT_YES)
                .in(PaymentConfigPO::getPayway, PaymentMethodEnum.FaceRecognition.getValue(), PaymentMethodEnum.AliPay.getValue(), PaymentMethodEnum.WeChatPay.getValue(), PaymentMethodEnum.ElectronicCard.getValue())
                .list();

        return BaseResult.success(PaymentConfigMsMapper.INSTANCE.poList2dtoList(list));
    }

    @Override
    @Transactional
    public BaseResult pay(OrderPayVO orderPay) {

        OrderPO order = orderMpService.lambdaQuery()
                .eq(OrderPO::getDelFlag, Constants.DEL_NO)
                .eq(OrderPO::getId, orderPay.getOrderId())
                .eq(OrderPO::getStatus, OrderStatusEnum.WAIT_PAYMENT.value())
                .eq(OrderPO::getCreateBy, SecurityUtils.getUserId())
                .last(Constants.limit).one();
        if (Objects.isNull(order)) {
            return BaseResult.error("未找到该支付订单");
        }

//        查询支付配置
        PaymentConfigPO paymentConfig = paymentConfigMpService.getById(orderPay.getPayWay());
        PaymentMethodEnum methodEnum = PaymentMethodEnum.getEnum(paymentConfig.getPayway());

//        设置订单支付完成信息
        order.setPaymentMethodId(paymentConfig.getId());
        order.setPaymentMethodName(paymentConfig.getPaymentMethod());

        switch (methodEnum) {
            case Cash://    现金
                //                更新订单信息
                order.setStatus(OrderStatusEnum.COMPLETED.value());
                order.setPaymentTime(LocalDateTime.now());

                orderMpService.updateById(order);
                return BaseResult.success();

            case PhysicalCard://   实体卡
            case ElectronicCard://  电子卡

                Long userId = SecurityUtils.getUserId();
                UserAmountPO userAmount = userAmountMpService.lambdaQuery()
                        .eq(UserAmountPO::getUserId, userId)
                        .eq(UserAmountPO::getDelFlag, Constants.DEL_NO)
                        .last(Constants.limit).one();
                if (Objects.isNull(userAmount) || userAmount.getSubsidy().add(userAmount.getAmount()).compareTo(order.getOrderTotalPrice()) < 0) {
                    return BaseResult.error("账户余额不足，请充值");
                }
//                更新订单信息
                order.setStatus(OrderStatusEnum.COMPLETED.value());
                order.setPaymentTime(LocalDateTime.now());

                orderMpService.updateById(order);
//                处理用户余额信息
                payService.cardPayTask(order.getOrderTotalPrice(), userAmount);
                return BaseResult.success();
            case FaceRecognition:// 人脸认证
                userId = SecurityUtils.getUserId();
                userAmount = userAmountMpService.lambdaQuery()
                        .eq(UserAmountPO::getUserId, userId)
                        .eq(UserAmountPO::getDelFlag, Constants.DEL_NO)
                        .last(Constants.limit).one();
                if (Objects.isNull(userAmount) || userAmount.getSubsidy().add(userAmount.getAmount()).compareTo(order.getOrderTotalPrice()) < 0) {
                    return BaseResult.error("账户余额不足，请充值");
                }
//                更新订单信息
                order.setStatus(OrderStatusEnum.COMPLETED.value());
                order.setPaymentTime(LocalDateTime.now());

                orderMpService.updateById(order);
//                处理用户余额信息
                payService.faceEnginePayTask(order.getOrderTotalPrice(), userAmount);
                return BaseResult.success();
            case WeChatPay://   微信支付
//                PayWay payWay = PayWay.WECHAT_APP;
                PayWay payWay = PayWay.WECHAT_NATIVE;
                ReqPayVO reqPayVO = new ReqPayVO();
                reqPayVO.setPayWay(payWay);
                reqPayVO.setOrderId(order.getId().toString());
                reqPayVO.setSubject("食堂支付");
//                reqPayVO.setRunningNum(bizIdUtil.createPayOrderNumber(BizTypeEnum.ORDER.getValue().toString(), SecurityUtils.getUserId()));
                reqPayVO.setRunningNum(String.valueOf(System.currentTimeMillis()));
                reqPayVO.setPayMoney(order.getOrderTotalPrice());
                reqPayVO.setType(BizTypeEnum.ORDER);
                PayService payService = payFactory.createPayService(payWay);
                ResPayResultVO balance = payService.createBalance(reqPayVO);
                return BaseResult.success(balance.getWechatNativeResult());
            case AliPay://  支付宝支付
                payWay = PayWay.ALI_APP;
                reqPayVO = new ReqPayVO();
                reqPayVO.setPayWay(payWay);
                reqPayVO.setOrderId(order.getId().toString());
                reqPayVO.setSubject("食堂支付");
                reqPayVO.setRunningNum(bizIdUtil.createPayOrderNumber(BizTypeEnum.ORDER.getValue().toString(), SecurityUtils.getUserId()));
                reqPayVO.setPayMoney(order.getOrderTotalPrice());
                reqPayVO.setType(BizTypeEnum.ORDER);
                payService = payFactory.createPayService(payWay);
                balance = payService.createBalance(reqPayVO);
                return BaseResult.success(balance.getAliAppResult());
            default:
                break;
        }

        return BaseResult.error("支付失败");
    }

    @Override
    public BaseResult orderList(OrderVO vo) {
        Page<OrderPO> page = orderMpService.lambdaQuery()
                .eq(OrderPO::getCreateBy, SecurityUtils.getUserId())
                .eq(Objects.nonNull(vo.getStatus()), OrderPO::getStatus, vo.getStatus())
                .between(Objects.nonNull(vo.getStartTime()) && Objects.nonNull(vo.getEndTime()), OrderPO::getOrderTime, vo.getStartTime(), vo.getEndTime())
                .eq(OrderPO::getDelFlag, Constants.DEL_NO)
                .page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            for (OrderPO order : page.getRecords()) {

                List<OrderDetailPO> list = orderDetailMpService.lambdaQuery()
                        .eq(OrderDetailPO::getOrderId, order.getId())
                        .list();
                order.setOrderDetailList(list);
            }
        }

        return BaseResult.success(page);
    }

    @Override
    public BaseResult userAmount() {
        Long userId = SecurityUtils.getUserId();
//        个人账户信息
        UserAmountPO userAmount = userAmountMpService.lambdaQuery().eq(UserAmountPO::getUserId, userId).one();
        UserAmountDTO userAmountDTO = UserAmountMsMapper.INSTANCE.po2dto(userAmount);

//        查询实体卡信息
        PhysicalCardPO physicalCard = physicalCardMpService.lambdaQuery().eq(PhysicalCardPO::getUserId, userId).one();
        if (Objects.nonNull(physicalCard)) {
            userAmountDTO.setPhysicalCard(PhysicalCardMsMapper.INSTANCE.po2dto(physicalCard));
        }

        return BaseResult.success(userAmountDTO);
    }

    @Override
    public BaseResult getCanteenRecharge() {
        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();

        List<BizDictPO> bizDictList = bizDictMpService.lambdaQuery().eq(BizDictPO::getCanteenId, diningCanteenId)
                .eq(BizDictPO::getDictType, SysConstants.RECHARGE_SETTING)
                .eq(BizDictPO::getDelFlag, Constants.DEL_NO)
                .list();

        return BaseResult.success(BizDictMsMapper.INSTANCE.poList2dtoList(bizDictList));
    }

    @Override
    public BaseResult appRecharge(AppRechargeVO vo) {

        Long userId = SecurityUtils.getUserId();
//        用户余额
        UserAmountPO userAmount = userAmountMpService.lambdaQuery()
                .eq(UserAmountPO::getUserId, userId)
                .last(Constants.limit).one();

        if (Objects.isNull(userAmount)) {
            userAmount = new UserAmountPO();
            userAmount.setAmount(vo.getAmount());
            userAmount.setSubsidy(BigDecimal.ZERO);
            userAmount.setTimes(Constants.DEFAULT_NO);
            userAmount.setUserId(userId);
        } else {
            if (Constants.DEL_YES == userAmount.getDelFlag()) {
                userAmount.setAmount(vo.getAmount());
                userAmount.setSubsidy(BigDecimal.ZERO);
                userAmount.setTimes(Constants.DEFAULT_NO);
                userAmount.setDelFlag(Constants.DEL_NO);
            } else {
                userAmount.setAmount(userAmount.getAmount().add(vo.getAmount()));
            }
        }
        userAmountMpService.saveOrUpdate(userAmount);
//        充值记录
        RechargeAmountRecordsPO rechargeAmountRecords = new RechargeAmountRecordsPO();
        rechargeAmountRecords.setSerialNumber(bizIdUtil.getRechargeAmountNo());
        rechargeAmountRecords.setUserId(userId);
        rechargeAmountRecords.setRechargeType(RechargeTypeEnum.ELECTRONIC_CARD.type());
        rechargeAmountRecords.setUserId(userId);
        rechargeAmountRecords.setAmount(vo.getAmount());
        rechargeAmountRecords.setRechargeMethod(RechargeMethodEnum.APP.type());

        rechargeAmountRecordsMpService.save(rechargeAmountRecords);

//        获取用户实体卡信息
//        PhysicalCardPO physicalCardPO = physicalCardMpService.lambdaQuery().eq(PhysicalCardPO::getUserId, userId).eq(PhysicalCardPO::getDelFlag, Constants.DEL_NO).last(Constants.limit).one();
//        UserAmountDTO userAmountDTO = UserAmountMsMapper.INSTANCE.po2dto(userAmount);
//        userAmountDTO.setPhysicalCard(PhysicalCardMsMapper.INSTANCE.po2dto(physicalCardPO));

        PaymentConfigPO paymentConfig = paymentConfigMpService.getById(vo.getPayWay());
        PaymentMethodEnum methodEnum = PaymentMethodEnum.getEnum(paymentConfig.getPayway());
        ReqPayVO payVO = new ReqPayVO();
        payVO.setOpenId(rechargeAmountRecords.getSerialNumber());
        payVO.setPayMoney(vo.getAmount());
        payVO.setSubject("账户充值");
        payVO.setRunningNum(bizIdUtil.createPayOrderNumber(BizTypeEnum.RECHARGE_AMOUNT.getValue().toString(), SecurityUtils.getUserId()));
        payVO.setType(BizTypeEnum.RECHARGE_AMOUNT);

        switch (methodEnum) {
            case WeChatPay:
                PayWay payWay = PayWay.WECHAT_APP;
                payVO.setPayWay(payWay);
                PayService payService = payFactory.createPayService(payWay);
                ResPayResultVO balance = payService.createBalance(payVO);
                return BaseResult.success(balance.getWechatAppPayResult());
            case AliPay:
                payWay = PayWay.ALI_APP;
                payVO.setPayWay(PayWay.ALI_APP);
                payService = payFactory.createPayService(payWay);
                balance = payService.createBalance(payVO);
                return BaseResult.success(balance.getAliAppResult());
            default:
                break;
        }
        return BaseResult.error("预支付失败");
    }

    @Override
    public BaseResult opinionFeedback(OpinionFeedbackVO vo) {

        Page<OpinionFeedbackPO> page = opinionFeedbackMpService.lambdaQuery()
                .eq(OpinionFeedbackPO::getCreateBy, SecurityUtils.getUserId())
                .eq(OpinionFeedbackPO::getDelFlag, Constants.DEL_NO)
                .eq(Objects.nonNull(vo.getOpinionId()), OpinionFeedbackPO::getOpinionId, vo.getOpinionId())
                .eq(Objects.nonNull(vo.getStatus()), OpinionFeedbackPO::getStatus, vo.getStatus())
                .page(new Page<>(vo.getPageNum(), vo.getPageSize()));
        return BaseResult.success(page);
    }

    @Override
    public BaseResult setStudentPayWay() {

        BusinessConfigPO businessConfig = businessConfigMpService.lambdaQuery()
                .eq(BusinessConfigPO::getConfigName, BizConfigEnum.STUDENT_PAY_WAY.key())
                .eq(BusinessConfigPO::getCanteenId, SecurityUtils.getCanteenId())
                .eq(BusinessConfigPO::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();

        return BaseResult.success(businessConfig);
    }

    @Override
    public BaseResult setStudentPayWay(BusinessConfigVO vo) {

        /**
         * 查看是否有设置
         */
        BusinessConfigPO businessConfig = businessConfigMpService.lambdaQuery()
                .eq(BusinessConfigPO::getConfigName, BizConfigEnum.STUDENT_PAY_WAY.key())
                .eq(BusinessConfigPO::getCanteenId, SecurityUtils.getCanteenId())
                .eq(BusinessConfigPO::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();

//        当没有时进行添加
        if (Objects.isNull(businessConfig)) {
            businessConfig = new BusinessConfigPO();
            businessConfig.setConfigName(BizConfigEnum.STUDENT_PAY_WAY.key());
            businessConfig.setCanteenId(SecurityUtils.getCanteenId());
            businessConfig.setDataState(Constants.DEFAULT_YES);

        }
//        更新业务配置
        businessConfig.setConfigValue(vo.getConfigValue());
        businessConfigMpService.saveOrUpdate(businessConfig);
        return BaseResult.success(businessConfig);
    }

    private RecipeDTO resetRecipe(RecipePO recipe) {

        RecipeDTO recipeDTO = RecipeMsMapper.INSTANCE.po2dto(recipe);
        List<BizDictPO> dictList = bizDictMpService.lambdaQuery().in(BizDictPO::getDictValue, MealSettingConstants.defaultMeals).eq(BizDictPO::getDictType, SysConstants.MEAL_SETTING).list();

        List<Long> collect = dictList.stream().map(BizDictPO::getId).collect(Collectors.toList());
        List<RecipeDetailPO> recipeDetailList = recipeDetailMpService.lambdaQuery()
                .eq(RecipeDetailPO::getRecipeId, recipeDTO.getId())
                .in(RecipeDetailPO::getMealTimesId, collect)
                .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                .list();
        recipeDTO.setRecipeDetailList(resetRecipeDetailDTO(recipeDetailList));
//        对食谱详情进行分组
        Map<Long, List<RecipeDetailPO>> map = recipeDetailList.stream().collect(Collectors.groupingBy(RecipeDetailPO::getMealTimesId));

//        对返回数据进行封装
        for (BizDictPO bizDictPO : dictList) {
            if (MealSettingConstants.BREAKFAST.equals(bizDictPO.getDictValue())) {
//                早餐
                recipeDTO.setBreakfastList(resetRecipeDetailDTO(map.get(bizDictPO.getId())));

            } else if (MealSettingConstants.LUNCH.equals(bizDictPO.getDictValue())) {
//                午餐
                recipeDTO.setLunchList(resetRecipeDetailDTO(map.get(bizDictPO.getId())));
            } else if (MealSettingConstants.DINNER.equals(bizDictPO.getDictValue())) {
//                晚餐
                recipeDTO.setDinnerList(resetRecipeDetailDTO(map.get(bizDictPO.getId())));
            }
        }

        return recipeDTO;
    }

    private List<RecipeDetailDTO> resetRecipeDetailDTO(List<RecipeDetailPO> recipeDetailPOList) {

        if (CollectionUtils.isNotEmpty(recipeDetailPOList)) {
            List<RecipeDetailDTO> recipeDetailDTOList = new ArrayList<>();

            for (RecipeDetailPO recipeDetailPO : recipeDetailPOList) {
                RecipeDetailDTO recipeDetailDTO = RecipeDetailMsMapper.INSTANCE.po2dto(recipeDetailPO);

                DishesPO dishes = dishesMpService.getById(recipeDetailDTO.getDishesId());
                if (Objects.nonNull(dishes)) {
                    recipeDetailDTO.setDishesPicture(dishes.getDishesPicture());
                    recipeDetailDTO.setDishesTypeId(dishes.getDishesTypeId());
                    recipeDetailDTO.setDishesTypeName(dishes.getDishesTypeName());
                    recipeDetailDTOList.add(recipeDetailDTO);
                }
            }
            return recipeDetailDTOList;
        }
        return null;
    }


    /**
     * 获取当前食谱信息
     *
     * @return
     */
    private RecipePO getTodayRecipe() {
        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();

        return recipeMpService.lambdaQuery()
                .eq(RecipePO::getCanteenId, diningCanteenId)
                .eq(RecipePO::getRecipeDate, LocalDate.now())
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();
    }


}
