/**
 * 项目名称：wangzheguilai
 * 类名称：ErroInfo
 * 类描述：  错误信息
 * 创建人：suzhenpeng
 * 创建时间：2013-9-9 上午12:01:03
 * 修改人：suzhenpeng
 * 修改时间：2013-9-9 上午12:01:03
 * 修改备注：
 *
 * @version
 */
package neo.smemo.info.app;

public interface ErrorInfo {
    /** NETWORK_REQUEST_ERROR : 网络请求失败 */
    int NETWORK_REQUEST_ERROR = 1000101;
    /** 数据请求错误 */
    int REUQEST_DATA_ERROR = 1000102;

    /******************************* 000公共约束 ***********************************/
    /** 服务器内容错误，请稍后重试 */
    int API_ERROR=2000001001;

    /******************************* 002设备信息接口说明 ***********************************/
    /** 服务器异常,获取设备id失败 */
    int DEVICE_GET_ERROR = 1002001001;
    /******************************* 003新闻接口说明 ***********************************/
    /** 服务器内部错误 */
    int NEWS_ERROR = 1003003001;
    /** 新闻不存在 */
    int NEWS_NO_EXISTS = 1003003002;
    /** 评论内容为空 */
    int NEWS_EMPTY_COMMENT = 1003003003;
    /******************************* 004用户登录接口说明 ***********************************/
    /** 用户提交的信息包含不正确的文本格式 */
    int USER_LOGIN_ERROR_DATA = 2004001001;
    /** 不存在的用户名 */
    int USER_LOGIN_NO_USERNAME = 2004001002;
    /** 密码不正确 */
    int USER_LOGIN_ERROR_PASSWORD = 2004001003;
    /** 需要用户重新登录 */
    int USER_RE_LOGIN = 1004001004;
    /** 用户退出失败 */
    int USER_EXIT_ERROR = 1004003001;
    /** openid为空 */
    int USER_LOGIN_NO_OPENID = 2004004001;
    /** 巨人移动sdk服务端验证失败 */
    int USER_LOGIN_VIG_ERROR = 2004004003;
    /** 频繁登录，休息一下再登录吧*/
    int USER_LOGIN_TOO_MANY = 2004004004;
    /** 不存在的用户 */
    int USER_INFO_NO = 2004009002;

    /******************************* 005用户小生活 ***********************************/
    /** 素材类型不符合要求 */
    int COMMUNITY_MATERIAL_ERRPR_TYPE = 2005001001;
    /** 素材视频太大 */
    int COMMUNITY_VIDEO_TOO_LARGE = 2005001002;
    /** 图片太大 */
    int COMMUNITY_IMAGE_TOO_LARGE = 2005001003;
    /** 服务器内部错误，请稍后重试 */
    int COMMUNITY_SERVER_ERROR = 2005001004;
    /** 小动态信息不存在 */
    int COMMUNITY_PRAIS_NO_INFO = 2005002001;
    /** 已经点赞 */
    int COMMUNITY_PRAISEED = 2005002002;
    /** 服务器内部错误，请稍后重试 */
    int COMMUNITY_PRAISE_ERROR = 2005002003;
    /** 获取服务器内部错误，请稍后重试 */
    int COMMUNITY_LIST = 1005003001;
    /** 获取服务器内部错误，请稍后重试 */
    int COMMUNITY_COMMENT = 2005004001;
    /** 回复的动态信息不存在 */
    int COMMUNITY_NO_INFO = 2005004002;
    /** 回复的评论id不存在 */
    int COMMUNITY_NO_COMMENT = 2005004003;
    /** 回复的内容不符合要求(为空) */
    int COMMUNITY_EMPTY_CONTENT = 2005004004;
    /** 动态信息不存在 */
    int COMMUNITY_NOT_FUND = 1005005001;
    /** 获取周边的人失败 */
    int COMMENUITY_ERROR_AROUND_LIST = 1005007001;
    /** 删除失败 */
    int COMMUNITY_DELETE_ERROR=2005009001;
    /** 消息不存在 */
    int COMMUNITY_DELETE_NO=2005009002;
    /******************************* 006游戏接口 ***********************************/
    /** 服务器内部错误 */
    int GAME_FOLLOW_ERROR = 2006002001;
    /** 不存在的游戏 */
    int GAME_FOLLOW_NO_GAME = 2006002002;
    /** 已是测试用户不需要多次申请 */
    int GAME_TESTER_ED=2006003002;
    /** 申请失败 */
    int GAME_TESTER_REG=2006003001;
    /** 没有可修改的申请记录 */
    int GAME_TESTER_UPDATE_NO=2006005002;
    /** 服务器内部错误 */
    int GAME_TESTER_UPDATE_ERROR=2006005001;
    /** 游戏不存在 */
    int GAME_ADVICE_NO_GAME=2006008003;
    /** 图片上传错误 */
    int GAME_ADVICE_IMG_ERORR=2006008004;
    /** 服务器内部错误 */
    int GAME_ADVICE_POST_ERROR=2006008001;
    /** 不是测试用户 */
    int GAME_TASK_ISNOT_TESTER=2006012002;
    /** 任务已结束 */
    int GAME_TASK_ENDL=2006012003;
    /** 图片上传出错 */
    int GAME_TASK_IMG_ERORR=2006012004;
    /** 任务不存在 */
    int GAME_TASK_NO=2006016002;


    /** 任务不可重复领取 */
    int GAME_ACCEPT_TASK_ERROR=2006014002;
    /** 任务已结束 */
    int GAME_ACCEPT_TASK_ENDL=2006014004;
    /** 您不是测试用户，无法申请任务 */
    int GAME_ACCEPT_TASK_ISNOT_TESTER=2006014003;
    /******************************* 007积分接口 ***********************************/
    /** 签到失败 */
    int SIGN_IN_ERROR = 2007002001;
    /** 签到失败(重复签到) */
    int SIGN_IN_REPEAT = 2007001002;
    /** 获取历史记录失败 */
    int POINT_HISTORY_ERROR = 2007003001;
    /** 商品库存不足 */
    int GET_SHOP_NO = 2007005002;
    /** 用户积分不足*/
    int GET_SHOP_NO_POINT = 2007005003;
    /******************************* 008订单接口 ***********************************/
    /** 不存在的订单类型 */
    int ORDER_NO_TYPE=2008006001;
    /** 不存在的游戏区 */
    int ORDER_NO_GAME_ZONE=2008006002;
    /** 图片过多 */
    int ORDER_TOO_MANY_IMG=2008006003;
    /** 表单内容错误 */
    int ORDER_SYSTEM_ERROR=2008006004;
    /** 手游用户暂不支持提单 */
    int ORDER_NOT_SUPPORT_MOBILE=2008001002;

    /******************************* 009用户好友接口 ***********************************/
    /** 添加好友错误 */
    int FRIEND_ADD_ERROR=2009001001;
    /** 用户不存在 */
    int FRIEND_ADD_USER_NO=2009001002;
    /** 删除好友错误 */
    int FRIEND_DELETE_ERROR=2009002001;
    /** 用户不存在 */
    int FRIEND_DELETE_USER_NO=2009002002;
    /** 获取好友列表失败 */
    int FRIEND_LIST_ERROR=2009003001;

    /******************************* 010客服聊天 ***********************************/
    /** 聊天组不存在 */
    int CHAT_NO_GROUP=2010001002;
    /** 聊天已经被结束 */
    int CHAT_ENDED=2010001003;
    /** 结束打分聊天组不存在 */
    int CLOSE_TALK_NO_GROUP=2010007002;
    /** 客服尚未回话 */
    int CHAT_NO_ANSWER=2010007003;

}
