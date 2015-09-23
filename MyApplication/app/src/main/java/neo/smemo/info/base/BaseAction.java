/**
 * 类描述：   数据请求类
 * 创建人：suzhenpeng
 * 创建时间：2013-9-8 下午8:23:59
 * 修改人：suzhenpeng
 * 修改时间：2013-9-8 下午8:23:59
 * 修改备注：
 *
 * @version
 */
package neo.smemo.info.base;


import com.lidroid.xutils.http.RequestParams;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import neo.smemo.info.app.Constant;
import neo.smemo.info.app.ConstantApi;
import neo.smemo.info.app.ErrorInfo;
import neo.smemo.info.util.LogHelper;
import neo.smemo.info.util.StringUtil;
import neo.smemo.info.util.http.HttpConfig;
import neo.smemo.info.util.http.HttpHelper;

/**
 * @author suzhenpeng
 *         数据请求基类
 */
public class BaseAction implements ConstantApi,ErrorInfo {

    public static final String COOKIE = "Cookie";

    protected static void post(String url, Object object, final ActionResponse actionResponse) {
        post(url, object, actionResponse, null);
    }

    protected static void post(String url, Object object, final ActionResponse actionResponse, HttpConfig httpConfig) {
        new HttpHelper().postRequest(url, object, new HttpHelper.EasyHttpResponse() {
            @Override
            public void response(boolean success, int code, String response, CookieStore store) {
                analyzeData(success, code, response, actionResponse, store);
            }
        }, httpConfig);
    }

    protected static void get(String url, Object object, final ActionResponse actionResponse, HttpConfig httpConfig) {
        new HttpHelper().getRequest(url, object, new HttpHelper.EasyHttpResponse() {
            @Override
            public void response(boolean success, int code, String response, CookieStore store) {
                analyzeData(success, code, response, actionResponse, store);
            }
        }, httpConfig);
    }

    protected static void analyzeData(boolean success, int code, String response, ActionResponse actionResponse, CookieStore store) {
        if (success) {
            try {
                response = StringUtil.convertUnicode(response);
                JSONObject object = new JSONObject(response);
                int responseCode = object.getInt("code");
                if (responseCode == 0) {
                    if (actionResponse != null)
                        actionResponse.success(object, store);
                } else {
                    String message = object.getString("message");
                    if (actionResponse != null)
                        actionResponse.failure(responseCode, message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                LogHelper.Log_E(Constant.TAG_Network, "API请求失败,数据解析错误");
                if (actionResponse != null)
                    actionResponse.error(ErrorInfo.REUQEST_DATA_ERROR, "API请求失败,数据解析错误");
            }
        } else {
            LogHelper.Log_E(Constant.TAG_Network, "网络请求失败,code：" + code);
            if (actionResponse != null)
                actionResponse.error(ErrorInfo.NETWORK_REQUEST_ERROR, "网络请求失败,code：" + code);
        }
    }

    public interface ActionResponse {
        void success(JSONObject data, CookieStore store);

        void failure(int code, String message);

        void error(int error, String message);
    }

    public interface ActionSuccessResponse {

        void success(Object object);

        void failure(int code, String message);
    }


    /**
     * 生成Cookie字符串
     *
     * @param store
     * @return
     */
    public static String cookitToString(CookieStore store) {
        if (null == store || store.getCookies() == null || store.getCookies().size() == 0)
            return "";
        StringBuffer buffer = new StringBuffer();
        for (Cookie cookie : store.getCookies())
            buffer.append(cookie.getName() + "=" + cookie.getValue() + ";");
        return buffer.toString();
    }

    protected static void printData(RequestParams requestParams) {
        if (LogHelper.NO_DEBUG)
            return;
        try {
            LogHelper.Log_I(TAG_Network, URLEncoder.encode(EntityUtils.toString(requestParams.getEntity(),"UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
    }

}
