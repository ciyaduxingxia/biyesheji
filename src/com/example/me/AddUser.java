package com.example.me;

import java.util.ArrayList;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.User;
import com.example.wms.R;

public class AddUser extends Activity implements OnClickListener {

	private EditText username;
	private EditText userpwd;
	private EditText usertype;
	private EditText userid;
	private EditText permission;
	private EditText phone;

	private TextView edit_add;

	private Button summit;
	private ImageView arrow_back;

	private User user;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meother_adduser);

		init();
	}

	private void init() {
		username = (EditText) findViewById(R.id.tv_meother_adduser_username);
		userpwd = (EditText) findViewById(R.id.tv_meother_adduser_userpwd);
		usertype = (EditText) findViewById(R.id.tv_meother_adduser_usertype);
		userid = (EditText) findViewById(R.id.tv_meother_adduser_userid);
		permission = (EditText) findViewById(R.id.tv_meother_adduser_permission);
		phone = (EditText) findViewById(R.id.tv_meother_adduser_phone);
		edit_add = (TextView) findViewById(R.id.adduser_edituser);

		if (getIntent().getStringExtra("state").equals("edit")) {
			edit_add.setText("修改用户信息");
			user = LitePal
					.where("UserID=?", getIntent().getStringExtra("userid"))
					.find(User.class).get(0);
			username.setText(user.getUserName());
			userpwd.setText(user.getPassword());
			usertype.setText(user.getUserType());
			userid.setText(user.getUserID());
			permission.setText(user.getPermission());
			phone.setText(user.getPhone());
		}

		summit = (Button) findViewById(R.id.bt_meother_adduser_submit);
		arrow_back = (ImageView) findViewById(R.id.iv_adduser_arrow_back);

		arrow_back.setOnClickListener(this);
		summit.setOnClickListener(this);
		usertype.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_meother_adduser_submit:
			if (username.getText().toString().equals("")
					|| userpwd.getText().toString().equals("")
					|| usertype.getText().toString().equals("")
					|| userid.getText().toString().equals("")
					|| permission.getText().toString().equals("")
					|| phone.getText().toString().equals("")) {
				Toast.makeText(AddUser.this, "请填写完整信息", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (getIntent().getStringExtra("state").equals("edit")) {
				User use = new User();
				use.setUserID(userid.getText().toString());
				use.setUserName(username.getText().toString());
				use.setUserType(usertype.getText().toString());
				use.setPassword(userpwd.getText().toString());
				use.setPermission(permission.getText().toString());
				use.setPhone(phone.getText().toString());
				use.update(user.getId());
				Toast.makeText(AddUser.this, "修改成功", Toast.LENGTH_SHORT).show();
			} else {
				User user = new User();
				user.setUserID(userid.getText().toString());
				user.setUserName(username.getText().toString());
				user.setUserType(usertype.getText().toString());
				user.setPassword(userpwd.getText().toString());
				user.setPermission(permission.getText().toString());
				user.setPhone(phone.getText().toString());
				user.save();
				Toast.makeText(AddUser.this, "添加成功", Toast.LENGTH_SHORT).show();
			}
			finish();
			break;

		case R.id.iv_adduser_arrow_back:
			finish();
			break;

		case R.id.tv_meother_adduser_usertype:
			final String[] types = { "仓库管理员", "操作员" };

			AlertDialog.Builder builder = new AlertDialog.Builder(this, 0);
			builder.setTitle("请选择采购物品类型");
			builder.setSingleChoiceItems(types, -1,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							position = which;
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							usertype.setText(types[position]);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;

		default:
			break;
		}

	}
}
