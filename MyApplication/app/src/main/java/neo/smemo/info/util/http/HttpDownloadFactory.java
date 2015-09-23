package neo.smemo.info.util.http;

import com.lidroid.xutils.http.HttpHandler;

import java.io.File;
import java.util.ArrayList;

/**
 * 下载管理器
 * Created by suzhenpeng on 2015/6/26.
 */
public class HttpDownloadFactory {

    private ArrayList<MyDownLoadHandler> httpHandlerArrayList = new ArrayList<>();

    private static HttpDownloadFactory instance;

    private void HttpDownloadHelper() {

    }

    public static HttpDownloadFactory getInstance() {
        if (instance == null)
            instance = new HttpDownloadFactory();
        return instance;
    }

    /**
     * 下载文件
     *
     * @param url
     * @param file
     * @return
     */
    public MyDownLoadHandler downFile(String url, final File file) {
        remove(file);
        //创建新的下载进程并且开始下载
        HttpHandler handler = new HttpDownloadHelper().downloadFile(url, file, null);
        MyDownLoadHandler downLoadHandler = new MyDownLoadHandler(url, file, handler);
        downLoadHandler.setFinishListener(new DownFinishListener() {
            @Override
            public void success() {
                //下载完成自动删除
                remove(file);
            }
        });
        add(downLoadHandler);
        return downLoadHandler;
    }

    /**
     * 判断是否正在下载
     *
     * @param file
     * @return
     */
    public boolean isDownloading(File file) {
        MyDownLoadHandler downLoadHandler = find(file);
        if (file == null || downLoadHandler == null || downLoadHandler.httpHandler.isCancelled() || downLoadHandler.httpHandler.isPaused())
            return false;
        return downLoadHandler.isDowning;
    }

    public void stop(File file) {
        MyDownLoadHandler downLoadHandler = find(file);
        if (null != downLoadHandler)
            downLoadHandler.httpHandler.cancel();
    }

    public void resume(String url, final File file) {
        MyDownLoadHandler downLoadHandler = find(file);
        if (null != downLoadHandler) {
            downLoadHandler.httpHandler.resume();
            return;
        }
        //创建新的下载进程并且开始下载
        HttpHandler handler = new HttpDownloadHelper().downloadFile(url, file, null);
        MyDownLoadHandler resumeHandler = new MyDownLoadHandler(url, file, handler);
        resumeHandler.setFinishListener(new DownFinishListener() {
            @Override
            public void success() {
                //下载完成自动删除
                remove(file);
            }
        });
        add(resumeHandler);
    }

    public MyDownLoadHandler find(File file) {
        for (MyDownLoadHandler myDownLoadHandler : httpHandlerArrayList) {
            if (myDownLoadHandler.file.getAbsolutePath().equals(file.getAbsolutePath())) {
                return myDownLoadHandler;
            }
        }
        return null;
    }

    public MyDownLoadHandler find(MyDownLoadHandler bean) {
        for (MyDownLoadHandler myDownLoadHandler : httpHandlerArrayList) {
            if (myDownLoadHandler.file.getAbsolutePath().equals(bean.file.getAbsolutePath())) {
                return myDownLoadHandler;
            }
        }
        return null;
    }

    public void remove(MyDownLoadHandler bean) {
        for (MyDownLoadHandler myDownLoadHandler : httpHandlerArrayList) {
            if (myDownLoadHandler.file.getAbsolutePath().equals(bean.file.getAbsolutePath())) {
                httpHandlerArrayList.remove(myDownLoadHandler);
                break;
            }
        }
    }

    public void remove(File file) {
        for (MyDownLoadHandler myDownLoadHandler : httpHandlerArrayList) {
            if (myDownLoadHandler.file.getAbsolutePath().equals(file.getAbsolutePath())) {
                httpHandlerArrayList.remove(myDownLoadHandler);
                break;
            }
        }
    }

    public void add(MyDownLoadHandler bean) {
        if (find(bean) != null)
            remove(bean);
        httpHandlerArrayList.add(bean);

    }

    public interface DownFinishListener {
        void success();
    }

}
