package neo.smemo.info.action;


import com.lidroid.xutils.http.RequestParams;

import org.apache.http.client.CookieStore;
import org.json.JSONObject;

import java.util.ArrayList;

import neo.smemo.info.app.Constant;
import neo.smemo.info.base.BaseAction;
import neo.smemo.info.bean.FundBean;
import neo.smemo.info.util.JsonUtil;
import neo.smemo.info.util.LogHelper;

/**
 * 继承BaseAction
 * Created by suzhenpeng on 2015/9/23.
 */
public class FundAction extends BaseAction {

    /**
     * 网络请求方法
     *
     * @param actionSuccessResponse 请求回调
     */
    public static void getFundList(final ActionSuccessResponse actionSuccessResponse) {
        //HTTP模块使用xUtils
        //此处无需参数，仅作演示
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("deviceid", "id");
        requestParams.addHeader("Cookie", "usi=helloworld");
//        requestParams.addBodyParameter("file",new File(""));
        post(API_FUND_LIST, requestParams, new ActionResponse() {
            @Override
            public void success(JSONObject data, CookieStore store) {
                //数据返回制定格式使用BaseAction，如果非格式的数据，可以直接使用HttpHelper
                //{
                //    code: 0,         //返回错误值，根据错误值判断
                //    message: "",     //错误信息
                //    data:[]          //返回的数据内容
                // }

                //数据解析利用Gson进行解析
                ArrayList<FundBean> fundBeanArrayList = JsonUtil.getInstance().fromJsonArray(data, FundBean.class);
                if (null != actionSuccessResponse)
                    actionSuccessResponse.success(fundBeanArrayList);
            }

            @Override
            public void failure(int code, String message) {
                LogHelper.Log_E(Constant.TAG_Network, "getFundList API请求失败,responseCode：" + code + " message:" + message);
                if (actionSuccessResponse != null)
                    actionSuccessResponse.failure(code, message);
            }

            @Override
            public void error(int error, String message) {
                if (actionSuccessResponse != null)
                    actionSuccessResponse.failure(NETWORK_REQUEST_ERROR, message);
            }
        });
    }
}
