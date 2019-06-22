package com.example.fragment;

import com.example.activity.LoginActivity;
import com.example.outin.OutinInInboundActivity;
import com.example.outin.OutinInInboundOrderActivity;
import com.example.outin.OutinInPcOrderActivity;
import com.example.outin.OutinInPendingPcOrderActivity;
import com.example.wms.R;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class InFragment extends Fragment implements OnClickListener {

	private Context context;

	private View fragment_outin_in;

	private ImageView arrow_pcorder;
	private ImageView arrow_pending_pcorder;
	private ImageView arrow_item_inbound;
	private ImageView arrow_inbound_order;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 得到所在的Activity
		context = getActivity();
		fragment_outin_in = inflater.inflate(R.layout.fragment_outin_in,
				container, false);

		initView();

		return fragment_outin_in;
	}

	private void initView() {
		arrow_pcorder = (ImageView) fragment_outin_in
				.findViewById(R.id.arrow_purchaseorder);
		arrow_pending_pcorder = (ImageView) fragment_outin_in
				.findViewById(R.id.arrow_pending_purchaseorder);
		arrow_item_inbound = (ImageView) fragment_outin_in
				.findViewById(R.id.arrow_iteminbound);
		arrow_inbound_order = (ImageView) fragment_outin_in
				.findViewById(R.id.arrow_inboundorder);

		arrow_pcorder.setOnClickListener(this);
		arrow_pending_pcorder.setOnClickListener(this);
		arrow_item_inbound.setOnClickListener(this);
		arrow_inbound_order.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.arrow_purchaseorder:
			if (LoginActivity.permission.equals("2")) {
				Toast.makeText(context, "您的权限不够，请到'我的->权限申请'申请权限",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				// 创建组件，通过组件来响应
				ComponentName component = new ComponentName(context,
						OutinInPcOrderActivity.class);
				intent.setComponent(component);
				startActivity(intent);
			}
			break;
		case R.id.arrow_pending_purchaseorder:
			Intent intent1 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component1 = new ComponentName(context,
					OutinInPendingPcOrderActivity.class);
			intent1.setComponent(component1);
			startActivity(intent1);
			break;
		case R.id.arrow_iteminbound:
			Intent intent2 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component2 = new ComponentName(context,
					OutinInInboundActivity.class);
			intent2.setComponent(component2);
			startActivity(intent2);
			break;
		case R.id.arrow_inboundorder:
			Intent intent3 = new Intent();
			// 创建组件，通过组件来响应
			ComponentName component3 = new ComponentName(context,
					OutinInInboundOrderActivity.class);
			intent3.setComponent(component3);
			startActivity(intent3);
			break;

		default:
			break;
		}

	}

}
