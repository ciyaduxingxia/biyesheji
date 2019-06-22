package com.example.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class QueryFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private final int PAGER_COUNT = 4;
	private QueryDetailsFragment detailsFragment = null;
	private QuerySummaryFragment summaryFragment = null;
	private QueryInStaFragment instaFragment = null;
	private QueryOutStaFragment outstaFragment = null;

	public QueryFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		detailsFragment = new QueryDetailsFragment();
		summaryFragment = new QuerySummaryFragment();
		instaFragment = new QueryInStaFragment();
		outstaFragment = new QueryOutStaFragment();
		
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case QueryFragment.PAGE_ONE:
			fragment = detailsFragment;
			break;

		case QueryFragment.PAGE_TWO:
			fragment = summaryFragment;
			break;

		case QueryFragment.PAGE_THREE:
			fragment = instaFragment;
			break;
			
		case QueryFragment.PAGE_FOUR:
			fragment = outstaFragment;
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
