package com.ruoyi.pay.model.enums;

import lombok.Getter;

public enum RespEnum {

	/**
	 * System
	 */
	SUCCESS(200, "请求成功"),
	DATABASE_CONECT_ERROR(300, "数据库连接异常"),
	DATABASE_BIND_ERROR(301, "数据库参数绑定异常"),
	ERROR_SYSTEM_PARAM(302, "系统参数配置错误"),
	ERROR_INNER_DATA(303, "内部数据错误"),
	GET_ERROR_SYSTEM_PARAM(304, "获取系统参数错误"),
	REDIS_CONECT_ERROR(305, "Redis连接异常"),
	SERVER_ERROR(500, "服务器正在维护，请稍后重试"),
	REQUEST_PARAM_ERROR(2000, "请求参数不正确"),
	REQUEST_REDIS_PARAM_ERROR(2001, "缓存参数不正确"),
	REQUEST_REDIS_STATUS_ERROR(2002, "缓存状态不正确"),
	USER_AUTHENTICATION(400, "用户验证失败"),
	LONG_TERM_USER_NOT_LOGGED_IN(401, "用户长期未登录"),
	SYSTEM_AUTHENTICATION(405, "非法请求"),
	GET_MESSAGE_ERROR(406, "获取信息失败"),


	SERVER_TASK_ERROR(502, "定时器异常"),


	NETWORK_ERROR(601, "网络异常"),
	ERROR_RUNNING_NUM(701, "流水号错误"),
	SAME_RUNNING_NUM(702, "该笔订单已有人正在支付"),
	PHONE_CAPTCHA_ERROR(703, "手机验证码错误"),
	TOO_BUSY(800, "您的操作太频繁，请稍后几秒钟再试"),
	IS_DELETE(801, "该数据已被删除"),
	ERROR_MESSAGE(806, "%s"),
	ERROR_DB_WRITE(807, "数据库写入异常"),


	INCORRECT_ACCOUNT_OR_PASSWORD(2001, "账号或密码不正确"),
	THE_ACCOUNT_HAS_BEEN_LOCKED_PLEASE_CONTACT_THE_ADMINISTRATOR(2002, "账号已被锁定,请联系管理员"),
	NO_AUTHORITY_PLEASE_CONTACT_THE_ADMINISTRATOR_TO_AUTHORIZE(1008, "没有权限，请联系管理员授权"),


	/**
	 * User
	 */
	NO_THIS_USER(3001, "该用户不存在"),
	USERNAME_PASSWORD_ERROR(3014, "账号或者密码错误"),
	PASSWORD_IS_LOCK(3015, "输入密码出错过多，账户已被锁定%s小时，请稍后登录。"),
	USER_PWD_FAILED(3020, "密码修改失败"),
	USER_PWD_ERROR(3021, "密码不正确，您还可以输入%s次"),
	USER_PWD_VERIFY(3022, "新密码和确认密码输入不匹配"),
	USER_PHONE_VERIFY(3023, "请输入注册时填写的手机号"),
	USER_AUTHENTICATION_TYPE_FAIL(3024, "第三方登录验证，类型错误，当前仅支持qq，微信，新浪微博"),
	USER_AUTHENTICATION_OUT_TIME(3025, "第三方登录验证凭证已过期"),
	USER_AUTHENTICATION_FAIL(3026, "第三方登录失败"),
	USER_REGISTERED_FAILURE(3027, "用户注册失败"),
	UPDATE_MY_FILES_FAILED(3034, "修改我的信息失败"),
	OLD_PASSWORD_INCORRECT(3040, "旧密码不正确"),
	OLD_AND_NEW_PASSWORD_INCORRECT(3041, "新密码与旧密码一致，请重新填写"),
	PASSWORD_IS_NOT_SAME(3042, "您两次输入的密码不一致，请点击图标显示密码核对后再次设置"),
	USER_LOGIN_PWD_ERROR_COUNT(3043, "今日输入错误次数已达上限;请24小时后再次操作，若有疑问请联系客服"),
	USER_LOGIN_PWD_ERROR(3044, "您输入的密码有误，今日还有%s次输入机会"),
	USER_ACCOUNT_ABNORMAL(3045, "账户状态异常，请联系客服人员"),
	USER_EXIST(3046, "用户已存在"),
	MEMBER_INFORMATION_EXIST(3047, "会员身份已存在"),
	HIGHEST_MEMBERSHIP_LEVEL(3048, "您已经是最高会员等级"),

	ADDRESS_NO_EXIST(3101, "地址不存在"),
	VERIFICATION_CODE_HAS_FAILED(3102, "验证码已失效"),
	QRCODE_STATUS_FAILED(3103,"二维码扫描状态错误"),
	QRCODE_KEY_NOT_EXTIST_FAILED(3104,"二维码已失效"),
	QRCODE_TOKEN_NOT_EXTIST(3105,"用户token信息错误"),
	QRCODE_USER_ID_EXTIST(3106,"该二维码已经被人扫描了"),
	QRCODE_PARAM_FAILED(3107,"参数错误"),


	/**
	 * 微信登录注册
	 */
	USER_WE_CHART_LOGIN_ERROR(3200, "微信登录异常"),
	USER_WE_CHART_THIRD_INFO_ERROR(3201, "获取微信登录信息失败"),

	USER_TYPE_NOT_EXIST(3250,"用户类型不存在"),

	/**
	 * 微信登录注册
	 */
	USER_QQ_LOGIN_ERROR(3202, "QQ登录异常"),
	USER_QQ_THIRD_INFO_ERROR(3203, "获取QQ登录信息失败"),

	/**
	 * 新浪登录注册
	 */
	USER_SINA_LOGIN_ERROR(3204, "新浪微博登录异常"),
	USER_SINA__THIRD_INFO_ERROR(3205, "获取新浪微博登录信息失败"),


	USER_LREADY_THIRD(3206, "该用户已被绑定"),
	USER_TOKEN__ERROR(3227, "获取登录认证token或OpenId失败"),
	USER_AUTHENTICATION_ERROR(3223, "第三方登录，用户验证失败"),

	CALLBACK_PHONE_BIND_ERROR(14002, "该手机号已绑定，请更换手机号或登录App解绑"),
	USER_THIRD_PARTY_AUTHENTICATION_ERROR(3224, "第三方用户验证失败"),

	JSAPI_TICKE_SERVICE_ERROR(14003, "wechat获取JSAPI_TICKE接口失败"),
	JSAPI_TICKE_SERVICE_EXCEPTION(14005, "wechat获取JSAPI_TICKE接口异常"),
	JSAPI_ACCESS_TOKEN_SERVICE_EXCEPTION(14006, "wechat获取ACCESS_TOKEN接口异常"),


	/**
	 * Account
	 */
	NOT_THIS_ACCOUNT(4000, "用户账户不存在或已被禁用"),
	INSUFFICENT_MONEY(4001, "余额不足"),
	PAYMENT_PASSWORD_ERROR(4002, "设置支付密码失败"),
	PAYMENT_PASSWORD_NOT_SET(4003, "未设置支付密码"),
	PAYMENT_PASSWORD_VERIFY(4004, "支付密码验证失败"),
	REPEAT_TRANSATION(4005, "重复交易"),
	PAYMENT_CHANNEL_NOT_SUPPORT(4006, "支付渠道不支持"),
	ACCOUNT_ERROR(4009, "支付失败"),
	ACCOUNT_NEW_ERROR(4010, "创建账户失败"),
	ACCOUNT_NOT_ACTIVE(4011, "账户不可用"),
	ACCOUNT_SERVER_ERROR(4012, "账户系统异常"),
	ACCOUNT_NULL(4013, "账户不存在"),
	ACCOUNT_ORDER_STATUS_ERROR(4014, "订单状态不正确"),
	USER_ACCOUNT_PWD_ERROR_COUNT(4015, "今日输入支付密码错误次数已达上限，账号已冻结。账号将于24小时以后解冻！"),
	ACCOUNT_BANK_CARD_NUMBER_ERROR(4016, "银行卡号格式错误"),
	ACCOUNT_PWD_FORMAT_ERROR(4017, "账户密码不能为连续数字或相同数字"),
	USER_PAY_PWD_ERROR(4018, "支付密码不正确，您还可以输入%s次"),
	ERR_PAY_OPERAT(4019, "业务类型不符"),
	ERR_NOT_MONEY(4020, "账户余额不足"),

	DELEGATE_POOL_INSERT_FAILED(4020, "创建中间账户明细记录失败"),
	DELEGATE_PAYMENT_INSERT_FAILED(4021, "创建代收付交易失败"),
	DELEGATE_PAYMENT_UPDATE_FAILED(4023, "更新代付交易状态失败"),

	RECHARGE_INSERT_FAILED(4025, "创建充值记录失败"),
	EXIST_BANKCARD_ERROR(4031, "该银行卡已存在"),
	ILLEGAL_BANKCARD_ERROR(4032, "该银行卡已被禁用"),
	MONEY_ERROR(4033, "金额有误"),
	NO_BALANCE_PLATFORM(4034, "今日平台可用提现余额不足，请明日再试"),
	NO_BALANCE_USER(4037, "用户账户余额不足"),
	IDENTIFICATION_API_ERROR(4038, "银行卡认证服务出错，请联系客服处理"),
	NOT_SUPPORT_THIS_BANK(4041, "不支持此银行，请更换银行卡重试"),
	ORDER_COMMIT_ERROR(4043, "系统交易频繁,请稍候再试"),
	PAY_ORDER_NOT_EXIST(4044, "支付订单不存在"),
	PAY_ORDER_ERROR(4045, "订单支付异常"),
	PAY_ORDER_MONEY_ERROR(4046, "订单支付金额异常"),
	PAY_ORDER_PARAM_ERROR(4047, "支付参数异常"),
	WEIXIN_PAY_ERROR(4048, "微信预支付失败"),
	WEIXIN_PAY_SIGN_ERROR(4049, "微信预支付验签失败"),
	WEIXIN_PAY_EXCEPTION(4050, "微信预支付失败"),
	ALI_PAY_SIGN_ERROR(4060, "阿里支付验签失败"),
	ALI_PAY_ERROR(4061, "阿里预支付失败"),
	ALI_REFUND_ERROR(4068, "阿里退款失败"),
	ALI_TRANSFER_ERROR(4071, "阿里转账失败"),
	PAY_WAY_NOT_SUCCESS(4062, "暂不支持此支付途径"),
	PAY_WAY_IS_NOT_FOUND(4063, "未知的支付途径"),
	PAY_BUSINESS_TYPE_ERROR(4064, "未知的支付途径"),
	ERROR_ORDER_PARAM(4065, "订单数据异常"),
	ERROR_ORDER_OWNER(4067, "非此用户订单"),
	ERROR_ORDER_TWO_AREA(4068, "商户与用户不在同一城市"),

	WECHAT_REFUND_ERROR(4069, "微信退款失败"),
	WECHAT_TRANSFER_ERROR(4072, "微信转账失败"),
	WECHAT_CERT_ERROR(4070, "微信证书加载错误"),

	ERR_REFUND(4200, "退款失败，请重试"),
	ERR_WITHDRAW(4201, "提现失败，请重试"),
	ERR_REFUND_NO(4202, "退款失败，不允许退款"),
	REFLECT_ERROR(4203,"提现失败"),

	UNIONPAY_PAY_ERROR(4204,"云闪付支付失败"),



	/**
	 * Sms
	 */
	SMS_CODE_ERROR(5001, "验证码有误"),
	SMS_SEND_ERROR(5002, "发送验证码失败"),
	SMS_SEND_OUT_NUMBER(5003, "获取次数已达上限，请今晚12点后再试"),
	SMS_SEND_LOG_SAVE_ERROR(5004, "发送验证码,日志记录保存失败"),
	REPEAT_SEND_SMS_TIME(5005, "1分钟内只能发送1条验证码"),
	SMS_CODE_SUCCESS(5006, "手机号验证成功"),
	ENT_CODE_IS_GREATER_FIVE(5007, "验证码已输错3次将锁定时长为3小时，请稍后再试"),
	USER_MOBILE_FAIL(5008, "用户手机号校验失败"),

	/**
	 * 消息msg
	 */
	MESSAGE_TEMPLATE_DOES_NOT_EXIST(1001, "消息模板不存在"),
	MSG_SHORT_NAME_NO_UPDATE(1002, "消息模板简称不能修改"),
	MSG_SHORT_NAME_NO_NULL(1003, "消息模板简称不能为空"),
	MSG_SHORT_NAME_NO_REPEAT(1004, "消息模板简称不能重复"),

	/**
	 * Idcard
	 */
	IDCARD_EXIST(6001, "身份证已存在"),
	IDCARD_ERROR(6002, "身份证信息不匹配"),
	IDCARD_IS_SUBMIT(6003, "信息已提交，不可重复提交"),
	IDCARD_INSERT_FAILED(6004, "个人身份认证提交失败"),
	IDCARD_TYPE_ERROR(6005, "请选择正确的证件类型"),
	IDCARD_CALL_THIRD_ERROR(6006, "调用第三方身份认证校验失败"),
	IDCARD_PERSONAL_TYPE(6007, "请确认是否是个人身份认证类型"),
	IDCARD_NOT_EXIST(6008, "用户还未实人认证"),
	FACE_RECOGNITION_SUCCESS(6009, "人脸识别成功"),
	FACE_RECOGNITION_FAILED(6010, "人脸识别失败"),
	IDCARD_UPDATE_FAILED(6012, "身份证修改资料失败"),
	IDCARD_NAME_FAIL(6013, "身份证姓名校验失败"),
	IDCARD_IDCARD_FAIL(6014, "身份证号码校验失败"),


	/**
	 * 二维码
	 */
	QRC_ERROR(11001, "二维码生成异常"),

	/**
	 * 订单
	 */
	ORDER_IS_NULL(12000, "订单不存在"),
	ORDER_STATUS_IS_NULL(12001, "订单状态异常"),
	ORDER_UPDATE_FAIL(12002, "订单状态修改失败"),
	ORDER_NOT_EXIST(12003, "账户记录中不存在订单信息"),
	ORDER_MONEY_ERROR(12004, "订单金额异常"),
	PAY_ORDER_MONEY_DIFFER(12005, "支付金额和订单金额不匹配"),
	ORDER_SHOPPING_CHAT_IS_NULL(12006, "订单商品为空"),
	ORDER_RUNNING_NUMBER_IS_EXIST(12007, "流水号已存在"),
	NO_DEFAULT_USER_ADDRESS(12008, "未发现默认用户收货地址"),
	INVALID_USER_ADDRESS_ID(12009, "无效的用户收货地址信息"),
	INVALID_USER_ADDRESS_LOCATION(12010, "收货地址定位失败"),
	CAN_NOT_USE_COUPON_TYPE(12012, "该订单不符合此优惠券的要求"),
	ORDER_EXCEPTION(12013, "订单异常"),
	ORDER_GOODS_IS_MORE_MERCHANT(12014, "订单商品异常"),
	ORDER_USER_RUNNING_NUMBER_IS_EXIST(12015, "前端流水号已存在"),
	ORDER_UPDATE_STATUS_ERROR(12016, "订单操作失败,请联系管理员"),
	ORDER_TYPE_ERROR(12017, "订单类型异常"),
	ORDER_FAILURE(12018, "您投递的期刊本平台暂不支持，请联系客服"),
	PERIODICAL_CANNOT_BE_CHANGED(12019, "期刊不允许修改"),
	/**
	 * 订单评价
	 */
	SCORING_DOES_NOT_MEET_THE_RULES(12100, "评分不符合规则"),
	EVALUATION_IS_NOT_SUPPORTED_TEMPORARILY(12101, "此订单暂不支持评价"),
	EVALUATION_FAILURE(12102, "评价失败"),

	/**
	 * 优惠卷
	 */
	INVALID_COUPON(12400, "无效的优惠卷"),


	/**
	 * OCR
	 */
	OCR_IMAGE_ERROR(15000, "请检查您传入的图片是否正确"),

	/**
	 * 收藏
	 */
	CANCEL_COLLECTION_FAIL(18000, "取消收藏失败"),


	/**
	 * 分享
	 */
	ADD_SHARE_RECORD_FAIL(21000, "添加分享记录信息失败"),

	/**
	 * 用户定位
	 */
	ADD_USER_LOCATION_FAIL(22000, "添加用户定位信息失败"),


	/**
	 * 字典
	 */
	KEY_ERROR(24000, "字典key有误"),


	/**
	 * 金额额度
	 */
	AMOUNT_OF_CASH(27000, "请输入正确的提现金额"),
	TODAY_WITHDRAWAL_MAX_MONEY(27001, "超出当天最大提现金额"),
	REFUND_AMOUNT_OUT_RANGE(27002, "退款金额超出原订单支付金额"),


	/**
	 * 邀请
	 */
	THE_INVITATION_DOES_NOT_EXIST(701, "该邀请不存在"),
	ONESELF_UNABLE_INVITE_YOURSELF(702, "自己不能邀请自己"),
	ONESELF_BE_SUPERIOR(703, "此人是你的下级成员"),

	/**
	 * 临时
	 */
	SHOP_PAYMENT_USER_CANCEL(29103, "用户已取消支付"),
	TIME_OUT_PAY(29104, "订单因超时未付款已被取消"),


	MINI_APP_LOGIN_ERROR(300401, "小程序登陆失败"),

	ORDER_IS_PAY(300500, "订单已支付"),
	ORDER_IS_CLOSE(300501, "订单已关闭"),


	/**
	 * 微信
	 */
	USER_NOT_BINDING_WECHAT(400201, "用户未绑定微信"),

	/**
	 * 支付
	 */
	WECHAT_PAYMENT_CHANNEL_DOES_NOT_EXIST(400301, "微信支付渠道不存在"),
	WECHAT_PAYMENT_CHANNEL_NOT_EMPTY(400302, "微信支付渠道不空"),
	YOU_HAVE_REFUSED_TO_PAY(400303, "您已拒绝支付"),

	/**
	 * 支付
	 */
	BIOLOGICAL_CREATER_RSA_KEY_FAIL(500010, "开启生物识别支付失败"),
	BIOLOGICAL_RE_OPEN_AGENT(500011, "您的App环境已发生变化，需要重新开启生物识别支付"),
	BIOLOGICAL_VERIFY_FAIL(500012, "生物识别支付验证失败"),

	/**
	 * 论文查重
	 */
	DUPLICATE_STATE_TEST_FAILED(600001, "检测失败"),
	INSUFFICIENT_DETECTION_TIMES(600002, "检测次数不足"),
	GET_TEST_RESULTS_ERROR(600003, "获取检测结果失败"),
	TEST_RESULT_MODIFICATION_FAILED(600004, "检测结果修改失败"),
	FAILED_TO_GET_DUPLICATE_RECORDS(600005, "获取查重记录失败"),

	;
	@Getter
	private final int code;

	private final String desc;


	public String getDesc() {
		return desc;
	}

	public String getDefaultDesc() {
		return desc;
	}

	RespEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
