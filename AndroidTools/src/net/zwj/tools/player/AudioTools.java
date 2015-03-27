package net.zwj.tools.player;

import android.content.Context;
import android.media.AudioManager;

/**
 * 声音工具
 */
public class AudioTools {

	/**
	 * 降低音量<br>
	 * 需要增加权限：<uses-permission android:name="android.permission.VIBRATE" />
	 * 
	 * @param context
	 */
	public static void reduceAudio(Context context) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
	}

	/**
	 * 增加音量<br>
	 * 需要增加权限：<uses-permission android:name="android.permission.VIBRATE" />
	 * 
	 * @param context
	 */
	public static void addAudio(Context context) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
	}

}
