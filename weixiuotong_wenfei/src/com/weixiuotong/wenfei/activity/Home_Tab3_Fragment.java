package com.weixiuotong.wenfei.activity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import android.app.ProgressDialog;
import android.content.Context;

import android.content.SharedPreferences;

import android.content.Intent;

import android.os.AsyncTask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

import android.widget.Toast;

import android.widget.SimpleAdapter;

public class Home_Tab3_Fragment extends FragmentToOtherActivity implements android.widget.CompoundButton.OnCheckedChangeListener{
	

	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioButton rb4;
	private RadioButton rb5;
	private ProgressDialog pd;
	private SharedPreferences sharedPreferences;
	private Handler handler;
	private static final int GETORDER_STATUS = 11;
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initView();
	}

	private Timer timer;

	private ListView listView;
	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_repair_order, null);
		rb1 = (RadioButton) view.findViewById(R.id.radio1);
		rb2 = (RadioButton) view.findViewById(R.id.radio2);
		rb3 = (RadioButton) view.findViewById(R.id.radio3);
		rb4 = (RadioButton) view.findViewById(R.id.radio4);
		rb5 = (RadioButton) view.findViewById(R.id.radio5);
		sharedPreferences = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);

		listView = (ListView) view.findViewById(R.id.listView1);
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

					Toast.makeText(getActivity(),
							msg.obj.toString(), Toast.LENGTH_LONG).show();
					break;

				}

			}

		};

		


		return view;
	}
	
	private void initView() {
		pd = new ProgressDialog(getActivity());
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
			new MyAsyncTask1(getActivity()).execute(url);
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

				pd = new ProgressDialog(getActivity());
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
					new MyAsyncTask2(getActivity()).execute(url);
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
				pd = new ProgressDialog(getActivity());
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
					new MyAsyncTask3(getActivity()).execute(url);
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
				pd = new ProgressDialog(getActivity());
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
					new MyAsyncTask4(getActivity()).execute(url);
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
				pd = new ProgressDialog(getActivity());
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
					new MyAsyncTask5(getActivity()).execute(url);
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
							getActivity().overridePendingTransition(R.anim.in_from_right,
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
							getActivity().overridePendingTransition(R.anim.in_from_right,
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
							getActivity().overridePendingTransition(R.anim.in_from_right,
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
							getActivity().overridePendingTransition(R.anim.in_from_right,
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
							getActivity().overridePendingTransition(R.anim.in_from_right,
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
