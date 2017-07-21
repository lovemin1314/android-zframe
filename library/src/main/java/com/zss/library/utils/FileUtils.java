package com.zss.library.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 文件工具类
 * @author zm
 *
 */
public class FileUtils {
	/**
	 * 获取文件路径 /data/data/...
	 * @param mContext
	 * @return String
	 */
	public static String getFileDir(Context mContext) {
		if (mContext == null) {
			return "";
		}
		return mContext.getFilesDir().getPath() + File.separator;
	}

	/**
	 * 获取缓存路径 /data/data/com.example/cache/
	 * @param mContext
	 * @return String
	 */
	public static String getCacheDir(Context mContext) {
		if (mContext == null) {
			return "";
		}
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 挂载了外部存储器
			cachePath = mContext.getExternalCacheDir() + File.separator;
		} else {
			cachePath = mContext.getCacheDir() + File.separator;
		}
		return cachePath;
	}

	/**
	 * 获取外置目录
	 * @param mContext
	 * @return String
	 */
	public static String getExtDir(Context mContext) {
		if (mContext == null) {
			return "";
		}
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 挂载了外部存储器
			cachePath = Environment.getExternalStorageDirectory()
					+ File.separator;
		} else {
			cachePath = File.separator;
		}
		return cachePath;
	}

	/**
	 * 通过使用自带的文件管理器选中文件，解析它的路径
	 * @param mContext
	 * @param uri
	 * @return Sting
	 * @throws URISyntaxException
	 */
	public static String getPath(Context mContext, Uri uri)
			throws URISyntaxException {
		if (mContext == null) {
			return "";
		}
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = mContext.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {

			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	/**
	 * 根据文件判断该文件是否存在
	 * @param context
	 * @param file
	 * @return boolean
	 */
	public static boolean exists(Context context, String file) {
		return new File(file).exists();
	}

	/**
	 * 检查文件是否过期 <br />
	 * 若文件不存在，则直接返回true <br />
	 * time指定过期的秒数
	 * @param context
	 * @param file
	 * @param time 秒
	 * @return boolean
	 */
	public static boolean expire(Context context, String file, int time) {
		if (!exists(context, file)) {
			return true;
		}
		File f = new File(file);
		if (Calendar.getInstance().getTimeInMillis() - f.lastModified() > time * 1000) {
			return true;
		}
		return false;
	}

	/**
	 * 保存文件到包目录的files
	 * @param mContext
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void save(Context mContext, String file, String content)
			throws IOException {
		if (mContext == null) {
			return;
		}
		FileOutputStream outputStream = mContext.openFileOutput(file,
				Context.MODE_PRIVATE);
		outputStream.write(content.getBytes());
		outputStream.close();
	}

	/**
	 * 保存文件
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void saveFile(String file, String content) throws IOException {
		if (TextUtils.isEmpty(content)) {
			return;
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(content.getBytes());
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 读取文件
	 * @param context
	 * @param file
	 * @return String
	 * @throws IOException
	 */
	public static String read(Context context, String file) throws IOException {
		if (context == null) {
			return "";
		}
		FileInputStream in = context.openFileInput(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		byte[] data = out.toByteArray(); // 得到二进制数据
		out.close();
		in.close();
		return new String(data);
	}

	/**
	 * 读取文件
	 * @param file 文件完整路径
	 * @return 文件字符串
	 */
	public static String readFile(String file) {
		if (TextUtils.isEmpty(file) || "null".equalsIgnoreCase(file)) {
			return "";
		}
		File f = new File(file);
		if (!f.exists()) {
			return "";
		}
		return readFile(f);
	}

	/**
	 * 读取文件
	 * @param path 目录
	 * @param file 文件
	 * @return 文件字符串
	 */
	public static String readFile(String path, String file) {
		if (TextUtils.isEmpty(file) || "null".equalsIgnoreCase(file)
				|| TextUtils.isEmpty(path) || "null".equalsIgnoreCase(path)) {
			return "";
		}
		File f = new File(path, file);
		if (!f.exists()) {
			return "";
		}
		return readFile(f);
	}

	/**
	 * 读取文件
	 * @param file 文件
	 * @return 文件字符串
	 */
	public static String readFile(File file) {
		if (!file.exists()) {
			return "";
		}
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((len = fin.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			byte[] data = out.toByteArray(); // 得到二进制数据
			out.close();
			fin.close();
			return new String(data);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 复制Asset到制定目录
	 * @param context
	 * @param assetDir
	 * @param dir
	 */
	public static void copyAssets(Context context, String assetDir, String dir) {
		if (context == null) {
			return;
		}
		String[] files;
		try {
			files = context.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists()) {
			mWorkingPath.mkdirs();
		}
		for (int i = 0; i < files.length; i++) {
			try {
				String fileName = files[i];
				// we make sure file name not contains '.' to be a folder.
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						copyAssets(context, fileName, dir + fileName + "/");
					} else {
						copyAssets(context, assetDir + "/" + fileName, dir
								+ fileName + "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists())
					outFile.delete();
				InputStream in = null;
				if (0 != assetDir.length())
					in = context.getAssets().open(assetDir + "/" + fileName);
				else
					in = context.getAssets().open(fileName);
				OutputStream out = new FileOutputStream(outFile);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 遍历删除文件夹
	 * 
	 * @param filePath
	 */
	public static void delete(String filePath) {
		if (TextUtils.isEmpty(filePath) || "null".equalsIgnoreCase(filePath)) {
			return;
		}
		File file = new File(filePath);
		delete(file);
	}

	/**
	 * 删除文件
	 * @param file
	 */
	public static void delete(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					delete(files[i]);
				}
			}
			file.delete();
		}
	}
}
