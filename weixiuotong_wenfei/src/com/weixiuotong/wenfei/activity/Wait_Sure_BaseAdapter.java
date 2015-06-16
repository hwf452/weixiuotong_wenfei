package com.weixiuotong.wenfei.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Wait_Sure_BaseAdapter extends BaseAdapter {
	Context context;
	List<HashMap<String, String>> orderList;
	private LayoutInflater inflater;
	String UID;
	private ProgressDialog pd;
	private Timer timer;
	private Handler handler;
	private static final int NET_STATUS = 1;

	public Wait_Sure_BaseAdapter(final Context context,
			List<HashMap<String, String>> orderList, String UID) {
		this.inflater = LayoutInflater.from(context);
		this.orderList = orderList;
		this.context = context;
		this.UID = UID;
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case NET_STATUS:
					if (timer != null) {
						timer.cancel();
					}

					if (pd != null) {
						pd.dismiss();
					}

					Toast.makeText(context, "联网失败,请检查网络!", Toast.LENGTH_LONG)
							.show();
					break;

				}

			}

		};
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int position) {

		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.my_repair_list_wait_sure, null);
			holder = new ViewHolder();
			holder.textView1 = (TextView) view.findViewById(R.id.textView1);
			holder.textView2 = (TextView) view.findViewById(R.id.textView2);
			holder.textView3 = (TextView) view.findViewById(R.id.textView3);
			holder.btn1 = (Button) view.findViewById(R.id.button1);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.textView1.setText(orderList.get(position).get("textView1"));
		holder.textView2.setText(orderList.get(position).get("textView2"));
		holder.textView3.setText(orderList.get(position).get("textView3"));
		holder.btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setTitle("提醒")
						.setMessage("当前定单已经完成?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										pd = new ProgressDialog(context);
										pd.setTitle("正在确认订单...");
										pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
										pd.setCancelable(true);
										pd.show();
										Log.i("orderlist", orderList.toString());
										String url = ContantUtil.SURE_REPAIR_ORDER_URL
												+ "?UID="
												+ UID
												+ "&subNum="
												+ orderList.get(position).get(
														"textView2");
										Log.i("delurl", url);
										new MyAsyncTask1(context, orderList,
												position).execute(url);
										timer = new Timer();
										timer.schedule(new TimerTask() {

											@Override
											public void run() {

												Message msg = Message.obtain();
												msg.what = NET_STATUS;
												msg.obj = "联网失败!";

												handler.sendMessage(msg);

											}
										}, 8000);

										
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
		});

		return view;
	}

	public final class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		public TextView textView3;
		public Button btn1;

	}

	class MyAsyncTask1 extends AsyncTask<String, Void, String> {
		private Context context;
		List<HashMap<String, String>> orderList;
		int position;

		public MyAsyncTask1(Context context,
				List<HashMap<String, String>> orderList, int position) {
			this.position = position;
			this.orderList = orderList;
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
						orderList.remove(position);
						notifyDataSetChanged();
						Log.i("deletesuccess", "确认成功!");

					} else if (opCode == 100) {

						Toast.makeText(context, jsonObject.getString("opMess"),
								Toast.LENGTH_LONG).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
