package com.bondex.ysl.liblibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * date: 2019/11/18
 * Author: ysl
 * description: mds 对字符串进行加密
 */
public class Md5Uti {

    /**
     * MD5加盐
     * @param password 密码
     * @return 密码加盐后的MD5值
     */
    public static String salting(String password){
        Random random = new Random();
        //随机数字符串最大容量为16位
        StringBuilder sb = new StringBuilder(16);
        //生成最多为16位的随机字符串
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        //由于随机字符串的长度不一定都是16位，做统一16位长度处理
        if(len<16){
            for (int i = 0; i < 16-len; i++) {
                //在后面补0
                sb.append("0");
            }
        }
        //盐
        String salt = sb.toString();
        //得到加盐后密码的16进制字符串,此时password的长度为32
        password = md5toHex(password+salt);

        //最终的结果长度为48位
        char[] res = new char[48];
        //48位中，按一定的规则将加盐后的password存入res中
        //总共循环16次
        for (int i = 0; i < 48; i+=3) {
            res[i] = password.charAt(i/3*2);
            res[i+1] = salt.charAt(i/3);
            res[i+2] = password.charAt(i/3*2+1);
        }
        //最终的md5值为48位，由16位随机字符串和密码加盐后的md5值组成
        return new String(res);
    }

    /**
     * 验证服务器中的密码是否与输入的密码一致
     * @param password 输入的密码
     * @param md5 保存在服务器加盐后的md5值
     * @return 密码是否正确
     */
    public static boolean decode(String password,String md5){
        //盐，即随机数
        char[] salt = new char[16];
        //真正加盐后密码的MD5值
        char[] realMd5 = new char[32];
        //按照加盐规则提取出盐和真正的MD5值
        for (int i = 0; i < 48; i+=3) {
            realMd5[i/3*2] = md5.charAt(i);
            salt[i/3] = md5.charAt(i+1);
            realMd5[i/3*2+1] = md5.charAt(i+2);
        }
        //得出密码加盐后的MD5值
        String tempMd5 = md5toHex(password+new String(salt));
        //与从服务器提取出来的真正MD5值进行对比
        return new String(realMd5).equals(tempMd5);
    }

    /**
     * 获取16进制字符串形式的MD5值
     * @param passwordAndSalt 密码加入随机数后的字符串
     */
    private static String md5toHex(String passwordAndSalt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(passwordAndSalt.getBytes());
            StringBuilder result = new StringBuilder();
            //将字节数组转换成16进制式的字符串
            for (byte b : bytes) {
                //1个byte为8个bit,一个hex(16)进制为16bit,故1个byte可以用2个hex表示
                String temp = Integer.toHexString(b & 0xff);
                //不足2长度的用0来补充
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            //返回最终的字符串
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
