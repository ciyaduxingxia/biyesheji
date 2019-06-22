package com.example.listadapter;

import java.util.List;

import com.example.dbtable.Item;
import com.example.dbtable.ItemInfo;
import com.example.wms.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckListAdapter extends BaseAdapter {

	private List<ItemInfo> iteminfolist;
	private Context context;

	public CheckListAdapter(Context mcontext, List<ItemInfo> iteminfos) {
		context = mcontext;
		iteminfolist = iteminfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return iteminfolist.size();
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
			view = LayoutInflater.from(context).inflate(R.layout.adapter_supply,
					null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		ItemInfo iteminfo = iteminfolist.get(position);
		TextView itemID = (TextView) view.findViewById(R.id.s_t1);
		TextView itemName = (TextView) view.findViewById(R.id.s_t2);
		TextView itemCount = (TextView) view.findViewById(R.id.s_t3);
		TextView itemCheckCount = (TextView) view.findViewById(R.id.s_t4);

		itemID.setText(iteminfo.getItemid());
		itemName.setText(iteminfo.getItemname());
		itemCount.setText(iteminfo.getCount());
		itemCheckCount.setText(iteminfo.getCheckcount());

		return view;
	}

}
