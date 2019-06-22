package com.example.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.LoginActivity;
import com.example.activity.MainActivity;
import com.example.wms.R;

public class WarehouseFragment extends Fragment implements OnClickListener,
		OnPageChangeListener {

	// UI Objects
	private TextView txt_check;
	private TextView txt_inquiry;
	private TextView txt_alarm;

	private ViewPager vpager;

	private WarehouseFragmentPagerAdapter wAdapter;

	private FragmentManager fm;

	private View warehouse_fragment;

	public static Context mContext;

	// 几个代表页面的常量
	public static final int PAGE_ONE = 0;
	public static final int PAGE_TWO = 1;
	public static final int PAGE_THREE = 2;
	public static final int PAGE_FOUR = 3;

	public WarehouseFragment(FragmentManager f) {
		fm = f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		warehouse_fragment = inflater.inflate(R.layout.fragment_warehouse,
				container, false);

		wAdapter = new WarehouseFragmentPagerAdapter(fm);
		mContext = getActivity();

		init();

		return warehouse_fragment;
	}

	private void init() {
		txt_check = (TextView) warehouse_fragment.findViewById(R.id.txt_check);
		txt_inquiry = (TextView) warehouse_fragment
				.findViewById(R.id.txt_inquiry);
		txt_alarm = (TextView) warehouse_fragment.findViewById(R.id.txt_alarm);

		vpager = (ViewPager) warehouse_fragment.findViewById(R.id.vpager);

		txt_check.setOnClickListener(this);
		txt_inquiry.setOnClickListener(this);
		txt_alarm.setOnClickListener(this);

		vpager.setAdapter(wAdapter);
		if (MainActivity.flag) {
			vpager.setCurrentItem(2);
			MainActivity.flag = false;
			txt_alarm.setSelected(true);
		} else if (LoginActivity.permission.equals("1")) {
			vpager.setCurrentItem(3);
			txt_check.setSelected(true);
		} else {
			vpager.setCurrentItem(0);
			txt_check.setSelected(true);
		}
		vpager.setOnPageChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_check:
			if (LoginActivity.permission.equals("1")) {
				vpager.setCurrentItem(PAGE_FOUR);
			} else {
				Log.i("标志", "check");
				vpager.setCurrentItem(PAGE_ONE);
			}
			break;

		case R.id.txt_inquiry:
			vpager.setCurrentItem(PAGE_TWO);
			break;

		case R.id.txt_alarm:
			vpager.setCurrentItem(PAGE_THREE);
			break;

		default:
			break;
		}

	}

	// 重写ViewPager页面切换的处理方法
	@Override
	public void onPageScrollStateChanged(int state) {
		// state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
		if (state == 2) {
			switch (vpager.getCurrentItem()) {
			case PAGE_ONE:
				setSelected();
				txt_check.setSelected(true);
				break;
			case PAGE_TWO:
				setSelected();
				txt_inquiry.setSelected(true);
				break;
			case PAGE_THREE:
				setSelected();
				txt_alarm.setSelected(true);
				break;
			case PAGE_FOUR:
				setSelected();
				txt_check.setSelected(true);
				break;
			}
		}

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	// 重置所有文本的选中状态
	private void setSelected() {
		txt_check.setSelected(false);
		txt_inquiry.setSelected(false);
		txt_alarm.setSelected(false);
	}

}
