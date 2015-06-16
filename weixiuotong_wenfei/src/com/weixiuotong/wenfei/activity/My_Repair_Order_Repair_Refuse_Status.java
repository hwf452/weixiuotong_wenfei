package com.weixiuotong.wenfei.activity;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class My_Repair_Order_Repair_Refuse_Status extends Activity implements OnClickListener {
	private Button btn1,btn3,btn5;
	private EditText et1,et2,et3,et4,et5,et6;
	private String subNum;
	private TextView tv3;
	private SharedPreferences sharedPreferences;
	private ProgressDialog pd;
	private Handler handler;
	private Timer timer;
	private static final int GETORDER_STATUS = 11;
	private String picStreamID1 = new String("0");
	private String picStreamID2 = new String("0");
	private String picStreamID3 = new String("0");
	private String picStreamID4 = new String("0");
	private String picStreamID5 = new String("0");

	private String subConten_show;
	private ProgressBar pb1;
	private ProgressBar pb2;
	private ProgressBar pb3;
	private ProgressBar pb4;
	private ProgressBar pb5;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;
	private Bitmap bitmap11=null;
	private Bitmap bitmap22=null;
	private Bitmap bitmap33=null;
	private Bitmap bitmap44=null;
	private Bitmap bitmap55=null;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_repair_order_repair_refuse_status);
		btn1=(Button)findViewById(R.id.button1);
		btn3=(Button)findViewById(R.id.button3);
		btn5=(Button)findViewById(R.id.button5);
		
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3=(EditText)findViewById(R.id.editText3);
		et4=(EditText)findViewById(R.id.editText4);
		et5 = (EditText) findViewById(R.id.editText5);
		et6 = (EditText) findViewById(R.id.editText6);
		tv3 = (TextView) findViewById(R.id.textView3);
		pb1=(ProgressBar)findViewById(R.id.progressBar1);
		pb2=(ProgressBar)findViewById(R.id.progressBar2);
		pb3=(ProgressBar)findViewById(R.id.progressBar3);
		pb4=(ProgressBar)findViewById(R.id.progressBar4);
		pb5=(ProgressBar)findViewById(R.id.progressBar5);
		
		imageView1=(ImageView)findViewById(R.id.imageView1);
		imageView2=(ImageView)findViewById(R.id.imageView2);
		imageView3=(ImageView)findViewById(R.id.imageView3);
		imageView4=(ImageView)findViewById(R.id.imageView4);
		imageView5=(ImageView)findViewById(R.id.imageView5);
		btn1.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn5.setOnClickListener(this);
		imageView1.setOnClickListener(this);
		imageView2.setOnClickListener(this);
		imageView3.setOnClickListener(this);
		imageView4.setOnClickListener(this);
		imageView5.setOnClickListener(this);
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

					Toast.makeText(My_Repair_Order_Repair_Refuse_Status.this,
							msg.obj.toString(), Toast.LENGTH_LONG).show();
					break;

				}

			}

		};
		if (subNum != null) {
			getOrderDetail();
			Log.i("subNum", subNum);
		}

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
					+ String.valueOf(uid)+"&subNum="+subNum;
			Log.i("url", url);
			new MyAsyncTask1(this).execute(url);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = GETORDER_STATUS;
					msg.obj = "联网失败!";
					handler.sendMessage(msg);

				}
			}, 8000);
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			break;

		case R.id.button3:
			Intent intent3 = new Intent(this, Order_Detail_Show_Content.class);
			intent3.putExtra("content_detail", subConten_show);
			startActivity(intent3);
			
			break;
		case R.id.button5:
			Intent intent5=new Intent(this,Event_progress_Activity.class);
			
			startActivity(intent5);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.imageView1:
			if (bitmap11 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap11);
				
				startActivity(intent);
				

			} 
			
			break;
		case R.id.imageView2:
			if (bitmap22 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap22);
				
				startActivity(intent);
				

			} 
			
			break;
		case R.id.imageView3:
			if (bitmap33 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap33);
				
				startActivity(intent);
				

			} 
			
			break;
		case R.id.imageView4:
			if (bitmap44 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap44);
				
				startActivity(intent);
				

			} 
			
			break;
		case R.id.imageView5:
			if (bitmap55 != null) {
				Intent intent = new Intent(this,
						Submit_ShowPicture_Activity.class);
				intent.putExtra("bitmap", bitmap55);
				
				startActivity(intent);
				

			} 
			
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if(timer!=null){
				timer.cancel();
			}
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			
			break;

		}
		return true;
	}
	class MyAsyncTask1 extends AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTask1(Context context) {

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
						if(subNum!=null){
							tv3.setText(subNum);
						}
						
						
						et1.setText(jsonObject.getString("subTitle"));
						et2.setText(jsonObject.getString("subMachine"));
						if(jsonObject.getString("subCommunity")!=null){
							et3.setText(jsonObject.getString("subCommunity"));
							
						}else{
							et3.setText("");
							
						}
						
						et4.setText(jsonObject.getString("subName"));
						et5.setText(jsonObject.getString("subPhoneNum"));
						et6.setText(jsonObject.getString("subAddr"));
						
						String subContent=jsonObject.getString("subContent");
						if(subContent.length()<15){
							btn3.setText(subContent);
							subConten_show=subContent;
						}else{
							String str=subContent.substring(0, 15);
							StringBuffer sb=new StringBuffer();
							sb.append(str).append("...");
							btn3.setText(sb.toString());
							subConten_show=subContent;
						}
						picStreamID1 = jsonObject.getString("picStreamID1");
						 picStreamID2 = jsonObject.getString("picStreamID2");
						 picStreamID3 = jsonObject.getString("picStreamID3");
						 picStreamID4 = jsonObject.getString("picStreamID4");
						 picStreamID5 = jsonObject.getString("picStreamID5");
						if(picStreamID1.equals("0")==false){
							String picUrl=jsonObject.getString("picUrl1");
							new MyAsyncTaskPic1(context).execute(picUrl);

						}
						if(picStreamID2.equals("0")==false){
							String picUrl=jsonObject.getString("picUrl2");
							new MyAsyncTaskPic2(context).execute(picUrl);

						}
						if(picStreamID3.equals("0")==false){
							String picUrl=jsonObject.getString("picUrl3");
							new MyAsyncTaskPic3(context).execute(picUrl);

						}
						if(picStreamID4.equals("0")==false){
							String picUrl=jsonObject.getString("picUrl4");
							new MyAsyncTaskPic4(context).execute(picUrl);

						}
						if(picStreamID5.equals("0")==false){
							String picUrl=jsonObject.getString("picUrl5");
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
			imageView1.setVisibility(View.GONE);
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
				
				imageView1.setVisibility(View.VISIBLE);
				
					imageView1.setImageBitmap(result);
					bitmap11=result;
				
			}else{
				pb1.setVisibility(View.GONE);
				
				imageView1.setVisibility(View.VISIBLE);
				
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
			imageView2.setVisibility(View.GONE);
			pb2.setVisibility(View.VISIBLE);
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
				pb2.setVisibility(View.GONE);
				
				imageView2.setVisibility(View.VISIBLE);
				
					imageView2.setImageBitmap(result);
					bitmap22=result;
				
			}else{
				pb2.setVisibility(View.GONE);
				
				imageView2.setVisibility(View.VISIBLE);
				
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
			imageView3.setVisibility(View.GONE);
			pb3.setVisibility(View.VISIBLE);
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
				pb3.setVisibility(View.GONE);
				
				imageView3.setVisibility(View.VISIBLE);
				
					imageView3.setImageBitmap(result);
					bitmap33=result;
				
			}else{
				pb3.setVisibility(View.GONE);
				
				imageView3.setVisibility(View.VISIBLE);
				
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
			imageView4.setVisibility(View.GONE);
			pb4.setVisibility(View.VISIBLE);
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
				pb4.setVisibility(View.GONE);
				
				imageView4.setVisibility(View.VISIBLE);
				
					imageView4.setImageBitmap(result);
					bitmap44=result;
				
			}else{
				pb4.setVisibility(View.GONE);
				
				imageView4.setVisibility(View.VISIBLE);
				
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
			imageView5.setVisibility(View.GONE);
			pb5.setVisibility(View.VISIBLE);
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
				pb5.setVisibility(View.GONE);
				
				imageView5.setVisibility(View.VISIBLE);
				
					imageView5.setImageBitmap(result);
					bitmap55=result;
				
			}else{
				pb5.setVisibility(View.GONE);
				
				imageView5.setVisibility(View.VISIBLE);
				
			}

		}

	}


}
