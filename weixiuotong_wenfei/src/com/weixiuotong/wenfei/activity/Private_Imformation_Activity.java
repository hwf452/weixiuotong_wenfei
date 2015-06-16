package com.weixiuotong.wenfei.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.Date;


import org.json.JSONException;
import org.json.JSONObject;


import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.NewService;

import com.weixiuotong.wenfei.service.SyncHttp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;


import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Private_Imformation_Activity extends Activity implements
		OnClickListener, OnLongClickListener {

	

	private ProgressDialog pd;
	private SharedPreferences sharedPreferences;

	private TextView tv4, tv6, tv8;
	private ProgressBar pb1;

	private Button btn1;
	private Button btn2;
	private Button btn4;
	private Button btn6;
	private Button btn8;
	private boolean update=false;
	
	private ImageButton imageButton1;
	AlertDialog.Builder dialog;
	private Bitmap bitmap11;

	// 拍照
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private Bundle bundle;
	private String ImageName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private_imformation_activity);

		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn4 = (Button) findViewById(R.id.button4);
		btn6 = (Button) findViewById(R.id.button6);
		btn8 = (Button) findViewById(R.id.button8);
		pb1=(ProgressBar)findViewById(R.id.progressBar1);
		

		tv4 = (TextView) findViewById(R.id.textView4);
		tv6 = (TextView) findViewById(R.id.textView6);
		tv8 = (TextView) findViewById(R.id.textView8);
		update=true;
		
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn8.setOnClickListener(this);
		
		imageButton1.setOnClickListener(this);
		imageButton1.setOnLongClickListener(this);
		getPrivateInfo();
		getPhoto_head();
		
	}
	
	
	
	private void getPhoto_head() {
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if(uid!=0000000000){
			String url = ContantUtil.GET_USER_PHOTOHEAD + "?UID="
					+ String.valueOf(uid);
			new MyAsyncTask2(this).execute(url);
		}
		
	}



	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if(update){
			getPrivateInfo();
		}
	}




	private void getPrivateInfo() {

		pd = new ProgressDialog(this);
		pd.setTitle("正在获取个人信息...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		pd.show();
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if(uid!=0000000000){
			String url = ContantUtil.GETPRIVATEIMFORMATION + "?UID="
					+ String.valueOf(uid);
			new MyAsyncTask1(this).execute(url);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;
		case R.id.button2:
			if (bitmap11 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap11);
				
				startActivity(intent);
				

			} 
			
			break;
		case R.id.button4:
			Intent intent4 = new Intent(this, Modify_Nickname_Activity.class);
			intent4.putExtras(bundle);
			update=true;
			startActivity(intent4);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		
		
		case R.id.button6:
			Intent intent6 = new Intent(this, Modify_Address_Activity.class);
			intent6.putExtras(bundle);
			update=true;
			startActivity(intent6);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

			break;
		case R.id.button8:
			Intent intent8 = new Intent(this, Modify_Mobile_Bind.class);
			intent8.putExtras(bundle);
			update=true;
			startActivity(intent8);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;

		case R.id.imageButton1:
			update=false;
			if (bitmap11 != null) {
				
				dialog = new AlertDialog.Builder(this);
				dialog.setTitle("更新头像");
				dialog.setMessage("请选择上传方式");
				dialog.setPositiveButton("拍照",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ImageName = "/" + getStringToday() + ".jpg";
								File picdir=new File(Environment.getExternalStorageDirectory()+"/weixiuotong");
								if(!picdir.exists()){
									picdir.mkdir();
								}
								

								// 调用系统的拍照功能
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(picdir,ImageName)));
								startActivityForResult(intent, PHOTOHRAPH);

							}
						});
				dialog.setNegativeButton("从像册选择",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 调用系统的相册
								Intent intent = new Intent(Intent.ACTION_PICK,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										IMAGE_UNSPECIFIED);

								// 调用剪切功能
								startActivityForResult(intent, PHOTOZOOM);

							}
						});
				dialog.show();

			} else {
				
				dialog = new AlertDialog.Builder(this);
				dialog.setTitle("上传头像");
				dialog.setMessage("请选择上传方式");
				dialog.setPositiveButton("拍照",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ImageName = "/" + getStringToday() + ".jpg";
								
								File picdir=new File(Environment.getExternalStorageDirectory()+"/weixiuotong");
								if(!picdir.exists()){
									picdir.mkdir();
								}

								// 调用系统的拍照功能
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(picdir,
												ImageName)));
								startActivityForResult(intent, PHOTOHRAPH);

							}
						});
				dialog.setNegativeButton("从像册选择",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 调用系统的相册
								Intent intent = new Intent(Intent.ACTION_PICK,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										IMAGE_UNSPECIFIED);

								// 调用剪切功能
								startActivityForResult(intent, PHOTOZOOM);

							}
						});
				dialog.show();
			}

			break;
		}

	}

	
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	// 调用startActivityResult，返回之后的回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == NONE)
			
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			
			File picdir=new File(Environment.getExternalStorageDirectory()+"/weixiuotong");
			if(!picdir.exists()){
				picdir.mkdir();
			}
			
			File picture = new File(picdir
					+ ImageName);
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap11 = photo;
				// Bitmap photo =BitmapFactory.decodeResource(getResources(),
				// R.drawable.ic_launcher);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				
				
				
				//imageButton1.setImageBitmap(photo);
				
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PICTURE_HEADPHOTO+"?UID="+UID;
					new MyAsyncTask(this,in).execute(url);
				}
				
				
				
				
				
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	

	public void startPhotoZoom(Uri uri) {
		
		
	        ContentResolver resolver = getContentResolver(); 
	           //获得图片的uri 
            try {
				 Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, uri);
				 ByteArrayOutputStream bos=new ByteArrayOutputStream();
				 bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				 
				 Log.i("bm",String.valueOf(bos.toByteArray().length));
				 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        //显得到bitmap图片
		
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		}
		return true;
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton1:
			if (bitmap11 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前头像?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Resources resources = getResources();

										BitmapDrawable bitmapDrawable = (BitmapDrawable) resources
												.getDrawable(R.drawable.submit_order_submit_photo);
										Bitmap bitmap = bitmapDrawable
												.getBitmap();
										imageButton1.setImageBitmap(bitmap);
										bitmap11 = null;

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();
			}

			break;

		default:
			break;
		}
		return false;
	}
	
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		private Context context;
		
		private InputStream inputStream;

		public MyAsyncTask(Context context,InputStream in) {
			
			this.context = context;
			this.inputStream=in;
		}
		

		@Override
		protected void onPreExecute() {
			imageButton1.setVisibility(View.GONE);
			pb1.setVisibility(View.VISIBLE);
		}


		@Override
		protected String doInBackground(String... params) {

			try {
				Log.i("url", params[0]);
				NewService newService=new NewService(context);
				String str=newService.UploadHead(params[0],inputStream);
				Log.i("str",str);

				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						if(pd!=null){
							pd.dismiss();
						}
						
						pb1.setVisibility(View.GONE);
						
						imageButton1.setVisibility(View.VISIBLE);
						
						if(bitmap11!=null){
							imageButton1.setImageBitmap(bitmap11);
						}

					} else if (opCode == 100) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);
						Toast.makeText(context, jsonObject.getString("opMess"), Toast.LENGTH_LONG).show();
						
					}else if (opCode == 101) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);
						Toast.makeText(context, jsonObject.getString("opMess"), Toast.LENGTH_LONG).show();
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	class MyAsyncTask1 extends AsyncTask<String, Void, String> {
		private Context context;
		

		public MyAsyncTask1(Context context) {
			
			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				Log.i("url",params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				String str = syncHttp.httpGet(params[0]);

				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					Log.i("succes","success");
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						if(pd!=null){
							pd.dismiss();
						}
						

						tv4.setText(jsonObject.getString("NickName"));
						
						
						Log.d("phone",jsonObject.toString());
						tv8.setText(String.valueOf(jsonObject.getLong("Phone")));
						
						
						tv6.setText(jsonObject.getString("Address"));
						bundle=new Bundle();
						bundle.putString("NickName", jsonObject.getString("NickName"));
						
						
						bundle.putString("Address", jsonObject.getString("Address"));
						bundle.putString("Phone", String.valueOf(jsonObject.getLong("Phone")));

					} else if (opCode == 100) {
						pd.dismiss();
						Toast.makeText(context, jsonObject.getString("opMess"), Toast.LENGTH_LONG).show();

					} else if (opCode == 101) {
						pd.dismiss();
						Toast.makeText(context, jsonObject.getString("opMess"), Toast.LENGTH_LONG).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	class MyAsyncTask2 extends AsyncTask<String, Void, String> {
		private Context context;
		

		public MyAsyncTask2(Context context) {
			
			this.context = context;
		}
		
		
		@Override
		protected String doInBackground(String... params) {

			try {
				Log.i("url",params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				String str=syncHttp.httpGet(params[0]);
				
				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					Log.i("succes","success");
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						String picUrl=jsonObject.getString("PhotoUrl");
						new MyAsyncTask3(context).execute(picUrl);

						

					} else{
						Toast.makeText(context,"获取头像失败", Toast.LENGTH_LONG).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}

	}
	
	class MyAsyncTask3 extends AsyncTask<String, Void, Bitmap> {
		private Context context;
		

		public MyAsyncTask3(Context context) {
			
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			imageButton1.setVisibility(View.GONE);
			pb1.setVisibility(View.VISIBLE);
		}

		
		
		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Log.i("url",params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream=syncHttp.httpGetStream(params[0]);
				Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
				return bitmap;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				pb1.setVisibility(View.GONE);
				
				imageButton1.setVisibility(View.VISIBLE);
				
					imageButton1.setImageBitmap(result);
					bitmap11=result;
				
			}else{
				pb1.setVisibility(View.GONE);
				
				imageButton1.setVisibility(View.VISIBLE);
				Toast.makeText(context,"获取头像失败", Toast.LENGTH_LONG).show();
			}

		}

	}




}
