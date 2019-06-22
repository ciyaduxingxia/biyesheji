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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.User;
import com.example.wms.R;

/**
 * 找回密码界面
 * 
 * @author 肖相杰
 * @version 1.0
 */
public class ForgetPwdActivity extends Activity implements OnClickListener,
		OnFocusChangeListener, TextWatcher {

	private ImageButton FIbNavgationBack;
	private LinearLayout FLlRetrievePhone;
	private EditText FEtRetrievePhone;
	private ImageView FIvRetrievePhoneDel;
	private LinearLayout FLlRetrievePwd;
	private EditText FEtRetrievePwd;
	private TextView FTvtRetrievePwdCall;
	private Button FBtRetrieveSubmit;
	
    private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);

		initView();
		
		db = Connector.getDatabase();
//		//得到可操作的数据库
//        help = new SqlHelperDemo(this);
//        db = help.getReadableDatabase();
		
	}

	private void initView() {
		// 返回按钮
		FIbNavgationBack = (ImageButton) findViewById(R.id.ib_navigation_back);

		// phone
		FLlRetrievePhone = (LinearLayout) findViewById(R.id.ll_retrieve_tel);
		FEtRetrievePhone = (EditText) findViewById(R.id.et_retrieve_tel);
		FIvRetrievePhoneDel = (ImageView) findViewById(R.id.iv_retrieve_tel_del);

		// pwd
		FLlRetrievePwd = (LinearLayout) findViewById(R.id.ll_retrieve_Pwd);
		FEtRetrievePwd = (EditText) findViewById(R.id.et_retrieve_code_input);
		FTvtRetrievePwdCall = (TextView) findViewById(R.id.tv_retrieve_pwd_call);

		// submit
		FBtRetrieveSubmit = (Button) findViewById(R.id.bt_retrieve_submit);

		// 注册点击事件
		FIbNavgationBack.setOnClickListener(this);
		FEtRetrievePhone.setOnClickListener(this);
		FIvRetrievePhoneDel.setOnClickListener(this);
		FEtRetrievePwd.setOnClickListener(this);
		FTvtRetrievePwdCall.setOnClickListener(this);
		FBtRetrieveSubmit.setOnClickListener(this);

		// 注册其事件
		FEtRetrievePhone.setOnFocusChangeListener(this);
		FEtRetrievePhone.addTextChangedListener(this);
		FEtRetrievePwd.setOnFocusChangeListener(this);
		FEtRetrievePwd.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_navigation_back:
			startActivity(new Intent(ForgetPwdActivity.this,
					LoginActivity.class));
			finish();
			break;

		case R.id.et_retrieve_tel:
			// 点击填写手机号
			FEtRetrievePwd.clearFocus();// 失去焦点
			FEtRetrievePhone.setFocusableInTouchMode(true);// 通过触摸获取焦点
			FEtRetrievePhone.requestFocus();
			break;

		case R.id.et_retrieve_code_input:
			// 点击填写密码的编辑文本
			FEtRetrievePhone.clearFocus();
			FEtRetrievePwd.setFocusableInTouchMode(true);
			FEtRetrievePwd.requestFocus();
			break;

		case R.id.iv_retrieve_tel_del:
			// 清空手机号
			FEtRetrievePhone.setText(null);
			break;

		case R.id.tv_retrieve_pwd_call:
			// 查找密码的信息
			if (FEtRetrievePhone.getText().toString().equals("")) {
				Toast.makeText(ForgetPwdActivity.this, "用户编号不能为空", Toast.LENGTH_SHORT).show();
			} else {
				String phone = FEtRetrievePhone.getText().toString();
				//查找密码的sql语句
//				String sql = "select Password from User_Table where UserID = '"+phone+"'";
//				//赋值
//				Cursor cursor = db.rawQuery(sql, null);
//				cursor.moveToFirst();
//				FEtRetrievePwd.setText(cursor.getString(0));
				List<User> users = LitePal.where("UserID=?",phone).find(User.class);
				if(users.size()>0){
					FEtRetrievePwd.setText(users.get(0).getPassword());
				}else{
					Toast.makeText(ForgetPwdActivity.this, "用户名错误", Toast.LENGTH_SHORT)
							.show();
					return;
				}
			}
			break;

		case R.id.bt_retrieve_submit:
			// 点击确定键
			if (FEtRetrievePhone.getText().toString().equals("")) {
				Toast.makeText(ForgetPwdActivity.this, "请输入用户编号", Toast.LENGTH_SHORT).show();
			} else if (FEtRetrievePwd.getText().toString().equals("")) {
				Toast.makeText(ForgetPwdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = getIntent();
				intent.putExtra("password", FEtRetrievePwd.getText().toString());
				setResult(1, intent);
				finish();
			}
			break;

		default:
			break;
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

	// 手机号输入事件
	@Override
	public void afterTextChanged(Editable s) {
		String phone = FEtRetrievePhone.getText().toString();
		String pwd = FEtRetrievePwd.getText().toString();

		// 是否显示清除按钮
		if (phone.length() > 0) {
			FIvRetrievePhoneDel.setVisibility(View.VISIBLE);// 设置可见
		} else {
			FIvRetrievePhoneDel.setVisibility(View.INVISIBLE);// 设置不可见
		}

		// 确定按钮是否可用
		// 如果密码和手机号都没有输入
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
			FBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit);
			FBtRetrieveSubmit.setTextColor(getResources().getColor(
					R.color.white));
		} else {
			FBtRetrieveSubmit
					.setBackgroundResource(R.drawable.bg_login_submit_lock);
			FBtRetrieveSubmit.setTextColor(getResources().getColor(
					R.color.account_lock_font_color));
		}

	}

	// 焦点改变
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		int id = v.getId();

		if (id == R.id.et_retrieve_tel) {
			if (hasFocus) {
				FLlRetrievePhone.setActivated(true);
				FLlRetrievePwd.setActivated(false);
			}
		} else {
			if (hasFocus) {
				FLlRetrievePwd.setActivated(true);
				FLlRetrievePhone.setActivated(false);
			}
		}

	}
}
