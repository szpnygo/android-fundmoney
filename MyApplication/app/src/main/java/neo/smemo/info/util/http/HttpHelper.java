package neo.smemo.info.util.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.DefaultSSLSocketFactory;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.lang.reflect.Field;

import neo.smemo.info.app.Constant;
import neo.smemo.info.base.BaseHttpBean;
import neo.smemo.info.util.LogHelper;
import neo.smemo.info.util.StringUtil;

/**
 * Http请求，基于HttpUtils
 * Created by suzhenpeng on 2015/5/19.
 */
public class HttpHelper {

    private HttpUtils http = null;
    private EasyHttpResponse response = null;
    private NormalHttpResponse normalResponse = null;

    public HttpHelper() {
        super();
        http = new HttpUtils();
    }

    public void getRequest(String url, Object params, HttpConfig config) {
        request(HttpRequest.HttpMethod.GET, url, params, null, null, config);
    }

    public void getRequest(String url, Object params, EasyHttpResponse _response, HttpConfig config) {
        request(HttpRequest.HttpMethod.GET, url, params, _response, null, config);
    }

    public void getRequest(String url, Object params, NormalHttpResponse _normalResponse, HttpConfig config) {
        request(HttpRequest.HttpMethod.GET, url, params, null, _normalResponse, config);
    }

    public void getRequest(String url, Object params, EasyHttpResponse _response, NormalHttpResponse _normalResponse, HttpConfig config) {
        request(HttpRequest.HttpMethod.GET, url, params, _response, _normalResponse, config);
    }

    public void postRequest(String url, Object params, HttpConfig config) {
        request(HttpRequest.HttpMethod.POST, url, params, null, null, config);
    }

    public void postRequest(String url, Object params, EasyHttpResponse _response, HttpConfig config) {
        request(HttpRequest.HttpMethod.POST, url, params, _response, null, config);
    }

    public void postRequest(String url, Object params, EasyHttpResponse _response) {
        request(HttpRequest.HttpMethod.POST, url, params, _response, null, null);
    }

    public void postRequest(String url, Object params, NormalHttpResponse _normalResponse, HttpConfig config) {
        request(HttpRequest.HttpMethod.POST, url, params, null, _normalResponse, config);
    }

    public void postRequest(String url, Object params, NormalHttpResponse _normalResponse) {
        request(HttpRequest.HttpMethod.POST, url, params, null, _normalResponse, null);
    }

    public void postRequest(String url, Object params, EasyHttpResponse _response, NormalHttpResponse _normalResponse, HttpConfig config) {
        request(HttpRequest.HttpMethod.POST, url, params, _response, _normalResponse, config);
    }

    public void postRequest(String url, Object params, EasyHttpResponse _response, NormalHttpResponse _normalResponse) {
        request(HttpRequest.HttpMethod.POST, url, params, _response, _normalResponse, null);
    }

    /**
     * 网络请求
     *
     * @param method          请求方法
     * @param url             请求地址
     * @param object          数据对象
     * @param _response       简单回调
     * @param _normalResponse 复杂回调
     * @param _config
     */
    public void request(HttpRequest.HttpMethod method, String url, Object object, EasyHttpResponse _response, NormalHttpResponse _normalResponse, HttpConfig _config) {
        if (StringUtil.isEmpty(url)) return;

        LogHelper.Log_I(Constant.TAG_Network, url);

        this.response = _response;
        this.normalResponse = _normalResponse;

        if (null == object)
            object = new RequestParams();

        //是否采用自定义config
        HttpConfig config = null;
        if (_config != null || !(object instanceof BaseHttpBean) || object instanceof RequestParams) {
            config = _config;
        } else {
            config = ((BaseHttpBean) object).httpConfig;
        }

        //采用RequestParams
        RequestParams params = null;
        if (object instanceof RequestParams)
            params = (RequestParams) object;
        params = toRequestParams(object);
        params.setHeader("User-Agent", "ZtGame-AndroidClient");

        //配置Config
        if (null == config)
            config = new HttpConfig();
        if (!StringUtil.isEmpty(config.userAgent))
            http.configUserAgent(config.userAgent);
        if (null != config.cookieStore)
            http.configCookieStore(config.cookieStore);

        http.configCurrentHttpCacheExpiry(config.configCurrentHttpCacheExpiry);
        http.configSoTimeout(config.soTimeOut);
        http.configTimeout(config.timeOut);
        http.configRequestThreadPoolSize(config.threadPoolSize);
        if (url.startsWith("https"))
            http.configSSLSocketFactory(DefaultSSLSocketFactory.getSocketFactory());

        //数据请求
        http.send(method, url, params, new RequestCallBack<String>() {
            @Override
            public Object getUserTag() {
                return super.getUserTag();
            }

            @Override
            public void setUserTag(Object userTag) {
                super.setUserTag(userTag);
            }

            @Override
            public void onStart() {
                super.onStart();
                if (null != normalResponse)
                    normalResponse.onStart();
            }

            @Override
            public void onCancelled() {
                super.onCancelled();
                if (null != normalResponse)
                    normalResponse.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                if (null != normalResponse)
                    normalResponse.onLoading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogHelper.Log_I(Constant.TAG_Network, "HTTP RESPONSE" + (responseInfo.resultFormCache ? " CACHE" : "") + ":" + responseInfo.result);
                if (null != response)
                    response.response(true, responseInfo.statusCode, responseInfo.result, ((DefaultHttpClient) http.getHttpClient()).getCookieStore());
                if (null != normalResponse)
                    normalResponse.onSuccess(responseInfo, ((DefaultHttpClient) http.getHttpClient()).getCookieStore());
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogHelper.Log_E(Constant.TAG_Network, error.getExceptionCode() + ":" + msg);
                if (null != response)
                    response.response(false, error.getExceptionCode(), msg, ((DefaultHttpClient) http.getHttpClient()).getCookieStore());
                if (null != normalResponse)
                    normalResponse.onFailure(error, msg);
            }
        });
    }

    /**
     * 生成Params对象
     *
     * @param object
     * @return
     */
    public static RequestParams toRequestParams(Object object) {
        if (null == object)
            return null;
        if (object instanceof RequestParams)
            return (RequestParams) object;

        RequestParams requestParams = new RequestParams();
        //获取所有的字段
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取字段注释
            AnnotationHttpParam unUsed = field.getAnnotation(AnnotationHttpParam.class);
            //默认配置
            int paramType = AnnotationHttpParam.HTTP_NORMAL;
            if (null != unUsed)
                paramType = unUsed.type();
            if (paramType == AnnotationHttpParam.HTTP_UNUSED)
                continue;

            try {
                String fileTypeName = field.getType().getName();
                //生成param
                if (fileTypeName.equals("java.lang.String") ||
                        fileTypeName.equals("int") ||
                        fileTypeName.equals("long")
                        ) {
                    if (paramType == AnnotationHttpParam.HTTP_HEADER) {
                        requestParams.addHeader(field.getName(), String.valueOf(field.get(object)));
                    } else if (paramType == AnnotationHttpParam.HTTP_GET) {
                        requestParams.addQueryStringParameter(field.getName(), String.valueOf(field.get(object)));
                    } else {
                        requestParams.addBodyParameter(field.getName(), String.valueOf(field.get(object)));
                    }
                } else if (fileTypeName.equals("java.io.File")) {
                    requestParams.addBodyParameter(field.getName(), ((File) field.get(object)));
                } else {
                    requestParams.addBodyParameter(field.getName(), String.valueOf(field.get(object)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return requestParams;
    }

    public HttpUtils getHttp() {
        return http;
    }

    public interface EasyHttpResponse {
        void response(boolean success, int code, String response, CookieStore store);
    }

    public interface NormalHttpResponse {

        void onStart();

        void onCancelled();

        void onLoading(long total, long current, boolean isUploading);

        void onSuccess(ResponseInfo<String> responseInfo, CookieStore store);

        void onFailure(HttpException error, String msg);
    }

}
