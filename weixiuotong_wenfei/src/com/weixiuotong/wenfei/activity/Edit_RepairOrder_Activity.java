package com.weixiuotong.wenfei.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.weixiuotong.wenfei.activity.Refuse_BaseAdapter.MyAsyncTask1;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.widget.ProgressBar;
import android.widget.TextView;

public class Edit_RepairOrder_Activity extends Activity implements
		OnClickListener {
	private Button btn1, btn2, btn3, btn5, btn6, btn7, btn8, btn9;
	private EditText et1, et2, et4, et5, et6, et7;
	private String subNum;
	private TextView tv12;
	private SharedPreferences sharedPreferences;
	private ProgressDialog pd;
	private Handler handler;
	private ProgressBar pb1;
	private ProgressBar pb2;
	private ProgressBar pb3;
	private ProgressBar pb4;
	private ProgressBar pb5;
	private TextView tv3, tv13;
	private AlertDialog.Builder dialog;

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
	private String ImageName;

	private String subAddr;
	private static final int REQUESTCODE_SELECT_COMMUNITY = 30;

	private Timer timer;
	private static final int GETORDER_STATUS = 11;
	private String picStreamID1 = new String("0");
	private String picStreamID2 = new String("0");
	private String picStreamID3 = new String("0");
	private String picStreamID4 = new String("0");
	private String picStreamID5 = new String("0");

	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
	private ImageButton imageButton4;
	private ImageButton imageButton5;
	private Bitmap bitmap11 = null;
	private Bitmap bitmap22 = null;
	private Bitmap bitmap33 = null;
	private Bitmap bitmap44 = null;
	private Bitmap bitmap55 = null;
	private String subCommunity = null;
	private String subType = new String("个人");

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_repair_order_activity);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn5 = (Button) findViewById(R.id.button5);
		btn6 = (Button) findViewById(R.id.button6);
		btn7 = (Button) findViewById(R.id.button7);
		btn8 = (Button) findViewById(R.id.button8);
		btn9 = (Button) findViewById(R.id.button9);

		tv3 = (TextView) findViewById(R.id.textView3);
		tv12 = (TextView) findViewById(R.id.textView12);
		tv13 = (TextView) findViewById(R.id.textView13);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
		pb1 = (ProgressBar) findViewById(R.id.progressBar1);
		pb2 = (ProgressBar) findViewById(R.id.progressBar2);
		pb3 = (ProgressBar) findViewById(R.id.progressBar3);
		pb4 = (ProgressBar) findViewById(R.id.progressBar4);
		pb5 = (ProgressBar) findViewById(R.id.progressBar5);

		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);

		et4 = (EditText) findViewById(R.id.editText4);
		et5 = (EditText) findViewById(R.id.editText5);
		et6 = (EditText) findViewById(R.id.editText6);
		et7 = (EditText) findViewById(R.id.editText7);
		imageButton1.setOnClickListener(this);

		imageButton2.setOnClickListener(this);

		imageButton3.setOnClickListener(this);

		imageButton4.setOnClickListener(this);

		imageButton5.setOnClickListener(this);

		tv13.setOnClickListener(this);

		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		Intent intent = getIntent();
		subNum = intent.getStringExtra("subNum");
		Log.i("subNum", subNum);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {

				case GETORDER_STATUS:
					if (pd != null) {
						pd.dismiss();
					}

					if (timer != null) {
						timer.cancel();
					}

					tv12.setText(msg.obj.toString());
					break;

				}

			}

		};
		getOrderDetail();

	}

	private void getOrderDetail() {
		pd = new ProgressDialog(this);
		pd.setTitle("正在获取订单信息...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(true);
		pd.show();
		// 获取UID
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {

			String url = ContantUtil.GET_REPAIR_ORDER_DETAIL + "?UID="
					+ String.valueOf(uid) + "&subNum=" + subNum;
			Log.i("url", url);
			new MyAsyncTaskGetOrderDetail(this).execute(url);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = GETORDER_STATUS;
					msg.obj = "联网失败!";
					handler.sendMessage(msg);

				}
			}, 5000);
		}

	}

	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button1:
			Intent intentBack = new Intent(this, Repair_Detail_Activity.class);
			intentBack.putExtra("subNum", subNum);
			startActivity(intentBack);
			Edit_RepairOrder_Activity.this.finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		case R.id.button2:

			submitOrder();

			break;
		case R.id.button3:
			new AlertDialog.Builder(Edit_RepairOrder_Activity.this)
					.setTitle("提醒")
					.setMessage("您确定要删除当前订单吗?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									del_Order();

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
								File picdir = new File(Environment
										.getExternalStorageDirectory()
										+ "/weixiuotong");
								if (!picdir.exists()) {
									picdir.mkdir();
								}

								// 调用系统的拍照功能
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(picdir, ImageName)));
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
			if (bitmap11 != null) {
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

									File picdir = new File(Environment
											.getExternalStorageDirectory()
											+ "/weixiuotong");
									if (!picdir.exists()) {
										picdir.mkdir();
									}

									// 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(picdir,
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
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
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
			if (bitmap22 != null) {
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

									File picdir = new File(Environment
											.getExternalStorageDirectory()
											+ "/weixiuotong");
									if (!picdir.exists()) {
										picdir.mkdir();
									}

									// 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(picdir,
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
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
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
			if (bitmap33 != null) {
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

									File picdir = new File(Environment
											.getExternalStorageDirectory()
											+ "/weixiuotong");
									if (!picdir.exists()) {
										picdir.mkdir();
									}

									// 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(picdir,
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
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
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
			if (bitmap44 != null) {
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

									File picdir = new File(Environment
											.getExternalStorageDirectory()
											+ "/weixiuotong");
									if (!picdir.exists()) {
										picdir.mkdir();
									}

									// 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(picdir,
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
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
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
		case R.id.textView13:
			if (subCommunity != null) {
				Intent intent3 = new Intent(this,
						Select_Community_Activity.class);
				intent3.putExtra("REQUESTCODE_SELECT_COMMUNITY",
						REQUESTCODE_SELECT_COMMUNITY);
				startActivityForResult(intent3, REQUESTCODE_SELECT_COMMUNITY);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}

			break;
		case R.id.button5:

			picStreamID1 = new String("0");
			bitmap11 = null;
			imageButton1.setImageResource(R.drawable.submit_order_submit_photo);
			btn5.setVisibility(View.GONE);

			// imageButton1.setImageBitmap(bitmap11);

			break;
		case R.id.button6:
			picStreamID2 = new String("0");
			bitmap22 = null;
			imageButton2.setImageResource(R.drawable.submit_order_submit_photo);
			btn6.setVisibility(View.GONE);
			break;
		case R.id.button7:
			picStreamID3 = new String("0");
			bitmap33 = null;
			imageButton3.setImageResource(R.drawable.submit_order_submit_photo);
			btn7.setVisibility(View.GONE);
			break;
		case R.id.button8:
			picStreamID4 = new String("0");
			bitmap44 = null;
			imageButton4.setImageResource(R.drawable.submit_order_submit_photo);
			btn8.setVisibility(View.GONE);
			break;
		case R.id.button9:
			picStreamID5 = new String("0");
			bitmap55 = null;
			imageButton5.setImageResource(R.drawable.submit_order_submit_photo);
			btn9.setVisibility(View.GONE);
			break;

		}

	}

	private void del_Order() {
		pd = new ProgressDialog(this);
		pd.setTitle("正在删除订单...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(true);
		pd.show();

		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {
			String UID = String.valueOf(uid);

			String url = ContantUtil.DEL_REPAIR_ORDER_URL + "?UID=" + UID
					+ "&subNum=" + subNum;
			new MyAsyncTask6(this).execute(url);

		}

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Message msg = Message.obtain();
				msg.what = GETORDER_STATUS;
				msg.obj = "联网失败!";

				handler.sendMessage(msg);

			}
		}, 6000);

	}

	private void submitOrder() {

		if (et1.getText().toString().equals("")) {
			tv12.setText("标题不能为空!");
			return;
		}
		if (et2.getText().toString().equals("")) {
			tv12.setText("设备不能为空!");
			return;
		}
		if (et4.getText().toString().equals("")) {
			tv12.setText("姓名不能为空!");
			return;
		}
		if (et5.getText().toString().equals("")) {
			tv12.setText("号码不能为空!");
			return;
		}
		if (et6.getText().toString().equals("")) {
			tv12.setText("地址不能为空!");
			return;
		}
		if (et7.getText().toString().equals("")) {
			tv12.setText("内容不能为空!");
			return;
		}

		List<Parameter> params = new ArrayList<Parameter>();

		Parameter parameter1 = new Parameter("subTitle", et1.getText()
				.toString());
		params.add(parameter1);

		Parameter parameter2 = new Parameter("subMachine", et2.getText()
				.toString());
		params.add(parameter2);

		Parameter parameter3 = new Parameter("subContent", et7.getText()
				.toString());
		params.add(parameter3);

		Parameter parameter4 = new Parameter("subName", et4.getText()
				.toString());
		params.add(parameter4);

		Parameter parameter5 = new Parameter("subCommunity", subCommunity);
		params.add(parameter5);

		Parameter parameter6 = new Parameter("subAddr", et6.getText()
				.toString());
		params.add(parameter6);

		Parameter parameter7 = new Parameter("subPhoneNum", et5.getText()
				.toString());
		params.add(parameter7);

		Parameter parameter8 = new Parameter("subType", subType);
		params.add(parameter8);

		Parameter parameter9 = new Parameter("picStreamID1",
				String.valueOf(picStreamID1));
		params.add(parameter9);

		Parameter parameter10 = new Parameter("picStreamID2",
				String.valueOf(picStreamID2));
		params.add(parameter10);

		Parameter parameter11 = new Parameter("picStreamID3",
				String.valueOf(picStreamID3));
		params.add(parameter11);

		Parameter parameter12 = new Parameter("picStreamID4",
				String.valueOf(picStreamID4));
		params.add(parameter12);

		Parameter parameter13 = new Parameter("picStreamID5",
				String.valueOf(picStreamID5));
		params.add(parameter13);

		Parameter parameter14 = new Parameter("subNum", subNum);
		params.add(parameter14);

		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {
			String UID = String.valueOf(uid);
			Parameter parameter = new Parameter("UID", UID);
			params.add(parameter);

			String url = ContantUtil.MODIFY_REPAIR_ORDER_URL;
			new MyAsyncTaskUpdateOrder(this, params).execute(url);

		}

		pd = new ProgressDialog(this);
		pd.setTitle("更新订单中...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		pd.show();

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Message msg = Message.obtain();
				msg.what = GETORDER_STATUS;
				msg.obj = "联网失败,请检查网络!";

				handler.sendMessage(msg);

			}
		}, 5000);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_SELECT_COMMUNITY) {
			if (resultCode == Select_Community_Activity.RESULTCODE1) {
				Bundle bundle = data.getExtras();
				tv13.setText(bundle.getString("community"));
				et6.setText(bundle.getString("communityAddress"));

				subCommunity = bundle.getString("community");

			}

		}

		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH1) {
			// 设置文件保存路径这里放在跟目录下

			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/weixiuotong" + ImageName);
			startPhotoZoom1(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH2) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/weixiuotong" + ImageName);
			startPhotoZoom2(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH3) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/weixiuotong" + ImageName);
			startPhotoZoom3(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH4) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/weixiuotong" + ImageName);
			startPhotoZoom4(Uri.fromFile(picture));
		}

		if (requestCode == PHOTOHRAPH5) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/weixiuotong" + ImageName);
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
				Log.i("photo", String.valueOf(stream.toByteArray().length));

				// 100)压缩文件

				InputStream in = new ByteArrayInputStream(stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url = ContantUtil.UPLOAD_PIC_URL + "?UID=" + UID;
					new MyAsyncTask1(this, in).execute(url);
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
				Log.i("photo", String.valueOf(stream.toByteArray().length));

				// 100)压缩文件

				InputStream in = new ByteArrayInputStream(stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url = ContantUtil.UPLOAD_PIC_URL + "?UID=" + UID;
					new MyAsyncTask2(this, in).execute(url);
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
				Log.i("photo", String.valueOf(stream.toByteArray().length));

				// 100)压缩文件

				InputStream in = new ByteArrayInputStream(stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url = ContantUtil.UPLOAD_PIC_URL + "?UID=" + UID;
					new MyAsyncTask3(this, in).execute(url);
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
				Log.i("photo", String.valueOf(stream.toByteArray().length));

				// 100)压缩文件

				InputStream in = new ByteArrayInputStream(stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url = ContantUtil.UPLOAD_PIC_URL + "?UID=" + UID;
					new MyAsyncTask4(this, in).execute(url);
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
				Log.i("photo", String.valueOf(stream.toByteArray().length));

				// 100)压缩文件

				InputStream in = new ByteArrayInputStream(stream.toByteArray());
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {
					String UID = String.valueOf(uid);
					String url = ContantUtil.UPLOAD_PIC_URL + "?UID=" + UID;
					new MyAsyncTask5(this, in).execute(url);
				}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if (timer != null) {
				timer.cancel();
			}
			Intent intentBack = new Intent(this, Repair_Detail_Activity.class);
			intentBack.putExtra("subNum", subNum);
			startActivity(intentBack);
			Edit_RepairOrder_Activity.this.finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		}
		return true;
	}

	class MyAsyncTaskGetOrderDetail extends AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTaskGetOrderDetail(Context context) {

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
			if (pd != null) {
				pd.cancel();
			}
			if (timer != null) {
				timer.cancel();
			}

			if (result != null) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						Log.i("orderdetail", result);
						if (subNum != null) {
							tv3.setText(subNum);
						}

						et1.setText(jsonObject.getString("subTitle"));
						et2.setText(jsonObject.getString("subMachine"));
						if (jsonObject.getString("subCommunity").equals("") == false) {
							tv13.setText(jsonObject.getString("subCommunity"));
							subCommunity = jsonObject.getString("subCommunity");
							et6.setEnabled(false);
							et6.setFocusable(false);
							subType = "社区";

						} else {
							tv13.setText("");

						}

						et4.setText(jsonObject.getString("subName"));
						et5.setText(jsonObject.getString("subPhoneNum"));
						et6.setText(jsonObject.getString("subAddr"));
						et7.setText(jsonObject.getString("subContent"));

						picStreamID1 = jsonObject.getString("picStreamID1");
						picStreamID2 = jsonObject.getString("picStreamID2");
						picStreamID3 = jsonObject.getString("picStreamID3");
						picStreamID4 = jsonObject.getString("picStreamID4");
						picStreamID5 = jsonObject.getString("picStreamID5");
						if (picStreamID1.equals("0") == false) {
							String picUrl = jsonObject.getString("picUrl1");
							new MyAsyncTaskPic1(context).execute(picUrl);

						}
						if (picStreamID2.equals("0") == false) {
							String picUrl = jsonObject.getString("picUrl2");
							new MyAsyncTaskPic2(context).execute(picUrl);

						}
						if (picStreamID3.equals("0") == false) {
							String picUrl = jsonObject.getString("picUrl3");
							new MyAsyncTaskPic3(context).execute(picUrl);

						}
						if (picStreamID4.equals("0") == false) {
							String picUrl = jsonObject.getString("picUrl4");
							new MyAsyncTaskPic4(context).execute(picUrl);

						}
						if (picStreamID5.equals("0") == false) {
							String picUrl = jsonObject.getString("picUrl5");
							new MyAsyncTaskPic5(context).execute(picUrl);

						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	class MyAsyncTaskPic1 extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public MyAsyncTaskPic1(Context context) {

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
				Log.i("url", params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream = syncHttp.httpGetStream(params[0]);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
				btn5.setVisibility(View.VISIBLE);

				bitmap11 = result;

			} else {
				pb1.setVisibility(View.GONE);

				imageButton1.setVisibility(View.VISIBLE);

			}

		}

	}

	class MyAsyncTaskPic2 extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public MyAsyncTaskPic2(Context context) {

			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			imageButton2.setVisibility(View.GONE);
			pb2.setVisibility(View.VISIBLE);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Log.i("url", params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream = syncHttp.httpGetStream(params[0]);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
				pb2.setVisibility(View.GONE);

				imageButton2.setVisibility(View.VISIBLE);

				imageButton2.setImageBitmap(result);
				btn6.setVisibility(View.VISIBLE);
				bitmap22 = result;

			} else {
				pb2.setVisibility(View.GONE);

				imageButton2.setVisibility(View.VISIBLE);

			}

		}

	}

	class MyAsyncTaskPic3 extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public MyAsyncTaskPic3(Context context) {

			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			imageButton3.setVisibility(View.GONE);
			pb3.setVisibility(View.VISIBLE);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Log.i("url", params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream = syncHttp.httpGetStream(params[0]);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
				pb3.setVisibility(View.GONE);

				imageButton3.setVisibility(View.VISIBLE);

				imageButton3.setImageBitmap(result);
				btn7.setVisibility(View.VISIBLE);
				bitmap33 = result;

			} else {
				pb3.setVisibility(View.GONE);

				imageButton3.setVisibility(View.VISIBLE);

			}

		}

	}

	class MyAsyncTaskPic4 extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public MyAsyncTaskPic4(Context context) {

			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			imageButton4.setVisibility(View.GONE);
			pb4.setVisibility(View.VISIBLE);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Log.i("url", params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream = syncHttp.httpGetStream(params[0]);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
				pb4.setVisibility(View.GONE);

				imageButton4.setVisibility(View.VISIBLE);

				imageButton4.setImageBitmap(result);
				btn8.setVisibility(View.VISIBLE);
				bitmap44 = result;

			} else {
				pb4.setVisibility(View.GONE);

				imageButton4.setVisibility(View.VISIBLE);

			}

		}

	}

	class MyAsyncTaskPic5 extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public MyAsyncTaskPic5(Context context) {

			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			imageButton5.setVisibility(View.GONE);
			pb5.setVisibility(View.VISIBLE);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Log.i("url", params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
				InputStream inputStream = syncHttp.httpGetStream(params[0]);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
				pb5.setVisibility(View.GONE);

				imageButton5.setVisibility(View.VISIBLE);

				imageButton5.setImageBitmap(result);
				btn9.setVisibility(View.VISIBLE);
				bitmap55 = result;

			} else {
				pb5.setVisibility(View.GONE);

				imageButton5.setVisibility(View.VISIBLE);

			}

		}

	}

	class MyAsyncTaskUpdateOrder extends AsyncTask<String, Void, String> {
		private Context context;
		List<Parameter> params1;

		public MyAsyncTaskUpdateOrder(Context context, List<Parameter> params) {
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
						if (pd != null) {
							pd.dismiss();
						}

						if (timer != null) {
							timer.cancel();
						}
						Log.i("submitsuccess", result);
						Intent intent1 = new Intent(context,
								Repair_Detail_Activity.class);
						intent1.putExtra("subNum", subNum);
						startActivity(intent1);
						Toast.makeText(context, "更新成功", Toast.LENGTH_LONG)
								.show();
						Edit_RepairOrder_Activity.this.finish();
						overridePendingTransition(R.anim.out_to_right,
								R.anim.in_from_left);

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = GETORDER_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = GETORDER_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 102) {
						Message msg = Message.obtain();
						msg.what = GETORDER_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 103) {
						Message msg = Message.obtain();
						msg.what = GETORDER_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 104) {
						Message msg = Message.obtain();
						msg.what = GETORDER_STATUS;
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

		public MyAsyncTask1(Context context, InputStream in) {

			this.context = context;
			this.inputStream = in;
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
				NewService newService = new NewService(context);
				String str = newService.UploadHead(params[0], inputStream);
				Log.i("str", str);

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

						if (bitmap11 != null) {
							imageButton1.setImageBitmap(bitmap11);
							tv12.setText(jsonObject.getString("opMess"));
							btn5.setVisibility(View.VISIBLE);
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID1 = jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);

						tv12.setText(jsonObject.getString("opMess"));
					} else if (opCode == 101) {
						pb1.setVisibility(View.GONE);
						imageButton1.setVisibility(View.VISIBLE);
						tv12.setText(jsonObject.getString("opMess"));

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

		public MyAsyncTask2(Context context, InputStream in) {

			this.context = context;
			this.inputStream = in;
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
				NewService newService = new NewService(context);
				String str = newService.UploadHead(params[0], inputStream);
				Log.i("str", str);

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

						if (bitmap22 != null) {
							imageButton2.setImageBitmap(bitmap22);
							btn5.setVisibility(View.VISIBLE);
							tv12.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID2 = jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb2.setVisibility(View.GONE);
						imageButton2.setVisibility(View.VISIBLE);

						tv12.setText(jsonObject.getString("opMess"));
					} else if (opCode == 101) {
						pb2.setVisibility(View.GONE);
						imageButton2.setVisibility(View.VISIBLE);
						tv12.setText(jsonObject.getString("opMess"));

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

		public MyAsyncTask3(Context context, InputStream in) {

			this.context = context;
			this.inputStream = in;
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
				NewService newService = new NewService(context);
				String str = newService.UploadHead(params[0], inputStream);
				Log.i("str", str);

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

						if (bitmap33 != null) {
							imageButton3.setImageBitmap(bitmap33);
							btn5.setVisibility(View.VISIBLE);
							tv12.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID3 = jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb3.setVisibility(View.GONE);
						imageButton3.setVisibility(View.VISIBLE);

						tv12.setText(jsonObject.getString("opMess"));
					} else if (opCode == 101) {
						pb3.setVisibility(View.GONE);
						imageButton3.setVisibility(View.VISIBLE);
						tv12.setText(jsonObject.getString("opMess"));

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

		public MyAsyncTask4(Context context, InputStream in) {

			this.context = context;
			this.inputStream = in;
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
				NewService newService = new NewService(context);
				String str = newService.UploadHead(params[0], inputStream);
				Log.i("str", str);

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

						if (bitmap44 != null) {
							imageButton4.setImageBitmap(bitmap44);
							tv12.setText(jsonObject.getString("opMess"));
							btn5.setVisibility(View.VISIBLE);
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID4 = jsonObject.getString("picStreamID");
						}

					} else if (opCode == 100) {
						pb4.setVisibility(View.GONE);
						imageButton4.setVisibility(View.VISIBLE);

						tv12.setText(jsonObject.getString("opMess"));
					} else if (opCode == 101) {
						pb4.setVisibility(View.GONE);
						imageButton4.setVisibility(View.VISIBLE);
						tv12.setText(jsonObject.getString("opMess"));

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

		public MyAsyncTask5(Context context, InputStream in) {

			this.context = context;
			this.inputStream = in;
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
				NewService newService = new NewService(context);
				String str = newService.UploadHead(params[0], inputStream);
				Log.i("str", str);

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

						if (bitmap55 != null) {
							imageButton5.setImageBitmap(bitmap55);
							btn5.setVisibility(View.VISIBLE);
							tv12.setText(jsonObject.getString("opMess"));
							Log.i("picId", jsonObject.getString("picStreamID"));
							picStreamID5 = jsonObject.getString("picStreamID");
							Log.i("picstreamid5", picStreamID5);
						}

					} else if (opCode == 100) {
						pb5.setVisibility(View.GONE);
						imageButton5.setVisibility(View.VISIBLE);

						tv12.setText(jsonObject.getString("opMess"));
					} else if (opCode == 101) {
						pb5.setVisibility(View.GONE);
						imageButton5.setVisibility(View.VISIBLE);
						tv12.setText(jsonObject.getString("opMess"));

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

		public MyAsyncTask6(Context context) {

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

			if (pd != null) {
				pd.cancel();
			}
			if (timer != null) {
				timer.cancel();
			}
			if (result != null) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {

						Toast.makeText(context, "删除成功!", Toast.LENGTH_LONG)
								.show();
						Log.i("deletesuccess", "删除成功!");
						Edit_RepairOrder_Activity.this.finish();
						overridePendingTransition(R.anim.out_to_right,
								R.anim.in_from_left);
					} else if (opCode == 100) {
						tv12.setText(jsonObject.getString("opMess"));

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
