package com.example.me;

import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dbtable.User;
import com.example.listadapter.UserListAdapter;
import com.example.wms.R;

public class UserManage extends Activity implements OnClickListener,
		OnItemClickListener {

	private TextView adduser;
	private ImageView arrow_back;
	private ListView userlist;

	private List<User> users;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meother_usermanage);

		init();
	}

	private void init() {
		adduser = (TextView) findViewById(R.id.tv_usermanage_adduser);
		arrow_back = (ImageView) findViewById(R.id.iv_usermanage_arrow_back);
		userlist = (ListView) findViewById(R.id.meother_usermanage);

		adduser.setOnClickListener(this);
		arrow_back.setOnClickListener(this);

		users = LitePal.where("id>?", "0").find(User.class);
		userlist.setAdapter(new UserListAdapter(UserManage.this, users));
		userlist.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		user = users.get(position);
		Intent intent = new Intent(UserManage.this,UserInfoManage.class);
		intent.putExtra("userid", user.getUserID());
		startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_usermanage_adduser:
			Intent intent = new Intent(UserManage.this,AddUser.class);
			intent.putExtra("state", "add");
			startActivity(intent);
			break;

		case R.id.iv_usermanage_arrow_back:
			finish();
			break;

		default:
			break;
		}

	}
}
