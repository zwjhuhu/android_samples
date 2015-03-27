package net.zwj.tools.pub;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

public class AndroidTools {

	/**
	 * 用系统浏览器打开URL
	 * 
	 * @param context
	 * @param url
	 */
	public static void openUrl(Context context, String url) {
		if (url == null || url.equals(""))
			return;

		Uri uri = Uri.parse(url);
		context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	/**
	 * 开启新界面
	 * 
	 * @param context
	 * @param activity
	 */
	public static void startActivity(Context context, Class<?> activity) {
		startActivity(context, activity, null);
	}

	/**
	 * 开启新界面
	 * 
	 * @param context
	 * @param activity
	 */
	public static void startActivity(Context context, Class<?> activity, Bundle params) {
		Intent intent = new Intent(context, activity);
		if (params != null)
			intent.putExtras(params);

		context.startActivity(intent);
	}

	/**
	 * 检查是否有网络连接<br>
	 * 需要配置权限<uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasInternet(Context context) {
		return hasWifi(context) || hasMobileNetwork(context);
	}

	/**
	 * 检查WIFI网络是否连接<br>
	 * 需要配置权限<uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return networkInfo.isConnectedOrConnecting();
	}

	/**
	 * 检查是否允许数据连接<br>
	 * 需要配置权限<uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasMobileNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return networkInfo.isConnectedOrConnecting();
	}

	/**
	 * 跳转到网络设置界面
	 * 
	 * @param context
	 */
	public static void gotoWirelessSettingsView(Context context) {
		Intent intentToNetwork = new Intent("/");
		ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
		intentToNetwork.setComponent(componentName);
		intentToNetwork.setAction("android.intent.action.VIEW");
		context.startActivity(intentToNetwork);
	}

	/**
	 * 安装程序<br>
	 * 需要添加权限：<uses-permission
	 * android:name="android.permission.INSTALL_PACKAGES"/>
	 * 
	 * @param context
	 * @param filePath
	 */
	public static void installAPK(Context context, String filePath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 卸载程序<br>
	 * 需要添加权限：<uses-permission
	 * android:name="android.permission.DELETE_PACKAGES"/>
	 * 
	 * @param context
	 * @param appPackage
	 */
	public static void uninstallAPK(Context context, String appPackage) {
		Uri packageURI = Uri.parse("package:" + appPackage);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(uninstallIntent);
	}

	/**
	 * 杀死自己的进程
	 */
	public static void killMysef() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
