package com.weixiuotong.wenfei.service;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class UploadHeadPhotoService {
	private Context context;
	HttpURLConnection con;
	URL url1;
	public UploadHeadPhotoService(Context context){
		this.context=context;
	}

	public String UploadHead(String url,InputStream in) {

		byte[] buf = new byte[1024];
		String response=null;
	     
	        try{
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
				if(response!=null){
					showDialog(response);
				}
				return response;
	        }catch(Exception e)
	        {
	          showDialog("上传失败"+e);
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
	  /* 显示Dialog的method */
    private void showDialog(String mess)
    {
      new AlertDialog.Builder(context).setTitle("上传结果")
       .setMessage(mess)
       .setNegativeButton("确定",new DialogInterface.OnClickListener()
       {
         public void onClick(DialogInterface dialog, int which)
         {          
         }
       })
       .show();
    }
}
