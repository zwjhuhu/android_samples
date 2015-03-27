package net.zwj.tools.pub;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

/**
 * 计算摘要<br>
 * 暂时只有MD5和SHA256两种算法
 */
public class Digest {

	public static String LOG_TAG = "";

	final public static int TYPE_MD5 = 1;
	final public static int TYPE_SHA256 = 2;

	final public static String DEFAULT_ENCODING = "UTF-8";

	public static byte[] digest(byte[] info, int type) {
		try {
			String algorithm = null;
			switch (type) {
			case TYPE_MD5:
				algorithm = "md5";
				break;
			case TYPE_SHA256:
				algorithm = "SHA-256";
				break;
			default:
				return null;
			}
			MessageDigest md5 = MessageDigest.getInstance(algorithm);
			md5.update(info);
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			Log.w(LOG_TAG, e);
			return null;
		}
	}

	public static String digestToHex(byte[] info, int type) {
		if (info == null)
			return null;

		return Hex.encodeHexStr(digest(info, type));
	}

	public static byte[] digest(String info, int type) {
		return digest(info, DEFAULT_ENCODING, type);
	}

	public static byte[] digest(String info, String encodeing, int type) {
		try {
			byte[] msg = info.getBytes(encodeing);
			return digest(msg, type);
		} catch (UnsupportedEncodingException e) {
			Log.w(LOG_TAG, "encode[" + encodeing + "] error.", e);
			return null;
		}
	}

	public static String digestToHex(String info, int type) {
		return digestToHex(info, DEFAULT_ENCODING, type);
	}

	public static String digestToHex(String info, String encodeing, int type) {
		try {
			byte[] msg = info.getBytes(encodeing);
			return digestToHex(msg, type);
		} catch (UnsupportedEncodingException e) {
			Log.w(LOG_TAG, "encode[" + encodeing + "] error.", e);
			return null;
		}
	}
}
