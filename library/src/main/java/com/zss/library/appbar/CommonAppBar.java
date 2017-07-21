package com.zss.library.appbar;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.zss.library.R;

/**
 * 公用头部组件
 * @author zm
 *
 */
public class CommonAppBar extends AppBarLayout {

	//Toolbar
	private Toolbar toolbar;
	//中间标题
	private TextView title;
	//中间副标题
	private TextView subTitle;
	//设置一个右边button
	private Button menu;

	public CommonAppBar(Context context) {
		super(context);
		initView();
	}

	public CommonAppBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void initView() {
		inflate(getContext(), R.layout.common_appbar, this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		title = (TextView) findViewById(R.id.title);
		subTitle = (TextView) findViewById(R.id.sub_title);
		menu = (Button) findViewById(R.id.menu);
	}

	public void setCenterTitle(CharSequence text){
		toolbar.setTitle("");
		title.setText(text);
		title.setVisibility(VISIBLE);
	}

	public void setCenterSubtitle(CharSequence text){
		toolbar.setSubtitle("");
		subTitle.setText(text);
		subTitle.setVisibility(VISIBLE);
	}


	public void setTitle(CharSequence text){
		toolbar.setTitle(text);
		title.setVisibility(GONE);
	}


	public void setSubtitle(CharSequence text){
		toolbar.setSubtitle(text);
		subTitle.setVisibility(GONE);
	}

	public void setRightText(CharSequence text){
		menu.setVisibility(VISIBLE);
		menu.setText(text);
	}


	public void inflateMenu(int resId){
		toolbar.inflateMenu(resId);
		menu.setVisibility(GONE);
	}

	public void setNavigationIcon(int resId){
		toolbar.setNavigationIcon(resId);
	}

	public void setNavigationOnClickListener(OnClickListener listener){
		toolbar.setNavigationOnClickListener(listener);
	}

	public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener){
		toolbar.setOnMenuItemClickListener(listener);
	}

	public Button getRightMenu(){
		return menu;
	}

	public void removeAllMenu(){
		toolbar.getMenu().clear();
	}

}
