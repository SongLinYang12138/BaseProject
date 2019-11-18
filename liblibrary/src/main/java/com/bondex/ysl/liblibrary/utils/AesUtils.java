package com.bondex.ysl.liblibrary.utils;

import android.util.Base64;
import me.goldze.mvvmhabit.utils.constant.Constant;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * date: 2019/11/18
 * Author: ysl
 * description:
 * <p>
 * AES加解密工具类, 使用Base64进行编解码
 * String value = AESUtil.encrypt("mazaiting", "123456789");
 * Log.e("MainActivity", value);
 * Log.e("MainActivity", AESUtil.decrypt("mazaiting", value));
 * Created by mazaiting on 2018/6/22.
 */

public class AesUtils {

    /**
     * 密钥长度
     */
    private static final int KEY_LENGTH = 16;
    /**
     * 默认填充位数
     */
    private static final String DEFAULT_VALUE = "0";

    /**
     * 加密
     *
     * @param key 密钥
     * @param src 加密文本
     * @return 加密后的文本
     * @throws Exception
     */
    public static String encrypt(String key, String src) throws Exception {
        // 对源数据进行Base64编码
        src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        // 补全KEY为16位
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();
        // 获取加密后的字节数组
        byte[] result = getBytes(rawKey, src.getBytes("utf-8"), Cipher.ENCRYPT_MODE);
        // 对加密后的字节数组进行Base64编码
        result = Base64.encode(result, Base64.DEFAULT);
        // 返回字符串
        return new String(result, Charset.defaultCharset());
    }

    /**
     * 解密
     *
     * @param key       密钥
     * @param encrypted 待解密文本
     * @return 返回解密后的数据
     * @throws Exception
     */
    public static String decrypt(String key, String encrypted) throws Exception {
        // 补全KEY为16位
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();
        // 获取加密后的二进制字节数组
        byte[] enc = encrypted.getBytes(Charset.defaultCharset());
        // 对二进制数组进行Base64解码
        enc = Base64.decode(enc, Base64.DEFAULT);
        // 获取解密后的二进制字节数组
        byte[] result = getBytes(rawKey, enc, Cipher.DECRYPT_MODE);
        // 对解密后的二进制数组进行Base64解码
        result = Base64.decode(result, Base64.DEFAULT);
        // 返回字符串
        return new String(result, "utf-8");
    }

    /**
     * 密钥key ,默认补的数字，补全16位数，以保证安全补全至少16位长度,android和ios对接通过
     *
     * @param key    密钥key
     * @param length 密钥应有的长度
     * @param text   默认补的文本
     * @return 密钥
     */
    private static String toMakeKey(String key, int length, String text) {
        // 获取密钥长度
        int strLen = key.length();
        // 判断长度是否小于应有的长度
        if (strLen < length) {
            // 补全位数
            StringBuilder builder = new StringBuilder();
            // 将key添加至builder中
            builder.append(key);
            // 遍历添加默认文本
            for (int i = 0; i < length - strLen; i++) {
                builder.append(text);
            }
            // 赋值
            key = builder.toString();
        }
        return key;
    }

    /**
     * 加解密过程
     * 1. 通过密钥得到一个密钥专用的对象SecretKeySpec
     * 2. Cipher 加密算法，加密模式和填充方式三部分或指定加密算 (可以只用写算法然后用默认的其他方式)Cipher.getInstance("AES");
     *
     * @param key  二进制密钥数组
     * @param src  加解密的源二进制数据
     * @param mode 模式，加密为：Cipher.ENCRYPT_MODE;解密为：Cipher.DECRYPT_MODE
     * @return 加解密后的二进制数组
     * @throws NoSuchAlgorithmException           无效算法
     * @throws NoSuchPaddingException             无效填充
     * @throws InvalidKeyException                无效KEY
     * @throws InvalidAlgorithmParameterException 无效密钥
     * @throws IllegalBlockSizeException          非法块字节
     * @throws BadPaddingException                坏数据
     */
    private static byte[] getBytes(byte[] key, byte[] src, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // 密钥规格
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        // 密钥实例
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化密钥模式
        cipher.init(mode, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        // 加密数据
        return cipher.doFinal(src);
    }

    public static String getKey() {

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < Constant.AES_KEY.length(); ++i) {

            int index = i / 2 * 2;
            StringBuffer b = index < Constant.AES_KEY.length() ? buffer.append(Constant.AES_KEY.charAt(index)) : buffer.append(Constant.AES_KEY.charAt(i));
            buffer.append(Constant.AES_KEY.charAt(index));
            int index2 = i/2*2+1;
            StringBuffer b2 = index2 < Constant.AES_KEY.length() ? buffer.append(Constant.AES_KEY.charAt(index2)) : buffer.append(Constant.AES_KEY.charAt(i));
        }

        if(buffer.length() > 16) buffer = new StringBuffer(buffer.subSequence(0,15));
        return buffer.toString();
    }
}