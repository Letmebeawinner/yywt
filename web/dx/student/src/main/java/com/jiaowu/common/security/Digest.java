package com.jiaowu.common.security;

//import com.yizhilu.os.core.util.MD5;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.jiaowu.common.MD5;

public class Digest
{
  public static final String ENCODE = "UTF-8";

  public static String signMD5(String aValue, String encoding)
  {
    try
    {
      byte[] input = aValue.getBytes(encoding);
      MessageDigest md = MessageDigest.getInstance("MD5");
      return ConvertUtils.toHex(md.digest(input));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }return null;
  }

  public static String hmacSign(String aValue)
  {
    try
    {
      byte[] input = aValue.getBytes();
      MessageDigest md = MessageDigest.getInstance("MD5");
      return ConvertUtils.toHex(md.digest(input));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }return null;
  }

  public static String hmacSign(String aValue, String aKey)
  {
    return hmacSign(aValue, aKey, "UTF-8");
  }

  public static String hmacSign(String aValue, String aKey, String encoding)
  {
    byte[] k_ipad = new byte[64];
    byte[] k_opad = new byte[64];
    byte[] keyb;
    byte[] value;
    try
    {
      keyb = aKey.getBytes(encoding);
      value = aValue.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      keyb = aKey.getBytes();
      value = aValue.getBytes();
    }
    Arrays.fill(k_ipad, keyb.length, 64, (byte)54);
    Arrays.fill(k_opad, keyb.length, 64, (byte)92);
    for (int i = 0; i < keyb.length; i++) {
      k_ipad[i] = ((byte)(keyb[i] ^ 0x36));
      k_opad[i] = ((byte)(keyb[i] ^ 0x5C));
    }

    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    md.update(k_ipad);
    md.update(value);
    byte[] dg = md.digest();
    md.reset();
    md.update(k_opad);
    md.update(dg, 0, 16);
    dg = md.digest();
    return ConvertUtils.toHex(dg);
  }

  public static String hmacSHASign(String aValue, String aKey, String encoding)
  {
    byte[] k_ipad = new byte[64];
    byte[] k_opad = new byte[64];
    byte[] keyb;
    byte[] value;
    try
    {
      keyb = aKey.getBytes(encoding);
      value = aValue.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      keyb = aKey.getBytes();
      value = aValue.getBytes();
    }
    Arrays.fill(k_ipad, keyb.length, 64, (byte)54);
    Arrays.fill(k_opad, keyb.length, 64, (byte)92);
    for (int i = 0; i < keyb.length; i++) {
      k_ipad[i] = ((byte)(keyb[i] ^ 0x36));
      k_opad[i] = ((byte)(keyb[i] ^ 0x5C));
    }

    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    md.update(k_ipad);
    md.update(value);
    byte[] dg = md.digest();
    md.reset();
    md.update(k_opad);
    md.update(dg, 0, 20);
    dg = md.digest();
    return ConvertUtils.toHex(dg);
  }

  public static String digest(String aValue)
  {
    return digest(aValue, "UTF-8");
  }

  public static String digest(String aValue, String encoding)
  {
    aValue = aValue.trim();
    byte[] value;
    try
    {
      value = aValue.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      value = aValue.getBytes();
      e.printStackTrace();
    }
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    return ConvertUtils.toHex(md.digest(value));
  }

  public static String digest(String aValue, String alg, String encoding)
  {
    aValue = aValue.trim();
    byte[] value;
    try
    {
      value = aValue.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      value = aValue.getBytes();
      e.printStackTrace();
    }
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance(alg);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    return ConvertUtils.toHex(md.digest(value));
  }

  public static String udpSign(String aValue) {
    try {
      byte[] input = aValue.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("SHA1");
      return new String(Base64.encode(md.digest(input)), "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static void main(String[] args) {
    System.out.println(digest("11", "utf-8"));
    System.out.println(MD5.getMD5("1"));
    System.out.println(udpSign("17ba0791499db908433b80f37c5fbc89b870084b"));
  }
}