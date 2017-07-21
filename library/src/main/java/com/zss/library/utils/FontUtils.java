package com.zss.library.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtils {

	private static Typeface face;

	/**
	 * 初始化字体
	 * @param context
	 * @param fontPath fonts/face.ttf
     */
	public static void initFont(Context context, String fontPath) {
		face = Typeface.createFromAsset(context.getAssets(), fontPath);
	}

	// 设置字体
	public static void applyFont(View root) {
		try {
			if (root instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) root;
				for (int i = 0; i < viewGroup.getChildCount(); i++) {
					applyFont(viewGroup.getChildAt(i));
				}
			} else if (root instanceof TextView) {
				((TextView) root).setTypeface(face);
			}
		} catch (Exception e) {
		}
	}
}
