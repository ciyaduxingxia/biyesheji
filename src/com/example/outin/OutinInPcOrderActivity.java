package com.example.outin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dbtable.Item;
import com.example.dbtable.PurchaseOrder;
import com.example.dbtable.Supply;
import com.example.dbtable.User;
import com.example.wms.R;

/**
 * 采购单界面
 * 
 * @author 肖相杰
 * 
 */
public class OutinInPcOrderActivity extends Activity implements
		OnItemSelectedListener, OnClickListener {

	private ImageView arrow_back;

	private Context mContext;

	private EditText pcorderID;
	private Spinner supplyID;
	private Spinner managerID;
	private EditText itemType;
	private EditText itemName;
	private EditText count;
	private EditText price;
	private EditText date;

	private Button summit;

	private List<String> itemtypes;
	private List<String> itemnames;

	private String supplyid;
	private String managerid;

	// static {
	// LitePal.deleteAll(PurchaseOrder.class);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_in_pcorder);
		mContext = OutinInPcOrderActivity.this;

		init();
	}

	private void init() {
		pcorderID = (EditText) findViewById(R.id.et_in_pcorder_pcorderID);
		supplyID = (Spinner) findViewById(R.id.sp_in_pcorder_supplyID);
		managerID = (Spinner) findViewById(R.id.sp_in_pcorder_managerID);
		itemType = (EditText) findViewById(R.id.et_in_pcorder_itemType);
		itemName = (EditText) findViewById(R.id.et_in_pcorder_itemName);
		count = (EditText) findViewById(R.id.et_in_pcorder_count);
		price = (EditText) findViewById(R.id.et_in_pcorder_price);
		date = (EditText) findViewById(R.id.et_in_pcorder_date);
		summit = (Button) findViewById(R.id.bg_in_pcorder_summit);
		arrow_back = (ImageView) findViewById(R.id.iv_pcorder_arrow_back);

		// 为supplyID创建列表
		supplyIDSpinnerInit();
		// 为managerID创建列表
		managerIDSpinnerInit();

		// 绑定事件
		supplyID.setOnItemSelectedListener(this);
		managerID.setOnItemSelectedListener(this);

		itemType.setOnClickListener(this);
		itemName.setOnClickListener(this);

		summit.setOnClickListener(this);
		arrow_back.setOnClickListener(this);
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

	// 返回和提交按钮
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_pcorder_arrow_back:
			finish();
			break;

		case R.id.et_in_pcorder_itemType:
			itemType.setText("");
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemtype from supply where supplyid=? group by itemtype",
							supplyid);
			final String[] types = new String[cursor1.getCount()];
			final boolean[] selected = new boolean[cursor1.getCount()];
			if (cursor1.moveToFirst()) {
				for (int i = 0; i < cursor1.getCount(); i++) {
					types[i] = cursor1.getString(0);
					selected[i] = false;
					cursor1.moveToNext();
				}
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext, 0);
			builder.setTitle("请选择采购物品类型");
			itemtypes = new ArrayList<String>();
			builder.setMultiChoiceItems(types, selected,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {

							if (isChecked) {
								Toast.makeText(mContext, "你选择了" + types[which],
										Toast.LENGTH_SHORT).show();
								itemtypes.add(types[which]);
							}
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							StringBuilder sb = new StringBuilder();
							if (itemtypes.size() > 1) {
								for (int i = 0; i < itemtypes.size() - 1; i++) {
									sb.append(itemtypes.get(i)).append(",");
								}
								sb.append(itemtypes.get(itemtypes.size() - 1));
								itemType.setText(sb);
							} else if (itemtypes.size() == 1) {
								itemType.setText(itemtypes.get(0));
							} else {
								Toast.makeText(mContext, "你没有选择类型",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Toast.makeText(mContext, "你没有选择类型",
									Toast.LENGTH_SHORT).show();
						}
					});
			builder.create().show();
			break;

		case R.id.et_in_pcorder_itemName:
			itemName.setText("");
			String[] typearr = itemType.getText().toString().split(",");
			Cursor cursor2;
			if (typearr.length == 1) {
				cursor2 = LitePal.findBySQL(
						"select itemname from supply where itemtype=?",
						itemType.getText().toString());
			} else {
				cursor2 = LitePal
						.findBySQL(
								"select itemname from supply where itemtype=? or itemtype=?",
								itemType.getText().toString().split(",")[0],
								itemType.getText().toString().split(",")[1]);
			}
			final String[] types2 = new String[cursor2.getCount()];
			final boolean[] selected2 = new boolean[cursor2.getCount()];
			if (cursor2.moveToFirst()) {
				for (int i = 0; i < cursor2.getCount(); i++) {
					types2[i] = cursor2.getString(0);
					selected2[i] = false;
					cursor2.moveToNext();
				}
			}
			AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext, 0);
			builder2.setTitle("请选择采购物品类型");
			itemnames = new ArrayList<String>();
			builder2.setMultiChoiceItems(types2, selected2,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							if (isChecked) {
								Toast.makeText(mContext,
										"你选择了" + types2[which],
										Toast.LENGTH_SHORT).show();
								itemnames.add(types2[which]);
							}
						}
					});
			builder2.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							StringBuilder sb = new StringBuilder();
							if (itemnames.size() > 1) {
								for (int i = 0; i < itemnames.size() - 1; i++) {
									sb.append(itemnames.get(i)).append(",");
								}
								sb.append(itemnames.get(itemnames.size() - 1));
								itemName.setText(sb);

								StringBuilder prices = new StringBuilder();
								// 给price框赋值
								for (int i = 0; i < itemnames.size() - 1; i++) {
									prices.append(
											LitePal.where("itemname=?",
													itemnames.get(i))
													.find(Item.class).get(0)
													.getPrice()).append(",");
								}
								prices.append(LitePal
										.where("itemname=?",
												itemnames.get(itemnames.size() - 1))
										.find(Item.class).get(0).getPrice());
								price.setText(prices);
							} else if (itemnames.size() == 1) {
								itemName.setText(itemnames.get(0));
								price.setText(LitePal
										.where("itemname=?", itemnames.get(0))
										.find(Item.class).get(0).getPrice());
							} else {
								Toast.makeText(mContext, "你没有选择类型",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			builder2.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Toast.makeText(mContext, "你没有选择类型",
									Toast.LENGTH_SHORT).show();
						}
					});
			builder2.create().show();
			break;

		case R.id.bg_in_pcorder_summit:

			// 给pcorder赋值
			if (pcorderID.getText().toString().equals("")
					|| count.getText().toString().equals("")) {
				Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}

			String[] names = itemName.getText().toString().split(",");
			String[] prices = price.getText().toString().split(",");
			String[] counts = count.getText().toString().split(",");

			for (int i = 0; i < names.length; i++) {

				PurchaseOrder pcorder = new PurchaseOrder();

				pcorder.setSupplyID(supplyid);
				pcorder.setManagerID(managerid);
				pcorder.setPcOrderID(pcorderID.getText().toString());
				pcorder.setItemName(names[i]);
				pcorder.setPrice(prices[i]);
				pcorder.setItemType(LitePal.where("itemname=?", names[i])
						.find(Item.class).get(0).getItemType());
				pcorder.setPcCount(counts[i]);
				pcorder.setPcState("待处理");
				pcorder.setItemID(LitePal.where("itemname=?", names[i])
						.find(Item.class).get(0).getItemID());

				try {
					pcorder.setPcDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(date.getText().toString()));
				} catch (ParseException e) {
					Toast.makeText(mContext, "格式有误", Toast.LENGTH_SHORT).show();
				}
				// 存储到数据库
				pcorder.save();
				Supply supply = LitePal
						.where("SupplyID=?", pcorder.getSupplyID())
						.find(Supply.class).get(0);
				User user = LitePal.where("UserID=?", pcorder.getManagerID())
						.find(User.class).get(0);
				Item item = LitePal.where("ItemID=?", pcorder.getItemID())
						.find(Item.class).get(0);

				// 添加
				supply.getPcorderList().add(pcorder);
				user.getPcorderList().add(pcorder);
				item.getPcorderList().add(pcorder);
				if (supply.save() && user.save() && item.save()) {
					Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
				}

			}
			// 广播通知
			Intent intent = new Intent();
			intent.setAction("action.refreshPcOrder");
			intent.putExtra("ID", pcorderID.getText().toString());
			sendBroadcast(intent);
			finish();

		default:
			break;
		}
	}

	// 给date显示一个可选择的日历
	public void showDatePickDlg() {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
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

	// 点击下拉列表触发的事件
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// 给pcorder赋值
		switch (parent.getId()) {
		case R.id.sp_in_pcorder_supplyID:
			supplyid = parent.getItemAtPosition(position).toString();
			break;

		case R.id.sp_in_pcorder_managerID:
			managerid = parent.getItemAtPosition(position).toString();
			break;

		default:
			break;
		}

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

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		managerID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		managerID.setSelection(mData.size() - 1, true);

	}

	private void supplyIDSpinnerInit() {
		Cursor cursor = LitePal
				.findBySQL("select supplyid from supply group by supplyid ");
		List<String> mData = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
		}
		mData.add("请选择供应商编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		supplyID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		supplyID.setSelection(mData.size() - 1, true);

	}

	/**
	 * 定义一个MyAdapter类继承ArrayAdapter 重写以下两个方法
	 * */
	class MyAdapter<T> extends ArrayAdapter<T> {

		public MyAdapter(Context context, int resource, int TvID,
				List<T> objects) {
			super(mContext, resource, TvID, objects);
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
		Toast.makeText(mContext, "请选择一个选项", Toast.LENGTH_SHORT).show();

	}
}
