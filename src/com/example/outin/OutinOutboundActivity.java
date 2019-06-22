package com.example.outin;

import java.util.ArrayList;
import java.util.List;

import org.litepal.LitePal;

import com.example.dbtable.Item;
import com.example.outin.OutinInInboundActivity.MyAdapter;
import com.example.wms.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class OutinOutboundActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Context mContext;

	private Spinner itemID;

	private ImageView arrow_back;
	private EditText itemName;
	private EditText count;
	private EditText unit;
	private EditText itemType;

	private Button summit;

	private ImageView saomiao;

	private Item item = new Item();

	private String[] messages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_out_outbound);
		mContext = OutinOutboundActivity.this;

		init();
	}

	private void init() {
		itemID = (Spinner) findViewById(R.id.sp_out_outbound_itemID);

		saomiao = (ImageView) findViewById(R.id.iv_outbound_saoma);
		arrow_back = (ImageView) findViewById(R.id.iv_outbound_arrow_back);
		itemName = (EditText) findViewById(R.id.et_out_outbound_itemName);
		itemType = (EditText) findViewById(R.id.et_out_outbound_itemType);
		count = (EditText) findViewById(R.id.et_out_outbound_count);
		unit = (EditText) findViewById(R.id.et_out_outbound_unit);

		summit = (Button) findViewById(R.id.bg_out_outbound_summit);

		arrow_back.setOnClickListener(this);
		summit.setOnClickListener(this);
		saomiao.setOnClickListener(this);

		// 给itemID添加列表
		itemIDSpinnerInit();

		itemID.setOnItemSelectedListener(this);

		summit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bg_out_outbound_summit:
			if (count.getText().toString().equals("")) {
				Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
				return;
			}
			item.setCount(count.getText().toString());

			// 更新数据库
			List<Item> items = LitePal.where("ItemID=?", item.getItemID())
					.find(Item.class);

			int count1 = Integer.parseInt(item.getCount().toString());
			int count2 = Integer.parseInt(items.get(0).getCount().toString());
			Item it = new Item();
			it.setCount(String.valueOf(count2 - count1));
			it.update(items.get(0).getId());

			Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
			break;

		case R.id.iv_outbound_arrow_back:
			finish();
			break;

		case R.id.iv_outbound_saoma:
			Intent intent = new Intent(OutinOutboundActivity.this,
					com.example.scan.zxing.android.CaptureActivity.class);
			startActivityForResult(intent, 2);
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
		if (requestCode == 2 && resultCode == RESULT_OK) {
			if (data != null) {
				// 返回的文本内容
				String content = data.getStringExtra("codedContent");
				try {
					messages = content.split(",");
					itemName.setText(messages[1]);
					itemType.setText(messages[2]);
					count.setText(messages[3]);
					unit.setText(messages[4]);
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
//		itemID.setSelection(0, true);
//
//	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		item.setItemID(parent.getItemAtPosition(position).toString());
		// 同时给itemName和itemType和unit框赋值
		List<Item> list = LitePal.where("ItemID=?", item.getItemID()).find(
				Item.class);
		itemName.setText(list.get(0).getItemName().toString());
		itemType.setText(list.get(0).getItemType().toString());
		unit.setText(list.get(0).getUnit().toString());

	}

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
