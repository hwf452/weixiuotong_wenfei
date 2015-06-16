package com.weixiuotong.wenfei.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.KeyEvent;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.CompoundButton;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import android.widget.SimpleAdapter;

//我的维修单页面
public class My_Repair_Order_Activity extends Activity implements
		android.widget.CompoundButton.OnCheckedChangeListener {
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioButton rb4;
	private RadioButton rb5;
	private ProgressDialog pd;
	private SharedPreferences sharedPreferences;
	private Handler handler;
	private static final int GETORDER_STATUS = 11;
	private Timer timer;

	private ListView listView;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_repair_order);
		rb1 = (RadioButton) findViewById(R.id.radio1);
		rb2 = (RadioButton) findViewById(R.id.radio2);
		rb3 = (RadioButton) findViewById(R.id.radio3);
		rb4 = (RadioButton) findViewById(R.id.radio4);
		rb5 = (RadioButton) findViewById(R.id.radio5);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);

		listView = (ListView) findViewById(R.id.listView1);
		rb1.setOnCheckedChangeListener(this);
		rb2.setOnCheckedChangeListener(this);
		rb3.setOnCheckedChangeListener(this);
		rb4.setOnCheckedChangeListener(this);
		rb5.setOnCheckedChangeListener(this);
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

					Toast.makeText(My_Repair_Order_Activity.this,
							msg.obj.toString(), Toast.LENGTH_LONG).show();
					break;

				}

			}

		};

		initView();

	}
	

	@Override
	protected void onRestart() {
		
		super.onRestart();
		if(rb1.isChecked()){
			initView();
		}
		
		
		
	}


	private void initView() {
		pd = new ProgressDialog(this);
		pd.setTitle("正在获取维修单列表...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(true);
		pd.show();
		// 获取UID
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {

			String url = ContantUtil.GET_REPAIR_ORDER_LIST + "?UID="
					+ String.valueOf(uid) + "&subStatus=0";
			Log.i("url", url);
			new MyAsyncTask1(this).execute(url);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = GETORDER_STATUS;
					msg.obj = "获取地址失败,请检查网络!";
					handler.sendMessage(msg);

				}
			}, 8000);
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.radio1:
			if (rb1.isChecked()) {

				initView();
			}

			break;

		case R.id.radio2:
			if (rb2.isChecked()) {

				pd = new ProgressDialog(this);
				pd.setTitle("正在获取维修单列表...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCancelable(true);
				pd.show();
				// 获取UID
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {

					String url = ContantUtil.GET_REPAIR_ORDER_LIST + "?UID="
							+ String.valueOf(uid) + "&subStatus=1";
					Log.i("url", url);
					new MyAsyncTask2(this).execute(url);
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Message msg = Message.obtain();
							msg.what = GETORDER_STATUS;
							msg.obj = "获取地址失败,请检查网络!";
							handler.sendMessage(msg);

						}
					}, 5000);
				}

			}

			break;
		case R.id.radio3:
			if (rb3.isChecked()) {
				pd = new ProgressDialog(this);
				pd.setTitle("正在获取维修单列表...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCancelable(true);
				pd.show();
				// 获取UID
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {

					String url = ContantUtil.GET_REPAIR_ORDER_LIST + "?UID="
							+ String.valueOf(uid) + "&subStatus=-1";
					Log.i("url", url);
					new MyAsyncTask3(this).execute(url);
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Message msg = Message.obtain();
							msg.what = GETORDER_STATUS;
							msg.obj = "获取地址失败,请检查网络!";
							handler.sendMessage(msg);

						}
					}, 5000);
				}

			}

			break;
		case R.id.radio4:
			if (rb4.isChecked()) {
				pd = new ProgressDialog(this);
				pd.setTitle("正在获取维修单列表...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCancelable(true);
				pd.show();
				// 获取UID
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {

					String url = ContantUtil.GET_REPAIR_ORDER_LIST + "?UID="
							+ String.valueOf(uid) + "&subStatus=2";
					Log.i("url", url);
					new MyAsyncTask4(this).execute(url);
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Message msg = Message.obtain();
							msg.what = GETORDER_STATUS;
							msg.obj = "获取地址失败,请检查网络!";
							handler.sendMessage(msg);

						}
					}, 5000);
				}
			}

			break;
		case R.id.radio5:
			if (rb5.isChecked()) {
				pd = new ProgressDialog(this);
				pd.setTitle("正在获取维修单列表...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCancelable(true);
				pd.show();
				// 获取UID
				int uid = sharedPreferences.getInt("UID", 0000000000);
				if (uid != 0000000000) {

					String url = ContantUtil.GET_REPAIR_ORDER_LIST + "?UID="
							+ String.valueOf(uid) + "&subStatus=3";
					Log.i("url", url);
					new MyAsyncTask5(this).execute(url);
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Message msg = Message.obtain();
							msg.what = GETORDER_STATUS;
							msg.obj = "获取地址失败,请检查网络!";
							handler.sendMessage(msg);

						}
					}, 5000);
				}
			}

			break;

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要退出维修通?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (timer != null) {
										timer.cancel();
									}
									My_Repair_Order_Activity.this.finish();

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
					JSONArray jsonArray = new JSONArray(result);
					Log.i("wait_to_now", jsonArray.toString());

					final List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("textView1", "单号:");
						hashMap.put("textView2", jsonObject.getString("subNum"));
						hashMap.put("textView3",
								jsonObject.getString("subTitle"));
						hashMap.put("textView4",
								jsonObject.getString("subTime"));
						orderList.add(hashMap);

					}

					SimpleAdapter simpleAdapter = new SimpleAdapter(context,
							orderList, R.layout.my_repair_list_order,
							new String[] { "textView1", "textView2",
									"textView3", "textView4" }, new int[] {
									R.id.textView1, R.id.textView2,
									R.id.textView3, R.id.textView4 });
					simpleAdapter.notifyDataSetChanged();
					listView.setAdapter(simpleAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							String string = orderList.get(position).get(
									"textView2");
							// Toast.makeText(My_Repair_Order_Activity.this,
							// string,
							// Toast.LENGTH_LONG).show();
							Log.i("textview2", string);
							Intent intent = new Intent(context,
									Repair_Detail_Activity.class);
							intent.putExtra("subNum", string);

							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);

						}
					});

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
					JSONArray jsonArray = new JSONArray(result);
					Log.i("wait_to_now", jsonArray.toString());

					final List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("textView1", "单号:");
						hashMap.put("textView2", jsonObject.getString("subNum"));
						hashMap.put("textView3",
								jsonObject.getString("subTitle"));
						hashMap.put("textView4",
								jsonObject.getString("subTime"));
						orderList.add(hashMap);

					}

					SimpleAdapter simpleAdapter = new SimpleAdapter(context,
							orderList, R.layout.my_repair_list_order,
							new String[] { "textView1", "textView2",
									"textView3", "textView4" }, new int[] {
									R.id.textView1, R.id.textView2,
									R.id.textView3, R.id.textView4 });
					simpleAdapter.notifyDataSetChanged();
					listView.setAdapter(simpleAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							String string = orderList.get(position).get(
									"textView2");
							// Toast.makeText(My_Repair_Order_Activity.this,
							// string,
							// Toast.LENGTH_LONG).show();
							Log.i("textview2", string);
							Intent intent = new Intent(context,
									My_Repair_Order_Repair_Now.class);
							intent.putExtra("subNum", string);

							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);

						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	class MyAsyncTask3 extends AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTask3(Context context) {

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
					JSONArray jsonArray = new JSONArray(result);
					Log.i("wait_to_now", jsonArray.toString());

					final List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("textView1", "单号:");
						hashMap.put("textView2", jsonObject.getString("subNum"));
						hashMap.put("textView3",
								jsonObject.getString("subTitle"));
						hashMap.put("textView4",
								jsonObject.getString("subTime"));
						orderList.add(hashMap);

					}
					String UID = String.valueOf(sharedPreferences.getInt("UID",
							0000000000));

					Refuse_BaseAdapter refuse_BaseAdapter = new Refuse_BaseAdapter(
							context, orderList, UID);
					refuse_BaseAdapter.notifyDataSetChanged();
					listView.setAdapter(refuse_BaseAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							String string = orderList.get(position).get(
									"textView2");
							// Toast.makeText(My_Repair_Order_Activity.this,
							// string,
							// Toast.LENGTH_LONG).show();
							Log.i("textview2", string);
							Intent intent = new Intent(context,
									My_Repair_Order_Repair_Refuse_Status.class);
							intent.putExtra("subNum", string);
							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);

						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	class MyAsyncTask4 extends AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTask4(Context context) {

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
					JSONArray jsonArray = new JSONArray(result);
					Log.i("wait_to_now", jsonArray.toString());

					final List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("textView1", "单号:");
						hashMap.put("textView2", jsonObject.getString("subNum"));
						hashMap.put("textView3",
								jsonObject.getString("subTitle"));

						orderList.add(hashMap);

					}
					String UID = String.valueOf(sharedPreferences.getInt("UID",
							0000000000));

					Wait_Sure_BaseAdapter wait_Sure_BaseAdapter = new Wait_Sure_BaseAdapter(
							context, orderList, UID);

					wait_Sure_BaseAdapter.notifyDataSetChanged();

					listView.setAdapter(wait_Sure_BaseAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							String string = orderList.get(position).get(
									"textView2");
							// Toast.makeText(My_Repair_Order_Activity.this,
							// string,
							// Toast.LENGTH_LONG).show();
							Log.i("textview2", string);
							Intent intent = new Intent(context,
									My_Repair_Order_Repair_Wait_Sure.class);
							intent.putExtra("subNum", string);
							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);

						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	class MyAsyncTask5 extends AsyncTask<String, Void, String> {
		private Context context;

		public MyAsyncTask5(Context context) {

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
					JSONArray jsonArray = new JSONArray(result);
					Log.i("wait_to_now", jsonArray.toString());

					final List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("textView1", "单号:");
						hashMap.put("textView2", jsonObject.getString("subNum"));
						hashMap.put("textView3",
								jsonObject.getString("subTitle"));
						hashMap.put("textView4",
								jsonObject.getString("subTime"));
						orderList.add(hashMap);

					}
					String UID = String.valueOf(sharedPreferences.getInt("UID",
							0000000000));
					Refuse_BaseAdapter refuse_BaseAdapter = new Refuse_BaseAdapter(
							context, orderList, UID);
					refuse_BaseAdapter.notifyDataSetChanged();
					listView.setAdapter(refuse_BaseAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							String string = orderList.get(position).get(
									"textView2");
							// Toast.makeText(My_Repair_Order_Activity.this,
							// string,
							// Toast.LENGTH_LONG).show();
							Log.i("textview2", string);
							Intent intent = new Intent(context,
									My_Repair_Order_Repair_Refuse_Status.class);
							intent.putExtra("subNum", string);
							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);

						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
