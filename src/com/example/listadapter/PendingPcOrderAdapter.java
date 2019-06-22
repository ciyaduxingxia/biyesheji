package com.example.listadapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.dbtable.PurchaseOrder;
import com.example.dbtable.Supply;
import com.example.outin.pcorderRefreshBroadcastReceiver;
import com.example.wms.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PendingPcOrderAdapter extends BaseAdapter {

	private List<PurchaseOrder> pcorderlist;
	private Context context;

	public PendingPcOrderAdapter(Context mcontext, List<PurchaseOrder> pcorders) {
		context = mcontext;
		pcorderlist = pcorders;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pcorderlist.size();
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
		 * 放到Recycler中的视图，若需要用到其他Layout，则用inflate ()，同一视图用fingViewBy()
		 */
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.adapter_supply, null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		PurchaseOrder order = pcorderlist.get(position);
		TextView pcOrderID = (TextView) view.findViewById(R.id.s_t1);
		TextView itemName = (TextView) view.findViewById(R.id.s_t2);
		TextView date = (TextView) view.findViewById(R.id.s_t3);
		TextView state = (TextView) view.findViewById(R.id.s_t4);

		pcOrderID.setText(order.getPcOrderID());
		itemName.setText(order.getItemName());
		date.setText(new SimpleDateFormat("yyyy-MM-dd").format(order
				.getPcDate()));
		state.setText(order.getPcState());

		return view;
	}

}
