package com.example.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.litepal.LitePal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dbtable.Item;
import com.example.dbtable.PickOrder;
import com.example.dbtable.Picker;
import com.example.dbtable.User;
import com.example.outin.OutinInPcOrderActivity;
import com.example.wms.R;

public class PickorderFragment extends Fragment implements OnClickListener,
		OnItemSelectedListener {

	private Context context;

	private View fragment_pickorder;

	private EditText pickorderID;
	private Spinner managerID;
	private EditText itemID;
	private Spinner pickerID;
	private EditText itemName;
	private EditText count;
	private EditText date;

	private Button summit;

	private List<String> itemids = new ArrayList<String>();

	private String managerid;
	private String pickerid;

	// static{
	// LitePal.deleteAll(PickOrder.class);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 得到所在的Activity
		context = getActivity();
		fragment_pickorder = inflater.inflate(
				R.layout.fragment_outin_out_pickorder, container, false);

		init();

		return fragment_pickorder;
	}

	private void init() {
		pickorderID = (EditText) fragment_pickorder
				.findViewById(R.id.et_out_pickorder_pickorderID);
		managerID = (Spinner) fragment_pickorder
				.findViewById(R.id.sp_out_pickorder_managerID);
		itemID = (EditText) fragment_pickorder
				.findViewById(R.id.sp_out_pickorder_itemID);
		pickerID = (Spinner) fragment_pickorder
				.findViewById(R.id.sp_out_pickorder_pickerID);
		itemName = (EditText) fragment_pickorder
				.findViewById(R.id.et_out_pickorder_itemName);
		count = (EditText) fragment_pickorder
				.findViewById(R.id.et_out_pickorder_count);
		date = (EditText) fragment_pickorder
				.findViewById(R.id.et_out_pickorder_date);
		summit = (Button) fragment_pickorder
				.findViewById(R.id.bg_out_pickorder_summit);

		// 为managerID创建列表
		managerIDSpinnerInit();
		// 为pickerID创建列表
		pickerIDSpinnerInit();

		// 绑定事件
		managerID.setOnItemSelectedListener(this);
		pickerID.setOnItemSelectedListener(this);

		itemID.setOnClickListener(this);
		summit.setOnClickListener(this);

		// 给date绑定点击事件
		date.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					showDatePickDlg();
					return true;
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bg_out_pickorder_summit:
			// 给pickorder赋值
			if (pickorderID.getText().toString().equals("")
					|| itemName.getText().toString().equals("")
					|| count.getText().toString().equals("")) {
				Toast.makeText(context, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}

			String[] ids = itemID.getText().toString().split(",");
			String[] names = itemName.getText().toString().split(",");
			String[] counts = count.getText().toString().split(",");

			for (int i = 0; i < ids.length; i++) {

				PickOrder pickorder = new PickOrder();

				pickorder.setPickOrderID(pickorderID.getText().toString());
				pickorder.setManagerID(managerid);
				pickorder.setPickerID(pickerid);
				pickorder.setItemID(ids[i]);
				pickorder.setItemName(names[i]);
				pickorder.setPcCount(counts[i]);
				pickorder.setItemType(LitePal.where("itemid=?", ids[i])
						.find(Item.class).get(0).getItemType());
				pickorder.setPcState("未处理");
				try {
					pickorder.setPcDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(date.getText().toString()));
				} catch (ParseException e) {
					Toast.makeText(context, "格式有误", Toast.LENGTH_SHORT).show();
				}

				// 判断此类货物是否充足
				List<Item> items = LitePal.where("ItemID=?",
						pickorder.getItemID()).find(Item.class);

				// 如果库存不够
				if (Integer.parseInt(items.get(0).getCount()) < Integer
						.parseInt(counts[i])) {
					Toast.makeText(context,
							"库存数量不够，请前去填写采购单" + "\n" + "即将跳转到采购单界面",
							Toast.LENGTH_SHORT).show();
					// 延迟三秒
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							startActivity(new Intent(context,
									OutinInPcOrderActivity.class));
							// 只延迟一次
							this.cancel();

						}
					}, 3000);

					return;
				}
				// 存储到数据库
				pickorder.save();
				Picker picker = LitePal
						.where("PickerID=?", pickorder.getPickerID())
						.find(Picker.class).get(0);
				User user = LitePal.where("UserID=?", pickorder.getManagerID())
						.find(User.class).get(0);
				Item item = LitePal.where("ItemID=?", pickorder.getItemID())
						.find(Item.class).get(0);

				// 添加
				picker.getPickorderList().add(pickorder);
				user.getPickorderList().add(pickorder);
				item.getPickorderList().add(pickorder);

			}
			Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
			// 广播通知
			Intent intent = new Intent();
			intent.setAction("action.refreshPickOrder");
			intent.putExtra("ID", pickorderID.getText().toString());
			context.sendBroadcast(intent);
			break;

		case R.id.sp_out_pickorder_itemID:
			itemID.setText("");
			Cursor cursor = LitePal.findBySQL("select itemid from item");
			final String[] types = new String[cursor.getCount()];
			final boolean[] selected = new boolean[cursor.getCount()];
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					types[i] = cursor.getString(0);
					selected[i] = false;
					cursor.moveToNext();
				}
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
			builder.setTitle("请选择物品编号");
			builder.setMultiChoiceItems(types, selected,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							if (isChecked) {
								Toast.makeText(context, "你选择了" + types[which],
										Toast.LENGTH_SHORT).show();
								itemids.add(types[which]);
							}
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							StringBuilder sb = new StringBuilder();
							StringBuilder sb2 = new StringBuilder();
							if (itemids.size() > 1) {
								for (int i = 0; i < itemids.size() - 1; i++) {
									sb.append(itemids.get(i)).append(",");
									sb2.append(
											LitePal.where("itemid=?",
													itemids.get(i))
													.find(Item.class).get(0)
													.getItemName()).append(",");
								}
								sb.append(itemids.get(itemids.size() - 1));
								sb2.append(LitePal
										.where("itemid=?",
												itemids.get(itemids.size() - 1))
										.find(Item.class).get(0).getItemName());
								itemID.setText(sb);
								itemName.setText(sb2);
							} else if (itemids.size() == 1) {
								itemID.setText(itemids.get(0));
								itemName.setText(LitePal
										.where("itemid=?", itemids.get(0))
										.find(Item.class).get(0).getItemName());
							} else {
								Toast.makeText(context, "你没有选择类型",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Toast.makeText(context, "你没有选择类型",
									Toast.LENGTH_SHORT).show();
						}
					});
			builder.create().show();

			break;

		default:
			break;
		}

	}

	// 点击下拉列表触发的事件
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// 给pickorder赋值
		switch (parent.getId()) {

		case R.id.sp_out_pickorder_managerID:
			managerid = parent.getItemAtPosition(position).toString();
			break;

		case R.id.sp_out_pickorder_pickerID:
			pickerid = parent.getItemAtPosition(position).toString();
			break;

		default:
			break;
		}

	}

	// 给date显示一个可选择的日历
	public void showDatePickDlg() {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
		// 绑定监听器
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						date.setText(new StringBuilder().append(year)
								.append("-").append(monthOfYear + 1)
								.append("-").append(dayOfMonth));

					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));// 后面三个参数是设置初始日期

		// 显示日历Dialog
		datePickerDialog.show();
	}

	private void pickerIDSpinnerInit() {
		List<Picker> pickers = LitePal.where("id>?", "0").find(Picker.class);
		List<String> mData = new ArrayList<String>();
		for (Picker picker : pickers) {
			mData.add(picker.getPickerID());
		}
		mData.add("请选择取货员编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(context,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		pickerID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		pickerID.setSelection(mData.size() - 1, true);

	}

	private void managerIDSpinnerInit() {
		List<User> users = LitePal.where("id>?", "0").find(User.class);
		List<String> mData = new ArrayList<String>();
		for (User user : users) {
			// 如果以g开头就添加,只添加管理员
			if (user.getUserID().startsWith("g")) {
				mData.add(user.getUserID());
			}
		}
		mData.add("请选择管理员编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(context,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		managerID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		managerID.setSelection(mData.size() - 1, true);

	}

	/**
	 * 定义一个MyAdapter类继承ArrayAdapter 重写以下两个方法
	 * */
	class MyAdapter<T> extends ArrayAdapter<T> {

		public MyAdapter(Context context, int resource, int TvID,
				List<T> objects) {
			super(context, resource, TvID, objects);
		}

		@Override
		public int getCount() {
			// 返回数据的统计数量，大于0项则减去1项，从而不显示最后一项
			int i = super.getCount();
			return i > 0 ? i - 1 : i;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
