package com.weixiuotong.wenfei.activity;


import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import org.json.JSONObject;


import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class Select_Community_Activity extends Activity implements OnClickListener {
	private Button btn1,btn2,btn3;
	private TextView tv2;
	
	
	private RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9,rb10,rb11,rb12,rb13,rb14,rb15,rb16,rb17,rb18,rb19,rb20;
	
	private String community_return;
	private String communityAdress_return;
	SharedPreferences sharedPreferences;
	
	public static final int RESULTCODE1=3;

	private String[] communityList;
	private String[] communityAddressList;
	private ProgressDialog pd;
	private Timer timer;
	private Handler handler;
	private static final int GET_COMMUNITY_STATUS = 1;


	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		setContentView(R.layout.select_community_activity);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		btn3=(Button)findViewById(R.id.button3);
		tv2=(TextView)findViewById(R.id.textView2);
		rb1=(RadioButton)findViewById(R.id.radio1);
		rb2=(RadioButton)findViewById(R.id.radio2);
		rb3=(RadioButton)findViewById(R.id.radio3);
		rb4=(RadioButton)findViewById(R.id.radio4);
		
		rb5=(RadioButton)findViewById(R.id.radio5);
		rb6=(RadioButton)findViewById(R.id.radio6);
		rb7=(RadioButton)findViewById(R.id.radio7);
		rb8=(RadioButton)findViewById(R.id.radio8);
		rb9=(RadioButton)findViewById(R.id.radio9);
		rb10=(RadioButton)findViewById(R.id.radio10);
		rb11=(RadioButton)findViewById(R.id.radio11);
		rb12=(RadioButton)findViewById(R.id.radio12);
		rb13=(RadioButton)findViewById(R.id.radio13);
		rb14=(RadioButton)findViewById(R.id.radio14);
		rb15=(RadioButton)findViewById(R.id.radio15);
		rb16=(RadioButton)findViewById(R.id.radio16);
		rb17=(RadioButton)findViewById(R.id.radio17);
		
		rb18=(RadioButton)findViewById(R.id.radio18);
		rb19=(RadioButton)findViewById(R.id.radio19);
		rb20=(RadioButton)findViewById(R.id.radio20);
		
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		Intent intent=getIntent();
		
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case GET_COMMUNITY_STATUS:
					if (timer != null) {
						timer.cancel();
					}

					if (pd != null) {
						pd.dismiss();
					}

					tv2.setText(msg.obj.toString());
					break;

				}

			}

		};
		getCommunityList();
				
	}
	


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getCommunityList();
	}



	private void getCommunityList() {
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if(uid!=0000000000){
			pd = new ProgressDialog(this);
			pd.setTitle("正在加载社区...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

			pd.show();
			String url = ContantUtil.SELECT_COMMUNITY_URL + "?UID="
					+ String.valueOf(uid);
			new MyAsyncTask(this).execute(url);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = GET_COMMUNITY_STATUS;
					msg.obj = "联网失败!";

					handler.sendMessage(msg);

				}
			}, 5000);
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			
			break;

		case R.id.button2:
			Intent intent1=new Intent(this,AddCommunityActivity.class);
			startActivity(intent1);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			
			break;
		case R.id.button3:
			//选择哪个社区
			if(rb1.isChecked()){
				community_return=communityList[0];
				communityAdress_return=communityAddressList[0];
				
			}
			if(rb2.isChecked()){
				community_return=communityList[1];
				communityAdress_return=communityAddressList[1];
			}
			if(rb3.isChecked()){
				community_return=communityList[2];
				communityAdress_return=communityAddressList[2];
			}
			if(rb4.isChecked()){
				community_return=communityList[3];
				communityAdress_return=communityAddressList[3];
			}
			if(rb5.isChecked()){
				community_return=communityList[4];
				communityAdress_return=communityAddressList[4];
			}
			if(rb6.isChecked()){
				community_return=communityList[5];
				communityAdress_return=communityAddressList[5];
			}
			if(rb7.isChecked()){
				
				community_return=communityList[6];
				communityAdress_return=communityAddressList[6];
			}
			if(rb8.isChecked()){
				community_return=communityList[7];
				communityAdress_return=communityAddressList[7];
			}
			if(rb9.isChecked()){
				community_return=communityList[8];
				communityAdress_return=communityAddressList[8];
			}
			if(rb10.isChecked()){
				community_return=communityList[9];
				communityAdress_return=communityAddressList[9];
			}
			if(rb11.isChecked()){
				community_return=communityList[10];
				communityAdress_return=communityAddressList[10];
				
			}
			if(rb12.isChecked()){
				community_return=communityList[11];
				communityAdress_return=communityAddressList[11];
			}
			if(rb13.isChecked()){
				community_return=communityList[12];
				communityAdress_return=communityAddressList[12];
			}
			if(rb14.isChecked()){
				community_return=communityList[13];
				communityAdress_return=communityAddressList[13];
			}
			if(rb15.isChecked()){
				community_return=communityList[14];
				communityAdress_return=communityAddressList[14];
			}
			if(rb16.isChecked()){
				community_return=communityList[15];
				communityAdress_return=communityAddressList[15];
			}
			if(rb17.isChecked()){
				
				community_return=communityList[16];
				communityAdress_return=communityAddressList[16];
			}
			if(rb18.isChecked()){
				community_return=communityList[17];
				communityAdress_return=communityAddressList[17];
			}
			if(rb19.isChecked()){
				community_return=communityList[18];
				communityAdress_return=communityAddressList[18];
			}
			if(rb20.isChecked()){
				community_return=communityList[19];
				communityAdress_return=communityAddressList[19];
			}
			
			
				Intent intent2 = new Intent();
				  
				intent2.putExtra("community",community_return);
				intent2.putExtra("communityAddress", communityAdress_return);
				setResult(RESULTCODE1, intent2);
				finish();
				overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
		
			break;
		}
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);

			break;

		}
		return true;
	}
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		private Context context;
		

		public MyAsyncTask(Context context) {
			
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
			if (timer != null) {
				timer.cancel();
			}

			if (pd != null) {
				pd.dismiss();
			}

			
			if (result != null&&result.equals("[]")==false) {
				try {
					Log.i("result",result);
					tv2.setText("");
					 RadioButton[] rb={rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9,rb10,rb11,rb12,rb13,rb14,rb15,rb16,rb17,rb18,rb19,rb20};
					Log.i("community",result);
					JSONArray jsonArray=new JSONArray(result);
					communityList=new String[jsonArray.length()];
					communityAddressList=new String[jsonArray.length()];
					Log.i("jsonArray",String.valueOf(jsonArray.length()));
					for(int i=0;i<jsonArray.length();i++){
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Log.i("jsonobject",jsonObject.toString());
						communityList[i]=jsonObject.getString("CommunitysName");
						communityAddressList[i]=jsonObject.getString("CommunitysAddr");
						
						rb[i].setText("社区      "+communityList[i]);
						rb[i].setVisibility(View.VISIBLE);
						if(i==19){
							break;
						}
						
					}
					btn3.setVisibility(View.VISIBLE);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				tv2.setTextSize(40);
				tv2.setText("请添加社区!");
				
			}

		}

	}
	




}
