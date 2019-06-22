package com.example.basic;

import java.util.List;

import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MainActivity;
import com.example.dbtable.Dept;
import com.example.dbtable.InboundOrder;
import com.example.dbtable.OutboundOrder;
import com.example.dbtable.PickOrder;
import com.example.dbtable.Picker;
import com.example.dbtable.PurchaseOrder;
import com.example.dbtable.Supply;
import com.example.listadapter.PickerListAdapter;
import com.example.wms.R;

public class BasicPickerActivity extends Activity implements OnClickListener {

	private ImageView arrow_back;

	private ListView picker_list;

	private List<Picker> pickerlist;

	private View basic_picker_activity;

	private TextView add;

	private Button picker_edit;
	private Button picker_del;
	private Button picker_create;

	private Picker picker;

	// static {
	// LitePal.deleteAll(Picker.class);
	// }
	//
	// //初始化Picker表并与Dept表建立联系
	// static {
	// Picker picker1 = new Picker();
	// picker1.setPickerID("q0001");
	// picker1.setPickerName("陈取货员");
	// picker1.setPhone("18795648695");
	// picker1.setDeptBelonged("dept0001");
	// picker1.save();
	//
	// Picker picker2 = new Picker();
	// picker2.setPickerID("q0002");
	// picker2.setPickerName("肖取货员");
	// picker2.setPhone("18857587575");
	// picker2.setDeptBelonged("dept0001");
	// picker2.save();
	// // 找出所属部门
	// Dept dept1 =
	// LitePal.where("DeptID=?",picker1.getDeptBelonged()).find(Dept.class).get(0);
	// // 添加picker1,picker2
	// dept1.getPickerList().add(picker1);
	// dept1.getPickerList().add(picker2);
	// dept1.save();
	//
	// Picker picker3 = new Picker();
	// picker3.setPickerID("q0003");
	// picker3.setPickerName("余取货员");
	// picker3.setPhone("15896765459");
	// picker3.setDeptBelonged("dept0002");
	// picker3.save();
	//
	// Picker picker4 = new Picker();
	// picker4.setPickerID("q0004");
	// picker4.setPickerName("赵取货员");
	// picker4.setPhone("13756966363");
	// picker4.setDeptBelonged("dept0002");
	// picker4.save();
	//
	// // 找出所属部门
	// Dept dept2 =
	// LitePal.where("DeptID=?",picker3.getDeptBelonged()).find(Dept.class).get(0);
	// // 添加picker3,picker4
	// dept2.getPickerList().add(picker3);
	// dept2.getPickerList().add(picker4);
	// dept2.save();
	//
	// Picker picker5 = new Picker();
	// picker5.setPickerID("q0005");
	// picker5.setPickerName("徐取货员");
	// picker5.setPhone("13967896565");
	// picker5.setDeptBelonged("dept0003");
	// picker5.save();
	//
	// // 找出所属部门
	// Dept dept3 =
	// LitePal.where("DeptID=?",picker5.getDeptBelonged()).find(Dept.class).get(0);
	// // 添加picker5
	// dept3.getPickerList().add(picker5);
	// dept3.save();
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_picker);

		init();
	}

	private void init() {
		add = (TextView) findViewById(R.id.tv_picker_add);
		arrow_back = (ImageView) findViewById(R.id.iv_picker_arrow_back);
		picker_list = (ListView) findViewById(R.id.picker_list);
		basic_picker_activity = (RelativeLayout) findViewById(R.id.basicpickeractivity);
		pickerlist = LitePal.where("id>?", "0").find(Picker.class);

		arrow_back.setOnClickListener(this);
		add.setOnClickListener(this);

		picker_list.setAdapter(new PickerListAdapter(BasicPickerActivity.this,
				pickerlist));

		picker_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				picker = pickerlist.get(position);
				// 用于PopupWindows的view2
				View view2 = LayoutInflater.from(BasicPickerActivity.this)
						.inflate(R.layout.basic_picker_popupwindows, null);
				picker_edit = (Button) view2.findViewById(R.id.bt_picker_edit);
				picker_del = (Button) view2.findViewById(R.id.bt_picker_del);
				picker_create = (Button) view2
						.findViewById(R.id.bt_picker_create);
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
				popwindow.showAtLocation(basic_picker_activity, Gravity.BOTTOM,
						0, 0);
				// 设置背景变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.3f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);

				// 给第三个按钮绑定事件操作
				// 修改操作
				picker_edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicPickerActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_picker, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicPickerActivity.this);
						builder.setTitle("修改取货员信息");
						// 得到所有的EditText并赋值
						final EditText et1 = (EditText) dialogview
								.findViewById(R.id.dialog_picker_et1);
						et1.setText(picker.getPickerID());
						final EditText et2 = (EditText) dialogview
								.findViewById(R.id.dialog_picker_et2);
						et2.setText(picker.getPickerName());
						final EditText et3 = (EditText) dialogview
								.findViewById(R.id.dialog_picker_et3);
						et3.setText(picker.getPhone());
						final EditText et4 = (EditText) dialogview
								.findViewById(R.id.dialog_picker_et4);
						et4.setText(picker.getDeptBelonged());

						et4.addTextChangedListener(new TextWatcher() {

							@Override
							public void onTextChanged(CharSequence s,
									int start, int before, int count) {
								// TODO Auto-generated method stub

							}

							@Override
							public void beforeTextChanged(CharSequence s,
									int start, int count, int after) {
								// TODO Auto-generated method stub

							}

							@Override
							public void afterTextChanged(Editable s) {
								Toast.makeText(BasicPickerActivity.this,
										"所属部门不能修改，请返回重新修改", Toast.LENGTH_SHORT)
										.show();
								return;
							}
						});

						builder.setPositiveButton("修改",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// 修改数据库
										Picker updatePicker = new Picker();
										updatePicker.setPickerID(et1.getText()
												.toString());
										updatePicker.setPickerName(et2
												.getText().toString());
										updatePicker.setPhone(et3.getText()
												.toString());
										updatePicker.setDeptBelonged(et4
												.getText().toString());
										updatePicker.update(picker.getId());

										Toast.makeText(
												BasicPickerActivity.this,
												"修改成功", Toast.LENGTH_SHORT)
												.show();

										// 刷新list表
										pickerlist = LitePal.where("id>?", "0")
												.find(Picker.class);
										picker_list
												.setAdapter(new PickerListAdapter(
														BasicPickerActivity.this,
														pickerlist));

									}
								});

						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();

					}
				});

				// 添加操作
				picker_create.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicPickerActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_picker, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicPickerActivity.this);
						builder.setTitle("添加取货员信息");
						builder.setPositiveButton("添加",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 得到所有的EditText
										EditText et1 = (EditText) dialogview
												.findViewById(R.id.dialog_picker_et1);
										EditText et2 = (EditText) dialogview
												.findViewById(R.id.dialog_picker_et2);
										EditText et3 = (EditText) dialogview
												.findViewById(R.id.dialog_picker_et3);
										EditText et4 = (EditText) dialogview
												.findViewById(R.id.dialog_picker_et4);

										List<Dept> depts = LitePal.where(
												"DeptID=?",
												et4.getText().toString()).find(
												Dept.class);
										if (depts.size() == 0) {
											Toast.makeText(
													BasicPickerActivity.this,
													"所属部门不存在，无法添加，请重试",
													Toast.LENGTH_SHORT).show();
											return;
										}

										// 存入数据库中
										Picker picker = new Picker();
										picker.setPickerID(et1.getText()
												.toString());
										picker.setPickerName(et2.getText()
												.toString());
										picker.setPhone(et3.getText()
												.toString());
										picker.setDeptBelonged(et4.getText()
												.toString());
										if (picker.save()) {
											// 创建关联并保存
											Dept d = depts.get(0);
											d.getPickerList().add(picker);
											d.save();
											Toast.makeText(
													BasicPickerActivity.this,
													"添加成功", Toast.LENGTH_SHORT)
													.show();
										} else {
											Toast.makeText(
													BasicPickerActivity.this,
													"添加失败", Toast.LENGTH_SHORT)
													.show();
										}
										// 刷新list表
										pickerlist = LitePal.where("id>?", "0")
												.find(Picker.class);
										picker_list
												.setAdapter(new PickerListAdapter(
														BasicPickerActivity.this,
														pickerlist));
									}
								});
						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 删除操作
				picker_del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicPickerActivity.this);
						builder.setTitle("删除取货员信息");
						builder.setMessage("是否删除此取货员信息？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 从数据库中删除此供应商信息
										try {
											LitePal.deleteAll(Picker.class,
													"id='" + picker.getId()
															+ "'");
											Toast.makeText(
													BasicPickerActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											// 刷新list表
											pickerlist = LitePal.where("id>?",
													"0").find(Picker.class);
											picker_list
													.setAdapter(new PickerListAdapter(
															BasicPickerActivity.this,
															pickerlist));
										} catch (Exception e) {
											Toast.makeText(
													BasicPickerActivity.this,
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
		case R.id.iv_picker_arrow_back:
			startActivity(new Intent(BasicPickerActivity.this,
					MainActivity.class));
			finish();
			break;

		case R.id.tv_picker_add:
			LayoutInflater factorys = LayoutInflater
					.from(BasicPickerActivity.this);
			final View dialogview = factorys.inflate(R.layout.dialog_picker,
					null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BasicPickerActivity.this);
			builder.setTitle("添加取货员信息");
			builder.setPositiveButton("添加",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 得到所有的EditText
							EditText et1 = (EditText) dialogview
									.findViewById(R.id.dialog_picker_et1);
							EditText et2 = (EditText) dialogview
									.findViewById(R.id.dialog_picker_et2);
							EditText et3 = (EditText) dialogview
									.findViewById(R.id.dialog_picker_et3);
							EditText et4 = (EditText) dialogview
									.findViewById(R.id.dialog_picker_et4);

							List<Dept> depts = LitePal.where("DeptID=?",
									et4.getText().toString()).find(Dept.class);
							if (depts.size() == 0) {
								Toast.makeText(BasicPickerActivity.this,
										"所属部门不存在，无法添加，请重试", Toast.LENGTH_SHORT)
										.show();
								return;
							}

							// 存入数据库中
							Picker picker = new Picker();
							picker.setPickerID(et1.getText().toString());
							picker.setPickerName(et2.getText().toString());
							picker.setPhone(et3.getText().toString());
							picker.setDeptBelonged(et4.getText().toString());
							if (picker.save()) {
								// 创建关联并保存
								Dept d = depts.get(0);
								d.getPickerList().add(picker);
								d.save();
								Toast.makeText(BasicPickerActivity.this,
										"添加成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(BasicPickerActivity.this,
										"添加失败", Toast.LENGTH_SHORT).show();
							}
							// 刷新list表
							pickerlist = LitePal.where("id>?", "0").find(
									Picker.class);
							picker_list.setAdapter(new PickerListAdapter(
									BasicPickerActivity.this, pickerlist));
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
