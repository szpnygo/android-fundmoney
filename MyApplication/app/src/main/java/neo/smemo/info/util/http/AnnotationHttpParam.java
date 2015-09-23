package neo.smemo.info.util.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by suzhenpeng on 2015/5/19.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationHttpParam {

    /** 不适用该字段 */
    int HTTP_UNUSED=0;
    /** 该字段为Header */
    int HTTP_HEADER = 1;
    /** GET请求 */
    int HTTP_GET = 2;
    /** 默认字段，POST请求 */
    int HTTP_NORMAL = 3;

    int type() default 3;

}
