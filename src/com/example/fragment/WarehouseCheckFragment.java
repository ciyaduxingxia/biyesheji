package com.example.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.LitePal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.example.activity.LoginActivity;
import com.example.dbtable.CheckInfo;
import com.example.dbtable.Item;
import com.example.dbtable.ItemInfo;
import com.example.listadapter.CheckListAdapter;
import com.example.outin.OutinPendingPickOrderActivity;
import com.example.outin.pickorderRefreshBroadcastReceiver;
import com.example.wms.R;

public class WarehouseCheckFragment extends Fragment implements
		OnClickListener, OnItemClickListener {
	private Context mContext;
	private View checkFragement;

	private ListView checklist;
	private ScrollView scrollView;

	private Button edit;

	public static List<ItemInfo> iteminfos;
	private ItemInfo iteminfo = new ItemInfo();
	private int pos;

	private TextView tv1;
	private EditText et1;
	
//	static{
//		LitePal.deleteAll(CheckInfo.class);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		checkFragement = inflater.inflate(R.layout.fragment_warehouse_check,
				container, false);
		mContext = WarehouseFragment.mContext;

		init();
		return checkFragement;
	}

	private void init() {
		checklist = (ListView) checkFragement
				.findViewById(R.id.warehouse_check_list);
		scrollView = (ScrollView) checkFragement.findViewById(R.id.scrollview);
		edit = (Button) checkFragement
				.findViewById(R.id.bg_warehouse_check_edit);

		// 赋值
		Cursor cursor = LitePal
				.findBySQL("select itemid,itemname,sum(count) from item group by itemid");
		if (cursor.moveToFirst()) {
			iteminfos = getItemInfos();
			for (int i = 0; i < cursor.getCount(); i++) {
				ItemInfo itemInfo = new ItemInfo(cursor.getString(0),
						cursor.getString(1), cursor.getString(2), "盘查");

				iteminfos.add(itemInfo);
				cursor.moveToNext();
			}
		} 
		checklist.setAdapter(new CheckListAdapter(mContext, iteminfos));
		checklist.setOnItemClickListener(this);
		edit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

//		for (ItemInfo i : iteminfos) {
//			if (!(i.getCount().equals(i.getCheckcount()))) {
//				Item item = new Item();
//				item.setCount(i.getCheckcount());
//				item.update(LitePal.where("ItemID=?", i.getItemid())
//						.find(Item.class).get(0).getId());
//			}
//		}
//
//		Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
		
		StringBuilder message = new StringBuilder();
		StringBuilder number = new StringBuilder();
		for(ItemInfo i : iteminfos){
			message.append(i.getItemname()+":"+i.getCheckcount()+"   ");
			number.append(i.getCheckcount()+",");
		}
		
		CheckInfo info = new CheckInfo();
		info.setOperator(LoginActivity.name);
		info.setDate(new Date());
		info.setState("待处理的盘点信息");
		info.setMessage(message.toString());
		info.setNumber(number.toString());
		info.save();
		
		Toast.makeText(mContext, "已将盘点信息提交至管理员", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		iteminfo = iteminfos.get(position);
		pos = position;

		LayoutInflater factorys = LayoutInflater.from(mContext);
		final View dialogview = factorys.inflate(R.layout.dialog_checklist,
				null);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("填写盘点信息");
		tv1 = (TextView) dialogview.findViewById(R.id.dialog_checklist_tv1);
		et1 = (EditText) dialogview.findViewById(R.id.dialog_checklist_et1);

		tv1.setText(iteminfo.getItemname() + " :");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// 将盘点数据显示在list中
				iteminfos.set(
						pos,
						new ItemInfo(iteminfo.getItemid(), iteminfo
								.getItemname(), iteminfo.getCount(), et1
								.getText().toString()));
				checklist.setAdapter(new CheckListAdapter(mContext, iteminfos));
			}
		});

		builder.setNegativeButton("取消", null);
		builder.create();
		builder.setView(dialogview);
		builder.show();

	}

	private List<ItemInfo> getItemInfos() {
		return new ArrayList<ItemInfo>();
	}

}
