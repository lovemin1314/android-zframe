package com.zss.library.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.Proxy.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * 文字工具类
 * 
 * @author zm
 * 
 */
public class StringUtils {

	/**
	 * 字符串高亮
	 * 
	 * @param content
	 * @param pattern
	 * @return SpannableStringBuilder
	 */
	public static SpannableStringBuilder parse(String content, String pattern, int color) {
		SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
		Pattern topicPattern = Pattern.compile(pattern);
		ForegroundColorSpan span = null;
		Matcher matcher = topicPattern.matcher(spannableString);
		while (matcher.find()) {
			span = new ForegroundColorSpan(color);
			spannableString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}

	/**
	 * 字符串高亮
	 * @param content
	 * @param start
	 * @param end
	 * @param color
     * @return SpannableStringBuilder
     */
	public static SpannableStringBuilder parse(String content, int start, int end, int color) {
		SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
		ForegroundColorSpan span = new ForegroundColorSpan(color);
		spannableString.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	/**
	 * 字符串局部字体变大
	 * 
	 * @param content
	 * @param pattern
	 * @return SpannableStringBuilder
	 */
	public static SpannableStringBuilder parseLager(String content, String pattern) {
		SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
		Pattern topicPattern = Pattern.compile(pattern);
		CharacterStyle span = null;
		Matcher matcher = topicPattern.matcher(spannableString);
		while (matcher.find()) {
			span = new RelativeSizeSpan(1.5f);
			spannableString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}

	/**
	 * InputStream到String
	 * 
	 * @param is
	 * @return String
	 * @throws IOException
	 */
	public static String streamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String s = "";
		StringBuilder sb = new StringBuilder();
		while ((s = reader.readLine()) != null) {
			sb.append(new String(s.getBytes(), "UTF-8"));
		}
		return sb.toString();
	}

	/**
	 * String到InputStream
	 * 
	 * @param str
	 * @return InputStream
	 */
	public static InputStream stringToStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 格式化.开始金额
	 * 
	 * @param str
	 * @return
	 */
	public static String formatPointStartAmount(String str) {
		if (!TextUtils.isEmpty(str)) {
			if (str.startsWith(".")) {
				return "0" + str;
			} else {
				return str;
			}
		}
		return str;
	}

	/**
	 * 格式化字符串<br />
	 * 主要用于金额格式化，制定小数点位数<br />
	 * 整数部分可以根据需要决定是否增加逗号分割
	 * 
	 * @param str
	 *            原始字符串，不包含小数点
	 * @param decimals
	 *            小数点后位置
	 * @param isSplit
	 *            是否需要,分割
	 * @return String
	 */
	public static String formatAmount(String str, int decimals, boolean isSplit) {
		if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str) || "0".equalsIgnoreCase(str)) {
			str = "0000000000";
		}
		if (str.length() < decimals) {
			str = "00000000" + str;
		}
		if (str.contains(",")) {
			str = str.replaceAll(",", "");
		}
		boolean negative = str.startsWith("-");
		if (negative) {
			str = str.replaceAll("-", "");
		}
		if (str.contains(".")) {
			str = str.replaceAll("\\.", "");
		}
		StringBuilder sb = new StringBuilder(str);
		if (sb.length() < decimals) {
			return str;
		}
		BigDecimal bigDecimal = new BigDecimal(sb.insert(sb.length() - decimals, ".").toString());
		sb.delete(0, sb.length());
		sb.append(bigDecimal.toPlainString());
		sb.reverse();
		StringBuilder sb2 = new StringBuilder();
		if (decimals > 0) {
			sb2.append(sb.subSequence(0, decimals + 1));
			sb.delete(0, decimals + 1);
		}
		if (isSplit) {
			for (int i = 0; i < (((sb.length() % 3) == 0) ? sb.length() / 3 : sb.length() / 3 + 1); i++) {
				sb2.append(sb.subSequence(i * 3, ((i + 1) * 3 > sb.length()) ? sb.length() : (i + 1) * 3)).append(",");
			}
		}
		sb2.deleteCharAt(sb2.length() - 1);
		if (negative) {
			return sb2.reverse().insert(0, "-").toString();
		}
		return sb2.reverse().toString();
	}

	/**
	 * 适用于以元为单位的金额
	 * 
	 * @param amt
	 *            以元为单位的金额,可包含小数点,不含小数点则会附加decimals位小数
	 * @param decimals
	 *            小数位数
	 * @param bl
	 *            整数部分是否需要用“,”分割
	 * @return String
	 */
	public static String formatAmountY(String amt, int decimals, boolean bl) {
		int n = 0;
		if (TextUtils.isEmpty(amt) || "null".equals(amt)) {
			amt = "";
		}
		if (amt.contains(".")) {
			n = amt.split("\\.")[1].length();
		}
		while (n < decimals) {
			amt += "0";
			n++;
		}
		char c = 0;
		while (n > decimals) {
			c = amt.charAt(amt.length() - 1);
			amt = amt.substring(0, amt.length() - 1);
			n--;
		}
		if(c>='5'){//四舍五入
			amt = amt+c;
			BigDecimal b = new BigDecimal(amt);
			b = b.setScale(decimals, BigDecimal.ROUND_HALF_UP);
			amt = b.toPlainString();
		}
		return StringUtils.formatAmount(amt, decimals, bl);
	}

	/**
	 * 是否手机号码
	 * 
	 * @param mobileNumber
	 * @return boolean
	 */
	public static boolean validateMobileNumber(String mobileNumber) {
		if (mobileNumber.length() != 11) {
			return false;
		}
		String pMobileNumber = "^(1)[0-9]{10}$";
		boolean result = Pattern.matches(pMobileNumber, mobileNumber);
		return result;
	}


	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).versionCode;
		} catch (NameNotFoundException e) {
			return 1;
		}
	}

	/**
	 * 获取版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).versionName;
		} catch (NameNotFoundException e) {
			return "1.0";
		}
	}

}
