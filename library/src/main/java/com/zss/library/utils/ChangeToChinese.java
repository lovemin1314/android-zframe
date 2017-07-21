package com.zss.library.utils;

import android.text.TextUtils;

/**
 * 数字金额转换为大写
 * 
 * @author zhaoyong
 * 
 */
public class ChangeToChinese {
	private static String[] num = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒",
			"捌", "玖" };
	// 10进制单位
	private static String[] dw = { "", "拾", "佰", "仟" };
	// 1000进制单位
	private static String[] dw2 = { "", "万", "亿", "兆", "京", "垓", "杼", "穰", "溝",
			"澗", "正", "載", "極", "恆河沙", "阿僧祇", "那由他", "不可思議", "无量", "大数" };
	private static String[] dw1 = { "角", "分", "厘" };

	private static boolean negative = false;//判断是否为负数

	public static String toChinese(String str) {
		// 判断是否为合格数字
		if (str.contains("-")) {
			str = str.replaceAll("-", "");
			negative = true;
		}
		str = str.replaceAll(",", "");
		
		if (str.matches("^\\d+(\\.\\d+)?\\d*$")) {
			String integer = "";
			String decimal = "";
			// 拆分成整数和小数部分
			int pos = str.indexOf(".");
			if (pos >= 0) {
				integer = str.substring(0, pos);
				decimal = str.substring(pos + 1);
				decimal = decimal.replaceAll("(.*?)(0+)$", "$1");
			} else {
				integer = str;
			}
			integer = integer.replaceAll("^(0+)(.*)", "$2");
			if (TextUtils.isEmpty(integer) && !TextUtils.isEmpty(decimal)) {
				integer = "0";
			}
			// 解析整数字符串
			String intStr = parseIntger(integer);
			// 解析小数字符串
			String decimalStr = parseDecimal(decimal,
					!TextUtils.isEmpty(intStr));
			if (!TextUtils.isEmpty(intStr)) {
				intStr += "元";
				if (integer.endsWith("0") && !TextUtils.isEmpty(decimal)
						&& !decimal.startsWith("0")) {
					intStr += "零";
				}
			} else if (TextUtils.isEmpty(decimalStr)) {
				intStr = "零元";
			}
			return (negative ? "负" : "") + intStr
					+ (TextUtils.isEmpty(decimalStr) ? "整" : decimalStr);
		} else {
		   return "";
		}

	}

	/**
	 * 
	 * 解析整数 规则为：
	 * 
	 * 将字符串从后往前分割成四个字符一组的字符串二维数组(对应1000进制单位)
	 * 
	 * 分别解析每个数组为10进制单位中对应的字符串
	 * 
	 * 合并到一起则为整个字符串
	 * 
	 * @param str
	 * 
	 * @return
	 */
	private static String parseIntger(String str) {
		if (null == str || TextUtils.isEmpty(str)) {
			return "";
		}
		// 分割字符串为四个字符一组的字符串二维数组
		char[][] nums = spit(str.toCharArray());
		StringBuilder s = new StringBuilder();
		// 上一组数字中最后一个字符是否为0 (方便拼接 “零” )
		boolean lastZero = false;
		for (int j = 0; j < nums.length; j++) {
			char[] chs = nums[j];
			// 整组数字中是不是全为 0 (方便拼接 千进制单位 )
			boolean allZero = true;
			// 以下循环将四位字符解析为 10进制单位中的数
			for (int i = 0, len = chs.length; i < len; i++) {
				int value = chs[i] - '0';
				// 当前数字大于0或者下一个数字不是0
				if (value > 0 || (i < (len - 1) && (chs[i + 1] - '0') > 0)) {
					if (lastZero) {
						// 如果上一次解析的时候最有一个数字为0，并且当前数字不为0的情况则在前面先拼接 “零”
						if (value > 0) {
							s.append(num[0]);
						}
						lastZero = false;
					}
					s.append(num[value]);
					allZero = false;
				}
				if (value > 0) { // 不是零的情况拼接单位
					s.append(dw[chs.length - i - 1]);
				}
			}
			lastZero = chs[chs.length - 1] == '0';
			if (!allZero) { // 如果当前解析的四个字符不是全为0 则拼接千进制单位
				s.append(dw2[nums.length - j - 1]);
			}
		}
		return s.toString();

	}

	/**
	 * 
	 * 拆分字符数组为四个字符一组的二位数组 (从后往前截取 如：
	 * 
	 * 10001 会截取成 arr[0] = {'1'}; arr[1] = {'0','0','0','1'};
	 * 
	 * )
	 * 
	 * @param chs
	 * 
	 * @return
	 */
	private static char[][] spit(char[] chs) {
		int tmpSize = chs.length / 4;
		int size = chs.length % 4 == 0 ? tmpSize : tmpSize + 1;
		char[][] nums = new char[size][];
		if (1 == size) {
			nums[0] = chs;
		} else {
			for (int i = chs.length; i > 0; i -= 4) {
				if (i < 4) {
					nums[--size] = copyOfRange(chs, 0, i);
				} else {
					nums[--size] = copyOfRange(chs, i - 4, i);
				}
			}
		}

		return nums;
	}

	/**
	 * 
	 * 解析小数单位 只精确到提供的单位 @see {@link Je#dw1} 多余数字直接忽略
	 * 
	 * @param str
	 * 
	 * @param existsInt
	 *            是否存在整数 (如果不存在整数，则不拼 零)
	 * 
	 * @return
	 */
	private static String parseDecimal(String str, boolean existsInt) {
		if (null == str || TextUtils.isEmpty(str)) {
			return "";
		}
		if (str.length() > dw1.length) {
			str = str.substring(0, dw1.length);
		}
		char[] chs = str.toCharArray();
		StringBuilder s = new StringBuilder();
		for (int i = 0, len = chs.length; i < len; i++) {
			int value = chs[i] - '0';
			if (value != 0 || (i < (len - 1) && (chs[i + 1] - '0') > 0)) {
				if (existsInt || value > 0) {
					s.append(num[value]);
				}
			}
			if (value > 0) {
				s.append(dw1[i]);
			}
		}
		return s.toString();
	}

	private static char[] copyOfRange(char[] original, int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException();
		}
		int originalLength = original.length;
		if (start < 0 || start > originalLength) {
			throw new ArrayIndexOutOfBoundsException();
		}
		int resultLength = end - start;
		int copyLength = Math.min(resultLength, originalLength - start);
		char[] result = new char[resultLength];
		System.arraycopy(original, start, result, 0, copyLength);
		return result;
	}

}
