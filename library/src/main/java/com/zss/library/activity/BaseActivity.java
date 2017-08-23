package com.zss.library.activity;

import com.zss.library.PermissionCallBack;
import com.zss.library.R;
import com.zss.library.autolayout.AutoLayoutActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.utils.SystemBarTintManager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import java.util.List;


/**
 * 为了实现代码统一， 不至于你看不懂我的代码，所有界面都继承BaseActivity， 子类实现getLayoutResId()、
 * initView()、initData()、setTopBar()来处理所有事件。
 * 
 * @author zm
 *
 */
public abstract class BaseActivity extends AutoLayoutActivity {

	private CoordinatorLayout rootView;
	private CommonAppBar appBar;
	private RelativeLayout contentView;
	private String fromTagBack = ""; // 从哪个Tag返回过来
	private int requestCode;
	private PermissionCallBack callBack;
	private boolean touchOutsideCancelSoft = false; //点击不是EditView隐藏键盘

	public boolean isTouchOutsideCancelSoft() {
		return touchOutsideCancelSoft;
	}

	public void setTouchOutsideCancelSoft(boolean touchOutsideCancelSoft) {
		this.touchOutsideCancelSoft = touchOutsideCancelSoft;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		super.onCreate(savedInstanceState);
		setContentView(createLayout());
		setContentViewResId(getLayoutResId());
		initView();
		initData(savedInstanceState);
		appBar = new CommonAppBar(this);
		CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setBehavior(new AppBarLayout.Behavior());
		appBar.setLayoutParams(params);
		rootView.addView(appBar, 0);
		setTopBar();

	}

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}

	/**
	 * 创建界面布局resId
	 */
	private  int createLayout() {
		return R.layout.activity_base;
	}

	/**
	 * 将子类布局resId添加到contentView中。
	 * @param resId
	 */
	private void setContentViewResId(int resId) {
		if (contentView != null) {
			contentView.removeAllViews();
			LayoutInflater.from(this).inflate(resId, contentView);
		}
	}
	

	public abstract int getLayoutResId();


	/**
	 * 初始化界面
	 */
	public void initView() {
		// 默认将Fragment添加到堆栈的监听加入。
		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				int count = getSupportFragmentManager().getBackStackEntryCount();
				if (count > 0) {
					BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
					String name = entry.getName();
					Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
					if (fragment instanceof BaseFragment) {
						BaseFragment base = (BaseFragment) fragment;
						base.setTopBar();
						if (!TextUtils.isEmpty(fromTagBack)) {
							base.onBackStackChanged(fromTagBack);
							fromTagBack = "";
						}

					}
				} else {
					setTopBar();
				}
			}
		});

	}

	/**
	 * 初始化界面数据
	 */
	public void initData(Bundle savedInstanceState) {

	}

	/**
	 * 设置setTopBar 在Activity顶部导航栏
	 */
	public void setTopBar() {
		appBar.setTitle(getString(R.string.app_name));
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		rootView = (CoordinatorLayout) findViewById(R.id.root_view);
		contentView = (RelativeLayout) findViewById(R.id.content_view);
	}

	public FragmentActivity getActivity() {
		return BaseActivity.this;
	}

	public ViewGroup getRootView() {
		return rootView;
	}

	public CommonAppBar getAppBar() {
		return appBar;
	}

	/**
	 * 添加头部组件
	 * @param view
	 */
	public void addAppBar(View view) {
		removeAppBar();
		rootView.addView(view, 0);
	}

	/**
	 * 删除头部组件
	 */
	public void removeAppBar(){
		getRootView().removeViewInLayout(getAppBar());
	}

	/**
	 * 添加Fragment
	 * @param fragment
	 */
	public void addFragment(Fragment fragment) {
		if (fragment == null)
			return;
		String tag = fragment.getClass().getName();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in,
				R.anim.slide_right_out);
		Fragment tempFragment = fm.findFragmentByTag(tag);
		if (tempFragment != null) {
			ft.replace(R.id.content_view, fragment, tag);
		} else {
			ft.add(R.id.content_view, fragment, tag);
		}
		ft.addToBackStack(tag);
		ft.commitAllowingStateLoss();
	}

	/**
	 * 返回前一堆栈Fragment
	 */
	public void backStackFragment() {
		fromTagBack = getCurrentFragmnet().getClass().getName();
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStackImmediate();
	}

	/**
	 * 通过tag返回堆栈Fragment，tag本身也会销毁。
	 * 
	 * @param tag
	 */
	public void backStackFragment(String tag) {
		fromTagBack = getCurrentFragmnet().getClass().getName();
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	/**
	 * 获取当前BaseFragment
	 * 
	 * @return
	 */
	public BaseFragment getCurrentFragmnet() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count > 0) {
			BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
			String name = entry.getName();
			return getTagFragment(name);
		} else {
			return null;
		}
	}

	/**
	 * 通过Tag获取BaseFragment
	 * 
	 * @return
	 */
	public BaseFragment getTagFragment(String tag) {
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
		if (fragment instanceof BaseFragment) {
			return (BaseFragment) fragment;
		}
		return null;
	}

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config = new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());
		return res;
	}

	/**
	 * 获取布局
	 * @param resid
	 * @return
	 */
	public View getLayoutInflater(int resid) {
		return LayoutInflater.from(this).inflate(resid, null);
	}

	/**
	 * 拦截TouchEvent
	 * @param ev
	 * @return
     */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isTouchOutsideCancelSoft() && ev.getAction() == MotionEvent.ACTION_DOWN) {
			 //获取当前拥有用户焦点的view
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				hideInput();
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 隐藏键盘
	 */
	public void hideInput() {
		View v = getCurrentFocus();
		if(v != null){
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 根据view所在坐标和用户点击的坐标进行比较，判断是否隐藏键盘
	 */
	private boolean isShouldHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof View)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0];
			int top = l[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
				// 键盘不用隐藏
				return false;
			} else {
				return true;
			}
		}
		return false;
	}



	public void verifyPermissions(Activity activity, String permission, String[] reqestPermissions, int requestCode, PermissionCallBack callBack) {
		this.requestCode = requestCode;
		this.callBack = callBack;
		int checkPermission = ActivityCompat.checkSelfPermission(activity, permission);
		if (checkPermission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(
					activity,
					reqestPermissions,
					requestCode
			);
		}else {
			callBack.onGranted();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if(this.requestCode == requestCode){
			if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission Granted
				callBack.onGranted();
			} else {
				// Permission Denied
				callBack.onDenied();
			}
		}
		getCurrentFragmnet().onRequestPermissionsResult(requestCode, permissions, grantResults);

	}

	public void setInnerLayoutFullScreen() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	public void setOuterLayoutFullScreen() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			View view = findViewById(android.R.id.content);
			if(view != null && view instanceof ViewGroup ){
				ViewGroup content = (ViewGroup)view;
				view = content.getChildAt(0);
				if(view!=null && view instanceof ViewGroup){
					ViewGroup root = (ViewGroup)view;
					root.setFitsSystemWindows(true);
					root.setClipToPadding(true);
				}
			}
		}
	}

	public void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	public void setStatusBarColor(int colorId) {
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintColor(colorId);
	}

	public void setStatusBarResource(int colorId) {
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(colorId);
	}

	public int getColorValue(int color) {
		return ContextCompat.getColor(BaseActivity.this, color);
	}

	public void setBackgroundResource(int resid){
		rootView.setBackgroundResource(resid);
	}

	public void setBackgroundColor(int resid){
		rootView.setBackgroundColor(resid);
	}


}
