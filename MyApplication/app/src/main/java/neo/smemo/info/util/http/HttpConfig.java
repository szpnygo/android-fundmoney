package neo.smemo.info.util.http;


import org.apache.http.client.CookieStore;

/**
 * Created by suzhenpeng on 2015/5/19.
 */
public class HttpConfig {

    /** 超时时间 */
    public int soTimeOut=10*1000;
    /** 超时时间 */
    public int timeOut=10*1000;
    /** 设置请求UA */
    public String userAgent="";
    /** COOKIE */
    public CookieStore cookieStore=null;
    /** 线程池数量 */
    public int threadPoolSize=5;
    /** 缓存时间 */
    public int configCurrentHttpCacheExpiry=10 * 60 * 1000;
}
