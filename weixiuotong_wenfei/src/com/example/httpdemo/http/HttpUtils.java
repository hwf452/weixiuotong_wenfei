package com.example.httpdemo.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video;
import android.util.Log;

import com.example.httpdemo.util.JSONUtils;
import com.example.httpdemo.util.NetworkUtils;
import com.example.httpdemo.util.Utils;
import com.weixiuotong.wenfei.service.ParseXmlService;

public class HttpUtils {
	private static final String TAG = HttpUtils.class.getSimpleName();

	private static final int CONNECTION_TIMEOUT = 1000 * 10;

	private static final int CONNECTION_SO_TIMEOUT = 1000 * 60;

	private boolean isCancel = false;

	private OnHttpToolsListener listener;
	private OnHttpVersionUpdate getVersionListener;
	
	private HashMap<String, String> mHashMap;

	/**
	 * Http请求连接成功状态码hwf452hwf452
	 */
	public static final int HTTP_CONNECTION_OK = 1;

	/**
	 * Http请求连接失败状态码
	 */
	public static final int HTTP_CONNECTION_ERROR = 0;

	/**
	 * 没有网络状态码
	 */
	public static final int HTTP_NETWORK_NOT_EXISTS = -1;

	/**
	 * 文件上传状态码(失败)
	 */
	public static final int FILE_UPLOAD_ERROR = -3;

	/**
	 * 文件上传状态码(成功)
	 */
	public static final int FILE_UPLOAD_OK = 2;
	/**
	 * 文件上传状态码(文件不存在)
	 */
	public static final int FILE_UPLOAD_NOT_EXISTS = 3;

	private ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务

	private String url;
	private Context mContext;
	private Map<String, Object> paramMap;

	public HttpUtils(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 发送HTTP（GET）请求
	 * 
	 * @param paramMap
	 *            参数Map
	 * @param url
	 *            请求URL
	 * @param mHandler
	 *            回调Handler
	 */
	public void sendGetHttpRequest(String url) {
		this.url = url;

		executorService.submit(new GetRequestTask());
	}
	
	public void sendGetHttpRequest_getXml(String url) {
		this.url = url;

		executorService.submit(new GetRequestTask_getXml());
	}

	/**
	 * 发送HTTP（POST）请求
	 * 
	 * @param paramMap
	 *            参数Map
	 * @param url
	 *            请求URL
	 * @param mHandler
	 *            回调Handler
	 */
	public void sendPostHttpRequest(Map<String, Object> paramMap, String url) {
		this.url = url;
		this.paramMap = paramMap;

		executorService.submit(new PostRequestTask());
	}

	public void closeHttpRequest() {
		Log.d(TAG, " Http Request Close ");

		isCancel = true;
	}

	class GetRequestTask extends Thread {

		@Override
		public void run() {

			Log.d(TAG, "GetRequestTask()...");
			String result = "";
			InputStream input = null;
			try {

				if (!NetworkUtils.isNetworkAvailable(mContext)) {
					sendMessage(HTTP_NETWORK_NOT_EXISTS, null);
					return;
				}
				Log.d(TAG,url);
				HttpGet request = new HttpGet(url);
				request.addHeader("Accept", "text/json");
				HttpParams params = new BasicHttpParams();
				Log.d(TAG, "HttpUtils GetRequestTask request url :" + url);
				HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, CONNECTION_SO_TIMEOUT);

				request.setParams(params);

				HttpResponse response = new DefaultHttpClient().execute(request);
				Log.d(TAG, " http request response state  is:" + response.getStatusLine().getStatusCode());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// input = response.getEntity().getContent();
					result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					// result = inStream2String(input);
				} else {
					sendMessage(HTTP_CONNECTION_ERROR, null);
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, " http request exception is:" + e.toString());
				sendMessage(HTTP_CONNECTION_ERROR, null);
				return;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
						Log.e(TAG, " http request exception is:" + e.toString());
						sendMessage(HTTP_CONNECTION_ERROR, null);
					}
				}
			}
			Log.i(TAG, " http request result =" + result);
			sendMessage(HTTP_CONNECTION_OK, result);
		}
	};
	
	class GetRequestTask_getXml extends Thread {

		@Override
		public void run() {

			Log.d(TAG, "GetRequestTask()...");
			HashMap<String, String> result=null;
			InputStream input = null;
			try {

				if (!NetworkUtils.isNetworkAvailable(mContext)) {
					sendMessage(HTTP_NETWORK_NOT_EXISTS, null);
					return;
				}
				Log.d(TAG,url);
				HttpGet request = new HttpGet(url);
				request.addHeader("Accept", "text/json");
				HttpParams params = new BasicHttpParams();
				Log.d(TAG, "HttpUtils GetRequestTask request url :" + url);
				HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, CONNECTION_SO_TIMEOUT);

				request.setParams(params);

				HttpResponse response = new DefaultHttpClient().execute(request);
				Log.d(TAG, " http request response state  is:" + response.getStatusLine().getStatusCode());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					input = response.getEntity().getContent();
					ParseXmlService service = new ParseXmlService();
					try {
						mHashMap = service.parseXml(input);
						if(mHashMap!=null){
							result=mHashMap;
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					 
				} else {
					sendMessage(HTTP_CONNECTION_ERROR, null);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, " http request exception is:" + e.toString());
				sendMessage(HTTP_CONNECTION_ERROR, null);
				return;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
						Log.e(TAG, " http request exception is:" + e.toString());
						sendMessage(HTTP_CONNECTION_ERROR, null);
					}
				}
			}
			
			sendMessage(HTTP_CONNECTION_OK, result);
		}
	};

	// 将输入流转换成字符串
	private String inStream2String(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), HTTP.UTF_8);
	}

	private void sendMessage(int what, Object obj) {
		if (!isCancel && mHandler != null) {
			Message msg = Message.obtain();
			msg.what = what;
			msg.obj = obj;
			mHandler.sendMessage(msg);
		}
	}
	
	

	class PostRequestTask extends Thread {

		@Override
		public void run() {

			Log.d(TAG, "PostRequestTask()...");

			String result = "";
			InputStream input = null;
			try {

				if (!NetworkUtils.isNetworkAvailable(mContext)) {
					sendMessage(HTTP_NETWORK_NOT_EXISTS, null);
					return;
				}

				HttpPost request = new HttpPost(url);
				HttpParams params = new BasicHttpParams();
				Log.d(TAG, "HttpUtils PostRequestTask request url :" + url);
				HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, CONNECTION_SO_TIMEOUT);

				request.setParams(params);

				List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();

				if (!Utils.isMapEmpty(paramMap)) {
					Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();

					while (iterator.hasNext()) {
						Map.Entry<String, Object> entry = iterator.next();
						String key = entry.getKey();
						Object value = entry.getValue();

						String paramValue = value == null ? "" : value.toString();
						Log.d(TAG, " HttpUtils request parsm :" + "name=" + key + ",value=" + paramValue);
						nameValueList.add(new BasicNameValuePair(key, paramValue));
					}
				}
				request.setEntity(new UrlEncodedFormEntity(nameValueList, HTTP.UTF_8));
				HttpResponse response = new DefaultHttpClient().execute(request);
				Log.d(TAG, " http request response state  is:" + response.getStatusLine().getStatusCode());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					
					result = EntityUtils.toString(response.getEntity(),"UTF-8");
				} else {
					sendMessage(HTTP_CONNECTION_ERROR, null);
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, " http request exception is:" + e.toString());
				sendMessage(HTTP_CONNECTION_ERROR, null);
				return;
			} 
			Log.d(TAG, " http response result = " + result);
			sendMessage(HTTP_CONNECTION_OK, result);
		}
	};

	Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			String message = "";
			boolean isSuccess = false;
			switch (msg.what) {
			case HTTP_NETWORK_NOT_EXISTS:
				message = "暂未检测到网络,请稍后重试";
				isSuccess = false;
				break;
			case HTTP_CONNECTION_ERROR:
				message = "网络连接出错";
				isSuccess = false;
				break;
			case HTTP_CONNECTION_OK:
				message = msg.obj.toString();
				isSuccess = true;
				break;
			case FILE_UPLOAD_ERROR:
				message = "文件上传失败";
				isSuccess = false;
				break;
			case FILE_UPLOAD_NOT_EXISTS:
				message = "本地文件不存在";
				isSuccess = false;
				break;
			case FILE_UPLOAD_OK:
				message = msg.obj.toString();
				isSuccess = true;
				break;
			default:
				isSuccess = false;
				message = "发生错误了";
				break;
			}

			if (listener != null) {
				listener.getData(isSuccess, message);
			}
			if(getVersionListener!=null){
				getVersionListener.getVersion_Code(isSuccess, mHashMap);
			}
		};
	};

	public interface OnHttpToolsListener {
		void getData(boolean isSuccess, String content);
		
	}
	public interface OnHttpVersionUpdate{
		void getVersion_Code(boolean isSuccess,HashMap<String, String> mHashMap);
	}

	public void registerToolsListener(OnHttpToolsListener listener) {
		this.listener = listener;
	}
	public void registerVersionUpdate(OnHttpVersionUpdate listener){
		this.getVersionListener=listener;
	}

	public Object getEntity(JSONObject note, Class<?> clazz) {
		String value = null;
		Object obj = null;
		try {
			Field[] fa = clazz.getDeclaredFields();
			obj = (Object) Class.forName(clazz.getName()).newInstance();
			for (Field field : fa) {
				value = JSONUtils.getStringForJson(note, field.getName());
				if (!Utils.isStringEmpty(value)) {
					field.setAccessible(true);
					field.set(obj, value);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}

	public List<?> getEntityList(String str, Class<?> clazz) {
		try {
			JSONArray ja = new JSONArray(str);
			return getEntity(ja, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getEntity(String str, Class<?> clazz) {
		try {
			JSONObject json = new JSONObject(str);
			return getEntity(json, clazz);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	public List<?> getEntity(JSONArray ja, Class<?> clazz) {
		List<Object> mList = new ArrayList<Object>();
		Object obj = null;
		JSONObject note = null;
		try {
			for (int i = 0; i < ja.length(); i++) {
				note = new JSONObject(ja.optString(i));
				obj = getEntity(note, clazz);
				mList.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mList;
	}
}
