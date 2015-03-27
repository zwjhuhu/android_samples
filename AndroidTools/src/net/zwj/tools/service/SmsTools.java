package net.zwj.tools.service;

import android.telephony.SmsManager;

/**
 * 短信工具<br>
 * 需要权限:<uses-permission android:name="android.permission.SEND_SMS" />
 */
public class SmsTools {

	/**
	 * 发送短信
	 * 
	 * @param desc
	 *            目的号码
	 * @param msg
	 *            短信内容
	 */
	public static void sendSms(String desc, String msg) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(desc, null, msg, null, null);
	}
}
