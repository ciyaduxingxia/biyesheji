package com.example.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.litepal.LitePal;

import com.example.dbtable.Details;
import com.example.dbtable.InboundOrder;
import com.example.dbtable.OutboundOrder;
import com.example.fragment.QueryDetailsFragment.MyAdapter;
import com.example.listadapter.DetailsListAdapter;
import com.example.listadapter.IborderListAdapter;
import com.example.wms.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class QueryInStaFragment extends Fragment implements OnClickListener,
		OnItemSelectedListener{

	private Context mContext;
	private View instaFragment;

	private EditText startDate;
	private EditText endDate;
	private Spinner supplyid;
	private Spinner id;

	private Button summit;
	private ListView iborderlist;

	private List<InboundOrder> list;

	private String SupplyId;
	private String Id;
	private String startdate;
	private String enddate;
	private long starttime;
	private long endtime;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		instaFragment = inflater.inflate(R.layout.fragment_query_inboundsta,
				container, false);
		mContext = QueryFragment.mContext;

		init();

		return instaFragment;
	}

	private void init() {
		startDate = (EditText) instaFragment
				.findViewById(R.id.et_insta_startdate);
		endDate = (EditText) instaFragment.findViewById(R.id.et_insta_enddate);
		supplyid = (Spinner) instaFragment.findViewById(R.id.sp_insta_type);
		id = (Spinner) instaFragment.findViewById(R.id.sp_insta_ID);
		summit = (Button) instaFragment
				.findViewById(R.id.bg_query_insta_summit);
		iborderlist = (ListView) instaFragment
				.findViewById(R.id.query_insta_list);

		// 为type创建列表
		supplyidSpinnerInit();
		// 为id创建列表
		idSpinnerInit();

		// 绑定事件
		supplyid.setOnItemSelectedListener(this);
		id.setOnItemSelectedListener(this);
		summit.setOnClickListener(this);

		startDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startDate.setText("");
					showDatePickDlg(1);
					return true;
				}
				return false;
			}
		});

		endDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					endDate.setText("");
					showDatePickDlg(2);
					return true;
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		startdate = startDate.getText().toString();
		try {
			starttime = new SimpleDateFormat("yyyy-MM-dd").parse(startdate)
					.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		enddate = endDate.getText().toString();
		try {
			endtime = new SimpleDateFormat("yyyy-MM-dd").parse(enddate)
					.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && SupplyId == null
				&& Id == null) {
			Log.i("标志", "标志1");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("id>?", "0").find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;

		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && SupplyId == null) {
			Log.i("标志", "标志2");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("itemid=?", Id).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			Log.i("标志", "标志3");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("supplyid=?", SupplyId).find(
					InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText())) {
			Log.i("标志", "标志4");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("itemid=?", Id).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && SupplyId == null
				&& Id == null) {
			Log.i("标志", "标志5");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate>? ", starttime + "").find(
					InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && SupplyId == null) {
			Log.i("标志", "标志6");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate>? and itemid=?", starttime + "", Id)
					.find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			Log.i("标志", "标志7");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate>? and supplyid=?", starttime + "",
					SupplyId).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText())) {
			Log.i("标志", "标志8");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate>? and itemid=?", starttime + "", Id)
					.find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && SupplyId == null
				&& Id == null) {
			Log.i("标志", "标志9");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate<?", endtime + "").find(
					InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && SupplyId == null) {
			Log.i("标志", "标志10");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate<? and itemid=?", endtime + "", Id)
					.find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && !Id.startsWith("z")) {
			Log.i("标志", "标志11");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate<? and supplyid=?", endtime + "",
					SupplyId).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())) {
			Log.i("标志", "标志12");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate<? and itemid=?", endtime + "", Id)
					.find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (SupplyId == null && Id == null) {
			Log.i("标志", "标志13");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate > ? and ibdate < ?", starttime + "",
					endtime + "").find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;

		}
		if (SupplyId == null) {
			Log.i("标志", "标志14");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate > ? and ibdate < ? and itemid=?",
					starttime + "", endtime + "", Id).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (!Id.startsWith("z")) {
			Log.i("标志", "标志15");
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate > ? and ibdate < ? and supplyid=?",
					starttime + "", endtime + "", SupplyId).find(
					InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}
		if (!TextUtils.isEmpty(startDate.getText())
				&& !TextUtils.isEmpty(endDate.getText()) && SupplyId != null
				&& Id.startsWith("z")) {
			list = new ArrayList<InboundOrder>();
			list = LitePal.where("ibdate > ? and ibdate < ? and itemid=?",
					starttime + "", endtime + "", Id).find(InboundOrder.class);

			iborderlist.setAdapter(new IborderListAdapter(mContext, list));
			return;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {

		case R.id.sp_insta_type:
			Log.i("点击type", "点击");
			SupplyId = parent.getItemAtPosition(position).toString();
			// 为id创建列表
			idSpinnerInit();
			break;

		case R.id.sp_insta_ID:
			Log.i("ID标志", "标志");
			Id = parent.getItemAtPosition(position).toString();
			Log.i("ID值", Id);
			break;

		default:
			break;
		}

	}

	private void idSpinnerInit() {
		Log.i("物品编号", "编号");
		List<String> mData = new ArrayList<String>();
		Cursor cursor;
		if (SupplyId != null) {
			cursor = LitePal
					.findBySQL(
							"select itemid from inboundorder where supplyid=? group by itemid ",
							SupplyId);
		} else {
			cursor = LitePal
					.findBySQL("select itemid from item group by itemid ");
		}
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
		}
		mData.add("请选择物品编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		id.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		id.setSelection(mData.size() - 1, true);

	}

	private void supplyidSpinnerInit() {
		List<String> mData = new ArrayList<String>();
		Cursor cursor = LitePal
				.findBySQL("select supplyid from inboundorder group by supplyid");
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

		supplyid.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		supplyid.setSelection(mData.size() - 1, true);

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

	// 给date显示一个可选择的日历
	public void showDatePickDlg(final int i) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
		// 绑定监听器
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						if (i == 1) {
							startDate.setText(new StringBuilder().append(year)
									.append("-").append(monthOfYear + 1)
									.append("-").append(dayOfMonth));
						} else {
							endDate.setText(new StringBuilder().append(year)
									.append("-").append(monthOfYear + 1)
									.append("-").append(dayOfMonth));
						}

					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));// 后面三个参数是设置初始日期

		// 显示日历Dialog
		datePickerDialog.show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
