package com.example.listadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dbtable.InboundOrder;
import com.example.dbtable.OutboundOrder;
import com.example.wms.R;

public class OborderListAdapter extends BaseAdapter {

	private List<OutboundOrder> oborderlist = new ArrayList<OutboundOrder>();
	private Context context;

	public OborderListAdapter(Context mContext, List<OutboundOrder> oborders) {
		oborderlist = oborders;
		context = mContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return oborderlist.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		/**
		 * 对ListView的优化，convertView为空时，创建一个新视图； convertView不为空时，代表它是滚出，
		 * 放到Recycler中的视图，若需要用到其他Layout，则用inflate()，同一视图用fingViewBy()
		 */
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.adapter_query,
					null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		OutboundOrder oborder = oborderlist.get(position);
		TextView ID = (TextView) view.findViewById(R.id.q_t1);
		TextView PickerId = (TextView) view.findViewById(R.id.q_t2);
		TextView ItemName = (TextView) view.findViewById(R.id.q_t3);
		TextView ItemType = (TextView) view.findViewById(R.id.q_t4);
		TextView Count = (TextView) view.findViewById(R.id.q_t5);

		ID.setText(oborder.getObOrderID());
		PickerId.setText(oborder.getPickerID());
		ItemName.setText(oborder.getItemName());
		ItemType.setText(oborder.getItemType());
		Count.setText(oborder.getObCount());
		
		return view;
	}

}
