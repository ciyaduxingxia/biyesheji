package com.example.fragment;

import org.litepal.LitePal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activity.LoginActivity;
import com.example.dbtable.User;
import com.example.me.EditPassword;
import com.example.me.PermissionRequest;
import com.example.me.UserInfo;
import com.example.wms.R;

public class MeFragment extends Fragment implements OnClickListener {

	private Context mContext;
	private View meFragment;

	private TextView userinfo;
	private ImageView operatorinfo;
	private ImageView editpwd;
	private ImageView permissionrequ;

	private Button quit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		meFragment = inflater.inflate(R.layout.fragment_me, container, false);
		mContext = getActivity();

		init();

		return meFragment;
	}

	private void init() {
		userinfo = (TextView) meFragment.findViewById(R.id.tv_operatorinfo);
		String type = LitePal.where("username=?", LoginActivity.name)
				.find(User.class).get(0).getUserType();
		Log.i("标志", type);
		userinfo.setText(LoginActivity.name + type);

		operatorinfo = (ImageView) meFragment
				.findViewById(R.id.arrow_operatorinfo);
		editpwd = (ImageView) meFragment
				.findViewById(R.id.arrow_me_operator_editpwd);
		permissionrequ = (ImageView) meFragment
				.findViewById(R.id.arrow_me_operator_permissionrequ);

		quit = (Button) meFragment.findViewById(R.id.bt_operator_quit);

		operatorinfo.setOnClickListener(this);
		editpwd.setOnClickListener(this);
		permissionrequ.setOnClickListener(this);
		quit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.arrow_operatorinfo:
			Intent intent1 = new Intent(mContext, UserInfo.class);
			startActivity(intent1);
			break;

		case R.id.arrow_me_operator_editpwd:
			Intent intent2 = new Intent(mContext, EditPassword.class);
			startActivity(intent2);
			break;

		case R.id.arrow_me_operator_permissionrequ:
			Intent intent3 = new Intent(mContext, PermissionRequest.class);
			startActivity(intent3);
			break;

		case R.id.bt_operator_quit:
			Intent intent4 = new Intent(mContext, LoginActivity.class);
			startActivity(intent4);
			break;

		default:
			break;
		}

	}
}
