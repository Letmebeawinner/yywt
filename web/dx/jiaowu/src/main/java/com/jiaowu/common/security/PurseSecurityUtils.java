package com.jiaowu.common.security;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PurseSecurityUtils
{
  private static CryptUtil cryptUtil = new CryptUtilImpl();
  private static final String publickey = "GTUqysMnTkUcKP3ouLgyrahQ";
  private static final int count = 24;

  public static CryptUtil getCrypt()
  {
    return cryptUtil;
  }

  public static String getKey(String key)
  {
    return key.substring(0, 24);
  }

  public static String hmacSign(String key, String value)
  {
    return Digest.hmacSign(value, key);
  }

  public static String secrect(String value, String key)
  {
    return cryptUtil.cryptDes(value, getKey(key));
  }

  public static String decryption(String value, String key)
    throws Exception
  {
    return cryptUtil.decryptDes(value, getKey(key));
  }

  public static boolean isPassHmac(String secretValue, String key, String value)
  {
    String svalue = Digest.hmacSign(value, key);
    boolean flag = false;
    if (secretValue.equals(svalue)) {
      flag = true;
    }
    return flag;
  }

  public static void generateHmac(Map<String, String> map, String customerKey)
  {
    StringBuilder sb = new StringBuilder();
    Set set = map.keySet();
    /*for (String string : set) {
      String value = (String)map.get(string);
      sb.append(value);
    }*/
    for (Object string : set) {
        String value = (String)map.get(string.toString());
        sb.append(value);
      }
    String hmacSign = hmacSign(customerKey, sb.toString());
    map.put("hmac", hmacSign);
  }

  public static synchronized String generateOrderNumber(String prefix)
  {
    StringBuffer sb = new StringBuffer();
    long time = System.currentTimeMillis();
    UUID uuid = UUID.randomUUID();
    System.out.println(uuid);
    String uu = uuid.toString().split("-")[0];
    sb.append(prefix);
    sb.append(time);
    sb = new StringBuffer(sb.substring(0, sb.toString().length() - 2));
    sb.append(uu);
    return sb.toString();
  }

  public static String encryptToHex(String data)
  {
    return Des3Encryption.encryptToHex(getKey("GTUqysMnTkUcKP3ouLgyrahQ"), data);
  }

  public static String decryptFromHex(String value)
    throws Exception
  {
    return Des3Encryption.decryptFromHex(getKey("GTUqysMnTkUcKP3ouLgyrahQ"), value);
  }

  public static void main(String[] args) {
    try {
      String pas = "uid:10987";
      String jiamihou = encryptToHex(pas);
      System.out.println(jiamihou);
      System.out.println(decryptFromHex(jiamihou));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}