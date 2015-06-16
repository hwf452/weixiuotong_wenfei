package com.weixiuotong.wenfei.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Event_progress_Activity extends Activity implements OnClickListener {
	private Button btn1,btn2;
	private ListView listView;
	private EditText et1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_progress_activity);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		et1=(EditText)findViewById(R.id.editText1);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		listView=(ListView)findViewById(R.id.listView1);
		initView();
				
	}


	private void initView() {
		List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("textView1", "系统");
			hashMap.put("textView2", "小蜜蜂向维修员发送了维修请求!");
			hashMap.put("textView3", "2012/3/3 11:23:32");
			orderList.add(hashMap);
			HashMap<String, String> hashMap1 = new HashMap<String, String>();
			hashMap1.put("textView1", "维修员");
			hashMap1.put("textView2", "小蜜蜂，我已经完成好多天了，怎么还没确认完成呢!");
			hashMap1.put("textView3", "2012/3/3 12:23:32");
			orderList.add(hashMap1);
			HashMap<String, String> hashMap2 = new HashMap<String, String>();
			hashMap2.put("textView1", "小蜜蜂");
			hashMap2.put("textView2", "不好意思，之前出差了!");
			hashMap2.put("textView3", "2012/3/3 12:23:32");
			
			
			
			orderList.add(hashMap2);

		}
		
		Event_Progress_BaseAdapter event_Progress_BaseAdapter=new Event_Progress_BaseAdapter(this,orderList);
		

//		SimpleAdapter simpleAdapter = new SimpleAdapter(this, orderList,
//				R.layout.event_progress_list_item, new String[] { "textView1",
//						"textView2", "textView3"}, new int[] {
//						R.id.textView1, R.id.textView2, R.id.textView3});

		listView.setAdapter(event_Progress_BaseAdapter);

		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left); 
			break;

		case R.id.button2:
			
			
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

}
