package com.example.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends Activity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.example.wms.R.layout.activity_welcome);
		mContext = this;
		init();
	}

	private void init() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				finish();

			}
		}, 2000);

	}
}
