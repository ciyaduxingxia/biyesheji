package com.example.fragment;

import com.example.activity.LoginActivity;
import com.example.outin.OutinInInboundActivity;
import com.example.outin.OutinInInboundOrderActivity;
import com.example.outin.OutinInPcOrderActivity;
import com.example.outin.OutinInPendingPcOrderActivity;
import com.example.outin.OutinOutboundActivity;
import com.example.outin.OutinOutboundorderActivity;
import com.example.outin.OutinPendingPickOrderActivity;
import com.example.outin.OutinPickorderActivity;
import com.example.wms.R;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class OutFragment extends Fragment implements
		android.view.View.OnClickListener {

	private Context context;

	private View fragment_outin_out;

	private ImageView arrow_pickorder;
	private ImageView arrow_pending_pickorder;
	private ImageView arrow_item_outbound;
	private ImageView arrow_outbound_order;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 得到所在的Activity
		context = getActivity();
		fragment_outin_out = inflater.inflate(R.layout.fragment_outin_out,
				container, false);
		initView();

		return fragment_outin_out;
	}

	private void initView() {
		arrow_pickorder = (ImageView) fragment_outin_out
				.findViewById(R.id.arrow_pickorder);
		arrow_pending_pickorder = (ImageView) fragment_outin_out
				.findViewById(R.id.arrow_pending_pickerorder);
		arrow_item_outbound = (ImageView) fragment_outin_out
				.findViewById(R.id.arrow_itemoutbound);
		arrow_outbound_order = (ImageView) fragment_outin_out
				.findViewById(R.id.arrow_outboundorder);

		arrow_pickorder.setOnClickListener(this);
		arrow_pending_pickorder.setOnClickListener(this);
		arrow_item_outbound.setOnClickListener(this);
		arrow_outbound_order.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.arrow_pickorder:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				// 创建组件，通过组件来响应
				ComponentName component = new ComponentName(context,
						OutinPickorderActivity.class);
				intent.setComponent(component);
				startActivity(intent);
			}
			break;
		case R.id.arrow_pending_pickerorder:
			Intent intent1 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component1 = new ComponentName(context,
					OutinPendingPickOrderActivity.class);
			intent1.setComponent(component1);
			startActivity(intent1);
			break;
		case R.id.arrow_itemoutbound:
			Intent intent2 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component2 = new ComponentName(context,
					OutinOutboundActivity.class);
			intent2.setComponent(component2);
			startActivity(intent2);
			break;
		case R.id.arrow_outboundorder:
			Intent intent3 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component3 = new ComponentName(context,
					OutinOutboundorderActivity.class);
			intent3.setComponent(component3);
			startActivity(intent3);
			break;

		default:
			break;
		}

	}
}
