package com.weixiuotong.wenfei.activity;


import java.util.List;
import java.util.Map;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;


public class Wait_Sure_Adapter extends SimpleAdapter {
	Context context;
	List<? extends Map<String, ?>> data;
	public Wait_Sure_Adapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,int[] to) {
		super(context, data, resource, from, to);
		this.context=context;
		this.data=data;
	}

	

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.my_repair_list_wait_sure, null);
			
			viewHolder.btnSure = (Button) view.findViewById(R.id.button1);
			view.setTag(viewHolder);
			} else {
			viewHolder = (ViewHolder) view.getTag();
			}
		viewHolder.btnSure.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
			data.remove(position);
			notifyDataSetChanged();
			}
			});
			return view;
		
		
	}
	
	final static class ViewHolder {
		
		Button btnSure;
		}

}
