package com.hg.httpdb;

import java.io.IOException;

/**
 * 加密处理
 * @author wanghg
 */
public class Crypt {
    private static final String CHARSET = "UTF-8";
	public static String encrypt(String str, String key) throws IOException {
		return Base64.encode(new AES().encrypt(str.getBytes(CHARSET), key.getBytes(CHARSET)));
	}
	public static String decrypt(String str, String key) throws IOException {
		return new String(new AES().decrypt(Base64.decode(str), key.getBytes(CHARSET)), CHARSET).trim();
	}
	public static byte[] decrypt(byte[] data, String key) throws IOException {
		return new AES().decrypt(data, key.getBytes(CHARSET));
	}
	public static byte[] encrypt(byte[] data, String key) throws IOException {
		return new AES().encrypt(data, key.getBytes(CHARSET));
	}
}
