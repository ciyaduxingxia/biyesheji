package com.example.basic;

import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MainActivity;
import com.example.dbtable.Supply;
import com.example.listadapter.SupplyListAdapter;
import com.example.wms.R;

public class BasicSupplyActivity extends Activity implements OnClickListener {

	private ImageView arrow_back;

	private ListView supply_list;

	private List<Supply> supplylist;

	private View basic_supply_activity;

	private Button supply_select;
	private Button supply_edit;
	private Button supply_del;
	private Button supply_create;

	private TextView add;

	private Supply supp;

	// static {
	// LitePal.deleteAll(Supply.class);
	// }
	//
	// // 初始化Supply表
	// static {
	// Supply supply1 = new Supply();
	// supply1.setSupplyID("gys0001");
	// supply1.setSupplyName("轴承1供应商");
	// supply1.setAddress("北京");
	// supply1.setPhone("13856933214");
	// supply1.setLinkMan("肖联系员");
	// supply1.setPostalCode("345214");
	// supply1.setItemType("小型轴承");
	// supply1.setItemName("1号轴承");
	// supply1.save();
	//
	// Supply supply2 = new Supply();
	// supply2.setSupplyID("gys0001");
	// supply2.setSupplyName("轴承1供应商");
	// supply2.setAddress("北京");
	// supply2.setPhone("13856933214");
	// supply2.setLinkMan("肖联系员");
	// supply2.setPostalCode("345214");
	// supply2.setItemType("小型轴承");
	// supply2.setItemName("2号轴承");
	// supply2.save();
	//
	// Supply supply3 = new Supply();
	// supply3.setSupplyID("gys0001");
	// supply3.setSupplyName("轴承1供应商");
	// supply3.setAddress("北京");
	// supply3.setPhone("13856933214");
	// supply3.setLinkMan("肖联系员");
	// supply3.setPostalCode("345214");
	// supply3.setItemType("中小型轴承");
	// supply3.setItemName("3号轴承");
	// supply3.save();
	//
	// Supply supply4 = new Supply();
	// supply4.setSupplyID("gys0001");
	// supply4.setSupplyName("轴承1供应商");
	// supply4.setAddress("北京");
	// supply4.setPhone("13856933214");
	// supply4.setLinkMan("肖联系员");
	// supply4.setPostalCode("345214");
	// supply4.setItemType("中小型轴承");
	// supply4.setItemName("4号轴承");
	// supply4.save();
	//
	// Supply supply5 = new Supply();
	// supply5.setSupplyID("gys0002");
	// supply5.setSupplyName("轴承2供应商");
	// supply5.setAddress("天津");
	// supply5.setPhone("13956635742");
	// supply5.setLinkMan("赵联系员");
	// supply5.setPostalCode("425748");
	// supply5.setItemType("中型轴承");
	// supply5.setItemName("5号轴承");
	// supply5.save();
	//
	// Supply supply6 = new Supply();
	// supply6.setSupplyID("gys0002");
	// supply6.setSupplyName("轴承2供应商");
	// supply6.setAddress("天津");
	// supply6.setPhone("13956635742");
	// supply6.setLinkMan("赵联系员");
	// supply6.setPostalCode("425748");
	// supply6.setItemType("中型轴承");
	// supply6.setItemName("6号轴承");
	// supply6.save();
	//
	// Supply supply7 = new Supply();
	// supply7.setSupplyID("gys0002");
	// supply7.setSupplyName("轴承2供应商");
	// supply7.setAddress("天津");
	// supply7.setPhone("13956635742");
	// supply7.setLinkMan("赵联系员");
	// supply7.setPostalCode("425748");
	// supply7.setItemType("大型轴承");
	// supply7.setItemName("7号轴承");
	// supply7.save();
	//
	// Supply supply8 = new Supply();
	// supply8.setSupplyID("gys0002");
	// supply8.setSupplyName("轴承2供应商");
	// supply8.setAddress("天津");
	// supply8.setPhone("13956635742");
	// supply8.setLinkMan("赵联系员");
	// supply8.setPostalCode("425748");
	// supply8.setItemType("大型轴承");
	// supply8.setItemName("8号轴承");
	// supply8.save();
	//
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_supply);

		init();
	}

	private void init() {
		add = (TextView) findViewById(R.id.tv_supply_add);
		arrow_back = (ImageView) findViewById(R.id.iv_supply_arrow_back);
		supply_list = (ListView) findViewById(R.id.supply_list);
		basic_supply_activity = (RelativeLayout) findViewById(R.id.basicsupplyactivity);
		supplylist = LitePal.where("id>?", "0").find(Supply.class);

		arrow_back.setOnClickListener(this);
		add.setOnClickListener(this);

		supply_list.setAdapter(new SupplyListAdapter(BasicSupplyActivity.this,
				supplylist));

		supply_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				supp = supplylist.get(position);
				// Log.i("供应商信息",
				// supply.getId()+supply.getSupplyID()+supply.getSupplyName());
				// 用于PopupWindows的view2
				View view2 = LayoutInflater.from(BasicSupplyActivity.this)
						.inflate(R.layout.basic_supply_popupwindows, null);
				supply_select = (Button) view2
						.findViewById(R.id.bt_supply_select);
				supply_edit = (Button) view2.findViewById(R.id.bt_supply_edit);
				supply_del = (Button) view2.findViewById(R.id.bt_supply_del);
				supply_create = (Button) view2
						.findViewById(R.id.bt_supply_create);
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
				popwindow.showAtLocation(basic_supply_activity, Gravity.BOTTOM,
						0, 0);
				// 设置背景变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.3f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);

				// 给四个按钮绑定事件操作
				// 查询操作
				supply_select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicSupplyActivity.this);
						builder.setTitle("供应商具体信息");
						builder.setMessage("供应商编号:" + supp.getSupplyID() + "\n"
								+ "供应商名称:" + supp.getSupplyName() + "\n"
								+ "供应商地址:" + supp.getAddress() + "\n"
								+ "供应商联系人:" + supp.getLinkMan() + "\n"
								+ "供应商邮编:" + supp.getPostalCode() + "\n"
								+ "供应商联系方式:" + supp.getPhone() + "\n" + "供应类别:"
								+ supp.getItemType() + "\n" + "供应物品:"
								+ supp.getItemName());
						builder.setPositiveButton("确定", null);
						builder.create().show();

					}
				});

				// 修改操作
				supply_edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicSupplyActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_supply, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicSupplyActivity.this);
						builder.setTitle("修改供应商信息");
						// 得到所有的EditText并赋值
						final EditText et1 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et1);
						et1.setText(supp.getSupplyID());
						final EditText et2 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et2);
						et2.setText(supp.getSupplyName());
						final EditText et3 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et3);
						et3.setText(supp.getAddress());
						final EditText et4 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et4);
						et4.setText(supp.getLinkMan());
						final EditText et5 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et5);
						et5.setText(supp.getPhone());
						final EditText et6 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et6);
						et6.setText(supp.getPostalCode());
						final EditText et7 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et7);
						et7.setText(supp.getItemType());
						final EditText et8 = (EditText) dialogview
								.findViewById(R.id.dialog_supply_et8);
						et8.setText(supp.getItemName());
						builder.setPositiveButton("修改",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// 修改数据库
										Supply updateSupply = new Supply();
										updateSupply.setSupplyID(et1.getText()
												.toString());
										updateSupply.setSupplyName(et2
												.getText().toString());
										updateSupply.setAddress(et3.getText()
												.toString());
										updateSupply.setLinkMan(et4.getText()
												.toString());
										updateSupply.setPhone(et5.getText()
												.toString());
										updateSupply.setPostalCode(et6
												.getText().toString());
										updateSupply.setItemType(et7.getText()
												.toString());
										updateSupply.setItemName(et8.getText()
												.toString());
										updateSupply.update(supp.getId());

										Toast.makeText(
												BasicSupplyActivity.this,
												"修改成功", Toast.LENGTH_SHORT)
												.show();

										// 刷新list表
										supplylist = LitePal.where("id>?", "0")
												.find(Supply.class);
										supply_list
												.setAdapter(new SupplyListAdapter(
														BasicSupplyActivity.this,
														supplylist));

									}
								});

						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 添加操作
				supply_create.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicSupplyActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_supply, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicSupplyActivity.this);
						builder.setTitle("添加供应商信息");
						builder.setPositiveButton("添加",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 得到所有的EditText
										EditText et1 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et1);
										EditText et2 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et2);
										EditText et3 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et3);
										EditText et4 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et4);
										EditText et5 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et5);
										EditText et6 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et6);
										EditText et7 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et7);
										EditText et8 = (EditText) dialogview
												.findViewById(R.id.dialog_supply_et8);

										// 存入数据库中
										Supply supply = new Supply();
										supply.setSupplyID(et1.getText()
												.toString());
										supply.setSupplyName(et2.getText()
												.toString());
										supply.setAddress(et3.getText()
												.toString());
										supply.setLinkMan(et4.getText()
												.toString());
										supply.setPhone(et5.getText()
												.toString());
										supply.setPostalCode(et6.getText()
												.toString());
										supply.setItemType(et7.getText()
												.toString());
										supply.setItemName(et8.getText()
												.toString());
										if (supply.save()) {
											Toast.makeText(
													BasicSupplyActivity.this,
													"添加成功", Toast.LENGTH_SHORT)
													.show();
										} else {
											Toast.makeText(
													BasicSupplyActivity.this,
													"添加失败", Toast.LENGTH_SHORT)
													.show();
										}
										// 刷新list表
										supplylist = LitePal.where("id>?", "0")
												.find(Supply.class);
										supply_list
												.setAdapter(new SupplyListAdapter(
														BasicSupplyActivity.this,
														supplylist));
									}
								});
						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 删除操作
				supply_del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicSupplyActivity.this);
						builder.setTitle("删除供应商信息");
						builder.setMessage("是否删除此供应商信息？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 从数据库中删除此供应商信息
										try {
											LitePal.deleteAll(Supply.class,
													"id='" + supp.getId() + "'");
											Toast.makeText(
													BasicSupplyActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											// 刷新list表
											supplylist = LitePal.where("id>?",
													"0").find(Supply.class);
											supply_list
													.setAdapter(new SupplyListAdapter(
															BasicSupplyActivity.this,
															supplylist));
										} catch (Exception e) {
											Toast.makeText(
													BasicSupplyActivity.this,
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_supply_arrow_back:
			startActivity(new Intent(BasicSupplyActivity.this,
					MainActivity.class));
			finish();
			break;

		case R.id.tv_supply_add:
			LayoutInflater factorys = LayoutInflater
					.from(BasicSupplyActivity.this);
			final View dialogview = factorys.inflate(R.layout.dialog_supply,
					null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BasicSupplyActivity.this);
			builder.setTitle("添加供应商信息");
			builder.setPositiveButton("添加",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 得到所有的EditText
							EditText et1 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et1);
							EditText et2 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et2);
							EditText et3 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et3);
							EditText et4 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et4);
							EditText et5 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et5);
							EditText et6 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et6);
							EditText et7 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et7);
							EditText et8 = (EditText) dialogview
									.findViewById(R.id.dialog_supply_et8);

							// 存入数据库中
							Supply supply = new Supply();
							supply.setSupplyID(et1.getText().toString());
							supply.setSupplyName(et2.getText().toString());
							supply.setAddress(et3.getText().toString());
							supply.setLinkMan(et4.getText().toString());
							supply.setPhone(et5.getText().toString());
							supply.setPostalCode(et6.getText().toString());
							supply.setItemType(et7.getText().toString());
							supply.setItemName(et8.getText().toString());
							if (supply.save()) {
								Toast.makeText(BasicSupplyActivity.this,
										"添加成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(BasicSupplyActivity.this,
										"添加失败", Toast.LENGTH_SHORT).show();
							}
							// 刷新list表
							supplylist = LitePal.where("id>?", "0").find(
									Supply.class);
							supply_list.setAdapter(new SupplyListAdapter(
									BasicSupplyActivity.this, supplylist));
						}
					});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.setView(dialogview);
			builder.show();
			break;

		default:
			break;
		}

	}

}
