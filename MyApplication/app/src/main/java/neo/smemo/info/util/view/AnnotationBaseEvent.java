package neo.smemo.info.util.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by suzhenpeng on 2015/5/21.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationBaseEvent {

    Class<?> listenerType();

    String listenerSetter();

    String methodName();

}
