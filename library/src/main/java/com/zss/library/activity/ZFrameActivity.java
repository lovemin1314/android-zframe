package com.zss.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;

import com.zss.library.fragment.BaseFragment;

import com.zss.library.R;
import com.zss.library.appbar.CommonAppBar;

/**
 * ZFrameActivity
 * 用法如下：
 * Intent intent = new Intent(this, ZFrameActivity.class);
 * intent.putExtra(ZFrameActivity.CLASS, BaseFragment.class);
 * startActivity(intent);
 * 
 * @author zm
 *
 */
public class ZFrameActivity extends BaseActivity {

	public static String CLASS = "CLASS";

	@Override
	public int getLayoutResId() {
		return R.layout.activity_zframe;
	}

	@Override
	public void initView() {
		super.initView();
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		super.initData(savedInstanceState);
		Intent intent = getIntent();
		Class className = (Class) intent.getSerializableExtra(CLASS);
		Fragment fragment = Fragment.instantiate(getActivity(), className.getName(), getIntent().getExtras());
		addFragment(fragment);
	}

	@Override
	public void setTopBar() {
		super.setTopBar();
		CommonAppBar toolbar = getAppBar();
		toolbar.setTitle(getString(R.string.app_name));
		toolbar.setNavigationIcon(R.mipmap.btn_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		BaseFragment curBase = getCurrentFragmnet();
		if (curBase != null && !curBase.onBackPressed()) {// Fragment未处理返回
			FragmentManager fm = getSupportFragmentManager();
			int count = fm.getBackStackEntryCount();
			if (count > 1) {
				backStackFragment();
			} else {
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
