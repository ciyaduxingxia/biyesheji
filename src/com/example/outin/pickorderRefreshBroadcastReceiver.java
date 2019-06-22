package com.example.outin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.litepal.LitePal;

import com.example.dbtable.PickOrder;
import com.example.dbtable.PurchaseOrder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class pickorderRefreshBroadcastReceiver extends BroadcastReceiver {

	// 用于作为数据源
	public static List<PickOrder> pickorders = new ArrayList<PickOrder>();

	static {

		// 先把非已处理的拿到数据源中
		List<PickOrder> lists = LitePal.where("pcstate!=?", "已处理").find(
				PickOrder.class);
		// 添加过一次就不再添加
		if (lists.size() > 0 && pickorders.size() == 0) {
			for (PickOrder list : lists) {
				pickorders.add(list);
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// 在这个方法中只执行数据源的数据的操作
		if (intent.getAction().equals("action.refreshPickOrder")) {
			// 再添加新添加的采购单
			List<PickOrder> lists = LitePal.where("PickOrderID=?",
					intent.getStringExtra("ID")).find(PickOrder.class);
			for (PickOrder pickorder : lists) {
				pickorders.add(pickorder);
			}

			// Log.i("数据", pcorder.getItemID() + pcorder.getItemName());
		} else {
			// 删除已处理的采购单
			List<PickOrder> lists = LitePal.where("PickOrderID=?",
					intent.getStringExtra("ID")).find(PickOrder.class);
			// 已处理，进行修改
			for (PickOrder pickorder : lists) {
				PickOrder p = new PickOrder();
				p.setPcState("已处理");
				p.update(pickorder.getId());
			}
			// 用迭代器删除
			Iterator<PickOrder> it = pickorders.iterator();
			while (it.hasNext()) {
				PickOrder order = it.next();
				if (order.getPickOrderID().equals(lists.get(0).getPickOrderID())) {
					it.remove();
				}

			}
		}

	}

}
