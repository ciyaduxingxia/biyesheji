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
import com.example.dbtable.Supply;
import com.example.listadapter.DeptListAdapter;
import com.example.listadapter.SupplyListAdapter;
import com.example.wms.R;

public class BasicDeptActivity extends Activity implements OnClickListener {

	private ImageView arrow_back;

	private ListView dept_list;

	private List<Dept> deptlist;

	private View basic_dept_activity;

	private TextView add;

	private Button dept_edit;
	private Button dept_del;
	private Button dept_create;

	private Dept dept;

	// static {
	// LitePal.deleteAll(Dept.class);
	// }
	//
	// // 初始化Dept表
	// static {
	// Dept dept1 = new Dept();
	// dept1.setDeptID("dept0001");
	// dept1.setDeptName("1号轴承生产厂");
	// dept1.setPhone("18856966773");
	// dept1.save();
	//
	// Dept dept2 = new Dept();
	// dept2.setDeptID("dept0002");
	// dept2.setDeptName("2号轴承生产厂");
	// dept2.setPhone("18769457364");
	// dept2.save();
	//
	// Dept dept3 = new Dept();
	// dept3.setDeptID("dept0003");
	// dept3.setDeptName("3号轴承生产厂");
	// dept3.setPhone("137649857643");
	// dept3.save();
	//
	// Dept dept4 = new Dept();
	// dept4.setDeptID("dept0004");
	// dept4.setDeptName("4号轴承生产厂");
	// dept4.setPhone("15879468643");
	// dept4.save();
	//
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_dept);

		init();
	}

	private void init() {
		add = (TextView) findViewById(R.id.tv_dept_add);
		arrow_back = (ImageView) findViewById(R.id.iv_dept_arrow_back);
		dept_list = (ListView) findViewById(R.id.dept_list);
		basic_dept_activity = (RelativeLayout) findViewById(R.id.basicdeptactivity);
		deptlist = LitePal.where("id>?", "0").find(Dept.class);

		arrow_back.setOnClickListener(this);
		add.setOnClickListener(this);

		dept_list.setAdapter(new DeptListAdapter(BasicDeptActivity.this,
				deptlist));

		dept_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dept = deptlist.get(position);
				// 用于PopupWindows的view2
				View view2 = LayoutInflater.from(BasicDeptActivity.this)
						.inflate(R.layout.basic_dept_popupwindows, null);
				dept_edit = (Button) view2.findViewById(R.id.bt_dept_edit);
				dept_del = (Button) view2.findViewById(R.id.bt_dept_del);
				dept_create = (Button) view2.findViewById(R.id.bt_dept_create);
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
				popwindow.showAtLocation(basic_dept_activity, Gravity.BOTTOM,
						0, 0);
				// 设置背景变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.3f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);

				// 给第三个按钮绑定事件操作
				// 修改操作
				dept_edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicDeptActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_dept, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicDeptActivity.this);
						builder.setTitle("修改部门信息");
						// 得到所有的EditText并赋值
						final EditText et1 = (EditText) dialogview
								.findViewById(R.id.dialog_dept_et1);
						et1.setText(dept.getDeptID());
						final EditText et2 = (EditText) dialogview
								.findViewById(R.id.dialog_dept_et2);
						et2.setText(dept.getDeptName());
						final EditText et3 = (EditText) dialogview
								.findViewById(R.id.dialog_dept_et3);
						et3.setText(dept.getPhone());

						builder.setPositiveButton("修改",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// 修改数据库
										Dept updateDept = new Dept();
										updateDept.setDeptID(et1.getText()
												.toString());
										updateDept.setDeptName(et2.getText()
												.toString());
										updateDept.setPhone(et3.getText()
												.toString());
										updateDept.update(dept.getId());

										Toast.makeText(BasicDeptActivity.this,
												"修改成功", Toast.LENGTH_SHORT)
												.show();

										// 刷新list表
										deptlist = LitePal.where("id>?", "0")
												.find(Dept.class);
										dept_list
												.setAdapter(new DeptListAdapter(
														BasicDeptActivity.this,
														deptlist));

									}
								});

						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();

					}
				});

				// 添加操作
				dept_create.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicDeptActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_dept, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicDeptActivity.this);
						builder.setTitle("添加部门信息");
						builder.setPositiveButton("添加",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 得到所有的EditText
										EditText et1 = (EditText) dialogview
												.findViewById(R.id.dialog_dept_et1);
										EditText et2 = (EditText) dialogview
												.findViewById(R.id.dialog_dept_et2);
										EditText et3 = (EditText) dialogview
												.findViewById(R.id.dialog_dept_et3);

										// 存入数据库中
										Dept dep = new Dept();
										dep.setDeptID(et1.getText().toString());
										dep.setDeptName(et2.getText()
												.toString());
										dep.setPhone(et3.getText().toString());
										if (dep.save()) {
											Toast.makeText(
													BasicDeptActivity.this,
													"添加成功", Toast.LENGTH_SHORT)
													.show();
										} else {
											Toast.makeText(
													BasicDeptActivity.this,
													"添加失败", Toast.LENGTH_SHORT)
													.show();
										}
										// 刷新list表
										deptlist = LitePal.where("id>?", "0")
												.find(Dept.class);
										dept_list
												.setAdapter(new DeptListAdapter(
														BasicDeptActivity.this,
														deptlist));
									}
								});
						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 删除操作
				dept_del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicDeptActivity.this);
						builder.setTitle("删除部门信息");
						builder.setMessage("是否删除此部门信息？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 从数据库中删除此供应商信息
										try {
											LitePal.deleteAll(Dept.class,
													"id='" + dept.getId() + "'");
											Toast.makeText(
													BasicDeptActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											// 刷新list表
											deptlist = LitePal.where("id>?",
													"0").find(Dept.class);
											dept_list
													.setAdapter(new DeptListAdapter(
															BasicDeptActivity.this,
															deptlist));
										} catch (Exception e) {
											Toast.makeText(
													BasicDeptActivity.this,
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
		case R.id.iv_dept_arrow_back:
			startActivity(new Intent(BasicDeptActivity.this, MainActivity.class));
			finish();
			break;

		case R.id.tv_dept_add:
			LayoutInflater factorys = LayoutInflater
					.from(BasicDeptActivity.this);
			final View dialogview = factorys
					.inflate(R.layout.dialog_dept, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BasicDeptActivity.this);
			builder.setTitle("添加部门信息");
			builder.setPositiveButton("添加",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 得到所有的EditText
							EditText et1 = (EditText) dialogview
									.findViewById(R.id.dialog_dept_et1);
							EditText et2 = (EditText) dialogview
									.findViewById(R.id.dialog_dept_et2);
							EditText et3 = (EditText) dialogview
									.findViewById(R.id.dialog_dept_et3);

							// 存入数据库中
							Dept dep = new Dept();
							dep.setDeptID(et1.getText().toString());
							dep.setDeptName(et2.getText().toString());
							dep.setPhone(et3.getText().toString());
							if (dep.save()) {
								Toast.makeText(BasicDeptActivity.this, "添加成功",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(BasicDeptActivity.this, "添加失败",
										Toast.LENGTH_SHORT).show();
							}
							// 刷新list表
							deptlist = LitePal.where("id>?", "0").find(
									Dept.class);
							dept_list.setAdapter(new DeptListAdapter(
									BasicDeptActivity.this, deptlist));
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
