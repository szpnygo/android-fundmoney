package neo.smemo.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import neo.smemo.info.R;
import neo.smemo.info.bean.FundBean;

public class FundAdapter extends RecyclerView.Adapter {

    private ArrayList<FundBean> fundBeans;
    private Context context;

    public FundAdapter(Context context, ArrayList<FundBean> fundBeans) {
        this.fundBeans = fundBeans;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_fund, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder itemHolder = (MyHolder) holder;
        FundBean bean = getItem(position);
        itemHolder.title.setText(bean.company + "-" + bean.name + "[" + bean.fund_title + "]");
    }

    public FundBean getItem(int position) {
        return fundBeans.get(position);
    }

    @Override
    public int getItemCount() {
        return fundBeans.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView profit;
        private TextView seven;
        private TextView four;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fund_name);
            profit = (TextView) itemView.findViewById(R.id.profit);
            seven = (TextView) itemView.findViewById(R.id.seven);
            four = (TextView) itemView.findViewById(R.id.four);
        }
    }

}
