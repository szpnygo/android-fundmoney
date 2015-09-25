package neo.smemo.info.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import neo.smemo.info.R;
import neo.smemo.info.action.FundAction;
import neo.smemo.info.adapter.ProfitAdapter;
import neo.smemo.info.base.BaseAction;
import neo.smemo.info.base.BaseFragmentActivity;
import neo.smemo.info.bean.FundBean;
import neo.smemo.info.util.LogHelper;
import neo.smemo.info.util.system.SystemUtil;
import neo.smemo.info.util.view.AnnotationView;

@AnnotationView(R.layout.activity_history)
public class HistoryActivity extends BaseFragmentActivity {

    private FundBean fundBean;

    private static final int REQUEST_SUCCESS = 1;

    @AnnotationView(R.id.recyclerView)
    private RecyclerView recyclerView;
    private ProfitAdapter adapter;

    private ArrayList<FundBean> fundBeanArrayList;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        handler = new MyHandler(this);
        fundBean = (FundBean) getIntent().getSerializableExtra("data");
        initActionBar(R.layout.actionbar, fundBean.name);

        fundBeanArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProfitAdapter(this, fundBeanArrayList);
        recyclerView.setAdapter(adapter);


        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        FundAction.getFundHistory(fundBean.fundid, 20, new BaseAction.ActionSuccessResponse() {
            @Override
            public void success(Object object) {
                ArrayList<FundBean> tmp = (ArrayList<FundBean>) object;
                fundBeanArrayList.clear();
                float min = 1000f;
                float max = 0f;
                for (FundBean bean : tmp) {
                    float profit = Float.valueOf(bean.fund_profit);
                    if (profit <= min)
                        min = profit;
                    if (profit >= max)
                        max = profit;
                }
                for (FundBean bean : tmp) {
                    bean.width = (Float.valueOf(bean.fund_profit) - min) / max;
                    fundBeanArrayList.add(bean);
                }
                LogHelper.Log_I(LOG_TAG, "获取本地数据" + tmp.size() + "条");
                SystemUtil.sendMessage(handler, REQUEST_SUCCESS);
            }

            @Override
            public void failure(int code, String message) {

            }
        });
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        finish();
    }

    static class MyHandler extends Handler {
        WeakReference<HistoryActivity> mActivity;

        public MyHandler(HistoryActivity activity) {
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
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
