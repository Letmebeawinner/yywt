package com.jiaowu.common.security;

//import com.yizhilu.os.core.util.StringUtils;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CryptUtilImpl
  implements CryptUtil
{
  public String cryptDes(String source, String key)
  {
    return Des3Encryption.encode(key, source);
  }

  public String decryptDes(String des, String key)
  {
    return Des3Encryption.decode(key, des);
  }

  public String cryptDes(String source)
  {
    return Des3Encryption.encode(source);
  }

  public String decryptDes(String des)
  {
    return Des3Encryption.decode(des);
  }
  public String cryptMd5(String source, String key) { byte[] k_ipad = new byte[64];
    byte[] k_opad = new byte[64];
    byte[] keyb;
    byte[] value;
    try { keyb = key.getBytes("UTF-8");
      value = source.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      keyb = key.getBytes();
      value = source.getBytes();
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
    }
    catch (NoSuchAlgorithmException e)
    {
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
    return toHex(dg); }

  public static String toHex(byte[] input)
  {
    if (input == null)
      return null;
    StringBuffer output = new StringBuffer(input.length * 2);
    for (int i = 0; i < input.length; i++) {
      int current = input[i] & 0xFF;
      if (current < 16)
        output.append("0");
      output.append(Integer.toString(current, 16));
    }

    return output.toString();
  }

  public static void main(String[] args)
  {
    /*String source = StringUtils.getRandStr(60);
    String key = "123456781234567812345678";
    CryptUtilImpl impl = new CryptUtilImpl();
    String des = impl.cryptDes(source, key);
    System.out.println(des);

    String s1 = impl.decryptDes(des, key);
    System.out.println(s1);*/
  }
}