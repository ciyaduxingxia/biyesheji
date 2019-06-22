package com.example.outin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.dbtable.InboundOrder;
import com.example.dbtable.Item;
import com.example.dbtable.PurchaseOrder;
import com.example.dbtable.Supply;
import com.example.dbtable.User;
import com.example.outin.OutinInPcOrderActivity.MyAdapter;
import com.example.wms.R;

public class OutinInInboundOrderActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private Context mContext;

	private ImageView arrow_back;

	private Spinner pcorderID;
	private Spinner operatorID;

	private EditText itemID;
	private EditText supplyID;
	private EditText inboundID;
	private EditText price;
	private EditText count;
	private EditText date;

	private Button summit;

	private String ID;
	private String opID;

//	static {
//		LitePal.deleteAll(InboundOrder.class);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_in_inboundorder);
		mContext = OutinInInboundOrderActivity.this;

		init();
	}

	private void init() {
		arrow_back = (ImageView) findViewById(R.id.iv_inboundorder_arrow_back);
		pcorderID = (Spinner) findViewById(R.id.sp_in_pcorder_pcorderID);
		operatorID = (Spinner) findViewById(R.id.sp_in_inboundorder_operatorID);

		inboundID = (EditText) findViewById(R.id.et_in_inboundorder_iborderID);
		supplyID = (EditText) findViewById(R.id.et_in_inboundorder_supplyID);
		itemID = (EditText) findViewById(R.id.et_in_inboundorder_itemID);
		price = (EditText) findViewById(R.id.et_in_inboundorder_price);
		count = (EditText) findViewById(R.id.et_in_inboundorder_count);
		date = (EditText) findViewById(R.id.et_in_inboundorder_date);

		summit = (Button) findViewById(R.id.bg_in_inboundorder_summit);

		// 为pcorderID创建列表
		pcorderIDSpinnerInit();
		// 为operatorID创建列表
		operatorIDSpinnerInit();

		// 绑定事件
		pcorderID.setOnItemSelectedListener(this);
		operatorID.setOnItemSelectedListener(this);
		arrow_back.setOnClickListener(this);
		summit.setOnClickListener(this);
		// 给date绑定点击事件
		date.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 显示一个时间选择Dialog
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
		case R.id.iv_inboundorder_arrow_back:
			finish();
			break;

		case R.id.bg_in_inboundorder_summit:
			// 给order赋值
			if (inboundID.getText().toString().equals("")
					|| date.getText().toString().equals("")
					|| price.getText().toString().equals("")) {
				Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}

			String[] ids = itemID.getText().toString().split(",");
			String[] prices = price.getText().toString().split(",");
			String[] counts = count.getText().toString().split(",");

			for (int i = 0; i < ids.length; i++) {
				InboundOrder order = new InboundOrder();

				order.setIbOrderID(inboundID.getText().toString());
				order.setSupplyID(supplyID.getText().toString());
				order.setOperatorID(opID);
				order.setItemID(ids[i]);
				order.setItemType(LitePal.where("itemid=?", ids[i])
						.find(Item.class).get(0).getItemType());
				order.setItemName(LitePal.where("itemid=?", ids[i])
						.find(Item.class).get(0).getItemName());
				order.setPrice(prices[i]);
				order.setIbCount(counts[i]);
				try {
					order.setIbDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(date.getText().toString()));
				} catch (ParseException e) {
					Toast.makeText(mContext, "格式有误", Toast.LENGTH_SHORT).show();
				}

				// 存储到数据库
				order.save();
				Supply supply = LitePal
						.where("SupplyID=?", order.getSupplyID())
						.find(Supply.class).get(0);
				User user = LitePal.where("UserID=?", order.getOperatorID())
						.find(User.class).get(0);
				Item item = LitePal.where("ItemID=?", order.getItemID())
						.find(Item.class).get(0);

				// 添加
				supply.getIborderList().add(order);
				user.getIborderList().add(order);
				item.getIborderList().add(order);
				if (supply.save() && user.save() && item.save()) {
					Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
				}

				// 广播通知
				Intent intent = new Intent();
				intent.setAction("action.delPcOrder");
				intent.putExtra("ID", ID);
				sendBroadcast(intent);
			}
			finish();
			break;

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

	private void operatorIDSpinnerInit() {
		List<User> users = LitePal.where("id>?", "0").find(User.class);
		List<String> mData = new ArrayList<String>();
		for (User user : users) {
			// 如果以c开头就添加,只添加操作员
			if (user.getUserID().startsWith("c")) {
				mData.add(user.getUserID());
			}
		}
		mData.add("请选择操作员编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		operatorID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		operatorID.setSelection(mData.size() - 1, true);

	}

	private void pcorderIDSpinnerInit() {
		Cursor cursor = LitePal
				.findBySQL("select pcorderid from purchaseorder group by pcorderid");
		List<String> mData = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				cursor.moveToNext();
			}
		}
		mData.add("请选择对应的采购单编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		pcorderID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		pcorderID.setSelection(mData.size() - 1, true);

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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.sp_in_pcorder_pcorderID:
			ID = parent.getItemAtPosition(position).toString();
			// 给itemID框赋值
			List<PurchaseOrder> list = LitePal.where("PcOrderID=?", ID).find(
					PurchaseOrder.class);

			StringBuilder iids = new StringBuilder();
			StringBuilder pris = new StringBuilder();
			StringBuilder cous = new StringBuilder();
			for (int i = 0; i < list.size() - 1; i++) {
				iids.append(list.get(i).getItemID()).append(",");
				pris.append(list.get(i).getPrice()).append(",");
				cous.append(list.get(i).getPcCount()).append(",");
			}
			iids.append(list.get(list.size() - 1).getItemID());
			pris.append(list.get(list.size() - 1).getPrice());
			cous.append(list.get(list.size() - 1).getPcCount());

			itemID.setText(iids);
			supplyID.setText(list.get(0).getSupplyID());
			price.setText(pris);
			count.setText(cous);
			break;

		case R.id.sp_in_inboundorder_operatorID:
			opID = parent.getItemAtPosition(position).toString();
			break;

		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
