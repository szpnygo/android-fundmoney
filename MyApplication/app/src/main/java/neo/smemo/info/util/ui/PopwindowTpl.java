package neo.smemo.info.util.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * popwindow
 *
 * @author suzhenpeng
 * @version 1.0.1
 * @since JDK 1.6 SDK 2.1
 */
public abstract class PopwindowTpl {
    protected boolean dismissView = true;

    public PopwindowTpl(Context context) {
        super();
        this.context = context;
        beforeInitView();
        initPop();
    }

    /**
     * 设置布局ID
     */
    protected abstract void beforeInitView();

    /**
     * 初始化
     */
    protected void initPop() {
        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        initPopWindow();
        initView();
    }

    /**
     * 初始化popwindow
     */
    protected void initPopWindow() {
        LayoutInflater inflater = LayoutInflater.from(context);
        popView = inflater.inflate(viewID, null);
        popWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popWindow.setFocusable(true); //设置PopupWindow可获得焦点
        popWindow.setTouchable(true); //设置PopupWindow可触摸
        popWindow.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
        popWindow.setAnimationStyle(android.R.style.Animation_Dialog);


        popView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dismissView) {
                    destory();
                    popWindow.dismiss();
                }
            }
        });
    }

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 界面刷新
     */
    public abstract void update();

    /**
     * 弹出pop
     *
     * @param view
     */
    public void showAsDropDown(View view) {
        if (popWindow != null) {
            popWindow.setFocusable(true);
            popWindow.setAnimationStyle(android.R.anim.fade_in);
            popWindow.showAsDropDown(view);
        }

    }

    protected void destory() {

    }


    /**
     * 默认全屏中央弹出
     *
     * @param viewID   页面
     * @param layoutID 布局ID
     */
    public void showAsDropDown(@LayoutRes int viewID, int layoutID) {
        // 获取整体布局，定位popwindow
        if (context != null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View popView = inflater.inflate(viewID, null);
            View view = popView.findViewById(layoutID);

            showAsDropDown(view);
        }

    }

    public void dismiss() {
        if (popWindow != null && popWindow.isShowing()) {
            destory();
            popWindow.dismiss();
        }
    }

    /**
     * @param viewID the viewID to set
     */
    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

	/*-------------------------------------------------属性-----------------------------------------------*/
    /**
     * 上下文对象
     */
    protected Context context;
    /**
     * popWindow
     */
    public PopupWindow popWindow;
    /**
     * 弹出框布局
     */
    protected View popView;
    /**
     * 布局ID
     */
    private int viewID;

}