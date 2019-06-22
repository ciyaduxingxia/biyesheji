package com.example.me;

import java.util.Date;

import org.litepal.LitePal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.LoginActivity;
import com.example.dbtable.User;
import com.example.wms.R;

public class PermissionRequest extends Activity implements OnClickListener {

	private TextView permission;
	private Button request;
	private ImageView arrow_back;

//	static {
//		LitePal.deleteAll(com.example.dbtable.PermissionRequest.class);
//	}
//	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_permissionrequ);

		init();
	}

	private void init() {
		permission = (TextView) findViewById(R.id.tv_me_permissionrequ_permission);
		request = (Button) findViewById(R.id.bt_me_permission_request);
		arrow_back = (ImageView) findViewById(R.id.iv_permissionrequ_arrow_back);

		permission.setText(LitePal.where("UserName=?", LoginActivity.name)
				.find(User.class).get(0).getPermission()+"级权限");

		request.setOnClickListener(this);
		arrow_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_permissionrequ_arrow_back:
			finish();
			break;

		case R.id.bt_me_permission_request:
			if (permission.getText().toString().startsWith("2")) {
				com.example.dbtable.PermissionRequest request = new com.example.dbtable.PermissionRequest();
				request.setRequester(LoginActivity.name);
				request.setDate(new Date());
				request.setState("待审核");
				request.save();
				Toast.makeText(PermissionRequest.this, "您提交的申请正在审核，请耐性等待",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(PermissionRequest.this, "您已经是最高权限",
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}

	}
}
