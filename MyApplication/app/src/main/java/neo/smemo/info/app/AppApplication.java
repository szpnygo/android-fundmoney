package neo.smemo.info.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
/**
 * Created by suzhenpeng on 15/5/2.
 */
public class AppApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        if (null == mContext) return getContext();
        return mContext;
    }

}

