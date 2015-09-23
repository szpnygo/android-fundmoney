package neo.smemo.info.util.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 自定义Dialog
 * Created by suzhenpeng on 2015/7/17.
 */
public abstract class ViewDialogTpl extends MaterialDialog {

    private View dialogView;
    private int viewID;
    protected boolean setView = false;

    public ViewDialogTpl(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
    }

    abstract protected void initView(View view);

    protected void setContentView(@LayoutRes int id) {
        this.viewID = id;
        loadView();
    }

    public void setSetView(boolean setView) {
        this.setView = setView;
    }

    protected void loadView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(viewID, null);
        initView(dialogView);
        if (setView) {
            this.setView(dialogView);
        } else {
            this.setContentView(dialogView);
        }
    }

    @Override
    public void show() {
        super.show();
    }

}
