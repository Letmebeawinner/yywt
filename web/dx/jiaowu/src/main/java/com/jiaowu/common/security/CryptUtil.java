package com.jiaowu.common.security;

public abstract interface CryptUtil
{
  public abstract String cryptDes(String paramString1, String paramString2);

  public abstract String decryptDes(String paramString1, String paramString2);

  public abstract String cryptDes(String paramString);

  public abstract String decryptDes(String paramString);

  public abstract String cryptMd5(String paramString1, String paramString2);
}