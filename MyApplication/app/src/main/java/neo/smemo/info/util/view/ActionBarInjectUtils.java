package neo.smemo.info.util.view;

import android.app.Activity;

import java.lang.reflect.Method;

/**
 * actionbar 注解
 * Created by suzhenpeng on 2015/5/21.
 */
public class ActionBarInjectUtils {

    private static final String METHOD_INIT_ACTION_BAR = "initActionBar";

    /**
     * 查找FragmentActivity Actionbar注解
     * 生成Actionbar
     * @param activity
     */
    public static void inject(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 查询类上是否存在ContentView注解
        AnnotationActionBar actionBar = clazz.getAnnotation(AnnotationActionBar.class);
        if (actionBar != null)// 存在
        {
            int abLayout = actionBar.abLayout();
            int abTitle = actionBar.abTitle();
            try {
                Method method = clazz.getMethod(METHOD_INIT_ACTION_BAR,
                        int.class, int.class);
                method.setAccessible(true);
                method.invoke(activity, abLayout, abTitle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
