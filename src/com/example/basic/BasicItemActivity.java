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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.example.dbtable.Item;
import com.example.listadapter.ItemListAdapter;
import com.example.wms.R;

public class BasicItemActivity extends Activity implements OnClickListener {

	private ImageView arrow_back;

	private ListView item_list;

	private List<Item> itemlist;

	private View basic_item_activity;

	private TextView add;

	private Button item_select;
	private Button item_edit;
	private Button item_del;
	private Button item_create;

	private Item item;

	// static {
	// LitePal.deleteAll(Item.class);
	// }
	//
	// // 初始化Item表
	// static {
	// Item item1 = new Item();
	// item1.setItemID("zc0001");
	// item1.setItemName("1号轴承");
	// item1.setItemType("小型轴承");
	// item1.setCount("120");
	// item1.setUnit("件");
	// item1.setLocation("第1货架第1层");
	// item1.setPrice("30");
	// item1.setUpperLimit("500");
	// item1.setLowerLimit("40");
	// item1.save();
	//
	// Item item2 = new Item();
	// item2.setItemID("zc0002");
	// item2.setItemName("2号轴承");
	// item2.setItemType("小型轴承");
	// item2.setCount("250");
	// item2.setUnit("件");
	// item2.setLocation("第1货架第2层");
	// item2.setPrice("35");
	// item2.setUpperLimit("600");
	// item2.setLowerLimit("30");
	// item2.save();
	//
	// Item item3 = new Item();
	// item3.setItemID("zc0003");
	// item3.setItemName("3号轴承");
	// item3.setItemType("中小型轴承");
	// item3.setCount("96");
	// item3.setUnit("件");
	// item3.setLocation("第2货架第1层");
	// item3.setPrice("45");
	// item3.setUpperLimit("200");
	// item3.setLowerLimit("15");
	// item3.save();
	//
	// Item item4 = new Item();
	// item4.setItemID("zc0004");
	// item4.setItemName("4号轴承");
	// item4.setItemType("中小型轴承");
	// item4.setCount("60");
	// item4.setUnit("件");
	// item4.setLocation("第2货架第2层");
	// item4.setPrice("65");
	// item4.setUpperLimit("130");
	// item4.setLowerLimit("10");
	// item4.save();
	//
	// Item item5 = new Item();
	// item5.setItemID("zc0005");
	// item5.setItemName("5号轴承");
	// item5.setItemType("中型轴承");
	// item5.setCount("88");
	// item5.setUnit("件");
	// item5.setLocation("第2货架第3层");
	// item5.setPrice("85");
	// item5.setUpperLimit("160");
	// item5.setLowerLimit("15");
	// item5.save();
	//
	// Item item6 = new Item();
	// item6.setItemID("zc0006");
	// item6.setItemName("6号轴承");
	// item6.setItemType("中型轴承");
	// item6.setCount("45");
	// item6.setUnit("件");
	// item6.setLocation("第3货架第1层");
	// item6.setPrice("75");
	// item6.setUpperLimit("120");
	// item6.setLowerLimit("10");
	// item6.save();
	//
	// Item item7 = new Item();
	// item7.setItemID("zc0007");
	// item7.setItemName("7号轴承");
	// item7.setItemType("大型轴承");
	// item7.setCount("40");
	// item7.setUnit("件");
	// item7.setLocation("第3货架第2层");
	// item7.setPrice("105");
	// item7.setUpperLimit("100");
	// item7.setLowerLimit("10");
	// item7.save();
	//
	// Item item8 = new Item();
	// item8.setItemID("zc0008");
	// item8.setItemName("8号轴承");
	// item8.setItemType("大型轴承");
	// item8.setCount("35");
	// item8.setUnit("件");
	// item8.setLocation("第3货架第3层");
	// item8.setPrice("115");
	// item8.setUpperLimit("80");
	// item8.setLowerLimit("5");
	// item8.save();
	//
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_item);

		init();
	}

	private void init() {
		add = (TextView) findViewById(R.id.tv_item_add);
		arrow_back = (ImageView) findViewById(R.id.iv_item_arrow_back);
		item_list = (ListView) findViewById(R.id.item_list);
		basic_item_activity = (RelativeLayout) findViewById(R.id.basicitemactivity);
		itemlist = LitePal.where("id>?", "0").find(Item.class);

		arrow_back.setOnClickListener(this);
		add.setOnClickListener(this);

		item_list.setAdapter(new ItemListAdapter(BasicItemActivity.this,
				itemlist));

		item_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				item = itemlist.get(position);
				// Log.i("供应商信息",
				// supply.getId()+supply.getSupplyID()+supply.getSupplyName());
				// 用于PopupWindows的view2
				View view2 = LayoutInflater.from(BasicItemActivity.this)
						.inflate(R.layout.basic_item_popupwindows, null);
				item_select = (Button) view2.findViewById(R.id.bt_item_select);
				item_edit = (Button) view2.findViewById(R.id.bt_item_edit);
				item_del = (Button) view2.findViewById(R.id.bt_item_del);
				item_create = (Button) view2.findViewById(R.id.bt_item_create);
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
				popwindow.showAtLocation(basic_item_activity, Gravity.BOTTOM,
						0, 0);
				// 设置背景变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.3f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);

				// 给四个按钮绑定事件操作
				// 查询操作
				item_select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicItemActivity.this);
						builder.setTitle("物品具体信息");
						builder.setMessage("物品编号:" + item.getItemID() + "\n"
								+ "物品名称:" + item.getItemName() + "\n" + "物品类型:"
								+ item.getItemType() + "\n" + "物品数量:"
								+ item.getCount() + "\n" + "物品单位:"
								+ item.getUnit() + "\n" + "所处位置:"
								+ item.getLocation() + "\n" + "物品价格:"
								+ item.getPrice() + "\n" + "物品上限:"
								+ item.getUpperLimit() + "\n" + "物品下限:"
								+ item.getLowerLimit());
						builder.setPositiveButton("确定", null);
						builder.create().show();

					}
				});

				// 修改操作
				item_edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicItemActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_item, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicItemActivity.this);
						builder.setTitle("修改供应商信息");
						// 得到所有的EditText并赋值
						final EditText et1 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et1);
						et1.setText(item.getItemID());
						final EditText et2 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et2);
						et2.setText(item.getItemName());
						final EditText et3 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et3);
						et3.setText(item.getItemType());
						final EditText et4 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et4);
						et4.setText(item.getCount());
						final EditText et5 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et5);
						et5.setText(item.getUnit());
						final EditText et6 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et6);
						et6.setText(item.getLocation());
						final EditText et7 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et7);
						et7.setText(item.getPrice());
						final EditText et8 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et8);
						et8.setText(item.getUpperLimit());
						final EditText et9 = (EditText) dialogview
								.findViewById(R.id.dialog_item_et9);
						et9.setText(item.getLowerLimit());
						builder.setPositiveButton("修改",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// 修改数据库
										Item updateItem = new Item();
										updateItem.setItemID(et1.getText()
												.toString());
										updateItem.setItemName(et2.getText()
												.toString());
										updateItem.setItemType(et3.getText()
												.toString());
										updateItem.setCount(et4.getText()
												.toString());
										updateItem.setUnit(et5.getText()
												.toString());
										updateItem.setLocation(et6.getText()
												.toString());
										updateItem.setPrice(et7.getText()
												.toString());
										updateItem.setUpperLimit(et8.getText()
												.toString());
										updateItem.setLowerLimit(et9.getText()
												.toString());
										updateItem.update(item.getId());

										Toast.makeText(BasicItemActivity.this,
												"修改成功", Toast.LENGTH_SHORT)
												.show();

										// 刷新list表
										itemlist = LitePal.where("id>?", "0")
												.find(Item.class);
										item_list
												.setAdapter(new ItemListAdapter(
														BasicItemActivity.this,
														itemlist));

									}
								});

						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 添加操作
				item_create.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// layoutInflater要随用随生成,否则会报错child view already has a
						// parent
						LayoutInflater factorys = LayoutInflater
								.from(BasicItemActivity.this);
						final View dialogview = factorys.inflate(
								R.layout.dialog_item, null);
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicItemActivity.this);
						builder.setTitle("添加供应商信息");
						builder.setPositiveButton("添加",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 得到所有的EditText
										EditText et1 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et1);
										EditText et2 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et2);
										EditText et3 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et3);
										EditText et4 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et4);
										EditText et5 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et5);
										EditText et6 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et6);
										EditText et7 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et7);
										EditText et8 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et8);
										EditText et9 = (EditText) dialogview
												.findViewById(R.id.dialog_item_et9);

										// 存入数据库中
										Item item = new Item();
										item.setItemID(et1.getText().toString());
										item.setItemName(et2.getText()
												.toString());
										item.setItemType(et3.getText()
												.toString());
										item.setCount(et4.getText().toString());
										item.setUnit(et5.getText().toString());
										item.setLocation(et6.getText()
												.toString());
										item.setPrice(et7.getText().toString());
										item.setUpperLimit(et8.getText()
												.toString());
										item.setLowerLimit(et9.getText()
												.toString());

										if (item.save()) {
											Toast.makeText(
													BasicItemActivity.this,
													"添加成功", Toast.LENGTH_SHORT)
													.show();
										} else {
											Toast.makeText(
													BasicItemActivity.this,
													"添加失败", Toast.LENGTH_SHORT)
													.show();
										}
										// 刷新list表
										itemlist = LitePal.where("id>?", "0")
												.find(Item.class);
										item_list
												.setAdapter(new ItemListAdapter(
														BasicItemActivity.this,
														itemlist));
									}
								});

						builder.setNegativeButton("取消", null);
						builder.create();
						builder.setView(dialogview);
						builder.show();
					}
				});

				// 删除操作
				item_del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BasicItemActivity.this);
						builder.setTitle("删除物品信息");
						builder.setMessage("是否删除此物品信息？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 从数据库中删除此物品信息
										try {
											LitePal.deleteAll(Item.class,
													"id='" + item.getId() + "'");
											Toast.makeText(
													BasicItemActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											// 刷新list表
											itemlist = LitePal.where("id>?",
													"0").find(Item.class);
											item_list
													.setAdapter(new ItemListAdapter(
															BasicItemActivity.this,
															itemlist));
										} catch (Exception e) {
											Toast.makeText(
													BasicItemActivity.this,
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
		case R.id.iv_item_arrow_back:
			startActivity(new Intent(BasicItemActivity.this, MainActivity.class));
			finish();
			break;

		case R.id.tv_item_add:
			LayoutInflater factorys = LayoutInflater
					.from(BasicItemActivity.this);
			final View dialogview = factorys
					.inflate(R.layout.dialog_item, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BasicItemActivity.this);
			builder.setTitle("添加供应商信息");
			builder.setPositiveButton("添加",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 得到所有的EditText
							EditText et1 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et1);
							EditText et2 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et2);
							EditText et3 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et3);
							EditText et4 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et4);
							EditText et5 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et5);
							EditText et6 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et6);
							EditText et7 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et7);
							EditText et8 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et8);
							EditText et9 = (EditText) dialogview
									.findViewById(R.id.dialog_item_et9);

							// 存入数据库中
							Item item = new Item();
							item.setItemID(et1.getText().toString());
							item.setItemName(et2.getText().toString());
							item.setItemType(et3.getText().toString());
							item.setCount(et4.getText().toString());
							item.setUnit(et5.getText().toString());
							item.setLocation(et6.getText().toString());
							item.setPrice(et7.getText().toString());
							item.setUpperLimit(et8.getText().toString());
							item.setLowerLimit(et9.getText().toString());

							if (item.save()) {
								Toast.makeText(BasicItemActivity.this, "添加成功",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(BasicItemActivity.this, "添加失败",
										Toast.LENGTH_SHORT).show();
							}
							// 刷新list表
							itemlist = LitePal.where("id>?", "0").find(
									Item.class);
							item_list.setAdapter(new ItemListAdapter(
									BasicItemActivity.this, itemlist));
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
