package com.example.fragment;

import java.text.SimpleDateFormat;
import java.util.List;

import org.litepal.LitePal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.CheckInfo;
import com.example.dbtable.Item;
import com.example.dbtable.ItemInfo;
import com.example.wms.R;

public class WarehouseCheck1Fragment extends Fragment implements
		OnClickListener {

	private Context mContext;
	private View check1Fragement;

	private TextView info1;
	private TextView info2;

	private Button check;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		check1Fragement = inflater.inflate(R.layout.fragment_warehouse_check1,
				container, false);
		mContext = WarehouseFragment.mContext;

		init();
		return check1Fragement;
	}

	private void init() {
		info1 = (TextView) check1Fragement
				.findViewById(R.id.tv_warehouse_checkinfo);
		info2 = (TextView) check1Fragement
				.findViewById(R.id.tv_warehouse_checkinfo1);

		info2.setVisibility(View.INVISIBLE);
		List<CheckInfo> infos = LitePal.where("state=?", "待处理的盘点信息").find(
				CheckInfo.class);
		info1.setText("有" + infos.size() + "条来自操作员提交的盘点信息");
		if (infos.size() > 0) {
			info2.setText("操作员:"
					+ infos.get(0).getOperator()
					+ "操作员"
					+ "\n"
					+ "盘点日期:"
					+ new SimpleDateFormat("yyyy-MM-dd").format(infos.get(0)
							.getDate()) + "\n" + "盘点信息:" + "\n"
					+ infos.get(0).getMessage() + "\n" + "请仔细核查盘点信息");
		}

		check = (Button) check1Fragement
				.findViewById(R.id.bg_warehouse_check1_edit);

		info1.setOnClickListener(this);
		check.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_warehouse_checkinfo:
			info2.setVisibility(View.VISIBLE);

			break;

		case R.id.bg_warehouse_check1_edit:
			List<CheckInfo> infos = LitePal.where("state=?", "待处理的盘点信息").find(
					CheckInfo.class);
			CheckInfo info = new CheckInfo();
			info.setState("已处理的盘点信息");
			info.update(infos.get(0).getId());
			
			String[] numbers = infos.get(0).getNumber().split(",");
			List<Item> items = LitePal.where("id>0").find(Item.class);
			for (int i = 0; i < items.size(); i++) {
				Item item = new Item();
				item.setCount(numbers[i]);
				item.update(items.get(i).getId());
			}

			Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}
}
