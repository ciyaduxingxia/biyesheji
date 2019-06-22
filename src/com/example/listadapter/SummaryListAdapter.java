package com.example.listadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dbtable.Details;
import com.example.dbtable.Summary;
import com.example.wms.R;

public class SummaryListAdapter extends BaseAdapter {

	private List<Summary> summarylist = new ArrayList<Summary>();
	private Context context;

	public SummaryListAdapter(Context mContext, List<Summary> summary) {
		summarylist = summary;
		context = mContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return summarylist.size();
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
		Summary summ = summarylist.get(position);
		TextView ItemID = (TextView) view.findViewById(R.id.q_t1);
		TextView ItemName = (TextView) view.findViewById(R.id.q_t2);
		TextView ItemType = (TextView) view.findViewById(R.id.q_t3);
		TextView incount = (TextView) view.findViewById(R.id.q_t4);
		TextView outcount = (TextView) view.findViewById(R.id.q_t5);

		ItemID.setText(summ.getItemID());
		ItemName.setText(summ.getItemName());
		ItemType.setText(summ.getItemType());
		incount.setText(summ.getIncount());
		outcount.setText(summ.getOutcount());

		return view;
	}

}
