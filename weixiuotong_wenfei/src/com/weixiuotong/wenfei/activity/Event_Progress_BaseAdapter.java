package com.weixiuotong.wenfei.activity;

import java.util.HashMap;
import java.util.List;

import com.weixiuotong.wenfei.activity.Wait_Sure_BaseAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Event_Progress_BaseAdapter extends BaseAdapter {
	Context context;
	List<HashMap<String, String>> orderList;
	private LayoutInflater inflater;
	

	public Event_Progress_BaseAdapter(Context context,
			List<HashMap<String, String>> orderList) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.orderList = orderList;
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (view == null) {
//			
//				if(orderList.get(position).get("textView1").equals("系统")||orderList.get(position).get("textView1").equals("维修员")){
//					view = inflater.inflate(
//							R.layout.event_progress_list_system, null);
//					
//					
//				}else{
//					view = inflater.inflate(
//							R.layout.event_progress_list_user, null);
//				}
//				
//				
//				
//
//			
				
				if(orderList.get(position).get("textView1").equals("系统")||orderList.get(position).get("textView1").equals("维修员")){
					view = inflater.inflate(
							R.layout.event_progress_list_system1, null);
					
					
				}else{
					view = inflater.inflate(
							R.layout.event_progress_list_user1, null);
				}
				
			
			
			
			holder.textView1 = (TextView) view.findViewById(R.id.textView1);
			holder.textView2 = (TextView) view.findViewById(R.id.textView2);
			holder.textView3 = (TextView) view.findViewById(R.id.textView3);
			holder.textView1.setText(orderList.get(position).get("textView1"));
			holder.textView2.setText(orderList.get(position).get("textView2"));
			holder.textView3.setText(orderList.get(position).get("textView3"));
			//view.setTag(holder);

		} 
//			else {
//			//holder = (ViewHolder) view.getTag();
//		}
		
		return view;
	}
	public final class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		public TextView textView3;
		

	}

}
