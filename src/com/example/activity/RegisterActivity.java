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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.dbtable.User;
import com.example.wms.R;

/**
 * 用户注册界面
 * 
 * @author 肖相杰
 * @version 1.0
 */
public class RegisterActivity extends Activity implements OnClickListener,
		OnFocusChangeListener, TextWatcher, OnCheckedChangeListener {

	private ImageButton RIbNavgationBack;
	private LinearLayout RLlRegisterUsername;
	private EditText REtRegisterUsername;
	private ImageView RIvRegisterUsernameDel;
	private LinearLayout RLlRegisterPhone;
	private EditText REtRegisterPhone;
	private ImageView RIvRegisterPhoneDel;
	private LinearLayout RLlRegisterUserid;
	private EditText REtRegisterUserid;
	private ImageView RIvRegisterUseridDel;
	private LinearLayout RLlRegisterPwd;
	private EditText REtRegisterPwd;
	private ImageView RIvRegisterPwdDel;
	private RadioGroup RRgGroup;
	private Button RBtRegisterSubmit;
	// 用户类型
	private String type;

	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();

		db = Connector.getDatabase();

		// help = new SqlHelperDemo(this);
		// db = help.getWritableDatabase();

	}

	// 初始化视图
	private void initView() {
		// 返回按钮
		RIbNavgationBack = (ImageButton) findViewById(R.id.ib_navigation_back);

		// username
		RLlRegisterUsername = (LinearLayout) findViewById(R.id.ll_register_username);
		REtRegisterUsername = (EditText) findViewById(R.id.et_register_username);
		RIvRegisterUsernameDel = (ImageView) findViewById(R.id.iv_register_username_del);

		// phone
		RLlRegisterPhone = (LinearLayout) findViewById(R.id.ll_register_phone);
		REtRegisterPhone = (EditText) findViewById(R.id.et_register_phone);
		RIvRegisterPhoneDel = (ImageView) findViewById(R.id.iv_register_phone_del);

		// userid
		RLlRegisterUserid = (LinearLayout) findViewById(R.id.ll_register_userid);
		REtRegisterUserid = (EditText) findViewById(R.id.et_register_userid);
		RIvRegisterUseridDel = (ImageView) findViewById(R.id.iv_register_userid_del);

		// pwd
		RLlRegisterPwd = (LinearLayout) findViewById(R.id.ll_register_password);
		REtRegisterPwd = (EditText) findViewById(R.id.et_register_password);
		RIvRegisterPwdDel = (ImageView) findViewById(R.id.iv_register_password_del);

		// type
		RRgGroup = (RadioGroup) findViewById(R.id.rg_group);

		// register
		RBtRegisterSubmit = (Button) findViewById(R.id.bt_register_submit);

		// 注册点击事件
		RIbNavgationBack.setOnClickListener(this);
		REtRegisterUsername.setOnClickListener(this);
		RIvRegisterUsernameDel.setOnClickListener(this);
		REtRegisterPhone.setOnClickListener(this);
		RIvRegisterPwdDel.setOnClickListener(this);
		REtRegisterUserid.setOnClickListener(this);
		RIvRegisterUseridDel.setOnClickListener(this);
		REtRegisterPwd.setOnClickListener(this);
		RIvRegisterPwdDel.setOnClickListener(this);
		RBtRegisterSubmit.setOnClickListener(this);

		// 注册其他事件
		REtRegisterUsername.setOnFocusChangeListener(this);
		REtRegisterUsername.addTextChangedListener(this);
		REtRegisterPhone.setOnFocusChangeListener(this);
		REtRegisterPhone.addTextChangedListener(this);
		REtRegisterUserid.setOnFocusChangeListener(this);
		REtRegisterUserid.addTextChangedListener(this);
		REtRegisterPwd.setOnFocusChangeListener(this);
		REtRegisterPwd.addTextChangedListener(this);
		RRgGroup.setOnCheckedChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_navigation_back:
			// 返回
			startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
			finish();
			break;

		case R.id.et_register_username:
			// 点击填写用户名编辑文本
			REtRegisterPwd.clearFocus();// 失去焦点
			REtRegisterPhone.clearFocus();
			REtRegisterUserid.clearFocus();
			REtRegisterUsername.setFocusableInTouchMode(true);// 通过触摸获取焦点
			REtRegisterUsername.requestFocus();
			break;

		case R.id.et_register_password:
			// 点击填写密码的编辑文本
			REtRegisterUsername.clearFocus();
			REtRegisterPhone.clearFocus();
			REtRegisterUserid.clearFocus();
			REtRegisterPwd.setFocusableInTouchMode(true);
			REtRegisterPwd.requestFocus();
			break;

		case R.id.et_register_phone:
			// 点击填写手机号的编辑文本
			REtRegisterUsername.clearFocus();
			REtRegisterPwd.clearFocus();
			REtRegisterUserid.clearFocus();
			REtRegisterPhone.setFocusableInTouchMode(true);
			REtRegisterPhone.requestFocus();
			break;

		case R.id.et_register_userid:
			// 点击填写用户编号编辑文本
			REtRegisterPwd.clearFocus();// 失去焦点
			REtRegisterPhone.clearFocus();
			REtRegisterUsername.clearFocus();
			REtRegisterUserid.setFocusableInTouchMode(true);// 通过触摸获取焦点
			REtRegisterUserid.requestFocus();
			break;

		case R.id.iv_register_username_del:
			// 清空用户名
			REtRegisterUsername.setText(null);
			break;

		case R.id.iv_register_phone_del:
			// 清空手机号
			REtRegisterPhone.setText(null);
			break;

		case R.id.iv_register_userid_del:
			// 清空用户编号
			REtRegisterUserid.setText(null);
			break;

		case R.id.iv_register_password_del:
			// 清空密码
			REtRegisterPwd.setText(null);
			break;

		case R.id.bt_register_submit:
			// 注册
			registerRequest();
			break;

		default:
			break;
		}

	}

	private void registerRequest() {

		// 键盘输入的用户名，手机号，用户编号，密码，用户类别
		String username = REtRegisterUsername.getText().toString();
		String userphone = REtRegisterPhone.getText().toString();
		String userid = REtRegisterUserid.getText().toString();
		String userpwd = REtRegisterPwd.getText().toString();
		String usertype = type;
		String permission = "1";
		try {
			if (usertype.equals("操作员")) {
				permission = "2";
			}
		} catch (Exception e) {
			Toast.makeText(RegisterActivity.this, "没有选择用户类型",
					Toast.LENGTH_SHORT).show();
		}

		// 用户名为空
		if (username.equals("")) {
			Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 手机号为空
		if (userphone.equals("")) {
			Toast.makeText(RegisterActivity.this, "用户编号不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 用户编号为空
		if (userid.equals("")) {
			Toast.makeText(RegisterActivity.this, "用户编号不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// 密码为空
		if (userpwd.equals("")) {
			Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (!userid.startsWith("g") || userid.startsWith("c")) {
			Toast.makeText(RegisterActivity.this, "您不是本公司工作人员", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// Cursor cursor =
		// db.rawQuery("select * from User_Table where UserID = '"
		// + userphone + "'", null);
		// if (cursor.moveToFirst()) {
		// Toast.makeText(RegisterActivity.this, "此用户已存在", Toast.LENGTH_SHORT)
		// .show();
		// return;
		// }
		// 插入数据
		// if (db.isOpen()) {
		// String[] insertValue = { userphone, username, userpwd, usertype,
		// permission };
		// db.execSQL("insert into User_Table values(?,?,?,?,?)", insertValue);
		// }
		// 使用LitePal来注册
		List<User> users = LitePal.where("UserID=?", userid).find(User.class);
		if (users.size() > 0) {
			Toast.makeText(RegisterActivity.this, "此用户已存在", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 储存数据
		User user = new User();
		user.setUserName(username);
		user.setUserID(userid);
		user.setPhone(userphone);
		user.setPassword(userpwd);
		user.setUserType(usertype);
		user.setPermission(permission);
		user.save();

		Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
				.show();

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

	// 用户密码手机号输入事件
	public void afterTextChanged(Editable s) {
		String username = REtRegisterUsername.getText().toString();
		String userid = REtRegisterUserid.getText().toString();
		String userphone = REtRegisterPhone.getText().toString();
		String userpw = REtRegisterPwd.getText().toString();

		// 是否显示清除按钮
		if (username.length() > 0) {
			RIvRegisterUsernameDel.setVisibility(View.VISIBLE);// 设置可见
		} else {
			RIvRegisterUsernameDel.setVisibility(View.INVISIBLE);// 设置不可见
		}
		if (userphone.length() > 0) {
			RIvRegisterPhoneDel.setVisibility(View.VISIBLE);// 设置可见
		} else {
			RIvRegisterPhoneDel.setVisibility(View.INVISIBLE);// 设置不可见
		}
		if (userid.length() > 0) {
			RIvRegisterUseridDel.setVisibility(View.VISIBLE);// 设置可见
		} else {
			RIvRegisterUseridDel.setVisibility(View.INVISIBLE);// 设置不可见
		}
		if (userpw.length() > 0) {
			RIvRegisterPwdDel.setVisibility(View.VISIBLE);
		} else {
			RIvRegisterPwdDel.setVisibility(View.INVISIBLE);
		}

		// 注册按钮是否可用
		// 如果用户名和密码和手机号都没有输入
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpw)
				&& !TextUtils.isEmpty(userphone)) {
			RBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
			RBtRegisterSubmit.setTextColor(getResources().getColor(
					R.color.white));
		} else {
			RBtRegisterSubmit
					.setBackgroundResource(R.drawable.bg_login_submit_lock);
			RBtRegisterSubmit.setTextColor(getResources().getColor(
					R.color.account_lock_font_color));
		}

	}

	// 焦点改变
	public void onFocusChange(View v, boolean hasFocus) {
		int id = v.getId();

		if (id == R.id.et_register_username) {
			if (hasFocus) {
				RLlRegisterUsername.setActivated(true);// 当用户名输入框获得焦点时，设置RLlRegisterUsername为激活状态。
				RLlRegisterPhone.setActivated(false);
				RLlRegisterUserid.setActivated(false);
				RLlRegisterPwd.setActivated(false);
			}
		} else if (id == R.id.et_register_phone) {
			if (hasFocus) {
				RLlRegisterPhone.setActivated(true);
				RLlRegisterUserid.setActivated(false);
				RLlRegisterUsername.setActivated(false);
				RLlRegisterPwd.setActivated(false);
			}
		} else if (id == R.id.et_register_userid) {
			if (hasFocus) {
				RLlRegisterUserid.setActivated(true);
				RLlRegisterUsername.setActivated(false);
				RLlRegisterPhone.setActivated(false);
				RLlRegisterPwd.setActivated(false);
			}
		} else {
			if (hasFocus) {
				RLlRegisterPwd.setActivated(true);
				RLlRegisterUserid.setActivated(false);
				RLlRegisterPhone.setActivated(false);
				RLlRegisterUsername.setActivated(false);
			}
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int radioButtonId = RRgGroup.getCheckedRadioButtonId();
		RadioButton btn = (RadioButton) findViewById(radioButtonId);
		type = btn.getText().toString();

	}
}
