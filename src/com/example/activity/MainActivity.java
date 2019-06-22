package com.example.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.litepal.LitePal;

import com.example.dbtable.ItemInfo;
import com.example.fragment.BasicFragment;
import com.example.fragment.MeAnotherFragment;
import com.example.fragment.MeFragment;
import com.example.fragment.OutinFragment;
import com.example.fragment.QueryFragment;
import com.example.fragment.WarehouseFragment;
import com.example.outin.OutinPickorderActivity;
import com.example.wms.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	// UI Object
	private RadioGroup rg_tab_bar;
	private RadioButton rb_basic;
	private RadioButton rb_warehouse;

	// Fragment Object
	private BasicFragment basicFg;
	private OutinFragment outinFg;
	private WarehouseFragment warehouseFg;
	private QueryFragment queryFg;
	private MeFragment meFg;
	private FragmentManager fManager;
	private MeAnotherFragment meotherFg;

	public static boolean flag = false;

	private static int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		fManager = getFragmentManager();
		// 初始化布局元素
		initView();

		if (LoginActivity.permission.equals("1") && count == 0) {
			// 库存警报提示
			alarm();
			count++;
		}

	}

	private void alarm() {
		Cursor cursor = LitePal
				.findBySQL("select itemid,itemname,upperlimit,lowerlimit,sum(count) from item group by itemid");
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				// 如果库存溢出或小于下限
				if (Integer.parseInt(cursor.getString(4)) > Integer
						.parseInt(cursor.getString(2))) {
					alarmdialog();
					return;
				} else if (Integer.parseInt(cursor.getString(4)) < Integer
						.parseInt(cursor.getString(3))) {
					alarmdialog();
					return;
				} else {
					cursor.moveToNext();
				}
			}
		} 

	}

	private void alarmdialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("跳转页面提示");
		builder.setMessage("有库存发生警报，是否跳转到库存警报页面");
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				flag = true;
				rb_warehouse.setChecked(true);
				if (warehouseFg == null) {
					warehouseFg = new WarehouseFragment(
							getSupportFragmentManager());
					fManager.beginTransaction()
							.add(R.id.fl_content, warehouseFg).commit();
				} else {
					fManager.beginTransaction().show(warehouseFg).commit();
				}
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create();
		builder.show();

	}

	private void initView() {
		rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
		rb_basic = (RadioButton) findViewById(R.id.rb_basic);
		rb_warehouse = (RadioButton) findViewById(R.id.rb_warehouse);
		// 设置第一个单选按钮为选中状态
		rb_basic.setChecked(true);
		if (basicFg == null) {
			basicFg = new BasicFragment();
			fManager.beginTransaction().add(R.id.fl_content, basicFg).commit();
		} else {
			fManager.beginTransaction().show(basicFg).commit();
		}

		// 绑定点击事件
		rg_tab_bar.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		FragmentTransaction fTransaction = fManager.beginTransaction();
		hideFragments(fTransaction);
		switch (checkedId) {
		case R.id.rb_basic:
			if (basicFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				basicFg = new BasicFragment();
				fTransaction.add(R.id.fl_content, basicFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(basicFg);
			}
			break;

		case R.id.rb_outin:
			if (outinFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				outinFg = new OutinFragment();
				fTransaction.add(R.id.fl_content, outinFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(outinFg);
			}
			break;

		case R.id.rb_warehouse:
			if (warehouseFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				warehouseFg = new WarehouseFragment(getSupportFragmentManager());
				fTransaction.add(R.id.fl_content, warehouseFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(warehouseFg);
			}
			break;

		case R.id.rb_query:
			if (queryFg == null) {
				// 如果basicFg为空，创建一个并添加到界面上
				queryFg = new QueryFragment(getSupportFragmentManager());
				fTransaction.add(R.id.fl_content, queryFg);
			} else {
				// 如果basicFg不为空则直接显示在界面上
				fTransaction.show(queryFg);
			}
			break;

		case R.id.rb_me:
			if (LoginActivity.permission.equals("2")) {
				if (meFg == null) {
					// 如果basicFg为空，创建一个并添加到界面上
					meFg = new MeFragment();
					fTransaction.add(R.id.fl_content, meFg);
				} else {
					// 如果basicFg不为空则直接显示在界面上
					fTransaction.show(meFg);
				}
			} else {
				if (meotherFg == null) {
					// 如果basicFg为空，创建一个并添加到界面上
					meotherFg = new MeAnotherFragment();
					fTransaction.add(R.id.fl_content, meotherFg);
				} else {
					// 如果basicFg不为空则直接显示在界面上
					fTransaction.show(meotherFg);
				}
			}
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
		if (basicFg != null) {
			transaction.hide(basicFg);
		}
		if (outinFg != null) {
			transaction.hide(outinFg);
		}
		if (warehouseFg != null) {
			transaction.hide(warehouseFg);
		}
		if (queryFg != null) {
			transaction.hide(queryFg);
		}
		if (meFg != null) {
			transaction.hide(meFg);
		}
		if (meotherFg != null) {
			transaction.hide(meotherFg);
		}
	}

}
