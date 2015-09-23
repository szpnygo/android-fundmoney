package neo.smemo.info.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
	
	public static String read(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream in = new FileInputStream(file);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readInStream(InputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}
			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static String fromIputStreamToString(InputStream is) {
		if (is == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	public static boolean fromIputStreamToFile(InputStream is,
			String outfilepath) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;

		try {
			inBuff = new BufferedInputStream(is);

			outBuff = new BufferedOutputStream(
					new FileOutputStream(outfilepath));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (inBuff != null)

					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public static InputStream fromFileToIputStream(String infilepath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(infilepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fis;
	}

	public static InputStream fromStringToIputStream(String s) {
		if (s != null && !s.equals("")) {
			try {

				ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
						s.getBytes());
				return stringInputStream;
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	public static void zipFiles(ArrayList<File> resFileList, File zipFile)
			throws IOException {

		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			for (File resFile : resFileList) {
				zipFile(resFile, zipout, "");
			}
		} finally {
			if (zipout != null)
				zipout.close();
		}
	}

	public static void zipFile(File resFile, File zipFile) {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			zipFile(resFile, zipout, "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zipout != null)
				try {
					zipout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private static void zipFile(File resFile, ZipOutputStream zipout,
			String rootpath) throws IOException {
		rootpath = rootpath
				+ (rootpath.trim().length() == 0 ? "" : File.separator)
				+ resFile.getName();
		rootpath = new String(rootpath.getBytes("8859_1"), "utf-8");
		BufferedInputStream in = null;
		try {
			if (resFile.isDirectory()) {
				File[] fileList = resFile.listFiles();
				for (File file : fileList) {
					zipFile(file, zipout, rootpath);
				}
			} else {
				byte buffer[] = new byte[BUFF_SIZE];
				in = new BufferedInputStream(new FileInputStream(resFile),
						BUFF_SIZE);
				zipout.putNextEntry(new ZipEntry(rootpath));
				int realLength;
				while ((realLength = in.read(buffer)) != -1) {
					zipout.write(buffer, 0, realLength);
				}
				in.close();
				zipout.flush();
				zipout.closeEntry();
			}
		} finally {
			if (in != null)
				in.close();
		}
	}
}
