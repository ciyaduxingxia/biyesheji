package com.example.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import com.example.dbtable.InboundOrder;
import com.example.dbtable.OutboundOrder;
import com.example.listadapter.IborderListAdapter;
import com.example.listadapter.OborderListAdapter;
import com.example.wms.R;

public class QueryOutStaFragment extends Fragment implements OnClickListener,OnItemSelectedListener{

	private Context mContext;
	private View outstaFragment;
	
	private EditText startDate;
	private EditText endDate;
	private Spinner pickerid;
	private Spinner id;

	private Button summit;
	private ListView oborderlist;

	private List<OutboundOrder> list;
	private InboundOrder oborder;

	private String PickerId;
	private String Id;
	private String startdate;
	private String enddate;
	private long starttime;
	private long endtime;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		outstaFragment = inflater.inflate(R.layout.fragment_query_outboundsta,container,false);
		mContext = QueryFragment.mContext;
		
		init();
		
		return outstaFragment;
	}

	private void init() {
		startDate = (EditText) outstaFragment
				.findViewById(R.id.et_outsta_startdate);
		endDate = (EditText) outstaFragment.findViewById(R.id.et_outsta_enddate);
		pickerid = (Spinner) outstaFragment.findViewById(R.id.sp_outsta_type);
		id = (Spinner) outstaFragment.findViewById(R.id.sp_outsta_ID);
		summit = (Button) outstaFragment
				.findViewById(R.id.bg_query_outsta_summit);
		oborderlist = (ListView) outstaFragment
				.findViewById(R.id.query_outsta_list);

		// 为type创建列表
		pickeridSpinnerInit();
		// 为id创建列表
		idSpinnerInit();

		// 绑定事件
		pickerid.setOnItemSelectedListener(this);
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
				&& TextUtils.isEmpty(endDate.getText()) && PickerId == null
				&& Id == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("id>?", "0").find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;

		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && PickerId == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("itemid=?", Id).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("pickerid=?", PickerId).find(
					OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())
				&& TextUtils.isEmpty(endDate.getText())) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("itemid=?", Id).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && PickerId == null
				&& Id == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate>? ", starttime + "").find(
					OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && PickerId == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate>? and itemid=?", starttime + "", Id)
					.find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate>? and pickerid=?", starttime + "",
					PickerId).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(endDate.getText())) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate>? and itemid=?", starttime + "", Id)
					.find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && PickerId == null
				&& Id == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate<?", endtime + "").find(
					OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && PickerId == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate<? and itemid=?", endtime + "", Id)
					.find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText()) && !Id.startsWith("z")) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate<? and pickerid=?", endtime + "",
					PickerId).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (TextUtils.isEmpty(startDate.getText())) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate<? and itemid=?", endtime + "", Id)
					.find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (PickerId == null && Id == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate > ? and obdate < ?", starttime + "",
					endtime + "").find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;

		}
		if (PickerId == null) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate > ? and obdate < ? and itemid=?",
					starttime + "", endtime + "", Id).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (!Id.startsWith("z")) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate > ? and obdate < ? and pickerid=?",
					starttime + "", endtime + "", PickerId).find(
							OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		if (!TextUtils.isEmpty(startDate.getText())
				&& !TextUtils.isEmpty(endDate.getText()) && PickerId != null
				&& Id.startsWith("z")) {
			list = new ArrayList<OutboundOrder>();
			list = LitePal.where("obdate > ? and obdate < ? and itemid=?",
					starttime + "", endtime + "", Id).find(OutboundOrder.class);

			oborderlist.setAdapter(new OborderListAdapter(mContext, list));
			return;
		}
		
	}
	
	private void idSpinnerInit() {
		Log.i("物品编号", "编号");
		List<String> mData = new ArrayList<String>();
		Cursor cursor;
		if (PickerId != null) {
			cursor = LitePal
					.findBySQL(
							"select itemid from outboundorder where pickerid=? group by itemid ",
							PickerId);
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

	private void pickeridSpinnerInit() {
		List<String> mData = new ArrayList<String>();
		Cursor cursor = LitePal
				.findBySQL("select pickerid from outboundorder group by pickerid");
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				mData.add(cursor.getString(0));
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
		}
		mData.add("请选择取货员编号");

		ArrayAdapter<String> myAdapter = new MyAdapter<String>(mContext,
				R.layout.spinner_style, R.id.tv_Spinner, mData);

		pickerid.setAdapter(myAdapter);
		myAdapter.setDropDownViewResource(R.layout.spinner_style);
		// 默认选中最后一项
		pickerid.setSelection(mData.size() - 1, true);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {

		case R.id.sp_outsta_type:
			Log.i("点击type", "点击");
			PickerId = parent.getItemAtPosition(position).toString();
			// 为id创建列表
			idSpinnerInit();
			break;

		case R.id.sp_outsta_ID:
			Log.i("ID标志", "标志");
			Id = parent.getItemAtPosition(position).toString();
			Log.i("ID值", Id);
			break;

		default:
			break;
		}
		
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
