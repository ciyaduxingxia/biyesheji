package com.example.outin;

import java.util.ArrayList;
import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbtable.Item;
import com.example.wms.R;

public class OutinInInboundActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private Context mContext;

	private Spinner itemID;
	private Spinner location;

	private ImageView saomiao;

	private ImageView arrow_back;
	private EditText itemName;
	private EditText count;
	private EditText unit;
	private EditText price;
	private EditText itemType;

	private Button summit;
	private TextView other;

	private String itemid;
	private String loc;
	private String[] messages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_in_inbound);
		mContext = OutinInInboundActivity.this;

		init();
	}

	private void init() {
		itemID = (Spinner) findViewById(R.id.sp_in_inbound_itemID);
		location = (Spinner) findViewById(R.id.sp_in_inbound_location);
		saomiao = (ImageView) findViewById(R.id.iv_inbound_saoma);

		arrow_back = (ImageView) findViewById(R.id.iv_inbound_arrow_back);
		itemName = (EditText) findViewById(R.id.et_in_inbound_itemName);
		itemType = (EditText) findViewById(R.id.et_in_inbound_itemType);
		count = (EditText) findViewById(R.id.et_in_inbound_count);
		unit = (EditText) findViewById(R.id.et_in_inbound_unit);
		price = (EditText) findViewById(R.id.et_in_inbound_price);

		summit = (Button) findViewById(R.id.bg_in_inbound_summit);
		other = (TextView) findViewById(R.id.tv_in_pcorder_else);

		arrow_back.setOnClickListener(this);
		summit.setOnClickListener(this);
		other.setOnClickListener(this);
		saomiao.setOnClickListener(this);

		// 给itemID添加列表
		itemIDSpinnerInit();
		// 给location添加列表
		locationSpinnerInit();

		itemID.setOnItemSelectedListener(this);
		location.setOnItemSelectedListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.sp_in_inbound_itemID:
			itemid = parent.getItemAtPosition(position).toString();
			// 同时给itemName和itemType和unit框赋值
			List<Item> list = LitePal.where("ItemID=?", itemid)
					.find(Item.class);
			itemName.setText(list.get(0).getItemName().toString());
			itemType.setText(list.get(0).getItemType().toString());
			unit.setText(list.get(0).getUnit().toString());
			price.setText(list.get(0).getPrice().toString());
			break;

		case R.id.sp_in_inbound_location:
			loc = parent.getItemAtPosition(position).toString();
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_inbound_arrow_back:
			finish();
			break;

		case R.id.iv_inbound_saoma:
			Intent intent = new Intent(OutinInInboundActivity.this,
					com.example.scan.zxing.android.CaptureActivity.class);
			startActivityForResult(intent, 1);
			break;

		case R.id.bg_in_inbound_summit:
			if (count.getText().toString().equals("")
					|| unit.getText().toString().equals("")
					|| price.getText().toString().equals("")) {
				Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}
			List<Item> list = LitePal.where("ItemID=?", itemid)
					.find(Item.class);
			int sum = Integer.parseInt(list.get(0).getCount())
					+ Integer.parseInt(count.getText().toString());
			Item item = new Item();
			item.setCount(sum + "");
			item.update(list.get(0).getId());

			Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();

			break;

		case R.id.tv_in_pcorder_else:
			LayoutInflater factorys = LayoutInflater.from(mContext);
			final View dialogview = factorys
					.inflate(R.layout.dialog_item, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle("添加新物品信息");
			builder.setPositiveButton("添加",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 得到所有的EditText
							EditText et1 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et1);
							EditText et2 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et2);
							EditText et3 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et3);
							EditText et4 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et4);
							EditText et5 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et5);
							EditText et6 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et6);
							EditText et7 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et7);
							EditText et8 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et8);
							EditText et9 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et9);

							// 存入数据库中
							Item item = new Item();
							item.setItemID(et1.getText().toString());
							item.setItemName(et2.getText().toString());
							item.setItemType(et3.getText().toString());
							item.setCount(et4.getText().toString());
							item.setUnit(et5.getText().toString());
							item.setLocation(et6.getText().toString());
							item.setPrice(et7.getText().toString());
							item.setUpperLimit(et8.getText().toString());
							item.setLowerLimit(et9.getText().toString());

							if (item.save()) {
								Toast.makeText(mContext, "添加成功",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mContext, "添加失败",
										Toast.LENGTH_SHORT).show();
							}
							finish();
						}
					});

			builder.setNegativeButton("取消", null);
			builder.create();
			builder.setView(dialogview);
			builder.show();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 扫描二维码/条码回传
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (data != null) {
				// 返回的文本内容
				String content = data.getStringExtra("codedContent");
				try {
					messages = content.split(",");
					itemName.setText(messages[1]);
					itemType.setText(messages[2]);
					count.setText(messages[3]);
					unit.setText(messages[4]);
					price.setText(messages[5]);
				} catch (Exception e) {
					Toast.makeText(mContext, "此二维码不符合规范", Toast.LENGTH_SHORT).show();
				}
				
				
			} else {
				Toast.makeText(mContext, "扫描内容为空", Toast.LENGTH_SHORT).show();
			}
		}
	}

//	private void itemIDSetText(String string) {
//		List<String> mData = new ArrayList<String>();
//		
//		mData.add(string);
//		mData.add("请选择物品编号");
//
//		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
//				R.layout.spinner_style, R.id.tv_Spinner, mData);
//
//		itemID.setAdapter(myAdapter);
//		myAdapter.setDropDownViewResource(R.layout.spinner_style);
//
//		itemID.setSelection(mData.size() - 2, true);
//		
//	}

	private void itemIDSpinnerInit() {
		List<Item> items = LitePal.where("id>?", "0").find(Item.class);
		List<String> mData = new ArrayList<String>();
		for (Item item : items) {

			mData.add(item.getItemID());
		}

		mData.add("请选择物品编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		itemID.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		itemID.setSelection(mData.size() - 1, true);

	}

	private void locationSpinnerInit() {
		List<String> mData = new ArrayList<String>();
		mData.add("默认货架");
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				mData.add("第" + i + "货架" + "第" + j + "层");
			}
		}

		mData.add("请选择物品位置");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		location.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		location.setSelection(mData.size() - 1, true);

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
		// TODO Auto-generated method stub

	}

}
