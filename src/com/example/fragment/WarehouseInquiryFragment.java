package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import org.litepal.LitePal;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dbtable.Item;
import com.example.listadapter.CheckListAdapter;
import com.example.listadapter.InquiryListAdapter;
import com.example.wms.R;

public class WarehouseInquiryFragment extends Fragment implements
		OnClickListener {

	private View inquiryFragment;
	private AutoCompleteTextView auto;
	private Button select;

	private ListView inquiryList;

	private Context mContext;
	
	private List<Item> lists = new ArrayList<Item>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		inquiryFragment = inflater.inflate(R.layout.fragment_warehouse_inquiry,
				container, false);
		mContext = WarehouseFragment.mContext;

		init();

		return inquiryFragment;
	}

	private void init() {
		auto = (AutoCompleteTextView) inquiryFragment
				.findViewById(R.id.main_warehouse_inquiry_sel);
		select = (Button) inquiryFragment
				.findViewById(R.id.bg_warehouse_inquiry_summit);
		inquiryList = (ListView) inquiryFragment
				.findViewById(R.id.warehouse_inquiry_list);

		autoInit();

		select.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (auto.getText().toString().startsWith("z")) {
			lists = LitePal.where("ItemID=?",
					auto.getText().toString()).find(Item.class);
		}else{
			lists = LitePal.where("ItemName=?",
					auto.getText().toString()).find(Item.class);
		}
		
		inquiryList.setAdapter(new InquiryListAdapter(mContext, lists));
	}

	private void autoInit() {
		Cursor cursor = LitePal
				.findBySQL("select itemid,itemname from item group by itemid");
		List<String> mData = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				mData.add(cursor.getString(1));
				cursor.moveToNext();
			}
		} 

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		auto.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);

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

}
