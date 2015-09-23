package neo.smemo.info.util.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import neo.smemo.info.R;
import neo.smemo.info.util.StringUtil;

public class ActionBar extends Fragment {

    /**
     * 标题
     */
    private String title;
    /**
     * 布局ID
     */
    private int resId;

    private ImageView share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.title = bundle.getString("title");
        this.resId = bundle.getInt("resid");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 获取父类接口
        try {
            // 获取OnLeftBtnClickListenr接口
            leftBtnClickListenr = (ActionBarLeftBtnClickListenr) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 获取OnRightBtnClickListenr接口
            rightBtnClickListenr = (ActionBarRightBtnClickListenr) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (resId == -1)
            resId = R.layout.actionbar;
        View view = inflater.inflate(resId, container, false);
        /** 左侧按钮 */
        View leftBtn = view.findViewById(R.id.actionbar_left);
        /** 右侧按钮 */
        View rightBtn = view.findViewById(R.id.actionbar_right);

        TextView titleTv = (TextView) view.findViewById(R.id.index_ab_title);
        if (!StringUtil.isEmpty(title)) {
            titleTv.setText(title);
        }

        if (leftBtn != null) {
            leftBtn.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    if (leftBtnClickListenr != null) {
                        leftBtnClickListenr.onLeftClick();
                    }
                }
            });
        }
        if (rightBtn != null) {
            rightBtn.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    if (rightBtnClickListenr != null)
                        rightBtnClickListenr.onRightClick();
                }
            });
        }

        return view;
    }

    public void showShareIcon(boolean show) {
        if (share != null)
            share.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * @author Administrator 左侧按钮点击事件
     */
    public interface ActionBarLeftBtnClickListenr {
        void onLeftClick();
    }

    /**
     * @author Administrator 右侧按钮点击事件
     */
    public interface ActionBarRightBtnClickListenr {
        void onRightClick();
    }

    /***************************** 变量 ************************************/
    /**
     * 左侧按钮事件
     */
    private ActionBarLeftBtnClickListenr leftBtnClickListenr;
    /**
     * 右侧按钮事件
     */
    private ActionBarRightBtnClickListenr rightBtnClickListenr;
}
