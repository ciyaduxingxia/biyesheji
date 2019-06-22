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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.wms.R;

public class QueryFragment extends Fragment implements OnClickListener,
		OnPageChangeListener {

	// UI Objects
	private TextView txt_details;
	private TextView txt_summary;
	private TextView txt_insta;
	private TextView txt_outsta;

	private ViewPager vpager;

	private QueryFragmentPagerAdapter qAdapter;

	private View queryFragment;
	public static Context mContext;

	private FragmentManager fm;

	// 几个代表页面的常量
	public static final int PAGE_ONE = 0;
	public static final int PAGE_TWO = 1;
	public static final int PAGE_THREE = 2;
	public static final int PAGE_FOUR = 3;

	public QueryFragment(FragmentManager f) {
		fm = f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		queryFragment = inflater.inflate(R.layout.fragment_query, container,
				false);
		qAdapter = new QueryFragmentPagerAdapter(fm);
		mContext = getActivity();

		init();

		return queryFragment;
	}

	private void init() {
		txt_details = (TextView) queryFragment
				.findViewById(R.id.txt_outindetails);
		txt_summary = (TextView) queryFragment
				.findViewById(R.id.txt_outinsummary);
		txt_insta = (TextView) queryFragment.findViewById(R.id.txt_inboundsta);
		txt_outsta = (TextView) queryFragment
				.findViewById(R.id.txt_outboundsta);

		vpager = (ViewPager) queryFragment.findViewById(R.id.vpager2);

		txt_details.setOnClickListener(this);
		txt_summary.setOnClickListener(this);
		txt_insta.setOnClickListener(this);
		txt_outsta.setOnClickListener(this);

		vpager.setAdapter(qAdapter);
		vpager.setCurrentItem(0);
		vpager.setOnPageChangeListener(this);

		txt_details.setSelected(true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_outindetails:
			vpager.setCurrentItem(PAGE_ONE);
			break;

		case R.id.txt_outinsummary:
			vpager.setCurrentItem(PAGE_TWO);
			break;

		case R.id.txt_inboundsta:
			vpager.setCurrentItem(PAGE_THREE);
			break;

		case R.id.txt_outboundsta:
			vpager.setCurrentItem(PAGE_FOUR);
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
				txt_details.setSelected(true);
				break;
			case PAGE_TWO:
				setSelected();
				txt_summary.setSelected(true);
				break;
			case PAGE_THREE:
				setSelected();
				txt_insta.setSelected(true);
				break;
			case PAGE_FOUR:
				setSelected();
				txt_outsta.setSelected(true);
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
		txt_details.setSelected(false);
		txt_summary.setSelected(false);
		txt_insta.setSelected(false);
		txt_outsta.setSelected(false);
	}
}
