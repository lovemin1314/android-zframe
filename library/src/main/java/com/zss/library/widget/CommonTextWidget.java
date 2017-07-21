package com.zss.library.widget;

import com.zss.library.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 公用文本组件
 * @author zm
 *
 */
public class CommonTextWidget extends CommonWidget {

	private TextView common_tv_left;
	private TextView common_tv_right;
	private TextView common_tv_summary;

	public CommonTextWidget(Context context) {
		super(context);
	}

	public CommonTextWidget(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CommonTextWidget);

		CharSequence leftText = mTypedArray
				.getText(R.styleable.CommonTextWidget_left_text);

		CharSequence rightText = mTypedArray
				.getText(R.styleable.CommonTextWidget_right_text);

		CharSequence summaryText = mTypedArray
				.getText(R.styleable.CommonTextWidget_summary_text);

		if (!TextUtils.isEmpty(leftText)) {
			setLeftText(leftText);
		}
		if (!TextUtils.isEmpty(rightText)) {
			setRightText(rightText);
		}

		if (!TextUtils.isEmpty(summaryText)){
			setSummaryText(summaryText);
		}

		mTypedArray.recycle();
	}

	@Override
	public void createView() {
		setMiddleView(R.layout.common_text);
		common_tv_left = (TextView) findViewById(R.id.common_tv_left);
		common_tv_right = (TextView) findViewById(R.id.common_tv_right);
		common_tv_summary = (TextView)findViewById(R.id.common_tv_summary);
		common_tv_left.setVisibility(GONE);
		common_tv_right.setVisibility(GONE);
	}

	public void setLeftText(CharSequence text) {
		common_tv_left.setVisibility(VISIBLE);
		common_tv_left.setText(text);
	}

	public void setLeftHintText(CharSequence text){
		common_tv_left.setVisibility(VISIBLE);
		common_tv_left.setHint(text);
	}

	public void setLeftHintTextColor(int color){
		common_tv_left.setTextColor(color);
	}

	public void setLeftTextColor(int color){
		common_tv_left.setTextColor(color);
	}

	public void setRightText(CharSequence text) {
		common_tv_right.setVisibility(VISIBLE);
		common_tv_right.setText(text);
	}

	public void setRightHintText(CharSequence text){
		common_tv_right.setVisibility(VISIBLE);
		common_tv_right.setHint(text);
	}

	public void setRightHintTextColor(int color){
		common_tv_right.setTextColor(color);
	}

	public void setRightTextColor(int color){
		common_tv_right.setTextColor(color);
	}

	public TextView getLeftTextView(){
		return common_tv_left;
	}

	public TextView getRightTextView(){
		return common_tv_right;
	}

	public String getLeftText(){
		return common_tv_left.getText().toString();
	}

	public String getRightText(){
		return common_tv_right.getText().toString();
	}

	public void setSummaryHintTextColor(int color){
		common_tv_summary.setTextColor(color);
	}

	public void setSummaryTextColor(int color){
		common_tv_summary.setTextColor(color);
	}

	public void setSummaryText(CharSequence text){
		if(TextUtils.isEmpty(text)){
			common_tv_summary.setText(text);
			common_tv_summary.setVisibility(GONE);
		}else{
			common_tv_summary.setText(text);
			common_tv_summary.setVisibility(VISIBLE);
		}
	}

	public TextView getSummaryTextView(){
		return common_tv_summary;
	}

	public String getSummaryText(){
		return common_tv_summary.getText().toString();
	}
}
