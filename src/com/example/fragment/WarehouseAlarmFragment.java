package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.litepal.LitePal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.LoginActivity;
import com.example.dbtable.ItemInfo;
import com.example.listadapter.CheckListAdapter;
import com.example.outin.OutinInPcOrderActivity;
import com.example.outin.OutinPickorderActivity;
import com.example.wms.R;

public class WarehouseAlarmFragment extends Fragment implements
		OnItemClickListener {

	private TextView alarm_message;
	private ListView alarmlist;

	private Context mContext;
	private View alarmFragment;

	private List<ItemInfo> iteminfos;
	private ItemInfo iteminfo = new ItemInfo();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		alarmFragment = inflater.inflate(R.layout.fragment_warehouse_alarm,
				container, false);
		mContext = WarehouseFragment.mContext;

		init();

		return alarmFragment;
	}

	private void init() {
		alarm_message = (TextView) alarmFragment
				.findViewById(R.id.warehouse_alarm_message);
		alarmlist = (ListView) alarmFragment
				.findViewById(R.id.warehouse_alarm_list);

		// 赋值
		Cursor cursor = LitePal
				.findBySQL("select itemid,itemname,upperlimit,lowerlimit,sum(count) from item group by itemid");
		if (cursor.moveToFirst()) {
			iteminfos = getItemInfos();
			for (int i = 0; i < cursor.getCount(); i++) {
				// 如果库存溢出或小于下限
				if (Integer.parseInt(cursor.getString(4)) > Integer
						.parseInt(cursor.getString(2))) {
					ItemInfo iteminfo = new ItemInfo(cursor.getString(0),
							cursor.getString(1), cursor.getString(4), "超出上限");
					iteminfos.add(iteminfo);
					cursor.moveToNext();
				} else if (Integer.parseInt(cursor.getString(4)) < Integer
						.parseInt(cursor.getString(3))) {
					ItemInfo iteminfo = new ItemInfo(cursor.getString(0),
							cursor.getString(1), cursor.getString(4), "低于下限");
					iteminfos.add(iteminfo);
					cursor.moveToNext();
				} else {
					cursor.moveToNext();
				}
			}
		} 
		alarm_message.setText("有" + iteminfos.size() + "件货物超出上限/低于下限");

		alarmlist.setAdapter(new CheckListAdapter(mContext, iteminfos));
		alarmlist.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (LoginActivity.permission.equals("1")) {
			if (iteminfos.get(position).getCheckcount().equals("超出上限")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("跳转页面提示");
				builder.setMessage("是否跳转到取货单处理界面");
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(mContext, "即将跳转到取货单处理界面",
								Toast.LENGTH_SHORT).show();
						// 延迟三秒
						new Timer().schedule(new TimerTask() {

							@Override
							public void run() {
								startActivity(new Intent(mContext,
										OutinPickorderActivity.class));
								// 只延迟一次
								this.cancel();

							}
						}, 3000);

					}
				});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();

			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("跳转页面提示");
				builder.setMessage("是否跳转到采购单处理界面");
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(mContext, "即将跳转到采购单处理界面",
								Toast.LENGTH_SHORT).show();
						// 延迟三秒
						new Timer().schedule(new TimerTask() {

							@Override
							public void run() {
								startActivity(new Intent(mContext,
										OutinInPcOrderActivity.class));
								// 只延迟一次
								this.cancel();

							}
						}, 3000);

					}
				});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();
			}
		}else{
			Toast.makeText(mContext, "请让管理员来处理", Toast.LENGTH_SHORT).show();
		}

	}

	private List<ItemInfo> getItemInfos() {
		return new ArrayList<ItemInfo>();
	}
}
