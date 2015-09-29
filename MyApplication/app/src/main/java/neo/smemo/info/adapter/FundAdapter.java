package neo.smemo.info.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import neo.smemo.info.R;
import neo.smemo.info.activity.HistoryActivity;
import neo.smemo.info.bean.FundBean;
import neo.smemo.info.util.ui.RecyclerViewDragHolder;

/**
 * 基金列表
 */
public class FundAdapter extends RecyclerView.Adapter {

    private ArrayList<FundBean> fundBeans;
    private Context context;

    public FundAdapter(Context context, ArrayList<FundBean> fundBeans) {
        this.fundBeans = fundBeans;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取背景菜单
        View mybg = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_fund_bg, null);
        mybg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //前台菜单
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_fund, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(context, mybg, view, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyHolder itemHolder = (MyHolder) RecyclerViewDragHolder.getHolder(holder);
        final FundBean bean = getItem(position);
        itemHolder.title.setText(bean.name + "[" + bean.fund_title + "]");
        itemHolder.profit.setText(bean.fund_profit);
        itemHolder.seven.setText(bean.fund_p_seven);
        itemHolder.four.setText(bean.fund_p_fourteen);
        //使用fresco加载图片
        itemHolder.icon.setImageURI(Uri.parse(bean.img));
        itemHolder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryActivity.class);
                intent.putExtra("data", bean);
                context.startActivity(intent);
            }
        });
        itemHolder.close();
        itemHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fundBeans.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    public FundBean getItem(int position) {
        return fundBeans.get(position);
    }

    @Override
    public int getItemCount() {
        return fundBeans.size();
    }

    class MyHolder extends RecyclerViewDragHolder {
        private TextView title;
        private TextView profit;
        private TextView seven;
        private TextView four;
        private SimpleDraweeView icon;

        private TextView delete;

        public MyHolder(Context context, View bgView, View topView) {
            super(context, bgView, topView);
        }

        public MyHolder(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            title = (TextView) itemView.findViewById(R.id.fund_name);
            profit = (TextView) itemView.findViewById(R.id.profit);
            seven = (TextView) itemView.findViewById(R.id.seven);
            four = (TextView) itemView.findViewById(R.id.four);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.icon);
            delete = (TextView) itemView.findViewById(R.id.delete);
        }
    }

}
