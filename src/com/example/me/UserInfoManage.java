package com.example.me;

import org.litepal.LitePal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.User;
import com.example.wms.R;

public class UserInfoManage extends Activity implements OnClickListener {

	private TextView username;
	private TextView userid;
	private TextView usertype;
	private TextView permission;
	private TextView phone;

	private ImageView arrow_back;
	private Button deluser;
	private Button edituser;
	
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meother_userinfo);

		init();
	}

	private void init() {
		username = (TextView) findViewById(R.id.tv_meother_username);
		userid = (TextView) findViewById(R.id.tv_meother_userid);
		usertype = (TextView) findViewById(R.id.tv_meother_usertype);
		permission = (TextView) findViewById(R.id.tv_meother_permission);
		phone = (TextView) findViewById(R.id.tv_meother_phone);

		arrow_back = (ImageView) findViewById(R.id.iv_meother_userinfo_arrow_back);
		deluser = (Button) findViewById(R.id.bt_meother_deluser_submit);
		edituser = (Button) findViewById(R.id.bt_meother_edituser_submit);

		String userId = getIntent().getStringExtra("userid");
		user = LitePal.where("UserID=?", userId).find(User.class).get(0);

		username.setText(user.getUserName());
		userid.setText(user.getUserID());
		permission.setText(user.getPermission());
		usertype.setText(user.getUserType());
		phone.setText(user.getPhone());
		
		arrow_back.setOnClickListener(this);
		deluser.setOnClickListener(this);
		edituser.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_meother_userinfo_arrow_back:
			finish();
			break;

		case R.id.bt_meother_deluser_submit:
			LitePal.delete(User.class, user.getId());
			Toast.makeText(UserInfoManage.this, "删除成功", Toast.LENGTH_SHORT).show();
			finish();
			break;

		case R.id.bt_meother_edituser_submit:
			Intent intent = new Intent(UserInfoManage.this,AddUser.class);
			intent.putExtra("state", "edit");
			intent.putExtra("userid", userid.getText().toString());
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}
