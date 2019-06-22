package com.example.me;

import org.litepal.LitePal;

import com.example.activity.LoginActivity;
import com.example.dbtable.User;
import com.example.wms.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditPassword extends Activity implements OnClickListener {

	private TextView pwd;
	private TextView newpwd;
	private TextView againpwd;

	private Button summit;
	private ImageView arrowback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_editpwd);

		init();
	}

	private void init() {
		pwd = (TextView) findViewById(R.id.tv_me_pwd);
		newpwd = (TextView) findViewById(R.id.tv_me_newpwd);
		againpwd = (TextView) findViewById(R.id.tv_me_againpwd);

		summit = (Button) findViewById(R.id.bt_me_editpwd_submit);
		arrowback = (ImageView) findViewById(R.id.iv_editpwd_arrow_back);

		summit.setOnClickListener(this);
		arrowback.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_me_editpwd_submit:
			if (pwd.getText().toString().equals("")
					|| newpwd.getText().toString().equals("")
					|| againpwd.getText().toString().equals("")) {
				Toast.makeText(EditPassword.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}
			User user = LitePal.where("UserName=?", LoginActivity.name)
					.find(User.class).get(0);
			if (!user.getPassword().equals(pwd.getText().toString())) {
				Toast.makeText(EditPassword.this, "您输入的密码有误，请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!newpwd.getText().toString().equals(againpwd.getText().toString())){
				Toast.makeText(EditPassword.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
				return;
			}
			User u = new User();
			u.setPassword(newpwd.getText().toString());
			u.update(user.getId());
			Toast.makeText(EditPassword.this, "修改成功", Toast.LENGTH_SHORT).show();
			break;

		case R.id.iv_editpwd_arrow_back:
			finish();
			break;

		default:
			break;
		}

	}
}
