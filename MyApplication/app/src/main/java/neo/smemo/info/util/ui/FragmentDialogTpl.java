package neo.smemo.info.util.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import neo.smemo.info.app.Constant;
import neo.smemo.info.app.ErrorInfo;

/**
 * 自定义Dialog
 * Created by suzhenpeng on 2015/7/17.
 */
public abstract class FragmentDialogTpl extends DialogFragment implements Constant, ErrorInfo {

    private View dialogView;
    private int viewID;
    protected Context mContext;
    private boolean outoSideDismiss = false;

    public FragmentDialogTpl() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent);
        setCancelable(true);
        mContext = getActivity();
    }

    protected void setContentView(@LayoutRes int id) {
        this.viewID = id;
    }

    abstract protected void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        dialogView = inflater.inflate(viewID, container, false);
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outoSideDismiss) dismiss();
            }
        });
        initView(dialogView);
        return dialogView;
    }

    public void show(FragmentManager manager) {
        super.show(manager, "");
    }

    public void setOutoSideDismiss(boolean outoSideDismiss) {
        this.outoSideDismiss = outoSideDismiss;
    }
}
