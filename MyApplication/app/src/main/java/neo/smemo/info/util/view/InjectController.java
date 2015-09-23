package neo.smemo.info.util.view;

import android.app.Activity;

/**
 * Created by suzhenpeng on 2015/5/21.
 */
public class InjectController {

    public static void injectActionBar(Activity activity) {
        ActionBarInjectUtils.inject(activity);
    }

    public static void injectEvents(Activity activity) {
        EventInjectUtil.injectEvents(activity);
    }

    public static void injectBothView(Activity activity) {
        ViewInjectUtils.inject(activity);
    }

    public static void injectContentView(Activity activity) {
        ViewInjectUtils.injectContentView(activity);
    }

    public static void injectView(Activity activity) {
        ViewInjectUtils.injectView(activity);
    }

}
