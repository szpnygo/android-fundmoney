package neo.smemo.info.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 把网络图片保存到本地 1.强引用，正常实例化一个对象。 jvm无论内存是否够用系统都不会释放这块内存
 * 2.软引用（softReference）:当我们系统内存不够时，会释放掉 3.弱引用：当我们系统清理内存时发现是一个弱引用对象，直接清理掉
 * 4.虚引用：当我们清理内存时会 把虚引用对象放入一个清理队列当中， 让我们程序员保存当前对象的状态
 * <p/>
 * FileUtiles 作用: 用来向我们的sdcard保存网络接收来的图片
 */
public class FileUtiles {

    public static final long B = 1;
    public static final long KB = B * 1024;
    public static final long MB = KB * 1024;
    public static final long GB = MB * 1024;

    /**
     * 格式化文件大小<b> 带有单位
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        StringBuilder sb = new StringBuilder();
        String u = null;
        double tmpSize = 0;
        if (size < KB) {
            sb.append(size).append("B");
            return sb.toString();
        } else if (size < MB) {
            tmpSize = getSize(size, KB);
            u = "KB";
        } else if (size < GB) {
            tmpSize = getSize(size, MB);
            u = "MB";
        } else {
            tmpSize = getSize(size, GB);
            u = "GB";
        }
        return sb.append(twodot(tmpSize)).append(u).toString();
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String twodot(double d) {
        return String.format("%.2f", d);
    }

    public static double getSize(long size, long u) {
        return (double) size / (double) u;
    }

    /**
     * sd卡挂载且可用
     *
     * @return
     */
    public static boolean isSdCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    // 获取手机在sdcard保存cache的地址
    public static String getAbsolutePath(Context context) {
        File root = context.getCacheDir();
        // 返回手机端的绝对路径，当我们软件卸载，以及清理缓存时会被清理掉
        if (root != null)
            return root.getAbsolutePath();
        return null;
    }

    /**
     * 递归创建文件目录
     *
     * @param path
     */
    public static void CreateDir(String path) {
        if (!isSdCardMounted())
            return;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                Log.e("hulutan", "error on creat dirs:" + e.getStackTrace());
            }
        }
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readTextFile(File file) throws IOException {
        String text = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            text = readTextInputStream(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return text;
    }

    /**
     * 从流中读取文件
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readTextInputStream(InputStream is) throws IOException {
        StringBuffer strbuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                strbuffer.append(line).append("\r\n");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return strbuffer.toString();
    }

    /**
     * 将文本内容写入文件
     *
     * @param file
     * @param str
     * @throws IOException
     */
    public static void writeTextFile(File file, String str) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 读取表情配置文件
     *
     * @param context
     * @return
     */
    public static List<String> getEmojiFile(Context context) {
        try {
            List<String> list = new ArrayList<String>();
            InputStream in = context.getResources().getAssets().open("emoji");// 文件名字为rose.txt  
            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {

        if (file.exists()) { // 判断文件是否存在  
            if (file.isFile()) { // 判断是否是文件  
                file.delete(); // delete()方法 你应该知道 是删除的意思;  
            } else if (file.isDirectory()) { // 否则如果它是一个目录  
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];  
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件  
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代  
                }
            }
            file.delete();
        }
    }


    // 判断图片在本地缓存当中是否存在，如果存在返回一个true
    public static boolean isBitmap(String name, Context ctx) {
        File root = ctx.getCacheDir();
        // file地址拼接
        File file = new File(root, name);
        return file.exists();
    }

    // 添加到本地缓存当中
    public void saveBitmap(String name, Bitmap bitmap, Context context) {
        if (bitmap == null)
            return;
        // 如果sdcard不能使用
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED)) {
            return;
        }
        // 拼接图片要保存到sd卡的地址
        String BitPath = getAbsolutePath(context) + File.separator + name;
        File f = new File(getAbsolutePath(context));
        if (!f.exists()) {
            f.mkdir();
        }
        // mtn/sdcard/android/com.anjoyo.zhangxinyi/files/
        try {
            FileOutputStream fos = new FileOutputStream(BitPath);
            /**
             * bitmap.compress把图片通过输出流保存到本地 Bitmap.CompressFormat.JPEG 保存图片的格式
             * 100 保存到本地的图片质量，需要压缩时适当调整大小
             *
             * */
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 添加到本地缓存当中
    public static void saveBitmap(String name, byte[] bitByte, Context context) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        if (bitByte == null)
            return;
        // 如果sdcard不能使用
        if (!isSdCardMounted()) {
            return;
        }
        // 拼接图片要保存到sd卡的地址
        String BitPath = getAbsolutePath(context) + "/" + name;
        File f = new File(getAbsolutePath(context));
        if (!f.exists()) {
            f.mkdir();
        }
        // mtn/sdcard/android/com.anjoyo.zhangxinyi/files/
        try {
            fos = new FileOutputStream(BitPath);
            bos = new BufferedOutputStream(fos);
            /**
             * bitmap.compress把图片通过输出流保存到本地 Bitmap.CompressFormat.JPEG 保存图片的格式
             * 100 保存到本地的图片质量，需要压缩时适当调整大小
             *
             * */
            bos.write(bitByte);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }


    public static byte[] readPicture(String name, Context context) {
        String pic_path = getAbsolutePath(context) + File.separator + name;
        FileInputStream is;
        byte[] data = null;
        try {
            is = new FileInputStream(pic_path);
            int i = is.available(); // 得到文件大小
            data = new byte[i];
            is.read(data); // 读数据
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return data;
    }

}
