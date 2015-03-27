package net.zwj.tools.pub;

import java.io.UnsupportedEncodingException;

import android.util.Base64;
import android.util.Log;

public abstract class AbstractEncryptDTools {

	protected String logTag = "";

	public void setLogTag(String logTag) {
		this.logTag = logTag;
	}

	protected String keyEncoding = "utf-8";

	public void setKeyEncoding(String keyEncoding) {
		this.keyEncoding = keyEncoding;
	}

	protected String msgEncoding = "utf-8";

	public void setMsgEncoding(String msgEncoding) {
		this.msgEncoding = msgEncoding;
	}

	public abstract byte[] decrypt(byte[] miwen, byte[] key);

	public abstract byte[] encrypt(byte[] minwen, byte[] key);

	/**
	 * 解密
	 * 
	 * @param miwen
	 *            密文
	 * @param keyStr
	 *            密钥
	 * @param encoding
	 *            密钥转化成byte时的编码
	 * @return
	 */
	public byte[] decrypt(byte[] miwen, String keyStr) {
		try {
			byte[] key = keyStr.getBytes(keyEncoding);
			return decrypt(miwen, key);
		} catch (UnsupportedEncodingException e) {
			Log.e(logTag, "encode[" + keyEncoding + "] error.");
			return null;
		}
	}

	/**
	 * 将初始密钥MD5后作为密钥进行解密
	 * 
	 * @param miwen
	 *            密文
	 * @param keyStr
	 *            密钥
	 * @return
	 */
	public byte[] decryptWithMD5Key(byte[] miwen, String keyStr) {
		return decrypt(miwen, Digest.digest(keyStr, keyEncoding, Digest.TYPE_MD5));
	}

	/**
	 * 将原始密钥先进行SHA256摘要后再进行解密
	 * 
	 * @param miwen
	 * @param keyStr
	 * @param encoding
	 * @return
	 */
	public byte[] decryptWithSHA256Key(byte[] miwen, String keyStr) {
		return decrypt(miwen, Digest.digest(keyStr, keyEncoding, Digest.TYPE_SHA256));
	}

	public byte[] decryptFromHex(String miwen, byte[] key) {
		return decrypt(Hex.decodeHex(miwen.toCharArray()), key);
	}

	public byte[] decryptFromHex(String miwen, String keyStr) {
		return decrypt(Hex.decodeHex(miwen.toCharArray()), keyStr);
	}

	public byte[] decryptWithMD5KeyFromHex(String miwen, String keyStr) {
		return decryptWithMD5Key(Hex.decodeHex(miwen.toCharArray()), keyStr);
	}

	public byte[] decryptWithSHA256KeyFromHex(String miwen, String keyStr) {
		return decryptWithSHA256Key(Hex.decodeHex(miwen.toCharArray()), keyStr);
	}

	public byte[] decryptFromBase64(String miwen, byte[] key) {
		return decryptFromBase64(miwen, key, Base64.DEFAULT);
	}

	public byte[] decryptFromBase64(String miwen, byte[] key, int flag) {
		return decrypt(Base64.decode(miwen, flag), key);
	}

	public byte[] decryptFromBase64(String miwen, String keyStr) {
		return decryptFromBase64(miwen, keyStr, Base64.DEFAULT);
	}

	public byte[] decryptFromBase64(String miwen, String keyStr, int flag) {
		return decrypt(Base64.decode(miwen, flag), keyStr);
	}

	public byte[] decryptWithMD5KeyFromBase64(String miwen, String keyStr) {
		return decryptWithMD5KeyFromBase64(miwen, keyStr, Base64.DEFAULT);
	}

	public byte[] decryptWithMD5KeyFromBase64(String miwen, String keyStr, int flag) {
		return decryptWithMD5Key(Base64.decode(miwen, flag), keyStr);
	}

	public byte[] decryptWithSHA256KeyFromBase64(String miwen, String keyStr) {
		return decryptWithSHA256KeyFromBase64(miwen, keyStr, Base64.DEFAULT);
	}

	public byte[] decryptWithSHA256KeyFromBase64(String miwen, String keyStr, int flag) {
		return decryptWithSHA256Key(Base64.decode(miwen, flag), keyStr);
	}

	/**
	 * 加密
	 * 
	 * @param minwen
	 *            密文
	 * @param keyStr
	 *            密钥
	 * @return
	 */
	public byte[] encrypt(byte[] minwen, String keyStr) {
		try {
			byte[] key = keyStr.getBytes(keyEncoding);
			return encrypt(minwen, key);
		} catch (UnsupportedEncodingException e) {
			Log.e(logTag, "encode[" + keyEncoding + "] error.");
			return null;
		}
	}

	/**
	 * 将初始密钥MD5后作为密钥进行加密
	 * 
	 * @param minwen
	 *            密文
	 * @param keyStr
	 *            密钥
	 * @param encoding
	 *            密钥转化成byte时的编码
	 * @return
	 */
	public byte[] encryptWithMD5Key(byte[] minwen, String keyStr) {
		return encrypt(minwen, Digest.digest(keyStr, keyEncoding, Digest.TYPE_MD5));
	}

	/**
	 * 将原始密钥先进行SHA256摘要后再进行加密
	 * 
	 * @param minwen
	 * @param keyStr
	 * @param encoding
	 * @return
	 */
	public byte[] encryptWithSHA256Key(byte[] minwen, String keyStr) {
		return encrypt(minwen, Digest.digest(keyStr, keyEncoding, Digest.TYPE_SHA256));
	}

	public String encryptToHex(byte[] minwen, byte[] key) {
		return Hex.encodeHexStr(encrypt(minwen, key));
	}

	public String encryptToHex(byte[] minwen, String keyStr) {
		return Hex.encodeHexStr(encrypt(minwen, keyStr));
	}

	public String encryptWithMD5KeyToHex(byte[] minwen, String keyStr) {
		return Hex.encodeHexStr(encryptWithMD5Key(minwen, keyStr));
	}

	public String encryptWithSHA256KeyToHex(byte[] minwen, String keyStr) {
		return Hex.encodeHexStr(encryptWithSHA256Key(minwen, keyStr));
	}

	public String encryptToBase64(byte[] minwen, byte[] key) {
		return encryptToBase64(minwen, key, Base64.DEFAULT);
	}

	public String encryptToBase64(byte[] minwen, byte[] key, int flag) {
		return Base64.encodeToString(encrypt(minwen, key), flag);
	}

	public String encryptToBase64(byte[] minwen, String keyStr) {
		return encryptToBase64(minwen, keyStr, Base64.DEFAULT);
	}

	public String encryptToBase64(byte[] minwen, String keyStr, int flag) {
		return Base64.encodeToString(encrypt(minwen, keyStr), flag);
	}

	public String encryptWithMD5KeyToBase64(byte[] minwen, String keyStr) {
		return encryptWithMD5KeyToBase64(minwen, keyStr, Base64.DEFAULT);
	}

	public String encryptWithMD5KeyToBase64(byte[] minwen, String keyStr, int flag) {
		return Base64.encodeToString(encryptWithMD5Key(minwen, keyStr), flag);
	}

	public String encryptWithSHA256KeyToBase64(byte[] minwen, String keyStr) {
		return encryptWithSHA256KeyToBase64(minwen, keyStr, Base64.DEFAULT);
	}

	public String encryptWithSHA256KeyToBase64(byte[] minwen, String keyStr, int flag) {
		return Base64.encodeToString(encryptWithSHA256Key(minwen, keyStr), flag);
	}
}
