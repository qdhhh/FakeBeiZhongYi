package com.android.qdhhh.fakebeizhongyi.commom;

import android.os.Environment;

public abstract class Constants {
	/** 会话信息包名 **/
	public static final String SYS_PACKAGE = "com.android.qdhhh.fakebeizhongyi.commom";

	/** 第一次使用 **/
	public static final String IS_USED_APP = "IS_USED_APP";
	/** 是否显示捐赠 **/
	public static final String IS_DONATE_SHOW = "IsPayIos";
	/** 第一次进入新闻主页 **/
	public static final String IS_FIRST_TO_NEWS = "IS_FIRST_TO_NEWS";
	/** 系统级别的参数 **/
	public static final String SYS_PARAM_CHARSET = "charset";
	public static final String SYS_PARAM_FORMAT = "format";
	public static final String SYS_PARAM_SIGN = "sign";
	/** 系统缓存map **/
	public static final String CACHE_SYS_MAP = "cache_sys_map";

	public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String STANDARD_DATE_YMD = "yyyy-MM-dd";

	/** 持久化业务字段 **/
	public static final String IM_LOGIN_TOKEN = "IM_LOGIN_TOKEN";// 美丽云im登录成功后返回的token
	public static final String SESSION_USER_TOKEN = "SESSION_USER_TOKEN";
	public static final String USER_NAME = "USER_NAME";// 登陆用户名md5
	public static final String USER_PASSWD = "USER_PASSWD";// 登陆密码md5
	public static final String SCREEN_NAME = "SCREEN_NAME";// 用户昵称
	public static final String HELP_URL = "GetQiuzhuUrl";// 求助URL
	// public static final String HELP_URL = "HELP_URL";//求助URL
	public static final String USER_ID = "USER_ID";// 登陆ID
	public static final String CLIENTID = "clientId";// 登陆ID
	public static final String USER_IM_ID = "USER_IM_ID";// 登陆imID
	public static final String USER_TOKEN = "USER_TOKEN";// 登陆用户token
	public static final String USER_FACE_PIC = "USER_FACE_PIC";// 用户头像
	public static final String USER_TYPE = "USER_TYPE";// 用户类型
	public static final String USER_IDENTITY_TYPE = "USER_IDENTITY_TYPE";// 用户身份类型
	public static final String USER_LOGIN_TIMESTAMP = "USER_LOGIN_TIMESTAMP";// 登录时的用户时间戳
	public static final String IS_SUPER = "IS_SUPER"; // 否拥有超级读权限 1有，0没有
	public static final String IS_OPEN_PUSH = "IS_OPEN_PUSH";// 是否已经开启推送功能

	public static final String PUSH_TAG = "PUSH_TAG";// 推送开关
	public static final String AUDIO_TAG = "AUDIO_TAG";// 声音提示
	public static final String VIBRATE_TAG = "VIBRATE_TAG";// 震动提示

	public static final String GROUP_MESSAGE_TIP_BATCH_TAG = "GROUP_MESSAGE_TIP_BATCH_TAG";// 群聊天批量提醒开关

	public static final String GROUP_MESSAGE_TIP = "GROUP_MESSAGE_TIP";// 群聊天提醒

	// public static final String AUDIO_GROUP_MESSAGE = "AUDIO_GROUP_MESSAGE";//
	// 群消息声音提示
	public static final String AUDIO_FRIEND_MESSAGE = "AUDIO_FRIEND_MESSAGE";// 好友消息声音提示
	public static final String VIBRATE_FRIEND_MESSAGE = "VIBRATE_FRIEND_MESSAGE";// 好友消息震动提示
	// public static final String NEW_MESSAGE_NOTIFY = "NEW_MESSAGE_NOTIFY";//
	// 新消息通知
	public static final String NEW_MESSAGE_BACKGROUND = "NEW_MESSAGE_BACKGROUND";// 后台保持在线

	public static final String SAFE_LUODINGYOUSHENG = "SAFE_LUODINGYOUSHENG";// 落地有声音
	public static final String SAFE_JIESHOU_LUODINGYOUSHENG = "SAFE_JIESHOU_LUODINGYOUSHENG";// 接收落地有声通知
	public static final String NEWS_MAX_ID = "NEWS_MAX_ID";
	public static final String COURSE_MAX_ID = "COURSE_MAX_ID";
	public static final String HUODONG_MAX_ID = "HUODONG_MAX_ID";

	public static final String PUSH_ONOFF = "PUSH_ONOFF";// 更多推送开关
	public static final String BEAUTIFUL_ONOFF = "BEAUTIFUL_WARN_ONOFF";// 推送开关

	public static final String BIND_XINLAN_ONOFF = "BIND_XINLAN_ONOFF";// 更多绑定新浪
	public static final String BIND_QQ_ONOFF = "BIND_QQ_ONOFF";// 更多绑定QQ

	public static final String WEI_CHAT_APPID = "wx595799ede4e85ec9";// 更多绑定QQ。。微信id
	public static final String WEI_CHAT_APPSERTE = "d7edf6b7aceb215cd50ec70991675f43";

	public static final String SING_APPID = "3552241776";
	public static final String SING_APPSERTE = "bee16b8db63bfb0e9f826173220441a8";

	public static final String helpUrl = "http://183.232.32.209:7070/";// 求助IP

	public static final String APPShareTitle = "北中医人APP";
	public static final String APPShareDescribe = "北京中医药大学的校友APP";
	public static final String APPShareUrl = "";

	// 一些handlar和request code以及宏

	// 报名信息类型请求码
	public static final int ENROLLOPTION_ONETEXT = 400;
	public static final int ENROLLOPTION_MORETEXT = 401;
	public static final int ENROLLOPTION_ONESELECT = 402;
	public static final int ENROLLOPTION_MORESELET = 403;
	public static final int RESULT_CHANGE = 404;// 报名信息的修改操作结果码
	public static final int COSTOPTION = 405;// 设置费用选项请求码
	public static final int ENROLLOPTION = 406;// 设置报名选项请求码
	public static final int NUMLIMIT = 407;// 设置报名选项请求码

	// 报名信息类型
	public static final String TYPE_ONESELECT = "oneselect";// 设置单选
	public static final String TYPE_MORESELECT = "moreselect";// 设置多选
	public static final String TYPE_ONETEXT = "onetext";// 设置单行文本
	public static final String TYPE_MORETEXT = "moretext";// 设置多行文本

	// 网络请求后的消息
	public static final int NETSUC = 408;// 网络请求成功
	public static final int NETERR = 409;// 网络请求失败

	// 选择活动所属群组请求码
	public static final int SELECTATYGROUP = 409;// 网络请求失败

	public static final int GETDETAILSUC = 410;// 获取活动详情成功
	public static final int GETDETAILFAL = 411;// 获取活动详情失败

	public static final int SIGNFAIL = 412;// 签到失败

	public static final int GETDOWNLOADURLSUC = 413;// 获取下载URL成功
	public static final int GETDOWNLOADURLFAL = 414;// 获取下载URL失败

	public static final int DELETEACTIVITYSUC = 415;// 删除我发布的活动成功
	public static final int DELETEACTIVITYFAL = 416;// 删除我发布的活动失败

	public static final int GETDONATIONURLSUC = 417;// 获取捐赠页面的URL成功
	public static final int GETDONATIONURLFAL = 418;// 获取捐赠页面的URL失败

	public static final int GETORDERINFOSUC = 419;// 获取订单信息成功
	public static final int GETORDERINFOFAL = 420;// 获取订单信息失败
	public static final int GETORDERINFOERR = 421;// 获取订单信息错误

	public static final int PAYREQUEST = 422;// 支付请求
	public static final int PAYSUCCESS = 423;// 订单经支付

	public static final int ZHIWEIPIC = 424;// 职位信息选择图片
	public static final int GONGSIPIC = 425;// 职位信息选择图片

	public static final int PUBLIHZHIWEISUC = 426;// 职位发布成功
	public static final int PUBLIHZHIWEIFAL = 427;// 职位发布失败

	public static final int EXITGROUPORDISCUSSION = 428;// 退出群或讨论组

	/** 下载Excel表存储到本地的路径 */
	public static final String EXCELDOWNLOADPATH = Environment
			.getExternalStorageDirectory().getPath()
			+ "/data/data/com.donson.beiligong/files/";

	/**
	 * 用友IM相关参数
	 */

	/** 登录IM的userName */
	public static final String FRONT_LAST_LOGIN_ACCOUNT = "FRONT_LAST_LOGIN_ACCOUNT";
	/** 登录IM的passWord */
	public static final String FRONT_LAST_LOGIN_PASSWORD = "FRONT_LAST_LOGIN_PASSWORD";
	/** 登录IM的userID */
	public static final String FRONT_LAST_LOGIN_USERID = "FRONT_LAST_LOGIN_USERID";
	/** 登录IM的nickName */
	public static final String FRONT_LAST_LOGIN_NICKNAME = "FRONT_LAST_LOGIN_NICKNAME";
	/** 获取Token的URL */
	public static final String getIMToken = "https://im.yyuap.com/sysadmin/rest/";

	/** 企业ID */
	public static final String etpid = "cidtech";
	/** 应用ID */
	public static final String appid = "1078501223";

	/** YYIM Client ID */
	public static final String ClientID = "38aed8db16361315d1f53c0c26a6c8e0";
	/** YYIM Client Secret */
	public static final String ClientSecret = "DB495C6A0823E061A10A63F1FE2AB6F2";

	/** 是否使用有信IM */
	public static final boolean ISYYIM = true;

	// 群组操作消息的公众号
	public static final String PUBLICACCOUNT = "bzygroupnotifiy";

	/** 小米推送AppID */
	public static final String MiPushAppID = "2882303761517465832";

	/** 小米推送AppKey */
	public static final String MiPushAppKey = "5591746541832";

	/** 小米推送AppSecret */
	public static final String MiPushAppSecret = "roHpgsseVcEyGoD3+DcnLQ==";

	/** 小米推送信息存储的文件名 */
	public static final String MiPushFile = "MiPushFile";

	/** 小米推送信息存储的变量名MiPushRegID */
	public static final String MiPushRegID = "MiPushRegID";

	/**
	 * 程序是否是第一次启动
	 */
	public static boolean IS_FIRSTLOG = false;
	/**
	 * QunMemberActivity中Handler更新ui的变量
	 */
	public static final byte QUN_MEMBER_HANDLER = 1;
	public static final byte QUN_MEMBER_H = 2;
	public static final byte QUN_MEMBER_B = 3;
	/**
	 * 登录带返回值跳转
	 */
	public static final int LOGIN_REQUESET = 4;
	public static final int LOGIN_RESULT = 5;
	/**
	 * 群组详情更新
	 */
	public static final int QUN_DETAIL_DELETE = 6;
	public static final int QUN_DETAIL_DELETE_TOW = 7;

	public static final int TITLE_DISMISS = 8;
	public static final int TITLE_SHOW = 9;
	public static final int DONGTAI_DISMISS = 10;
	public static final int DONGTAI_SHOW = 11;
	/*
	 * 群组页进入内层界面后返回是否刷新,默认0不刷新，1刷新群组概要信息，2刷新动态，3只刷新活动，4只刷新置顶，5删除活动，刷新活动和置顶
	 */
	public static int IF_REFRESH = 0;
	public static int IS_THREAD_RUN = 0;

	public static double SCREEN_INCH = 0;

	/**
	 * 我的广播
	 */
	public static final String ME_RECEVER = "com.me.recever";
	// 头像
	public static final String HAND_IAMGE = "beizhongyihand.png";
	// 签名
	public static final String AUTOGRAPH = "beizhongyisign";

}
