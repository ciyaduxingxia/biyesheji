package com.example.fragment;

import org.litepal.LitePal;

import com.example.activity.LoginActivity;
import com.example.dbtable.User;
import com.example.me.EditPassword;
import com.example.me.PermissionSet;
import com.example.me.UserInfo;
import com.example.me.UserManage;
import com.example.wms.R;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MeAnotherFragment extends Fragment implements OnClickListener {
	private Context mContext;
	private View meanotherFragment;

	private TextView userinfo;
	private ImageView managerinfo;
	private ImageView editpwd;
	private ImageView permissionset;
	private ImageView usermanage;

	private Button quit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		meanotherFragment = inflater.inflate(R.layout.fragment_me_another,
				container, false);
		mContext = getActivity();

		init();

		return meanotherFragment;
	}

	private void init() {
		userinfo = (TextView) meanotherFragment
				.findViewById(R.id.tv_managerinfo);
		String type = LitePal.where("username=?", LoginActivity.name)
				.find(User.class).get(0).getUserType();
		userinfo.setText(LoginActivity.name + type);

		managerinfo = (ImageView) meanotherFragment
				.findViewById(R.id.arrow_mamagerinfo);
		editpwd = (ImageView) meanotherFragment
				.findViewById(R.id.arrow_me_manager_editpwd);
		permissionset = (ImageView) meanotherFragment
				.findViewById(R.id.arrow_me_manager_permissionset);
		usermanage = (ImageView) meanotherFragment
				.findViewById(R.id.arrow_me_manager_usermanage);

		quit = (Button) meanotherFragment.findViewById(R.id.bt_manager_quit);

		managerinfo.setOnClickListener(this);
		editpwd.setOnClickListener(this);
		permissionset.setOnClickListener(this);
		usermanage.setOnClickListener(this);
		quit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.arrow_mamagerinfo:
			Intent intent1 = new Intent(mContext, UserInfo.class);
			startActivity(intent1);
			break;

		case R.id.arrow_me_manager_editpwd:
			Intent intent2 = new Intent(mContext, EditPassword.class);
			startActivity(intent2);
			break;

		case R.id.arrow_me_manager_permissionset:
			Intent intent3 = new Intent(mContext, PermissionSet.class);
			startActivity(intent3);
			break;

		case R.id.arrow_me_manager_usermanage:
			Intent intent4 = new Intent(mContext, UserManage.class);
			startActivity(intent4);
			break;

		case R.id.bt_manager_quit:
			Intent intent5 = new Intent(mContext, LoginActivity.class);
			startActivity(intent5);
			break;

		default:
			break;
		}

	}
}
