package net.zwj.tools.pub;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

/**
 * 3DES加密
 */
public class TripleDES extends AbstractEncryptDTools {

	final private static int KEY_LENGTH = 24;

	/**
	 * 构造密钥
	 * 
	 * @param temp
	 * @return
	 */
	private byte[] getKey(byte[] temp) {
		byte[] key = new byte[KEY_LENGTH];
		if (temp.length >= 16) {
			System.arraycopy(temp, 0, key, 0, 16);
			System.arraycopy(temp, 0, key, 16, 8);
		} else {
			System.arraycopy(temp, 0, key, 0, temp.length);
			for (int i = 0; i < KEY_LENGTH; i++) {
				key[i] = temp[i % temp.length];
			}
		}
		return key;
	}

	/**
	 * 解密
	 * 
	 * @param miwen
	 * @param key
	 * @return
	 */
	public byte[] decrypt(byte[] miwen, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(getKey(key), "DESede");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(miwen);
		} catch (NoSuchPaddingException e) {
			Log.e(logTag, "", e);
		} catch (NoSuchAlgorithmException e) {
			Log.e(logTag, "", e);
		} catch (InvalidKeyException e) {
			Log.e(logTag, "", e);
		} catch (IllegalBlockSizeException e) {
			Log.e(logTag, "", e);
		} catch (BadPaddingException e) {
			Log.e(logTag, "", e);
		}
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param minwen
	 * @param key
	 * @return
	 */
	public byte[] encrypt(byte[] minwen, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(getKey(key), "DESede");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(minwen);
		} catch (NoSuchPaddingException e) {
			Log.e(logTag, "", e);
		} catch (NoSuchAlgorithmException e) {
			Log.e(logTag, "", e);
		} catch (InvalidKeyException e) {
			Log.e(logTag, "", e);
		} catch (IllegalBlockSizeException e) {
			Log.e(logTag, "", e);
		} catch (BadPaddingException e) {
			Log.e(logTag, "", e);
		}
		return null;
	}

}
