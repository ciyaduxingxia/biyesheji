package com.example.listadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dbtable.Dept;
import com.example.dbtable.Supply;
import com.example.wms.R;

public class DeptListAdapter extends BaseAdapter {

	private List<Dept> deptlist;
	private Context context;

	public DeptListAdapter(Context mcontext, List<Dept> dept) {
		context = mcontext;
		deptlist = dept;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return deptlist.size();
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
		Dept dept = deptlist.get(position);
		TextView deptID = (TextView) view.findViewById(R.id.d_t1);
		TextView deptName = (TextView) view.findViewById(R.id.d_t2);
		TextView deptPhone = (TextView) view.findViewById(R.id.d_t3);

		deptID.setText(dept.getDeptID());
		deptName.setText(dept.getDeptName());
		deptPhone.setText(dept.getPhone());

		return view;
	}

}
