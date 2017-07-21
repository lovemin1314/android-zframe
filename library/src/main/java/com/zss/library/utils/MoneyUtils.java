package com.zss.library.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Handler;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

/**
 * 金额工具
 * @author zm
 * 
 */
public class MoneyUtils {


	public static void startMoneyAnimtor(TextView textView, String money){
		TextViewAnimtor animtor = new TextViewAnimtor(textView, money);
		animtor.startTimer();
	}

	public static InputFilter getMoneyInputFilter() {
		return new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
									   Spanned dest, int dstart, int dend) {
				SpannableStringBuilder builder = new SpannableStringBuilder(
						dest).replace(dstart, dend,
						source.subSequence(start, end));
				if (!builder.toString().equals("")) {
					Pattern p = Pattern
							.compile("^([0-9]|[1-9]\\d{0,14})([.]\\d{0,2})?$");
					Matcher matcher = p.matcher(builder.toString());
					if (matcher.matches()) {
						return null;
					} else {
						if (dest.toString().equals("")) {
							if (source.toString().equals(".")) {
								return "0.";
							}
						}
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static class TextViewAnimtor{

		private TextView textView;
		private String money;
		private Handler mHandler = new Handler();

		public TextViewAnimtor(TextView textView, String money) {
			this.textView = textView;
			this.money = money;
			textView.setText("0.00");
		}

		public void startTimer() {
			mHandler.removeCallbacks(runableThread);
			mHandler.postDelayed(runableThread, 20);
		}

		private Runnable runableThread = new Runnable() {
			@Override
			public void run() {
				String str;
				if (textView.getText().toString().indexOf(",") >= 0) {
					str = textView.getText().toString().replace(",", "");
				} else {
					str = textView.getText().toString();
				}
				BigDecimal bigDecimal = new BigDecimal(str);
				BigDecimal realBigDecimal = new BigDecimal(money);
				BigDecimal i = null;
				if (realBigDecimal.compareTo(strToBigDecimal("9999999999999")) > 0) {
					i = realBigDecimal;
				} else if (realBigDecimal.compareTo(strToBigDecimal("999999999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("98765432198.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("99999999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("9876543219.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("9999999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("987654321.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("999999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("98765432.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("99999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("9876543.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("9999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("987654.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("999999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("98765.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("99999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("9876.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("9999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("987.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("999")) > 0) {
					i = bigDecimal.add(strToBigDecimal("98.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("99")) > 0) {
					i = bigDecimal.add(strToBigDecimal("9.12"));
				} else if (realBigDecimal.compareTo(strToBigDecimal("9")) > 0) {
					i = bigDecimal.add(strToBigDecimal("1.12"));
				} else {
					i = bigDecimal.add(strToBigDecimal("0.12"));
				}

				if (i.compareTo(realBigDecimal.subtract(realBigDecimal.divide(strToBigDecimal("10")))) < 0) {
					textView.setText(StringUtils.formatAmountY(i.toString(), 2, true));
				} else {
					String stt = String.valueOf(money);
					textView.setText(StringUtils.formatAmountY(stt, 2, true));
				}

				if (i.compareTo(realBigDecimal) < 0) {
					mHandler.postDelayed(runableThread, 10);
				}
			}

			public BigDecimal strToBigDecimal(String str) {
				if (str != null) {
					return new BigDecimal(str);
				} else {
					return new BigDecimal("0");
				}
			}
		};
	}

}
