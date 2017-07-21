package com.zss.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zss.library.R;
import com.zss.library.wheelview.OnWheelChangedListener;
import com.zss.library.wheelview.WheelView;
import com.zss.library.wheelview.adapter.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 公用仿IPhone车轮对话框
 * @author zm
 *
 */
public class CommonWheelDialog extends Dialog {

	private List<String[]> mItems;
	private List<WheelView> mWheelView;
	private OnSelectedListener listener;
	private LinearLayout bottomLayout;
	private Button leftBtn;
	private Button rightBtn;

	public CommonWheelDialog(Context context, String[] mItems) {
		super(context, R.style.CommonDialog);
		setContentView(R.layout.common_wheel_dialog);
		setCanceledOnTouchOutside(true);
		if(mItems == null){
			return;
		}
		this.mItems = new ArrayList<>();
		this.mItems.add(mItems);
		int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		getWindow().setLayout(width, width);
		WindowManager.LayoutParams params = getWindow().getAttributes();;
		params.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(params);
		initView();
	}

	public CommonWheelDialog(Context context, List<String[]> mItems) {
		super(context, R.style.CommonDialog);
		setContentView(R.layout.common_wheel_dialog);
		setCanceledOnTouchOutside(true);
		if(mItems == null){
			return;
		}
		this.mItems = mItems;
		int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
		getWindow().setLayout(width, width);
		WindowManager.LayoutParams params = getWindow().getAttributes();;
		params.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(params);
		initView();
	}

	private void initView() {
		mWheelView = new ArrayList<WheelView>();
		bottomLayout = (LinearLayout)findViewById(R.id.common_ll_bottom);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		params.weight = 1;
		for(int i=0; i<mItems.size(); i++){
			String[] arr = mItems.get(i);
			ArrayWheelAdapter mAdapter = new ArrayWheelAdapter<String>(getContext(), arr);
			mAdapter.setItemResource(R.layout.common_item_wheel);
			mAdapter.setItemTextResource(R.id.text);
			mAdapter.setEmptyItemResource(R.layout.common_item_wheel);

			WheelView wheelView = new WheelView(getContext());
			wheelView.setTag(i);
			wheelView.setViewAdapter(mAdapter);
			wheelView.setCurrentItem(0, false);
			wheelView.addChangingListener(new OnWheelChangedListener() {
				@Override
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					if(listener != null){
						String tag = wheel.getTag().toString();
						int wheelItem = Integer.parseInt(tag);
						String value = mItems.get(wheelItem)[newValue];
						listener.onSelected(wheelItem, newValue, value);
					}
				}
			});
			bottomLayout.addView(wheelView, params);
			mWheelView.add(wheelView);
		}

		leftBtn = (Button) findViewById(R.id.common_btn_left);
		leftBtn.setOnClickListener(mDefaultDismiss);

		rightBtn = (Button) findViewById(R.id.common_btn_right);
		rightBtn.setOnClickListener(mDefaultDismiss);

	}

	public void setOnSelectedListener(OnSelectedListener listener) {
		this.listener = listener;
	}

	public List<Integer> getCurrentItems() {
		List<Integer> curentItems = new ArrayList<>();
		for(int i=0; i<mWheelView.size(); i++){
			Integer item = mWheelView.get(i).getCurrentItem();
			curentItems.add(item);
		}
		return curentItems;
	}

	public List<String> getCurrentValues() {
		List<String> curentValues = new ArrayList<>();
		for(int i=0; i<mWheelView.size(); i++){
			int currentItem = mWheelView.get(i).getCurrentItem();
			String value = mItems.get(i)[currentItem];
			curentValues.add(value);
		}
		return curentValues;
	}

	public void setCurrentItems(List<Integer> curentItems) {
		for(int i=0; i<mWheelView.size(); i++){
			int curentItem = curentItems.get(i);
			mWheelView.get(i).setCurrentItem(curentItem, false);
		}
	}

	public void setCurrentItems(Integer[] curentItems) {
		for(int i=0; i<mWheelView.size(); i++){
			int curentItem = curentItems[i];
			mWheelView.get(i).setCurrentItem(curentItem, false);
		}
	}

	public boolean isMulWheelView(){
		return mItems.size() > 1;
	}


	public CommonWheelDialog setOnLeftListener(
			final View.OnClickListener listener) {
		leftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onClick(v);
				}
				dismiss();
			}
		});
		return this;
	}

	public CommonWheelDialog setOnRightListener(final View.OnClickListener listener) {
		rightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onClick(v);
				}
				dismiss();
			}
		});
		return this;
	}

	private View.OnClickListener mDefaultDismiss = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			dismiss();
		}
	};

	/**
	 * 显示对话框
	 */
	public void show() {
		try {
			super.show();
		} catch (Exception e) {
		}
	}
	
	public void dismiss(){
		try {
			super.dismiss();
		} catch (Exception e) {
		}
	}

	public interface OnSelectedListener {
		public void onSelected(int wheelItem, int position, String value);
	}
}
