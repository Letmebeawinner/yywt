package com.jiaowu.common.security;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des3Encryption
{
  public static final String CHAR_ENCODING = "UTF-8";

  public static byte[] encode(byte[] key, byte[] data)
    throws Exception
  {
    return MessageAuthenticationCode.des3Encryption(key, data);
  }

  public static byte[] decode(byte[] key, byte[] value) throws Exception {
    return MessageAuthenticationCode.des3Decryption(key, value);
  }

  public static String encode(String key, String data) {
    try {
      byte[] keyByte = key.getBytes("UTF-8");
      byte[] dataByte = data.getBytes("UTF-8");
      byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);

      return new String(Base64.encode(valueByte), "UTF-8");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String decode(String key, String value)
  {
    try {
      byte[] keyByte = key.getBytes("UTF-8");
      byte[] valueByte = Base64.decode(value.getBytes("UTF-8"));
      byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);

      return new String(dataByte, "UTF-8");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String encryptToHex(String key, String data)
  {
    try {
      byte[] keyByte = key.getBytes("UTF-8");
      byte[] dataByte = data.getBytes("UTF-8");
      byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);

      return ConvertUtils.toHex(valueByte);
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String decryptFromHex(String key, String value)
  {
    try {
      byte[] keyByte = key.getBytes("UTF-8");
      byte[] valueByte = ConvertUtils.fromHex(value);
      byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);

      return new String(dataByte, "UTF-8");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String udpEncrypt(String key, String data)
  {
    try {
      Key k = updGenerateKey(key);
      IvParameterSpec IVSpec = new IvParameterSpec(new byte[8]);
      Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      c.init(1, k, IVSpec);
      byte[] output = c.doFinal(data.getBytes("UTF-8"));
      return new String(Base64.encode(output), "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static Key updGenerateKey(String key)
  {
    try {
      DESedeKeySpec KeySpec = new DESedeKeySpec(UdpHexDecode(key));
      SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance("DESede");
      return KeyFactory.generateSecret(KeySpec);
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String udpDecrypt(String key, String data)
  {
    try {
      byte[] input = Base64.decode(data.getBytes("UTF-8"));
      Key k = updGenerateKey(key);
      IvParameterSpec IVSpec = new IvParameterSpec(new byte[8]);
      Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      c.init(2, k, IVSpec);
      byte[] output = c.doFinal(input);
      return new String(output, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static byte[] UdpHexDecode(String s)
  {
    byte[] abyte0 = new byte[s.length() / 2];
    String s1 = s.toLowerCase();
    for (int i = 0; i < s1.length(); i += 2) {
      char c = s1.charAt(i);
      char c1 = s1.charAt(i + 1);
      int j = i / 2;
      if (c < 'a')
        abyte0[j] = ((byte)(c - '0' << 4));
      else
        abyte0[j] = ((byte)(c - 'a' + 10 << 4));
      if (c1 < 'a')
      {
        int tmp92_90 = j;
        byte[] tmp92_89 = abyte0; tmp92_89[tmp92_90] = ((byte)(tmp92_89[tmp92_90] + (byte)(c1 - '0')));
      }
      else
      {
        int tmp109_107 = j;
        byte[] tmp109_106 = abyte0; tmp109_106[tmp109_107] = ((byte)(tmp109_106[tmp109_107] + (byte)(c1 - 'a' + 10)));
      }
    }
    return abyte0;
  }

  public static String encode(String value) {
    return encode("z9aa179L5c2g0253375qx67G", value);
  }

  public static String decode(String value) {
    return decode("z9aa179L5c2g0253375qx67G", value);
  }

  public static void main(String[] args)
    throws UnsupportedEncodingException
  {
    System.out.println(encryptToHex("l3Z5q138122111tyl3Z53812", "1,2,3"));
    System.out.println(decryptFromHex("l3Z5q138122111tyl3Z53812", "10cfebdc47b27286"));
  }
}