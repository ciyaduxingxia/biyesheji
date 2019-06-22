package com.example.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.litepal.LitePal;

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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dbtable.Item;
import com.example.dbtable.Summary;
import com.example.listadapter.SummaryListAdapter;
import com.example.wms.R;

public class QuerySummaryFragment extends Fragment implements
		OnItemSelectedListener, OnClickListener {

	private Context mContext;
	private View summaryFragment;

	private EditText startDate;
	private EditText endDate;
	private Spinner type;
	private Spinner id;

	private Button summit;
	private ListView summarylist;
	private List<Summary> list;

	private String Type;
	private String Id;

	private String startdate;
	private String enddate;
	private long starttime;
	private long endtime;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		summaryFragment = inflater.inflate(
				R.layout.fragment_query_outinsummary, container, false);
		mContext = QueryFragment.mContext;

		init();

		return summaryFragment;
	}

	private void init() {
		startDate = (EditText) summaryFragment
				.findViewById(R.id.et_summary_startdate);
		endDate = (EditText) summaryFragment
				.findViewById(R.id.et_summary_enddate);
		type = (Spinner) summaryFragment.findViewById(R.id.sp_summary_type);
		id = (Spinner) summaryFragment.findViewById(R.id.sp_summary_ID);
		summit = (Button) summaryFragment
				.findViewById(R.id.bg_query_summary_summit);
		summarylist = (ListView) summaryFragment
				.findViewById(R.id.query_summary_list);

		// 为type创建列表
		typeSpinnerInit();
		// 为id创建列表
		idSpinnerInit();

		// 绑定事件
		type.setOnItemSelectedListener(this);
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
				&& TextUtils.isEmpty(endDate.getText()) && Type == null
				&& Id == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL("select itemid,itemname,itemtype,sum(ibcount) from inboundorder group by itemid ");
			Cursor cursor2 = LitePal
					.findBySQL("select itemid,itemname,itemtype,sum(obcount) from outboundorder group by itemid ");

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && Type == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where itemid=? group by itemid",
							Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where itemid=? group by itemid",
							Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where itemtype=? group by itemid",
							Type);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where itemtype=? group by itemid",
							Type);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText())) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where itemid=? group by itemid",
							Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where itemid=? group by itemid",
							Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && Type == null && Id == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate>? group by itemid",
							starttime + "");
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate>? group by itemid",
							starttime + "");

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && Type == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate>? and itemid=? group by itemid",
							starttime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate>? and itemid=? group by itemid",
							starttime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate>? and itemtype=? group by itemid",
							starttime + "", Type);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate>? and itemtype=? group by itemid",
							starttime + "", Type);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText())) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate>? and itemid=? group by itemid",
							starttime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate>? and itemid=? group by itemid",
							starttime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && Type == null
				&& Id == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? group by itemid",
							endtime + "");
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? group by itemid",
							endtime + "");

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && Type == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and itemid=? group by itemid",
							endtime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and itemid=? group by itemid",
							endtime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and itemtype=? group by itemid",
							endtime + "", Type);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and itemtype=? group by itemid",
							endtime + "", Type);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and itemid=? group by itemid",
							endtime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and itemid=? group by itemid",
							endtime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (Type == null && Id == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and ibdate>? group by itemid",
							endtime + "", starttime + "");
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and obdate>? group by itemid",
							endtime + "", starttime + "");

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (Type == null) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and ibdate>? and itemid=? group by itemid",
							endtime + "", starttime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and obdate>? and itemid=? group by itemid",
							endtime + "", starttime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}
		if (!Id.startsWith("z")) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and ibdate>? and itemtype=? group by itemid",
							endtime + "", starttime + "", Type);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and obdate>? and itemtype=? group by itemid",
							endtime + "", starttime + "", Type);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}

		if (!TextUtils.isEmpty(startDate.getText())
				&& !TextUtils.isEmpty(endDate.getText()) && Type != null
				&& Id.startsWith("z")) {
			list = new ArrayList<Summary>();
			Cursor cursor1 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(ibcount) from inboundorder where ibdate<? and ibdate>? and itemid=? group by itemid",
							endtime + "", starttime + "", Id);
			Cursor cursor2 = LitePal
					.findBySQL(
							"select itemid,itemname,itemtype,sum(obcount) from outboundorder where obdate<? and obdate>? and itemid=? group by itemid",
							endtime + "", starttime + "", Id);

			getList(cursor1, cursor2);
			summarylist.setAdapter(new SummaryListAdapter(mContext, list));
			return;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {

		case R.id.sp_summary_type:
			Type = parent.getItemAtPosition(position).toString();
			// 为id创建列表
			idSpinnerInit();
			break;

		case R.id.sp_summary_ID:
			Id = parent.getItemAtPosition(position).toString();
			break;

		default:
			break;
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

	private void idSpinnerInit() {
		List<String> mData = new ArrayList<String>();
		Cursor cursor;
		if (Type != null) {
			cursor = LitePal
					.findBySQL(
							"select itemid from item where itemtype=? group by itemid ",
							Type);
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

	private void typeSpinnerInit() {
		List<String> mData = new ArrayList<String>();
		Cursor cursor = LitePal
				.findBySQL("select itemtype from item group by itemtype");
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
		}
		mData.add("请选择物品类别");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		type.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		type.setSelection(mData.size() - 1, true);

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

	private void getList(Cursor cursor1, Cursor cursor2) {

		List<Summary> list1 = new ArrayList<Summary>();
		List<Summary> list2 = new ArrayList<Summary>();
		Set<String> set = new HashSet<String>();
		if (cursor1.moveToFirst()) {
			Log.i("cursor1数量", cursor1.getCount() + "");
			for (int i = 0; i < cursor1.getCount(); i++) {
				Summary sum = new Summary();
				sum.setItemID(cursor1.getString(0));
				sum.setItemName(cursor1.getString(1));
				sum.setItemType(cursor1.getString(2));
				sum.setIncount(cursor1.getString(3));
				sum.setUnit(LitePal.where("itemid=?", cursor1.getString(0))
						.find(Item.class).get(0).getUnit());
				sum.setPrice(LitePal.where("itemid=?", cursor1.getString(0))
						.find(Item.class).get(0).getPrice());
				list1.add(sum);
				set.add(cursor1.getString(0));
				cursor1.moveToNext();
			}
		}
		if (cursor2.moveToFirst()) {
			Log.i("cursor2数量", cursor2.getCount() + "");
			for (int i = 0; i < cursor2.getCount(); i++) {
				Summary sum = new Summary();
				sum.setItemID(cursor2.getString(0));
				sum.setItemName(cursor2.getString(1));
				sum.setItemType(cursor2.getString(2));
				sum.setOutcount(cursor2.getString(3));
				sum.setUnit(LitePal.where("itemid=?", cursor2.getString(0))
						.find(Item.class).get(0).getUnit());
				sum.setPrice(LitePal.where("itemid=?", cursor2.getString(0))
						.find(Item.class).get(0).getPrice());
				list2.add(sum);
				set.add(cursor2.getString(0));
				cursor2.moveToNext();
			}
		}
		if (set.size() > 0) {
			for (String s : set) {
				Log.i("标志", s);
				Summary su = new Summary();
				if (list1.size() == 0) {
					su.setIncount("0");
				} else {
					for (Summary sum : list1) {
						if (sum.getItemID().equals(s)) {
							su.setItemID(s);
							su.setItemName(sum.getItemName());
							su.setItemType(sum.getItemType());
							su.setPrice(sum.getPrice());
							su.setUnit(sum.getUnit());
							su.setIncount(sum.getIncount());
							break;
						} else {
							su.setIncount("0");
						}
					}
				}
				if (list2.size() == 0) {
					su.setOutcount("0");
				} else {
					for (Summary sum : list2) {
						if (sum.getItemID().equals(s)) {
							if (su.getIncount().equals("0")) {
								su.setItemID(s);
								su.setItemName(sum.getItemName());
								su.setItemType(sum.getItemType());
								su.setPrice(sum.getPrice());
								su.setUnit(sum.getUnit());
							}

							su.setOutcount(sum.getOutcount());
							break;
						} else {
							su.setOutcount("0");
						}
					}
				}
				list.add(su);
			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
