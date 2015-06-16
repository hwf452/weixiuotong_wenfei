package com.weixiuotong.wenfei.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import com.weixiuotong.wenfei.activity.Private_Imformation_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;

public class NewService {

	HttpURLConnection con;
	URL url1;
	Context context;
	public NewService(Context context){
		this.context=context;
	}

	public String UploadHead(String url,InputStream in) {

		//InputStream in;
		byte[] buf = new byte[1024];
		String response=null;
		

		try {
			
			//in = new ByteArrayInputStream(bitmap.toByteArray());
			url1 = new URL(url);
			con = (HttpURLConnection) url1.openConnection();
			con.setConnectTimeout(200000);
			con.setReadTimeout(120000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content_Type","multipart/form-data;boundary="+UUID.randomUUID().toString());
			con.setRequestProperty("Charset", "utf-8");
			OutputStream osw = con.getOutputStream();
			while (in.read(buf) != -1) {
				osw.write(buf);
			}
			osw.flush();
			
			
			osw.close();
			in.close();
			InputStream inputStream=con.getInputStream();
			response=getResponse(inputStream); 
			
			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String getResponse(InputStream inStream) throws IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = -1;
		byte[] buffer = new byte[1024];
		while((len=inStream.read(buffer))!=-1)
		{
			outputStream.write(buffer, 0, len);
		}
		
		byte[] data = outputStream.toByteArray();
		return new String(data);
	}
	
}
