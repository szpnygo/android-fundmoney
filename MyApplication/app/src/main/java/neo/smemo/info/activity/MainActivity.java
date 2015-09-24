package neo.smemo.info.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import neo.smemo.info.R;
import neo.smemo.info.action.FundAction;
import neo.smemo.info.adapter.FundAdapter;
import neo.smemo.info.base.BaseAction;
import neo.smemo.info.base.BaseActivity;
import neo.smemo.info.base.BaseFragmentActivity;
import neo.smemo.info.bean.FundBean;
import neo.smemo.info.util.system.SystemUtil;
import neo.smemo.info.util.view.AnnotationActionBar;
import neo.smemo.info.util.view.AnnotationView;

/**
 * 主页
 * Created by suzhenpeng on 2015/9/23.
 */
@AnnotationActionBar(abLayout = R.layout.actionbar_index, abTitle = R.string.app_name)
@AnnotationView(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {

    @AnnotationView(R.id.recyclerView)
    private RecyclerView recyclerView;
    @AnnotationView(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    private FundAdapter fundAdapter;

    private ArrayList<FundBean> fundBeanArrayList;
    private MyHandler handler;
    private static final int REQUEST_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        handler = new MyHandler(this);
//        生成actionbar，布局和标题。方法同@AnnotationActionBar相同
//        initActionBar(R.layout.actionbar_index, R.string.app_name);

        fundBeanArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        fundAdapter = new FundAdapter(this, fundBeanArrayList);
        recyclerView.setAdapter(fundAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });

        loadData();
    }

    private void loadData() {
        FundAction.getFundList(new BaseAction.ActionSuccessResponse() {
            @Override
            public void success(Object object) {
                ArrayList<FundBean> tmp = (ArrayList<FundBean>) object;
                fundBeanArrayList.clear();
                for (FundBean bean : tmp)
                    fundBeanArrayList.add(bean);
                SystemUtil.sendMessage(handler, REQUEST_SUCCESS);
            }

            @Override
            public void failure(int code, String message) {
                swipeRefreshLayout.setRefreshing(false);
                //显示错误信息
                showMessage(message);
            }
        });
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        finish();
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
    }

    static class MyHandler extends Handler {
        WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
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
            case REQUEST_SUCCESS:
                swipeRefreshLayout.setRefreshing(false);
                fundAdapter.notifyDataSetChanged();
                break;
        }
    }
}
