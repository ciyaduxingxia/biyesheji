package com.example.listadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dbtable.Dept;
import com.example.dbtable.Picker;
import com.example.wms.R;

public class PickerListAdapter extends BaseAdapter {

	private List<Picker> pickerlist;
	private Context context;

	public PickerListAdapter(Context mcontext, List<Picker> picker) {
		context = mcontext;
		pickerlist = picker;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pickerlist.size();
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
			view = LayoutInflater.from(context).inflate(R.layout.adapter_picker,
					null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		Picker picker = pickerlist.get(position);
		TextView pickerID = (TextView) view.findViewById(R.id.p_t1);
		TextView pickerName = (TextView) view.findViewById(R.id.p_t2);
		TextView pickerPhone = (TextView) view.findViewById(R.id.p_t3);
		TextView pickerBelong = (TextView) view.findViewById(R.id.p_t4);

		pickerID.setText(picker.getPickerID());
		pickerName.setText(picker.getPickerName());
		pickerPhone.setText(picker.getPhone());
		pickerBelong.setText(picker.getDeptBelonged());

		return view;
	}

}
