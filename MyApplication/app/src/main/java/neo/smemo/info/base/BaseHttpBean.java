package neo.smemo.info.base;


import neo.smemo.info.util.http.AnnotationHttpParam;
import neo.smemo.info.util.http.HttpConfig;

/**
 * Created by suzhenpeng on 2015/5/19.
 */
public class BaseHttpBean {

    @AnnotationHttpParam(type = AnnotationHttpParam.HTTP_UNUSED)
    public HttpConfig httpConfig=new HttpConfig();

}
