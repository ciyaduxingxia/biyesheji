package com.example.outin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

import org.litepal.LitePal;

import com.example.dbtable.PickOrder;
import com.example.dbtable.PurchaseOrder;
import com.example.fragment.InFragment;
import com.example.fragment.PickorderFragment;
import com.example.wms.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OutinPickorderActivity extends Activity implements OnClickListener {

	private Context mContext;

	private ImageView arrow_back;

	private TextView message;

	private TextView look_pickorder;

	private TextView edit_pickorder;

	// fragment Object
	private PickorderFragment pickorderFg;
	private FragmentManager fManager;
	
//	static {
//		LitePal.deleteAll(PickOrder.class);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_out_pickorder);
		mContext = OutinPickorderActivity.this;
		fManager = getFragmentManager();

		// 初始化布局元素
		initView();
	}

	private void initView() {
		look_pickorder = (TextView) findViewById(R.id.tv_out_look_pickorder);
		edit_pickorder = (TextView) findViewById(R.id.tv_out_edit_pickorder);
		message = (TextView) findViewById(R.id.tv_out_new_pickorder);
		arrow_back = (ImageView) findViewById(R.id.iv_pickorder_arrow_back);
		
		message.setVisibility(View.VISIBLE);
		look_pickorder.setVisibility(View.VISIBLE);
		edit_pickorder.setVisibility(View.VISIBLE);

		look_pickorder.setOnClickListener(this);
		edit_pickorder.setOnClickListener(this);
		arrow_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		FragmentTransaction fTransaction = fManager.beginTransaction();
		switch (v.getId()) {
		case R.id.iv_pickorder_arrow_back:
			finish();
			
			break;
		case R.id.tv_out_look_pickorder:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					OutinPickorderActivity.this);
			builder.setTitle("取货申请信息");
			builder.setMessage("取货员编号: q0001" + "\n" + "物品编号: zc0001" + "\n"
					+ "物品名称: 1号轴承" + "\n" + "数量: 20" + "\n" + "取货日期:"
					+ "2019-06-04");
			builder.setPositiveButton("确定", null);
			builder.create().show();
			break;

		case R.id.tv_out_edit_pickorder:
			if (pickorderFg == null) {
				// 如果pickorderFg为空，创建一个并添加到界面上
				pickorderFg = new PickorderFragment();
				fTransaction.add(R.id.fl_outin_pickorder_content, pickorderFg);
				message.setVisibility(View.INVISIBLE);
				look_pickorder.setVisibility(View.INVISIBLE);
				edit_pickorder.setVisibility(View.INVISIBLE);
			} else {
				// 如果pickorderFg不为空则直接显示在界面上
				fTransaction.show(pickorderFg);
			}
			break;

		default:
			break;
		}

		// 提交事务
		fTransaction.commit();

	}
}
