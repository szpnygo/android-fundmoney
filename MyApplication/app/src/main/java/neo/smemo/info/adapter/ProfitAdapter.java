package neo.smemo.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import neo.smemo.info.R;
import neo.smemo.info.app.Constant;
import neo.smemo.info.bean.FundBean;
import neo.smemo.info.util.DipPxUtil;
import neo.smemo.info.util.LogHelper;

public class ProfitAdapter extends RecyclerView.Adapter {

    private ArrayList<FundBean> fundBeans;
    private Context context;

    public ProfitAdapter(Context context, ArrayList<FundBean> fundBeans) {
        this.fundBeans = fundBeans;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_profit, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        FundBean bean = fundBeans.get(position);
        myHolder.time.setText(bean.fund_time);
        myHolder.profit.setText(bean.fund_profit);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = DipPxUtil.dipToPx(context, 192 + 120 * bean.width);
        myHolder.profitBg.setBackgroundColor(context.getResources().getColor(position == 0 ? R.color.red : R.color.gray));
        myHolder.profitBg.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return fundBeans.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private LinearLayout profitBg;
        private TextView time;
        private TextView profit;

        public MyHolder(View itemView) {
            super(itemView);
            profitBg = (LinearLayout) itemView.findViewById(R.id.profit_bg);
            time = (TextView) itemView.findViewById(R.id.time);
            profit = (TextView) itemView.findViewById(R.id.profit);
        }
    }
}
