package com.weixiuotong.wenfei.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Remider_activity extends Activity {

	private ListViewExt listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remider_activity);
		listView = (ListViewExt) findViewById(R.id.listViewExt);
		initView();
		
	}

	private void initView() {
		List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 12; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("textView1", "我的冰箱坏了!");

			orderList.add(hashMap);

		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, orderList,
				R.layout.remider_list_item, new String[] { "textView1" },
				new int[] { R.id.textView1 });

		listView.setAdapter(simpleAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent=new Intent(Remider_activity.this,Event_progress_Activity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});

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
									Remider_activity.this.finish();

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

	void toOtherActivity() {

		Intent intent = new Intent(Remider_activity.this,
				Event_progress_Activity.class);

		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

}
