package com.zss.library.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.zss.library.R;

import android.app.Activity;
import android.content.Context;
import android.os.Process;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 公用Toast
 * @author zm
 * 
 */
public class CommonToastUtils {

	private static Toast mToast = null;
	private static boolean isExist = false;


	public static void showInCenterToast(Context context, String msg) {
		showInCenterToast(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showInCenterToast(Context context, int resId) {
		showInCenterToast(context, context.getString(resId), Toast.LENGTH_SHORT);
	}

	public static void showInCenterToast(Context context, String msg, int duration) {
		View view = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
		TextView text = (TextView) view.findViewById(R.id.msg);
		text.setText(msg);
		Toast toast = getToast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}

	private static Toast getToast(Context context) {
		if (mToast == null) {
			mToast = new Toast(context.getApplicationContext());
		}
		return mToast;
	}

	public static void exitClient(Activity context, boolean isExit) {
		if (isExit) {
			Timer timer = null;
			if (isExist == false) {
				isExist = true; // 准备退出
				CommonToastUtils.showInCenterToast(context, "再按一次退出程序", Toast.LENGTH_LONG);
				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						isExist = false; // 取消退出
					}
				}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
			} else {
				Process.killProcess(Process.myPid());
				System.exit(0);
				Runtime.getRuntime().exit(0);
			}
		} else {
			context.moveTaskToBack(true);
		}
	}

}
