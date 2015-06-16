package com.weixiuotong.wenfei.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
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

import android.content.Context;
import android.util.Log;


public class SyncHttp{
	private Context context;
	/**
	 * 通过GET方式发送请求
	 * @param url URL地址
	 * @param params 参数
	 * @return 
	 * @throws Exception
	 */
	public SyncHttp(Context context){
		this.context=context;
	}
	public String httpGet(String url) throws Exception
	{
		String response = null; //返回信息
		
		int timeoutConnection = 5000;  
		int timeoutSocket = 10000;  
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
	    
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		// 创建GET方法的实例
		HttpGet httpGet = new HttpGet(url);
		try
		{
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) //SC_OK = 200
			{
				// 获得返回结果
				response = EntityUtils.toString(httpResponse.getEntity());
			}
			else
			{
				response = "返回码："+statusCode;
			}
		} catch (Exception e)
		{
			throw new Exception(e);
		} 
		return response;
	}
	
	//获取输入流
	
		public InputStream httpGetStream(String url) throws Exception
		{
			InputStream inputStream; //返回信息
			//拼接请求URL
			
			
			int timeoutConnection = 5000;  
			int timeoutSocket = 10000;  
			HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
		    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
		    
			// 构造HttpClient的实例
			HttpClient httpClient = new DefaultHttpClient(httpParameters);  
			// 创建GET方法的实例
			HttpGet httpGet = new HttpGet(url);
			try
			{
				HttpResponse httpResponse = httpClient.execute(httpGet);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) //SC_OK = 200
				{
					// 获得返回结果
					inputStream = httpResponse.getEntity().getContent();
					return inputStream;
				}
				
			} catch (Exception e)
			{
				throw new Exception(e);
			} 
			return null;
		}


	/**
	 * 通过POST方式发送请求
	 * @param url URL地址
	 * @param params 参数
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String url, List<Parameter> params) throws Exception
	{
		String response = null;
		int timeoutConnection = 3000;  
		int timeoutSocket = 5000;  
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		HttpPost httpPost = new HttpPost(url);
		if (params.size()>=0)
		{
			//设置httpPost请求参数
			httpPost.setEntity(new UrlEncodedFormEntity(buildNameValuePair(params),HTTP.UTF_8));
		}
		//使用execute方法发送HTTP Post请求，并返回HttpResponse对象
		Log.i("posturl",buildNameValuePair(params).toString());
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode==HttpStatus.SC_OK)
		{
			//获得返回结果
			response = EntityUtils.toString(httpResponse.getEntity());
		}
		else
		{
			response = "返回码："+statusCode;
		}
		return response;
	}
	
	/**
	 * 把Parameter类型集合转换成NameValuePair类型集合
	 * @param params 参数集合
	 * @return
	 */
	private List<BasicNameValuePair> buildNameValuePair(List<Parameter> params)
	{
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for (Parameter param : params)
		{
			BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
			result.add(pair);
		}
		return result;
	}
}
