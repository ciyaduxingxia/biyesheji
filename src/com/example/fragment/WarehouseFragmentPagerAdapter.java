package com.example.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WarehouseFragmentPagerAdapter extends FragmentPagerAdapter {

	private final int PAGER_COUNT = 4;
	private WarehouseCheckFragment checkFragment = null;
	private WarehouseCheck1Fragment check1Fragment = null;
	private WarehouseInquiryFragment inquiryFragment = null;
	private WarehouseAlarmFragment alarmFragment = null;

	public WarehouseFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		checkFragment = new WarehouseCheckFragment();
		check1Fragment = new WarehouseCheck1Fragment();
		inquiryFragment = new WarehouseInquiryFragment();
		alarmFragment = new WarehouseAlarmFragment();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case WarehouseFragment.PAGE_ONE:
			fragment = checkFragment;
			break;

		case WarehouseFragment.PAGE_TWO:
			fragment = inquiryFragment;
			break;

		case WarehouseFragment.PAGE_THREE:
			fragment = alarmFragment;
			break;

		case WarehouseFragment.PAGE_FOUR:
			fragment = check1Fragment;
			break;

		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGER_COUNT;
	}

}
