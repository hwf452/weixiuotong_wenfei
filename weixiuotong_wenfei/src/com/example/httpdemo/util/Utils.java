package com.example.httpdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class Utils {
	private static final String TAG = Utils.class.getSimpleName();

	public static boolean isStringEmpty(String str) {
		return str == null || "null".equalsIgnoreCase(str) || str.isEmpty();
	}

	public static void showToast(Context mContext, String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context mContext, int resourceId) {
		Toast.makeText(mContext, resourceId, Toast.LENGTH_SHORT).show();
	}

	public static boolean isMapEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static String encode(String str, String format) {
		try {
			return URLEncoder.encode(str, format);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		return pattern.matcher(str).matches();
	}

	public static boolean isCollectionEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 是否存在SDCard
	 * 
	 * @param mContext
	 * @return true 存在
	 */
	public static boolean checkSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	public static String getHashName(String name) {
		if (!isStringEmpty(name)) {
			return String.valueOf(name.hashCode());
		}
		return null;
	}

	/**
	 * 获取SDCard路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		String filePath = Environment.getExternalStorageDirectory().getPath();
		Log.d(TAG, "sdcard filePath:" + filePath);
		return filePath;
	}

	// 将输入流转换成字符串
	public static String inStream2String(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), "utf-8");
	}

	/**
	 * 获取文件名称
	 * 
	 * @return
	 */
	public static String getImageFileName() {
		return System.currentTimeMillis() + ".png";
	}

	public static Date getDateForString(String format, String dateStr) {
		if (isStringEmpty(dateStr)) {
			return null;
		}
		if (isStringEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFormattingDate(String format, Date date) {
		String dateFormat = format;
		if (isStringEmpty(format)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null) {
			date = new Date();
		}
		return new SimpleDateFormat(dateFormat).format(date);
	}

	public static void setOrgGridViewHeight(GridView mGridView) {

		ListAdapter listAdapter = mGridView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, null);
			if (listItem == null) {
				continue;
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = mGridView.getLayoutParams();
		params.height = totalHeight + (5 * (listAdapter.getCount()));
		mGridView.setLayoutParams(params);
	}

	public static Boolean checkPhone(String phone) {
		if (isStringEmpty(phone)) {
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(phone);

		return m.matches();
	}

	/** 格式化Double类型 ，转换为保存两位小数 **/
	public static String formatNumber(double d) {
		DecimalFormat nf = new DecimalFormat();
		// nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(2);
		return nf.format(d);
	}

	/**
	 * MD5加密
	 * 
	 * @param s
	 *            加密参数
	 * @return 加密后的参数
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
