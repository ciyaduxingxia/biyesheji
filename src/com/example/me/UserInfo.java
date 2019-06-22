package com.example.me;

import java.util.List;

import org.litepal.LitePal;

import com.example.activity.LoginActivity;
import com.example.dbtable.User;
import com.example.wms.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfo extends Activity {
	
	private TextView username;
	private TextView usertype;
	private TextView userid;
	private TextView permission;
	private TextView phone;
	
	private ImageView arrow_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_userinfo);
		
		init();
	}

	private void init() {
		username = (TextView)findViewById(R.id.tv_me_username);
		usertype = (TextView)findViewById(R.id.tv_me_usertype);
		userid = (TextView)findViewById(R.id.tv_me_userid);
		permission = (TextView)findViewById(R.id.tv_me_permission);
		phone = (TextView)findViewById(R.id.tv_me_phone);
		arrow_back = (ImageView)findViewById(R.id.iv_userinfo_arrow_back);
		
		User user = LitePal.where("UserName=?",LoginActivity.name).find(User.class).get(0);
		username.setText(user.getUserName());
		usertype.setText(user.getUserType());
		userid.setText(user.getUserID());
		permission.setText(user.getPermission()+"级权限");
		phone.setText(user.getPhone());
		
		arrow_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}	
}
