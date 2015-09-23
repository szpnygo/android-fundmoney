package neo.smemo.info.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.lang.ref.WeakReference;

import neo.smemo.info.R;
import neo.smemo.info.app.Constant;
import neo.smemo.info.app.ErrorInfo;
import neo.smemo.info.util.system.SystemUtil;
import neo.smemo.info.util.ui.ActionBar;

public class BaseFragment extends Fragment implements ActionBar.ActionBarRightBtnClickListenr, ActionBar.ActionBarLeftBtnClickListenr, Constant, ErrorInfo {

    protected boolean showIng = false;
    protected ActionBar actionBar;
    private MyHandler handler;
    protected FragmentManager fragmentManager;

    /**
     * 载入中对话框
     */
    ProgressDialog mDialog;
    public static final int OPEN_PROGRESS = 0x901;
    public static final int CLOSE_PROGRESS = 0x902;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        handler=new MyHandler(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentManager = getChildFragmentManager();
    }

    public void fragmentResume() {

    }

    public void fragmentPause() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            showIng = true;
            // 相当于Fragment的onResume
            fragmentResume();
        } else {
            showIng = false;
            // 相当于Fragment的onPause
            fragmentPause();
        }
    }

    public void initActionBar(View contentView, @LayoutRes int res, @StringRes int str) {
        initActionBar(contentView, res, getActivity().getResources().getString(str));
    }

    public void initActionBar(View contetnView, @LayoutRes int res, String title) {
        View view = contetnView.findViewById(R.id.actionbar);
        if (null == view)
            return;
        actionBar = new ActionBar();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("resid", res);
        actionBar.setArguments(bundle);
        if (fragmentManager == null)
            fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.actionbar, actionBar);
        fragmentTransaction.commit();
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
            mDialog = new ProgressDialog(getActivity());
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
        SystemUtil.sendBroadcaset(getActivity(), action, extras);
    }


    private void OPEN_PROGRESS(Message msg) {
        showProgressDialog(msg.getData().getString("message"));
    }

    private void CLOSE_PROGRESS() {
        dismissProgressDialog();
    }

    static class MyHandler extends Handler {
        WeakReference<BaseFragment> mActivity;

        public MyHandler(BaseFragment activity) {
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
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
