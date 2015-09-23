package neo.smemo.info.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.view.View;

import java.lang.ref.WeakReference;

import neo.smemo.info.app.AppManager;
import neo.smemo.info.app.Constant;
import neo.smemo.info.util.system.SystemUtil;
import neo.smemo.info.util.view.InjectController;

public class BaseActivity extends Activity implements Constant {


    /**
     * 载入中对话框
     */
    ProgressDialog mDialog;
    public static final int OPEN_PROGRESS = 0x901;
    public static final int CLOSE_PROGRESS = 0x902;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyHandler(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AppManager.getAppManager().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        InjectController.injectBothView(this);
        InjectController.injectEvents(this);

    }

    protected View fv(int id) {
        return findViewById(id);
    }

    protected String fs(@StringRes int id) {
        return getResources().getString(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 显示载入中对话框
     */
    public void showProgressDialog(String msg) {
        if (null != mDialog) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        } else {
            mDialog = new ProgressDialog(this);
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage(msg);
            mDialog.show();
        }
    }

    /**
     * 隐藏载入中对话框
     */
    public void dismissProgressDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void showProgressDialogInThread(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("message", msg);
        SystemUtil.sendMessage(handler, OPEN_PROGRESS, bundle);
    }

    public void dismissProgressDialogInThread() {
        SystemUtil.sendMessage(handler, CLOSE_PROGRESS);
    }

    public void sendBroadcaset(String action, Bundle extras) {
        SystemUtil.sendBroadcaset(this, action, extras);
    }

    private void OPEN_PROGRESS(Message msg) {
        showProgressDialog(msg.getData().getString("message"));
    }

    private void CLOSE_PROGRESS() {
        dismissProgressDialog();
    }

    static class MyHandler extends Handler {
        WeakReference<BaseActivity> mActivity;

        public MyHandler(BaseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mActivity.get().handleMessage(msg);
        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case OPEN_PROGRESS:
                OPEN_PROGRESS(msg);
                break;
            case CLOSE_PROGRESS:
                CLOSE_PROGRESS();
                break;
            default:
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
