package neo.smemo.info.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import neo.smemo.info.R;
import neo.smemo.info.app.AppManager;
import neo.smemo.info.app.Constant;
import neo.smemo.info.app.ErrorInfo;
import neo.smemo.info.util.system.SystemUtil;
import neo.smemo.info.util.ui.ActionBar;
import neo.smemo.info.util.ui.MaterialDialog;
import neo.smemo.info.util.view.InjectController;

public class BaseFragmentActivity extends FragmentActivity implements ActionBar.ActionBarLeftBtnClickListenr, ActionBar.ActionBarRightBtnClickListenr, Constant, ErrorInfo {

    /**
     * 载入中对话框
     */
    ProgressDialog mDialog;
    public static final int OPEN_PROGRESS = 0x901;
    public static final int CLOSE_PROGRESS = 0x902;
    protected ActionBar actionBar;

    /**
     * fragment管理器
     */
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //fragment管理器
        fragmentManager = getSupportFragmentManager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        InjectController.injectBothView(this);
        InjectController.injectActionBar(this);
        InjectController.injectEvents(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        AppManager.getAppManager().finishActivity(this);
    }

    public void initActionBar(@LayoutRes int res, @StringRes int str) {
        initActionBar(res, getResources().getString(str));
    }

    public void initActionBar(@LayoutRes int res, String title) {
        initActionBar(res, title, false);
    }

    public void initActionBar(@LayoutRes int res, String title, boolean useFont) {
        View view = findViewById(R.id.actionbar);
        if (null == view)
            return;
        actionBar = new ActionBar();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("resid", res);
        bundle.putBoolean("font", useFont);
        actionBar.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.actionbar, actionBar);
        fragmentTransaction.commit();
    }

    protected View fv(int id) {
        return findViewById(id);
    }

    protected String fs(@StringRes int id) {
        return getResources().getString(id);
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

    Handler handler = new Handler() {
        @Override
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
    };

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected void showMessage(String msg) {
        final MaterialDialog messageDialog = new MaterialDialog(this);
        messageDialog.setTitle("提示");
        messageDialog.setCanceledOnTouchOutside(true);
        messageDialog.setMessage(msg);
        messageDialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageDialog.dismiss();
                    }
                }
        );
        messageDialog.show();
    }

}
