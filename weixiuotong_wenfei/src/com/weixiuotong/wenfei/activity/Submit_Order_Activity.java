package com.weixiuotong.wenfei.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;



import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.NewService;
import com.weixiuotong.wenfei.service.Parameter;

import com.weixiuotong.wenfei.service.SyncHttp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.graphics.Bitmap;


import android.graphics.drawable.BitmapDrawable;


import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import android.view.KeyEvent;

import android.view.View;

import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.ProgressBar;

import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;


//提交维修单页面
@SuppressLint("SimpleDateFormat")
public class Submit_Order_Activity extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnLongClickListener {

	// 系统控件
	private Button btn2, btn3;
	private CheckBox cb1, cb2;
	private Timer timer;
	private ImageButton imageButton1, imageButton2, imageButton3, imageButton4,
			imageButton5;
	private AlertDialog.Builder dialog;
	
	
	private Bitmap bitmap11 = null;
	private Bitmap bitmap22 = null;
	private Bitmap bitmap33 = null;
	private Bitmap bitmap44 = null;
	private Bitmap bitmap55 = null;
	private SharedPreferences sharedPreferences;
	private ProgressDialog pd;
	private String subAddr;
	private String subName;
	private Long subPhoneNum;
	private String subType=new String("个人");
	private String subCommunity=null;

	// 拍照
	public static final int NONE = 0;
	public static final int PHOTOHRAPH1 = 1;
	public static final int PHOTOHRAPH2 = 2;
	public static final int PHOTOHRAPH3 = 3;
	public static final int PHOTOHRAPH4 = 4;
	public static final int PHOTOHRAPH5 = 5;// 拍照
	public static final int PHOTOZOOM1 = 6;
	public static final int PHOTOZOOM2 = 7;
	public static final int PHOTOZOOM3 = 8;
	public static final int PHOTOZOOM4 = 9;
	public static final int PHOTOZOOM5 = 10; // 缩放
	public static final int PHOTORESOULT1 = 11;
	public static final int PHOTORESOULT2 = 12;
	public static final int PHOTORESOULT3 = 13;
	public static final int PHOTORESOULT4 = 14;
	public static final int PHOTORESOULT5 = 15;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private static final int REQUESTCODE_SELECT_COMMUNITY = 20;
	private static final int USE_NEW_ADDRESS_CODE = 21;
	private String ImageName;
	private TextView tv2;
	private EditText et1,et2,et3,et4,et5;

	private Handler handler;
	private static final int GETINFO_STATUS=11;
	private ProgressBar pb1,pb2,pb3,pb4,pb5;

	private String picStreamID1=new String("0");
	private String picStreamID2=new String("0");
	private String picStreamID3=new String("0");
	private String picStreamID4=new String("0");
	private String picStreamID5=new String("0");
	

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumit_order_activity);

		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
		pb1=(ProgressBar)findViewById(R.id.progressBar1);
		pb2=(ProgressBar)findViewById(R.id.progressBar2);
		pb3=(ProgressBar)findViewById(R.id.progressBar3);
		pb4=(ProgressBar)findViewById(R.id.progressBar4);
		pb5=(ProgressBar)findViewById(R.id.progressBar5);
	
		tv2 = (TextView) findViewById(R.id.textView2);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		et4=(EditText)findViewById(R.id.editText4);
		et5=(EditText)findViewById(R.id.editText5);
				
		imageButton1.setOnClickListener(this);
		imageButton1.setOnLongClickListener(this);
		imageButton2.setOnClickListener(this);
		imageButton2.setOnLongClickListener(this);
		imageButton3.setOnClickListener(this);
		imageButton3.setOnLongClickListener(this);
		imageButton4.setOnClickListener(this);
		imageButton4.setOnLongClickListener(this);
		imageButton5.setOnClickListener(this);
		imageButton5.setOnLongClickListener(this);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);

		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		cb1 = (CheckBox) findViewById(R.id.checkBox1);
		cb2 = (CheckBox) findViewById(R.id.checkBox2);
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {

				case GETINFO_STATUS:
					if(pd!=null){
						pd.dismiss();
					}
					
					if(timer!=null){
						timer.cancel();
					}
					
					tv2.setText(msg.obj.toString());
					break;

				
				}

			}

		};

		getPrivateInfo();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (cb1.isChecked()) {
			
			getPrivateInfo1();

		}
		
	}

	// 获取个人信息
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox1:
			if (cb1.isChecked()) {
				cb2.setChecked(false);
				subType="个人";
				getPrivateInfo();
				subCommunity=null;

			} else {
				cb2.setChecked(true);
				
			}

			break;

		case R.id.checkBox2:
			if (cb2.isChecked()) {
				cb1.setChecked(false);
				subType="社区";
				Intent intent = new Intent(this,
						Select_Community_Activity.class);
				intent.putExtra("REQUESTCODE_SELECT_COMMUNITY",REQUESTCODE_SELECT_COMMUNITY);
				startActivityForResult(intent, REQUESTCODE_SELECT_COMMUNITY);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

				
			} else {
				cb1.setChecked(true);

			}

			break;
		}

	}

	// 获取个人信息
	private void getPrivateInfo() {
		pd = new ProgressDialog(this);
		pd.setTitle("正在获取地址信息...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(true);
		pd.show();
		// 获取UID
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {

			String url = ContantUtil.GETPRIVATEIMFORMATION + "?UID="
					+ String.valueOf(uid);
			Log.i("url", url);
			new MyAsyncTaskGetPrivateImformation(this).execute(url);
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = GETINFO_STATUS;
					msg.obj = "联网失败!";
					handler.sendMessage(msg);

					
				}
			}, 8000);
		}

	}
	private void getPrivateInfo1() {
		
		// 获取UID
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {

			String url = ContantUtil.GETPRIVATEIMFORMATION + "?UID="
					+ String.valueOf(uid);
			Log.i("url", url);
			new MyAsyncTaskGetPrivateImformation(this).execute(url);
			
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button2:
			Intent intent2 = new Intent(this, Modify_PrivateActivity.class);
			startActivityForResult(intent2, USE_NEW_ADDRESS_CODE);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;

		case R.id.button3:
			
			
			submitOrder();			
			
			break;
		case R.id.imageButton1:
			if (bitmap11 != null) {

				
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap11);
				startActivity(intent);

			} else {
				dialog = new AlertDialog.Builder(this);
				dialog.setTitle("上传像片");
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
								startActivityForResult(intent, PHOTOHRAPH1);

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
								startActivityForResult(intent, PHOTOZOOM1);

							}
						});
				dialog.show();
			}

			break;
		case R.id.imageButton2:
			if(bitmap11!=null){
				if (bitmap22 != null) {

					Intent intent = new Intent(this,
							Submit_ShowPicture_Activity.class);
					intent.putExtra("bitmap", bitmap22);
					startActivity(intent);

				} else {
					dialog = new AlertDialog.Builder(this);
					dialog.setTitle("上传像片");
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
									startActivityForResult(intent, PHOTOHRAPH2);

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
									startActivityForResult(intent, PHOTOZOOM2);

								}
							});
					dialog.show();
				}
			}

			break;
		case R.id.imageButton3:
			if(bitmap22!=null){
				if (bitmap33 != null) {
					Intent intent = new Intent(this,
							Submit_ShowPicture_Activity.class);
					intent.putExtra("bitmap", bitmap33);
					startActivity(intent);

				} else {
					dialog = new AlertDialog.Builder(this);
					dialog.setTitle("上传像片");
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
									startActivityForResult(intent, PHOTOHRAPH3);

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
									startActivityForResult(intent, PHOTOZOOM3);

								}
							});
					dialog.show();
				}
			}

			break;
		case R.id.imageButton4:
			if(bitmap33!=null){
				if (bitmap44 != null) {
					Intent intent = new Intent(this,
							Submit_ShowPicture_Activity.class);
					intent.putExtra("bitmap", bitmap44);
					startActivity(intent);

				} else {
					dialog = new AlertDialog.Builder(this);
					dialog.setTitle("上传像片");
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
									startActivityForResult(intent, PHOTOHRAPH4);

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
									startActivityForResult(intent, PHOTOZOOM4);

								}
							});
					dialog.show();
				}

			}
			break;
		case R.id.imageButton5:
			if(bitmap44!=null){
				if (bitmap55 != null) {
					Intent intent = new Intent(this,
							Submit_ShowPicture_Activity.class);
					intent.putExtra("bitmap", bitmap55);
					startActivity(intent);
				} else {
					dialog = new AlertDialog.Builder(this);
					dialog.setTitle("上传像片");
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
									startActivityForResult(intent, PHOTOHRAPH5);

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
									startActivityForResult(intent, PHOTOZOOM5);

								}
							});
					dialog.show();
				}

			}
			break;
		}

	}

	private void submitOrder() {
		if(et1.getText().toString().equals("")){
			tv2.setText("标题不能为空!");
			return;
		}
		if(et2.getText().toString().equals("")){
			tv2.setText("设备不能为空!");
			return;
		}
		if(et3.getText().toString().equals("")){
			tv2.setText("地址不能为空!");
			return;
		}
		if(et4.getText().toString().equals("")){
			tv2.setText("提交内容不能为空!");
			return;
		}
		
		List<Parameter> params = new ArrayList<Parameter>();
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {
			String UID = String.valueOf(uid);
			Parameter parameter = new Parameter("UID", UID);
			params.add(parameter);
		}
		
		Parameter parameter1 = new Parameter("subTitle",et1.getText().toString());
		params.add(parameter1);
		
		Parameter parameter2 = new Parameter("subMachine",et2.getText().toString());
		params.add(parameter2);
		
		
		Parameter parameter3=new Parameter("subContent",et4.getText().toString());
		params.add(parameter3);
		
		Parameter parameter4 = new Parameter("subName",subName);
		params.add(parameter4);
		
		Parameter parameter5 = new Parameter("subCommunity",subCommunity);
		params.add(parameter5);
		
		Parameter parameter6 = new Parameter("subAddr",subAddr);
		params.add(parameter6);
		
		Parameter parameter7 = new Parameter("subPhoneNum",String.valueOf(subPhoneNum));
		params.add(parameter7);
		
		Parameter parameter8 = new Parameter("subType",String.valueOf(subType));
		params.add(parameter8);
		
		Parameter parameter9 = new Parameter("picStreamID1",String.valueOf(picStreamID1));
		params.add(parameter9);
		
		Parameter parameter10 = new Parameter("picStreamID2",String.valueOf(picStreamID2));
		params.add(parameter10);
		
		Parameter parameter11 = new Parameter("picStreamID3",String.valueOf(picStreamID3));
		params.add(parameter11);
		
		Parameter parameter12 = new Parameter("picStreamID4",String.valueOf(picStreamID4));
		params.add(parameter12);
		
		Parameter parameter13 = new Parameter("picStreamID5",String.valueOf(picStreamID5));
		params.add(parameter13);
		
		
		String url = ContantUtil.NEW_REPAIR_ORDER;

		pd = new ProgressDialog(this);
		pd.setTitle("提交订单中...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		pd.show();

		new MyAsyncTask6(this, params).execute(url);

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Message msg = Message.obtain();
				msg.what = GETINFO_STATUS;
				msg.obj = "联网失败,请检查网络!";

				handler.sendMessage(msg);

			}
		},8000);
		
		
		
		
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if(timer!=null){
				timer.cancel();
			}
			new AlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要退出维修通?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if(timer!=null){
										timer.cancel();
									}
									Submit_Order_Activity.this.finish();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

			break;

		}
		return true;
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
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH1) {
			// 设置文件保存路径这里放在跟目录下
			
			
			

			File picture = new File(Environment.getExternalStorageDirectory()+"/weixiuotong"
					+ ImageName);
			startPhotoZoom1(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH2) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()+"/weixiuotong"
					+ ImageName);
			startPhotoZoom2(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH3) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()+"/weixiuotong"
					+ ImageName);
			startPhotoZoom3(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH4) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()+"/weixiuotong"
					+ ImageName);
			startPhotoZoom4(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH5) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()+"/weixiuotong"
					+ ImageName);
			startPhotoZoom5(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM1) {
			startPhotoZoom1(data.getData());
		}

		if (requestCode == PHOTOZOOM2) {
			startPhotoZoom2(data.getData());
		}

		if (requestCode == PHOTOZOOM3) {
			startPhotoZoom3(data.getData());
		}

		if (requestCode == PHOTOZOOM4) {
			startPhotoZoom4(data.getData());
		}

		if (requestCode == PHOTOZOOM5) {
			startPhotoZoom5(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT1) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap11 = photo;

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PIC_URL+"?UID="+UID;
					new MyAsyncTask1(this,in).execute(url);
				}

				
			}

		}

		if (requestCode == PHOTORESOULT2) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap22 = photo;

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PIC_URL+"?UID="+UID;
					new MyAsyncTask2(this,in).execute(url);
				}
			}

		}

		if (requestCode == PHOTORESOULT3) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap33 = photo;

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PIC_URL+"?UID="+UID;
					new MyAsyncTask3(this,in).execute(url);
				}
			}

		}

		if (requestCode == PHOTORESOULT4) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap44 = photo;

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PIC_URL+"?UID="+UID;
					new MyAsyncTask4(this,in).execute(url);
				}
			}

		}

		if (requestCode == PHOTORESOULT5) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				bitmap55 = photo;

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				 Log.i("photo",String.valueOf(stream.toByteArray().length));
																	
				// 100)压缩文件
				
				InputStream in = new ByteArrayInputStream(
						stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url=ContantUtil.UPLOAD_PIC_URL+"?UID="+UID;
					new MyAsyncTask5(this,in).execute(url);
				}
			}

		}
		if (requestCode == REQUESTCODE_SELECT_COMMUNITY) {
			if (resultCode == Select_Community_Activity.RESULTCODE1) {
				Bundle bundle = data.getExtras();
				subCommunity = bundle.getString("community");
				subAddr=bundle.getString("communityAddress");
				
				et3.setText(subAddr);

			}

		}
		if (requestCode == USE_NEW_ADDRESS_CODE) {
			if (resultCode == Modify_PrivateActivity.RESULTCODE_USE_NEW_ADDRESS) {
				Bundle bundle = data.getExtras();
				
				subPhoneNum=Long.valueOf(bundle.getString("Phone"));
				subName = bundle.getString("name");
				StringBuffer newContrat=new StringBuffer();
				newContrat.append(subName+" ").append(String.valueOf(subPhoneNum));
				et5.setText(newContrat.toString());

			}

		}

	}

	public void startPhotoZoom1(Uri uri) {

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
		startActivityForResult(intent, PHOTORESOULT1);
	}

	public void startPhotoZoom2(Uri uri) {
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
		startActivityForResult(intent, PHOTORESOULT2);
	}

	public void startPhotoZoom3(Uri uri) {
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
		startActivityForResult(intent, PHOTORESOULT3);
	}

	public void startPhotoZoom4(Uri uri) {
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
		startActivityForResult(intent, PHOTORESOULT4);
	}

	public void startPhotoZoom5(Uri uri) {
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
		startActivityForResult(intent, PHOTORESOULT5);
	}

	@Override
	public boolean onLongClick(View v) {

		switch (v.getId()) {
		case R.id.imageButton1:
			if (bitmap11 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前像片?")
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
		case R.id.imageButton2:
			if (bitmap22 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前像片?")
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
										imageButton2.setImageBitmap(bitmap);
										bitmap22 = null;

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
		case R.id.imageButton3:
			if (bitmap33 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前像片?")
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
										imageButton3.setImageBitmap(bitmap);
										bitmap33 = null;

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
		case R.id.imageButton4:
			if (bitmap44 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前像片?")
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
										imageButton4.setImageBitmap(bitmap);
										bitmap44 = null;

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
		case R.id.imageButton5:
			if (bitmap55 != null) {
				new AlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您确定要删除当前像片?")
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
										imageButton5.setImageBitmap(bitmap);
										bitmap55 = null;

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

		}

		return false;
	}

	class MyAsyncTaskGetPrivateImformation extends
			AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTaskGetPrivateImformation(Context context) {

			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {

			try {
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
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						subAddr = jsonObject.getString("Address");
						subName = jsonObject.getString("NickName");
						subPhoneNum = jsonObject.getLong("Phone");
						StringBuffer sb = new StringBuffer();
						sb.append(subName + "  ")
								.append(String.valueOf(subPhoneNum));
						Log.d("private", result);
						tv2.setText("");
						et5.setText(sb.toString());
						et3.setText(subAddr);
						
						if (pd != null) {
							pd.cancel();
						}
						if(timer!=null){
							timer.cancel();
						}

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);
					} else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);
						

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
		
		private InputStream inputStream;

		public MyAsyncTask1(Context context,InputStream in) {
			
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
						
						
						pb1.setVisibility(View.GONE);
						
						imageButton1.setVisibility(View.VISIBLE);
						
						if(bitmap11!=null){
							imageButton1.setImageBitmap(bitmap11);
							tv2.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID1=jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);
						
						tv2.setText(jsonObject.getString("opMess"));
					}else if (opCode == 101) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);
						tv2.setText(jsonObject.getString("opMess"));
						
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
		
		private InputStream inputStream;

		public MyAsyncTask2(Context context,InputStream in) {
			
			this.context = context;
			this.inputStream=in;
		}
		

		@Override
		protected void onPreExecute() {
			imageButton2.setVisibility(View.GONE);
			pb2.setVisibility(View.VISIBLE);
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
						
						
						pb2.setVisibility(View.GONE);
						
						imageButton2.setVisibility(View.VISIBLE);
						
						if(bitmap22!=null){
							imageButton2.setImageBitmap(bitmap22);
							tv2.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID2=jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb2.setVisibility(View.GONE);
						imageButton2.setVisibility(View.VISIBLE);
						
						tv2.setText(jsonObject.getString("opMess"));
					}else if (opCode == 101) {
						pb2.setVisibility(View.GONE);
						imageButton2.setVisibility(View.VISIBLE);
						tv2.setText(jsonObject.getString("opMess"));
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	class MyAsyncTask3 extends AsyncTask<String, Void, String> {
		private Context context;
		
		private InputStream inputStream;

		public MyAsyncTask3(Context context,InputStream in) {
			
			this.context = context;
			this.inputStream=in;
		}
		

		@Override
		protected void onPreExecute() {
			imageButton3.setVisibility(View.GONE);
			pb3.setVisibility(View.VISIBLE);
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
						
						
						pb3.setVisibility(View.GONE);
						
						imageButton3.setVisibility(View.VISIBLE);
						
						if(bitmap33!=null){
							imageButton3.setImageBitmap(bitmap33);
							tv2.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID3=jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb3.setVisibility(View.GONE);
						imageButton3.setVisibility(View.VISIBLE);
						
						tv2.setText(jsonObject.getString("opMess"));
					}else if (opCode == 101) {
						pb3.setVisibility(View.GONE);
						imageButton3.setVisibility(View.VISIBLE);
						tv2.setText(jsonObject.getString("opMess"));
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	class MyAsyncTask4 extends AsyncTask<String, Void, String> {
		private Context context;
		
		private InputStream inputStream;

		public MyAsyncTask4(Context context,InputStream in) {
			
			this.context = context;
			this.inputStream=in;
		}
		

		@Override
		protected void onPreExecute() {
			imageButton4.setVisibility(View.GONE);
			pb4.setVisibility(View.VISIBLE);
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
						
						
						pb4.setVisibility(View.GONE);
						
						imageButton4.setVisibility(View.VISIBLE);
						
						if(bitmap44!=null){
							imageButton4.setImageBitmap(bitmap44);
							tv2.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID4=jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb4.setVisibility(View.GONE);
						imageButton4.setVisibility(View.VISIBLE);
						
						tv2.setText(jsonObject.getString("opMess"));
					}else if (opCode == 101) {
						pb4.setVisibility(View.GONE);
						imageButton4.setVisibility(View.VISIBLE);
						tv2.setText(jsonObject.getString("opMess"));
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	class MyAsyncTask5 extends AsyncTask<String, Void, String> {
		private Context context;
		
		private InputStream inputStream;

		public MyAsyncTask5(Context context,InputStream in) {
			
			this.context = context;
			this.inputStream=in;
		}
		

		@Override
		protected void onPreExecute() {
			imageButton5.setVisibility(View.GONE);
			pb5.setVisibility(View.VISIBLE);
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
						
						
						pb5.setVisibility(View.GONE);
						
						imageButton5.setVisibility(View.VISIBLE);
						
						if(bitmap55!=null){
							imageButton5.setImageBitmap(bitmap55);
							tv2.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID5=jsonObject.getString("picStreamID");
							Log.i("picstreamid5",picStreamID5);
						}

					} else if (opCode == 100) {
						pb5.setVisibility(View.GONE);
						imageButton5.setVisibility(View.VISIBLE);
						
						tv2.setText(jsonObject.getString("opMess"));
					}else if (opCode == 101) {
						pb5.setVisibility(View.GONE);
						imageButton5.setVisibility(View.VISIBLE);
						tv2.setText(jsonObject.getString("opMess"));
						
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	class MyAsyncTask6 extends AsyncTask<String, Void, String> {
		private Context context;
		List<Parameter> params1;

		public MyAsyncTask6(Context context, List<Parameter> params) {
			this.params1 = params;
			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				SyncHttp syncHttp = new SyncHttp(context);
				String str = syncHttp.httpPost(params[0], params1);

				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@SuppressWarnings("deprecation")
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
						
						if(timer!=null){
							timer.cancel();
						}
						Log.i("submitsuccess",result);
						Intent intent3 = new Intent(context,Repair_Order_Already_Activity.class);
						intent3.putExtra("subNum", jsonObject.getString("subNum"));
						
						startActivity(intent3);
						et1.setText("");
						et2.setText("");
						cb1.setChecked(true);
						et4.setText("");
						  bitmap11 = null;
						  bitmap22 = null;
						  bitmap33 = null;
						  bitmap44 = null;
						  bitmap55 = null;
						imageButton1.setImageResource(R.drawable.submit_order_submit_photo);
						imageButton2.setImageResource(R.drawable.submit_order_submit_photo);
						imageButton3.setImageResource(R.drawable.submit_order_submit_photo);
						imageButton4.setImageResource(R.drawable.submit_order_submit_photo);
						imageButton5.setImageResource(R.drawable.submit_order_submit_photo);
						
						
						
						overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}
					else if (opCode == 102) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}else if (opCode == 103) {
						Message msg = Message.obtain();
						msg.what = GETINFO_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}




}
