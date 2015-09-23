package neo.smemo.info.util;

import android.content.Context;

/**
 * Created by suzhenpeng on 2015/5/20.
 */
public abstract class ContextRunnable implements Runnable{

    private Context context;

    public ContextRunnable(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void run() {
        runThread(context);
    }

    abstract public void runThread(Context context);
}
