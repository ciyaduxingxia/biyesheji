package com.example.outin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.litepal.LitePal;

import com.example.dbtable.PickOrder;
import com.example.dbtable.PurchaseOrder;
import com.example.listadapter.PendingPcOrderAdapter;
import com.example.listadapter.PendingPickOrderAdapter;
import com.example.wms.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OutinPendingPickOrderActivity extends Activity implements
		OnClickListener {

	public ListView PickOrderList;

	private Context mContext;

	private ImageView arrow_back;

	private PickOrder pickorder;

	private View outin_pending_pickorder_activity;

	private Button pending_pickorder_sel;
	private Button pending_pickorder_del;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outin_out_pendingpickorder);
		mContext = OutinPendingPickOrderActivity.this;

		// 进入界面后对listview的所有项进行判断，看是否有过期的采购单
		judgment();

		init();
	}

	private void init() {
		arrow_back = (ImageView) findViewById(R.id.iv_pending_pickorder_arrow_back);
		PickOrderList = (ListView) findViewById(R.id.pending_pickorder_list);
		outin_pending_pickorder_activity = (RelativeLayout) findViewById(R.id.outin_pending_pickorder_activity);

		arrow_back.setOnClickListener(this);

		PickOrderList.setAdapter(new PendingPickOrderAdapter(mContext,
				pickorderRefreshBroadcastReceiver.pickorders));

		PickOrderList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pickorder = pickorderRefreshBroadcastReceiver.pickorders
						.get(position);

				View view2 = LayoutInflater.from(
						OutinPendingPickOrderActivity.this).inflate(
						R.layout.outin_pending_pcorder_popupwindows, null);
				pending_pickorder_sel = (Button) view2
						.findViewById(R.id.bt_pending_pcorder_select);
				pending_pickorder_del = (Button) view2
						.findViewById(R.id.bt_pending_pcorder_del);

				// 创建PopupWindow对象
				PopupWindow popwindow = new PopupWindow(view2,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT, true);
				// 设置加载动画
				popwindow.setAnimationStyle(R.style.animTranslate);
				// 为了点击非popwindow区域能够使其消失的三个设置
				// 设置能够响应点击事件
				popwindow.setTouchable(true);
				// 设置能够响应外部点击事件
				popwindow.setOutsideTouchable(true);
				// 设置背景
				popwindow.setBackgroundDrawable(new ColorDrawable(
						Color.TRANSPARENT));

				// 监听popwindow消失事件，消失时背景还原
				popwindow.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1.0f;
						getWindow().clearFlags(
								WindowManager.LayoutParams.FLAG_DIM_BEHIND);
						getWindow().setAttributes(lp);

					}
				});

				// 设置popwindow的显示的位置
				popwindow.showAtLocation(outin_pending_pickorder_activity,
						Gravity.BOTTOM, 0, 0);
				// 设置背景变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.3f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);

				// 查询操作
				pending_pickorder_sel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								OutinPendingPickOrderActivity.this);
						builder.setTitle("待处理取货单具体信息");
						builder.setMessage("取货单编号:"
								+ pickorder.getPickOrderID()
								+ "\n"
								+ "取货员编号:"
								+ pickorder.getPickerID()
								+ "\n"
								+ "管理员编号:"
								+ pickorder.getManagerID()
								+ "\n"
								+ "物品编号:"
								+ pickorder.getItemID()
								+ "\n"
								+ "物品名称:"
								+ pickorder.getItemName()
								+ "\n"
								+ "数量:"
								+ pickorder.getPcCount()
								+ "\n"
								+ "期限日期:"
								+ new SimpleDateFormat("yyyy-MM-dd")
										.format(pickorder.getPcDate()) + "\n"
								+ "状态:" + pickorder.getPcState());
						builder.setPositiveButton("确定", null);
						builder.create().show();

					}
				});

				// 删除操作
				pending_pickorder_del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								OutinPendingPickOrderActivity.this);
						builder.setTitle("删除取货单信息");
						builder.setMessage("是否删除此取货单信息？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 从数据库中删除此信息
										try {
											LitePal.deleteAll(PickOrder.class,
													"id='" + pickorder.getId()
															+ "'");
											Toast.makeText(
													OutinPendingPickOrderActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											// 刷新list表
											pickorderRefreshBroadcastReceiver.pickorders
													.remove(pickorder);
											PickOrderList
													.setAdapter(new PendingPickOrderAdapter(
															mContext,
															pickorderRefreshBroadcastReceiver.pickorders));
										} catch (Exception e) {
											Toast.makeText(mContext,
													"异常错误，请重试！",
													Toast.LENGTH_SHORT).show();
										}

									}
								});
						builder.setNegativeButton("取消", null);
						builder.create().show();

					}
				});

			}
		});

	}

	private void judgment() {
		Date nowDate = new Date();
		for (PickOrder po : pickorderRefreshBroadcastReceiver.pickorders) {
			// 如果期限日期在现在日期之前
			if ((po.getPcDate().compareTo(nowDate)) < 0) {
				// 过期，进行修改
				PickOrder p = new PickOrder();
				p.setPcState("已过期");
				p.update(po.getId());
				po.setPcState("已过期");
			}
		}

	}

	@Override
	public void onClick(View v) {
		finish();

	}
}
