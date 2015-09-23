package neo.smemo.info.util.http;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import java.io.File;

/**
 * Created by suzhenpeng on 2015/6/26.
 */
public class MyDownLoadHandler {

    public long total;
    public long current;
    public boolean isDowning = false;

    public String url;
    public File file;
    public HttpHandler httpHandler;

    private RequestCallBack<File> requestCallBack;
    private HttpDownloadFactory.DownFinishListener finishListener;

    public MyDownLoadHandler(String url, final File file, HttpHandler httpHandler) {
        this.url = url;
        this.file = file;
        this.httpHandler = httpHandler;
        httpHandler.setRequestCallBack(new RequestCallBack<File>() {

            @Override
            public void onStart() {
                super.onStart();
                isDowning = false;
                if (requestCallBack != null)
                    requestCallBack.onStart();
            }

            @Override
            public void onCancelled() {
                super.onCancelled();
                isDowning = false;
                if (requestCallBack != null)
                    requestCallBack.onCancelled();

            }

            @Override
            public void onLoading(long _total, long _current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                isDowning = true;
                total = _total;
                current = _current;
                if (requestCallBack != null)
                    requestCallBack.onLoading(_total, _current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                isDowning = false;
                if (requestCallBack != null)
                    requestCallBack.onSuccess(responseInfo);
                if (finishListener != null)
                    finishListener.success();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                isDowning = false;
                if (requestCallBack != null)
                    requestCallBack.onFailure(e, s);
                if (finishListener != null)
                    finishListener.success();
                if(e.getExceptionCode() != 416 && file != null && file.exists())
                    file.delete();
            }
        });
    }

    public void setRequestCallBack(RequestCallBack<File> requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public void setFinishListener(HttpDownloadFactory.DownFinishListener finishListener) {
        this.finishListener = finishListener;
    }

}
