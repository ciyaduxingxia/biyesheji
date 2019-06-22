package com.example.listadapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dbtable.ItemInfo;
import com.example.dbtable.PermissionRequest;
import com.example.wms.R;

public class PermissionSetAdapter extends BaseAdapter {
	
	private List<PermissionRequest> list = new ArrayList<PermissionRequest>();
	private Context context;
	
	public PermissionSetAdapter(Context mcontext,List<PermissionRequest> requests){
		context = mcontext;
		list = requests;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 为ListView设置一个适配器 getView()为每一行设置一个条目
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		/**
		 * 对ListView的优化，convertView为空时，创建一个新视图； convertView不为空时，代表它是滚出，
		 * 放到Recycler中的视图，若需要用到其他Layout，则用inflate()，同一视图用fingViewBy()
		 */
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.adapter_dept,
					null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		PermissionRequest request = list.get(position);
		TextView requester = (TextView) view.findViewById(R.id.d_t1);
		TextView date = (TextView) view.findViewById(R.id.d_t2);
		TextView excute = (TextView) view.findViewById(R.id.d_t3);

		requester.setText(request.getRequester());
		date.setText(new SimpleDateFormat("yyyy-MM-dd").format(request
				.getDate()));
		excute.setText("处理");

		return view;
	}

}
