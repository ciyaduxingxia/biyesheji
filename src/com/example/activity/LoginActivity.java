package com.example.activity;

import java.util.List;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.Dept;
import com.example.dbtable.Picker;
import com.example.dbtable.User;
import com.example.wms.R;

/**
 * 用户登录界面
 * 
 * @author 肖相杰
 * @version 1.0
 */
public class LoginActivity extends Activity implements OnClickListener,
		OnFocusChangeListener, TextWatcher {

	private EditText LEtLoginUsername;
	private EditText LEtLoginPwd;
	private LinearLayout LLlLoginUsername;
	private ImageView LIvLoginUsernameDel;
	private Button LBtLoginSubmit;
	private LinearLayout LLlLoginPwd;
	private ImageView LIvLoginPwdDel;
	private TextView LTvLoginForgetPwd;
	private Button LBtLoginRegister;

	// 找回密码界面传输的密码
	private String pwd;

	private SQLiteDatabase db;
	
	public static String permission;
	public static String name;
	
//	static{
//		LitePal.deleteAll(User.class);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();

		db = Connector.getDatabase();
	}

	// 初始化视图
	private void initView() {

		// username
		LLlLoginUsername = (LinearLayout) findViewById(R.id.ll_login_username);
		LEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
		LIvLoginUsernameDel = (ImageView) findViewById(R.id.iv_login_username_del);

		// password
		LLlLoginPwd = (LinearLayout) findViewById(R.id.ll_login_pwd);
		LEtLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
		LIvLoginPwdDel = (ImageView) findViewById(R.id.iv_login_pwd_del);

		// 登录、注册
		LBtLoginSubmit = (Button) findViewById(R.id.bt_login_submit);
		LBtLoginRegister = (Button) findViewById(R.id.bt_login_register);

		// 忘记密码
		LTvLoginForgetPwd = (TextView) findViewById(R.id.tv_login_forget_pwd);

		// 注册点击事件
		LTvLoginForgetPwd.setOnClickListener(this);
		LEtLoginUsername.setOnClickListener(this);
		LIvLoginUsernameDel.setOnClickListener(this);
		LBtLoginSubmit.setOnClickListener(this);
		LBtLoginRegister.setOnClickListener(this);
		LEtLoginPwd.setOnClickListener(this);
		LIvLoginPwdDel.setOnClickListener(this);

		// 注册其他事件
		LEtLoginUsername.setOnFocusChangeListener(this);
		LEtLoginUsername.addTextChangedListener(this);
		LEtLoginPwd.setOnFocusChangeListener(this);
		LEtLoginPwd.addTextChangedListener(this);

	}

	// 用户名密码焦点改变
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		int id = v.getId();

		if (id == R.id.et_login_username) {
			if (hasFocus) {
				LLlLoginUsername.setActivated(true);// 当用户名输入框获得焦点时，设置mLlLoginUsername为激活状态。
				LLlLoginPwd.setActivated(false);
			}
		} else {
			if (hasFocus) {
				LLlLoginPwd.setActivated(true);
				LLlLoginUsername.setActivated(false);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.et_login_username:
			// 点击填写用户名编辑文本
			LEtLoginPwd.clearFocus();// 失去焦点
			LEtLoginUsername.setFocusableInTouchMode(true);// 通过触摸获取焦点
			LEtLoginUsername.requestFocus();
			break;

		case R.id.et_login_pwd:
			// 点击填写密码的编辑文本
			LEtLoginUsername.clearFocus();
			LEtLoginPwd.setFocusableInTouchMode(true);
			LEtLoginPwd.requestFocus();
			break;

		case R.id.iv_login_username_del:
			// 清空用户名
			LEtLoginUsername.setText(null);
			break;

		case R.id.iv_login_pwd_del:
			// 清空密码
			LEtLoginPwd.setText(null);
			break;

		case R.id.bt_login_submit:
			// 登录
			loginRequest();
			break;

		case R.id.bt_login_register:
			// 注册
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;

		case R.id.tv_login_forget_pwd:
			// 忘记密码
			startActivityForResult(new Intent(LoginActivity.this,
					ForgetPwdActivity.class), 1);
			break;

		default:
			break;
		}

	}

	private void loginRequest() {
		// 用户名和密码
		String userName = "";
		String userPw = "";

		// 键盘输入的用户名和密码
		String username = LEtLoginUsername.getText().toString();
		String userpw = LEtLoginPwd.getText().toString();

		// 用户名为空
		if (username.equals("")) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 密码为空
		if (userpw.equals("")) {
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// // 数据库操作语句
		// String sql =
		// "select UserName,Password from User_Table where UserName = '"
		// + username + "'";
		//
		// // 用cursor获取查询的结果
		// Cursor cursor = db.rawQuery(sql, null);
		// if (cursor.moveToFirst()) {
		//
		// // 给userName和userPw赋值
		// try {
		// userName = cursor.getString(0);
		// userPw = cursor.getString(1);
		// } catch (Exception e) {
		// userName = "";
		// userPw = "";
		// }
		// } else {
		// Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT)
		// .show();
		// return;
		// }

		// 使用LitePal来查询语句
		List<User> users = LitePal.where("UserName=?", username).find(
				User.class);
		if (users.size() > 0) {
			userName = users.get(0).getUserName();
			userPw = users.get(0).getPassword();
		} else {
			Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 用户名或密码错误
		if (!(username.equals(userName) && userpw.equals(userPw))) {
			Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		permission = users.get(0).getPermission();
		name = username;
		// 用户名密码正确
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("username", username);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	// 接收来自找回密码界面的密码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (1 == requestCode) {
			pwd = data.getStringExtra("password");
			// 将pwd填入密码输入框中
			LEtLoginPwd.setText(pwd);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	// 用户名密码输入事件
	@Override
	public void afterTextChanged(Editable s) {
		String username = LEtLoginUsername.getText().toString();
		String userpw = LEtLoginPwd.getText().toString();

		// 是否显示清除按钮
		if (username.length() > 0) {
			LIvLoginUsernameDel.setVisibility(View.VISIBLE);// 设置可见
		} else {
			LIvLoginUsernameDel.setVisibility(View.INVISIBLE);// 设置不可见
		}
		if (userpw.length() > 0) {
			LIvLoginPwdDel.setVisibility(View.VISIBLE);
		} else {
			LIvLoginPwdDel.setVisibility(View.INVISIBLE);
		}

		// 登录按钮是否可用
		// 如果用户名和密码都没有输入
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpw)) {
			LBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
			LBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
		} else {
			LBtLoginSubmit
					.setBackgroundResource(R.drawable.bg_login_submit_lock);
			LBtLoginSubmit.setTextColor(getResources().getColor(
					R.color.account_lock_font_color));
		}

	}
}
