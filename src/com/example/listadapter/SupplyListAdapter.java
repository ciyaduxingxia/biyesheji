package com.example.listadapter;

import java.util.List;

import org.litepal.LitePal;

import com.example.basic.BasicSupplyActivity;
import com.example.dbtable.Supply;
import com.example.wms.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SupplyListAdapter extends BaseAdapter {
	
	private List<Supply> supplylist;
	private Context context;
	
	public SupplyListAdapter(Context mcontext,List<Supply> supp){
		context = mcontext;
		supplylist = supp;
	}
	
	@Override
	public int getCount() {
		return supplylist.size();
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
			view = LayoutInflater.from(context)
					.inflate(R.layout.adapter_supply, null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		Supply supply = supplylist.get(position);
		TextView supplyID = (TextView) view.findViewById(R.id.s_t1);
		TextView supplyName = (TextView) view.findViewById(R.id.s_t2);
		TextView supplyItemType = (TextView) view.findViewById(R.id.s_t3);
		TextView supplyItemName = (TextView) view
				.findViewById(R.id.s_t4);

		supplyID.setText(supply.getSupplyID());
		supplyName.setText(supply.getSupplyName());
		supplyItemType.setText(supply.getItemType());
		supplyItemName.setText(supply.getItemName());

		return view;
	}

}
