package com.zss.library.widget;

import com.zss.library.R;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * 公用输入组件
 * @author zm
 *
 */
public class CommonEditWidget extends CommonWidget {

	private EditText common_edit;
	private TextInputLayout common_text_input;

	public CommonEditWidget(Context context) {
		super(context);
	}

	public CommonEditWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void createView() {
		setMiddleView(R.layout.common_edit);
		common_text_input = (TextInputLayout) findViewById(R.id.common_text_input);
		common_edit = common_text_input.getEditText();
	}

	public void setText(CharSequence text) {
		common_edit.setText(text);
	}

	public void setHint(CharSequence text) {
		common_text_input.setHint(text);
	}

	public void setErrorEnabled(boolean enabled){
		common_text_input.setErrorEnabled(enabled);
	}

	public void setError(CharSequence error){
		common_text_input.setError(error);
	}

	public String getText() {
		return common_edit.getText().toString();
	}

	public void setInputType(int type) {
		common_edit.setInputType(type);
	}

	public EditText getEditText() {
		return common_edit;
	}

	public TextInputLayout getTextInputLayout() {
		return common_text_input;
	}

	public void addTextChangedListener(TextWatcher textWatcher){
		if(textWatcher != null){
			common_edit.addTextChangedListener(textWatcher);
		}
	}

}
