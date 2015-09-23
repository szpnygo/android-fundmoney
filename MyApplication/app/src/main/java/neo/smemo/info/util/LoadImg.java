package neo.smemo.info.util;

import android.content.Context;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制图片的加载类
 * 列表在滑动过程时,没有图片会进行下载,并保存到sdcard与 imageCaches 当中去,使用软引用进行封装，如果内存不够时
 * 我们的imageCaches 当中的Bitmap对象会被清理掉,图片被释放掉 再次需要加载的时候，先从1级缓存当中获取，如果没有的话，去
 * 本地获取，本地也获取不到的话，去网络下载。 一级缓存作用：对于listview当中刚刚滑动过的item显示的图片进行保存
 * 二级缓存作用：对于listview当中很久前查看的图片或已经被释放掉图片 进行保存
 * */
public class LoadImg {

	// 图片的一级缓存,保存在我们程序内部
	private Map<String, SoftReference<byte[]>> imageCaches = null;


	// 初始化上面的相关的变量
	public LoadImg(Context ctx) {
		imageCaches = new HashMap<String, SoftReference<byte[]>>();
	}
	public void putCash(String imageUrl,byte[] img,Context context){
		imageCaches.put(imageUrl,
				new SoftReference<byte[]>(img));
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length());
		// 添加到二级缓存
		FileUtiles.saveBitmap(filename, img,context);
	}
	
	public byte[] getCash(String imageUrl,Context context){
		final String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length());
		// 查找一级缓存，看看是否有这张图片
		// 如果map当中有这个key返回一个true
		if (imageCaches.containsKey(imageUrl)) {
			// 找到对应图片软引用的封装
			SoftReference<byte[]> soft = imageCaches.get(imageUrl);
			// 从软引用当中获取图片
			byte[] bit = soft.get();
			if (bit != null)
				return bit;
			// 从我们的一级缓存（程序内部获取图片）
		}
		// 从二级缓存当中获取图片
		if (FileUtiles.isBitmap(filename,context)) {
			byte[] bit = FileUtiles.readPicture(filename,context);
			// 在二级缓存读取的时候直接添加到一级缓存当中
			imageCaches.put(imageUrl, new SoftReference<byte[]>(bit));
			return bit;
		}
		return null;
	}
	


}
