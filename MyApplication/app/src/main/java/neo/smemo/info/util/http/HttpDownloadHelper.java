package neo.smemo.info.util.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by suzhenpeng on 2015/5/20.
 */
public class HttpDownloadHelper {

    private HttpUtils http = null;

    public HttpDownloadHelper() {
        super();
        http = new HttpUtils();
    }

    public HttpHandler downloadFile(String url, File file, final RequestCallBack<File> callBack) {
        return downloadFile(url, file, true, false, callBack);
    }

    public HttpHandler downloadFile(String url, File file, boolean redown, final RequestCallBack<File> callBack) {
        return downloadFile(url, file, redown, false, callBack);
    }

    public HttpHandler downloadFile(String url, File file) {
        return downloadFile(url, file, true, false, null);
    }

    public HttpHandler downloadFile(String url, File file, boolean redown) {
        return downloadFile(url, file, redown, false, null);
    }

    public HttpHandler downloadFile(String url, File file, boolean redown, boolean rename, final RequestCallBack<File> callBack) {
        HttpHandler handler = http.download(url, file.getPath(), redown, rename, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                if (null != callBack)
                    callBack.onSuccess(responseInfo);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if (null != callBack)
                    callBack.onFailure(error, msg);
            }

            @Override
            public void onStart() {
                super.onStart();
                if (null != callBack)
                    callBack.onStart();
            }

            @Override
            public void onCancelled() {
                super.onCancelled();
                if (null != callBack)
                    callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                if (null != callBack)
                    callBack.onLoading(total, current, isUploading);
            }
        });
        return handler;
    }

}
