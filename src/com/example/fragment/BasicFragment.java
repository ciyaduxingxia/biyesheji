package com.example.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.activity.LoginActivity;
import com.example.basic.BasicDeptActivity;
import com.example.basic.BasicItemActivity;
import com.example.basic.BasicPickerActivity;
import com.example.basic.BasicSupplyActivity;
import com.example.wms.R;

public class BasicFragment extends Fragment implements OnClickListener {

	private Context context;

	private View fragment_basic;

	private ImageView arrow_supply;
	private ImageView arrow_dept;
	private ImageView arrow_picker;
	private ImageView arrow_item;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 得到所在的Activity
		context = getActivity();
		fragment_basic = inflater.inflate(R.layout.fragment_basic, container,
				false);
		initView();
		return fragment_basic;
	}

	private void initView() {
		// 初始化
		arrow_supply = (ImageView) fragment_basic
				.findViewById(R.id.arrow_supply);
		arrow_dept = (ImageView) fragment_basic.findViewById(R.id.arrow_dept);
		arrow_picker = (ImageView) fragment_basic
				.findViewById(R.id.arrow_picker);
		arrow_item = (ImageView) fragment_basic.findViewById(R.id.arrow_item);

		// 绑定事件
		arrow_supply.setOnClickListener(this);
		arrow_dept.setOnClickListener(this);
		arrow_picker.setOnClickListener(this);
		arrow_item.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.arrow_supply:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(context, BasicSupplyActivity.class);
				startActivity(intent);
			}
			break;

		case R.id.arrow_dept:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(context, BasicDeptActivity.class);
				startActivity(intent);
			}
			break;

		case R.id.arrow_picker:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(context, BasicPickerActivity.class);
				startActivity(intent);
			}
			break;

		case R.id.arrow_item:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(context, BasicItemActivity.class);
				startActivity(intent);
			}
			break;

		default:
			break;
		}

	}

}
