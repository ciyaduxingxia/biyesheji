package com.example.me;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.activity.LoginActivity;
import com.example.dbtable.PermissionRequest;
import com.example.dbtable.User;
import com.example.listadapter.PermissionSetAdapter;
import com.example.outin.OutinPickorderActivity;
import com.example.wms.R;

public class PermissionSet extends Activity implements OnItemClickListener,
		android.view.View.OnClickListener {

	private TextView message;
	private ListView permissions;
	private ImageView arrow_back;

	private PermissionRequest request;
	private List<PermissionRequest> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meother_permissionset);

		init();
	}

	private void init() {
		message = (TextView) findViewById(R.id.tv_meother_permission_request);
		permissions = (ListView) findViewById(R.id.meother_permissions);
		arrow_back = (ImageView) findViewById(R.id.iv_permissionset_arrow_back);

		list = LitePal.where("state=?", "待审核").find(PermissionRequest.class);

		message.setText("有" + list.size() + "权限申请,请点击处理进行审核");

		permissions.setAdapter(new PermissionSetAdapter(PermissionSet.this,
				list));
		permissions.setOnItemClickListener(this);
		arrow_back.setOnClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		request = list.get(position);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				PermissionSet.this);
		builder.setTitle("处理申请");
		builder.setMessage("是否批准此申请?");
		builder.setPositiveButton("批准", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 添加权限处理单
				com.example.dbtable.PermissionSet set = new com.example.dbtable.PermissionSet();
				set.setSeter(LoginActivity.name);
				set.setDate(new Date());
				set.setRequestid(request.getId() + "");
				set.setSetResult("批准");
				set.save();
				// 更新用户权限
				User user = LitePal.where("UserName=?", request.getRequester())
						.find(User.class).get(0);
				User u = new User();
				u.setPermission("1");
				u.update(user.getId());
				// 更新权限申请单
				PermissionRequest r = new PermissionRequest();
				r.setState("已批准");
				r.update(request.getId());

				list = LitePal.where("state=?", "待审核").find(
						PermissionRequest.class);
				permissions.setAdapter(new PermissionSetAdapter(
						PermissionSet.this, list));

			}
		});
		builder.setNegativeButton("不批准", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 添加权限处理单
				com.example.dbtable.PermissionSet set = new com.example.dbtable.PermissionSet();
				set.setSeter(LoginActivity.name);
				set.setDate(new Date());
				set.setRequestid(request.getId() + "");
				set.setSetResult("未批准");
				// 更新权限申请单
				PermissionRequest r = new PermissionRequest();
				r.setState("未批准");
				r.update(request.getId());

				list = LitePal.where("state=?", "待审核").find(
						PermissionRequest.class);
				permissions.setAdapter(new PermissionSetAdapter(
						PermissionSet.this, list));

			}
		});
		builder.create();
		builder.show();

	}

	@Override
	public void onClick(View v) {
		finish();

	}
}
