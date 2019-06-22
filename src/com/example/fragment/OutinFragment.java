package com.example.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wms.R;

public class OutinFragment extends Fragment implements OnClickListener {

	private Context context;

	private View fragment_outin;

	// UI Object
	private Button outin_in;
	private Button outin_out;

	// Fragment Object
	private OutFragment outFg;
	private InFragment inFg;
	private FragmentManager fManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 得到所在的Activity
		context = getActivity();
		fragment_outin = inflater.inflate(R.layout.fragment_outin, container,
				false);
		fManager = getFragmentManager();
		// 初始化布局元素
		init();

		return fragment_outin;
	}

	private void init() {
		outin_in = (Button) fragment_outin.findViewById(R.id.bg_outin_in);
		outin_out = (Button) fragment_outin.findViewById(R.id.bg_outin_out);

		outin_in.setOnClickListener(this);
		outin_out.setOnClickListener(this);

		// 默认点击一次
		outin_in.performClick();

	}

	@Override
	public void onClick(View v) {
		FragmentTransaction fTransaction = fManager.beginTransaction();
		hideFragments(fTransaction);
		switch (v.getId()) {
		case R.id.bg_outin_in:
			if (inFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				inFg = new InFragment();
				fTransaction.add(R.id.fl_outin_content, inFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(inFg);
			}
			outin_out.setEnabled(true);
			outin_in.setEnabled(false);
			break;

		case R.id.bg_outin_out:
			if (outFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				outFg = new OutFragment();
				fTransaction.add(R.id.fl_outin_content, outFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(outFg);
			}
			outin_in.setEnabled(true);
			outin_out.setEnabled(false);
			break;
			

		default:
			break;
		}

		// 提交事务
		fTransaction.commit();
	}

	/**
	 * 将所有的fragment设为隐藏状态
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (inFg != null) {
			transaction.hide(inFg);
		}
		if (outFg != null) {
			transaction.hide(outFg);
		}
	}
}
