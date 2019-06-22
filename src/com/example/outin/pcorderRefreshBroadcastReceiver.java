package com.example.outin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.litepal.LitePal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.dbtable.PurchaseOrder;

public class pcorderRefreshBroadcastReceiver extends BroadcastReceiver {

	// 用于作为数据源
	public static List<PurchaseOrder> pcorders = new ArrayList<PurchaseOrder>();

	static {

		// 先把非已处理的拿到数据源中
		List<PurchaseOrder> lists = LitePal.where("pcstate!=?", "已处理").find(
				PurchaseOrder.class);
		// 添加过一次就不再添加
		if (pcorders.size() == 0) {
			for (PurchaseOrder list : lists) {
				pcorders.add(list);
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		// 在这个方法中只执行数据源的数据的操作
		if (intent.getAction().equals("action.refreshPcOrder")) {
			// 再添加新添加的采购单
			List<PurchaseOrder> lists = LitePal.where("PcOrderID=?",
					intent.getStringExtra("ID")).find(PurchaseOrder.class);

			for (PurchaseOrder pcorder : lists) {
				pcorders.add(pcorder);
			}

			// Log.i("数据", pcorder.getItemID() + pcorder.getItemName());
		} else {
			// 删除已处理的采购单
			List<PurchaseOrder> lists = LitePal.where("PcOrderID=?",
					intent.getStringExtra("ID")).find(PurchaseOrder.class);
			// 已处理，进行修改
			for (PurchaseOrder pcorder : lists) {
				PurchaseOrder p = new PurchaseOrder();
				p.setPcState("已处理");
				p.update(pcorder.getId());
			}
			// 用迭代器删除
			Iterator<PurchaseOrder> it = pcorders.iterator();
			while (it.hasNext()) {
				PurchaseOrder order = it.next();
				if (order.getPcOrderID().equals(lists.get(0).getPcOrderID())) {
					it.remove();
				}

			}
		}

	}

}
