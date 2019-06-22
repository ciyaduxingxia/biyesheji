package com.example.listadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dbtable.Item;
import com.example.dbtable.User;
import com.example.wms.R;

public class UserListAdapter extends BaseAdapter {

	private List<User> userlist;
	private Context context;
	
	public UserListAdapter(Context mcontext,List<User> users){
		context = mcontext;
		userlist = users;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userlist.size();
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
			view = LayoutInflater.from(context)
					.inflate(R.layout.adapter_usermanage, null);
		} else {
			view = convertView;
		}
		// 从supplylist中取出一行数据，position相当于数组下标，可以实现逐行取数据
		User user = userlist.get(position);
		ImageView picture = (ImageView) view.findViewById(R.id.u_t1);
		TextView username = (TextView) view.findViewById(R.id.u_t2);
		ImageView arrow = (ImageView) view.findViewById(R.id.u_t3);

		picture.setImageResource(R.drawable.manager);
		username.setText(user.getUserName());
		arrow.setImageResource(R.drawable.arrow);
		

		return view;
	}

}
