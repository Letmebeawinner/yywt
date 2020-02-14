package com.jiaowu.common.security;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MessageAuthenticationCode
{
  public static byte[] mac(byte[] key, byte[] data)
    throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    return mac(key, data, 0, data.length);
  }

  public static byte[] mac(byte[] key, byte[] data, int offset, int len)
    throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    String Algorithm = "DES";

    SecretKey deskey = new SecretKeySpec(key, "DES");

    Cipher c1 = Cipher.getInstance("DES");
    c1.init(1, deskey);

    byte[] buf = { 0, 0, 0, 0, 0, 0, 0, 0 };
    for (int i = 0; i < len; ) {
      for (int j = 0; (j < 8) && (i < len); j++)
      {
        int tmp100_98 = j;
        byte[] tmp100_96 = buf; tmp100_96[tmp100_98] = ((byte)(tmp100_96[tmp100_98] ^ data[(offset + i)]));

        i++;
      }

      buf = c1.update(buf);
    }
    c1.doFinal();
    return buf;
  }

  public static byte[] desEncryption(byte[] key, byte[] data)
    throws NoSuchAlgorithmException, Exception
  {
    String Algorithm = "DES/ECB/NoPadding";

    if ((key.length != 8) || (data.length != 8)) {
      throw new IllegalArgumentException("key or data's length != 8");
    }

    DESKeySpec desKS = new DESKeySpec(key);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    SecretKey deskey = skf.generateSecret(desKS);

    Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
    c1.init(1, deskey);

    byte[] buf = c1.doFinal(data);

    byte[] enc_data = new byte[8];
    System.arraycopy(buf, 0, enc_data, 0, 8);
    return enc_data;
  }

  public static byte[] desDecryption(byte[] key, byte[] data)
    throws NoSuchAlgorithmException, Exception
  {
    String Algorithm = "DES/ECB/NoPadding";

    if ((key.length != 8) || (data.length != 8)) {
      throw new IllegalArgumentException("key's len != 8 or data's length != 8");
    }

    SecretKey deskey = new SecretKeySpec(key, "DES");

    Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
    c1.init(2, deskey);

    byte[] decrypted = c1.doFinal(data);
    return decrypted;
  }

  protected byte[] encryptByDES(byte[] bytP, byte[] bytKey)
    throws Exception
  {
    DESKeySpec desKS = new DESKeySpec(bytKey);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    SecretKey sk = skf.generateSecret(desKS);
    Cipher cip = Cipher.getInstance("DES");
    cip.init(1, sk);
    return cip.doFinal(bytP);
  }

  protected byte[] decryptByDES(byte[] bytE, byte[] bytKey)
    throws Exception
  {
    DESKeySpec desKS = new DESKeySpec(bytKey);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    SecretKey sk = skf.generateSecret(desKS);
    Cipher cip = Cipher.getInstance("DES");
    cip.init(2, sk);
    return cip.doFinal(bytE);
  }

  public static byte[] des3Encryption(byte[] key, byte[] data)
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    String Algorithm = "DESede";
    SecretKey deskey = new SecretKeySpec(key, "DESede");
    Cipher c1 = Cipher.getInstance("DESede");
    c1.init(1, deskey);
    return c1.doFinal(data);
  }

  public static byte[] des3Decryption(byte[] key, byte[] data)
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    String Algorithm = "DESede";
    SecretKey deskey = new SecretKeySpec(key, "DESede");
    Cipher c1 = Cipher.getInstance("DESede");
    c1.init(2, deskey);
    return c1.doFinal(data);
  }

  public static byte[] des3Encryption(byte[] key, byte[] iv, byte[] data)
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidAlgorithmParameterException, InvalidKeySpecException
  {
    String Algorithm = "DESede/CBC/PKCS5Padding";
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    DESedeKeySpec spec = new DESedeKeySpec(key);
    SecretKey deskey = keyFactory.generateSecret(spec);
    IvParameterSpec tempIv = new IvParameterSpec(iv);
    Cipher c1 = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    c1.init(1, deskey, tempIv);
    return c1.doFinal(data);
  }

  public static byte[] des3Decryption(byte[] key, byte[] iv, byte[] data)
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidAlgorithmParameterException, InvalidKeySpecException
  {
    String Algorithm = "DESede/CBC/PKCS5Padding";
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    DESedeKeySpec spec = new DESedeKeySpec(key);
    SecretKey deskey = keyFactory.generateSecret(spec);

    IvParameterSpec tempIv = new IvParameterSpec(iv);
    Cipher c1 = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    c1.init(2, deskey, tempIv);
    return c1.doFinal(data);
  }

  public static void main(String[] args)
    throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidAlgorithmParameterException, InvalidKeySpecException, UnsupportedEncodingException
  {
    String key = "6647b807e97889fca8b60047e85d9186e380d3c234f71566";
    String iv = "9df131b13df6bdfe";

    byte[] keyb = StringArrayUtil.hex2byte(key, key.length());
    byte[] ivb = StringArrayUtil.hex2byte(iv, iv.length());
    String value = "2032309250345045,500";

    byte[] temp_bytes = des3Encryption(keyb, ivb, value.getBytes("UTF-8"));
    String en = StringArrayUtil.byte2hex(temp_bytes);
    System.out.println("en=" + en);
    byte[] decrptBytes = des3Decryption(keyb, ivb, temp_bytes);
    System.out.println("de=" + new String(decrptBytes, "UTF-8"));

    String md5key = "66ea3f65-382a-44f6-97e4-15b0a873332f";

    String tempMD5 = "PICODE=PI00001CARDDATA=" + StringArrayUtil.byte2hex(temp_bytes) + md5key;
    System.out.println(tempMD5);

    String md5 = Digest.hmacSign(tempMD5);
    System.out.println(md5);
  }
}