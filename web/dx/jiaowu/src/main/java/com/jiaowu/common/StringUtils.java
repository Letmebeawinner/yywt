package com.jiaowu.common;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.a_268.base.util.DateUtils;

public class StringUtils
{
  private static final String regex_mobile = "^1\\d{10}$";
  private static final String regex_digital = "^[1-9]\\d{0,}";

  public static String starMobile(String mobile)
  {
    if (mobile.length() == 11) {
      String starmobile = new StringBuilder().append(String.valueOf(mobile.charAt(0))).append(String.valueOf(mobile.charAt(1))).append(String.valueOf(mobile.charAt(2))).append("****").append(String.valueOf(mobile.charAt(7))).append(String.valueOf(mobile.charAt(8))).append(String.valueOf(mobile.charAt(9))).append(String.valueOf(mobile.charAt(10))).toString();

      return starmobile;
    }
    return mobile;
  }

  public static String getRandomString(int strLength)
  {
    StringBuffer buffer = new StringBuffer();
    Random random = new Random();
    for (int i = 0; i < strLength; i++)
    {
      if (random.nextBoolean()) {
        int charInt = 48 + random.nextInt(10);
        char c = (char)charInt;
        buffer.append(c);
      }
      else {
        int charInt = 65;
        if (random.nextBoolean())
          charInt = 65 + random.nextInt(26);
        else
          charInt = 97 + random.nextInt(26);
        if (charInt == 79)
          charInt = 111;
        char c = (char)charInt;
        buffer.append(c);
      }
    }
    return buffer.toString();
  }

  public static String md5(String str)
  {
    if (str == null) {
      return null;
    }
    byte[] newByte1 = str.getBytes();
    try {
      MessageDigest messagedigest = MessageDigest.getInstance("MD5");
      byte[] newByte2 = messagedigest.digest(newByte1);
      String cryptograph = "";
      for (int i = 0; i < newByte2.length; i++) {
        String temp = Integer.toHexString(newByte2[i] & 0xFF);
        if (temp.length() < 2)
          temp = new StringBuilder().append("0").append(temp).toString();
        cryptograph = new StringBuilder().append(cryptograph).append(temp).toString();
      }
      return cryptograph;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static boolean validEmail(String sEmail)
  {
    String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    return sEmail.matches(pattern);
  }

  public static boolean validMaxLen(String str, int length)
  {
    if ((str == null) || (str.equals(""))) {
      return false;
    }
    if (str.length() > length) {
      return false;
    }
    return true;
  }

  public static boolean validMinLen(String str, int length)
  {
    if ((str == null) || (str.equals(""))) {
      return false;
    }
    if (str.length() < length) {
      return false;
    }
    return true;
  }

  public static boolean equals(String str1, String str2)
  {
    if ((str1 == null) || (str1.equals("")) || (str2 == null) || (str2.equals(""))) {
      return false;
    }
    return str1.equals(str2);
  }

  public static int toInt(String str)
  {
    int value = 0;
    if ((str == null) || (str.equals("")))
      return 0;
    try
    {
      value = Integer.parseInt(str);
    } catch (Exception ex) {
      ex.printStackTrace();
      value = 0;
    }
    return value;
  }

  public static String arrayToString(Object[] array, String split)
  {
    if (array == null) {
      return "";
    }
    StringBuffer str = new StringBuffer("");
    for (int i = 0; i < array.length; i++) {
      if (i != array.length - 1)
        str.append(array[i].toString()).append(split);
      else {
        str.append(array[i].toString());
      }
    }
    return str.toString();
  }

  public static String getWebInfPath()
  {
    String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();
    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }
    if (filePath.toLowerCase().indexOf("classes") > -1) {
      filePath = filePath.replaceAll("/classes", "");
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = new StringBuilder().append("/").append(filePath).toString();
    }
    if (!filePath.endsWith("/"))
      filePath = new StringBuilder().append(filePath).append("/").toString();
    return filePath;
  }

  public static String getRootPath()
  {
    String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();
    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }
    if (filePath.toLowerCase().indexOf("classes") > -1) {
      filePath = filePath.replaceAll("/classes", "");
    }
    if (filePath.toLowerCase().indexOf("web-inf") > -1) {
      filePath = filePath.substring(0, filePath.length() - 9);
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = new StringBuilder().append("/").append(filePath).toString();
    }

    if (filePath.endsWith("/")) {
      filePath = filePath.substring(0, filePath.length() - 1);
    }
    return filePath;
  }

  public static int formatPage(String page)
  {
    int iPage = 1;
    if ((page == null) || (page.equals("")))
      return iPage;
    try
    {
      iPage = Integer.parseInt(page);
    } catch (Exception ex) {
      ex.printStackTrace();
      iPage = 1;
    }
    return iPage;
  }

  public static String getFileSize(String fileSize)
  {
    String temp = "";
    DecimalFormat df = new DecimalFormat("0.00");
    double dbFileSize = Double.parseDouble(fileSize);
    if (dbFileSize >= 1024.0D) {
      if (dbFileSize >= 1048576.0D) {
        if (dbFileSize >= 1073741824.0D)
          temp = new StringBuilder().append(df.format(dbFileSize / 1024.0D / 1024.0D / 1024.0D)).append(" GB").toString();
        else
          temp = new StringBuilder().append(df.format(dbFileSize / 1024.0D / 1024.0D)).append(" MB").toString();
      }
      else
        temp = new StringBuilder().append(df.format(dbFileSize / 1024.0D)).append(" KB").toString();
    }
    else {
      temp = new StringBuilder().append(df.format(dbFileSize / 1024.0D)).append(" KB").toString();
    }
    return temp;
  }

  public static String getEntry()
  {
    Random random = new Random(100L);
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(new String("yyyyMMddHHmmssS"));
    return md5(new StringBuilder().append(formatter.format(now)).append(random.nextDouble()).toString());
  }

  public static String toUTF8(String str)
  {
    if ((str == null) || (str.equals("")))
      return "";
    try
    {
      return new String(str.getBytes("ISO8859-1"), "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
    }return "";
  }

  public static String to(String str, String charset)
  {
    if ((str == null) || (str.equals("")))
      return "";
    try
    {
      return new String(str.getBytes("ISO8859-1"), charset);
    } catch (Exception ex) {
      ex.printStackTrace();
    }return "";
  }

  public static String getChineseNum(int num)
  {
    String[] chineseNum = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    return chineseNum[num];
  }

  public static String replaceEnter(String str) {
    if (str == null)
      return null;
    return str.replaceAll("\r", "").replaceAll("\n", "");
  }

  public static String getTxtWithoutHTMLElement(String element)
  {
    if (null == element) {
      return element;
    }
    Pattern pattern = Pattern.compile("<[^<|^>]*>");
    Matcher matcher = pattern.matcher(element);
    StringBuffer txt = new StringBuffer();
    while (matcher.find()) {
      String group = matcher.group();
      if (group.matches("<[\\s]*>"))
        matcher.appendReplacement(txt, group);
      else {
        matcher.appendReplacement(txt, "");
      }
    }
    matcher.appendTail(txt);
    String temp = txt.toString().replaceAll("[\r|\n]", "");

    temp = temp.replaceAll("\\s+", " ");
    return temp;
  }

  public static String toTrim(String strtrim)
  {
    if ((null != strtrim) && (!strtrim.equals(""))) {
      return strtrim.trim();
    }
    return "";
  }

  public static String createUUID()
  {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public static String cutffStr(String sourceStr, int length, String charactor)
  {
    String resultStr = sourceStr;
    if ((sourceStr == null) || ("".equals(sourceStr)))
    {
      return "";
    }
    if (sourceStr.length() > length) {
      resultStr = sourceStr.substring(0, length);
      resultStr = new StringBuilder().append(resultStr).append(charactor).toString();
    }

    return resultStr;
  }

  public static boolean isNumber(String str)
  {
    if ((str == null) || (str.trim().equals(""))) {
      return false;
    }
    boolean flag = false;
    try {
      Long.parseLong(str);
      flag = true;
    } catch (Exception e) {
      flag = false;
    }
    return flag;
  }

  public static String getLength(Object goodsName, int length) {
    if (goodsName == null) {
      return null;
    }
    String temp = String.valueOf(goodsName);
    if (temp.length() <= length) {
      return temp;
    }
    temp = new StringBuilder().append(temp.substring(0, length)).append("...").toString();
    return temp;
  }

  public static String handleEmail(String email)
  {
    if (email == null) {
      return "";
    }
    String[] aryEmail = email.split("@");
    if ((aryEmail != null) && (aryEmail.length == 2) && 
      (aryEmail[0] != null)) {
      String firstPart = aryEmail[0].substring(aryEmail[0].length() / 2, aryEmail[0].length());
      if ((firstPart != null) && (!"".equals(firstPart))) {
        char[] repeatChar = new char[firstPart.length()];
        for (int i = 0; i < firstPart.length(); i++) {
          repeatChar[i] = '*';
        }
        email = email.replaceFirst(new StringBuilder().append(firstPart).append("@").toString(), new StringBuilder().append(new String(repeatChar)).append("@").toString());
      }

    }

    return email;
  }

  public static boolean isMobileNo(String tocheckNo)
  {
    return Pattern.matches("^1\\d{10}$", tocheckNo);
  }

  public static boolean neNullAndDigital(String source, boolean ingoreDigital, Integer length)
  {
    boolean isvalid = false;
    if ((source != null) && (!"".equals(source.trim()))) {
      isvalid = true;
    }
    if ((!ingoreDigital) && (isvalid)) {
      isvalid = Pattern.matches("^[1-9]\\d{0,}", source);
    }
    if ((isvalid) && (length != null)) {
      isvalid = source.trim().length() <= length.intValue();
    }
    return isvalid;
  }

  public static boolean validNull(String str)
  {
    if ((str == null) || (str.trim().equals(""))) {
      return false;
    }
    return true;
  }

  public static boolean validNull(String[] str)
  {
    for (int i = 0; i < str.length; i++) {
      if ((str[i] == null) || (str[i].trim().equals(""))) {
        return false;
      }
    }
    return true;
  }

  public static String getRootPath(String resource) {
    String filePath = Thread.currentThread().getContextClassLoader().getResource(resource).toString();
    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }
    if (filePath.toLowerCase().indexOf("classes") > -1) {
      filePath = filePath.replaceAll("/classes", "");
    }
    if (filePath.toLowerCase().indexOf("web-inf") > -1) {
      filePath = filePath.substring(0, filePath.length() - 9);
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = new StringBuilder().append("/").append(filePath).toString();
    }

    if (!filePath.endsWith("/")) {
      filePath = new StringBuilder().append(filePath).append("/").toString();
    }
    return filePath;
  }

  public static String getRandStr(int n) {
    Random random = new Random();
    String sRand = "";
    for (int i = 0; i < n; i++) {
      String rand = String.valueOf(random.nextInt(10));
      sRand = new StringBuilder().append(sRand).append(rand).toString();
    }
    return sRand;
  }

  public static String getSysTimeRandom()
  {
    return new StringBuilder().append(System.currentTimeMillis()).append("").append(new Random().nextInt(100)).toString();
  }

  public static String getSysTimeRandom(int count)
  {
    String resultRandom = new StringBuilder().append(System.currentTimeMillis()).append("").append(new Random().nextInt(100)).toString();

    String resultRandomPro = "";
    int resultCount = resultRandom.length();
    if (count >= resultCount) {
      for (int i = 0; i < count - resultCount; i++)
      {
        resultRandomPro = new StringBuilder().append(resultRandomPro).append("0").toString();
      }

      return new StringBuilder().append(resultRandomPro).append(resultRandom).toString();
    }

    return resultRandom.substring(resultCount - 1 - count, resultCount - 1);
  }

  public static String[] parseParam(String source)
  {
    if ((source == null) || ("".equals(source))) {
      throw new IllegalArgumentException("source is null");
    }
    String[] resultAry = source.split("&");
    return resultAry;
  }

  public static String[] parseParamArray(String source)
  {
    if ((source == null) || ("".equals(source))) {
      throw new IllegalArgumentException("source is null");
    }
    String[] resultAry = source.split("\\|");
    return resultAry;
  }

  public static String convStrToHessian(String item, int count) throws UnsupportedEncodingException {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) {
      sb.append(URLEncoder.encode(item, "utf-8")).append("|");
    }
    if ((sb != null) && (sb.length() > 0)) {
      sb.deleteCharAt(sb.length() - 1);
    }

    return sb.toString();
  }

  public static String convToHessian(String item, int count) throws UnsupportedEncodingException {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) {
      sb.append(URLEncoder.encode(item, "utf-8")).append("|");
    }

    return sb.toString();
  }

  public static String convAryToStr(String sourceStr, String sourceChar, String resultChar, boolean isTrans) {
    if (isTrans) {
      sourceChar = new StringBuilder().append("\\").append(sourceChar).toString();
    }
    String[] sourceStrAry = sourceStr.split(sourceChar);

    int count = sourceStrAry.length;

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) {
      try {
        Long.parseLong(sourceStrAry[i]);
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      sb.append(sourceStrAry[i]).append(resultChar);
    }
    if ((sb != null) && (sb.length() > 0)) {
      sb.deleteCharAt(sb.length() - 1);
    }

    return sb.toString();
  }

  public static String convListToString(List<Map<String, Object>> list, String flag) {
    StringBuilder sb = new StringBuilder();
    int count = list.size();
    for (int i = 0; i < count; i++) {
      sb.append(((Map)list.get(i)).get(flag));
      sb.append(",");
    }
    if ((sb != null) && (sb.length() > 0)) {
      sb.deleteCharAt(sb.length() - 1);
    }

    return sb.toString();
  }

  public static String queryParam(String param, String queryParam) {
    if (validNull(param)) {
      return new StringBuilder().append(queryParam).append("=").append(param).append("&").toString();
    }
    return "";
  }

  public static Timestamp convertToTimestamp(String time)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date myDate = null;
    Timestamp myTimestamp = null;
    try {
      myDate = sdf.parse(time);
      myTimestamp = new Timestamp(myDate.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return myTimestamp;
  }

  public static String randomBase()
  {
    String result = String.valueOf(System.currentTimeMillis() % 10L);

    return result;
  }

  public static Long getDeliveryIdBase(Long id)
  {
    if (id == null) {
      throw new IllegalArgumentException();
    }

    return Long.valueOf(id.longValue() % 10L);
  }

  public static boolean checkIp(String strIp1, String StrIp2)
  {
    boolean boo = false;
    if ("".equals(StrIp2)) {
      return true;
    }
    boolean isOrderIpRule = strIp1.matches("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    if (!isOrderIpRule) {
      return boo;
    }
    String[] ipArray = StrIp2.split(",");
    for (int i = 0; i < ipArray.length; i++) {
      String ipArr = ipArray[i];
      String ipay = "";
      if (ipArr.contains("*")) {
        ipay = ipArr.substring(0, ipArr.lastIndexOf("."));
        boo = strIp1.substring(0, strIp1.lastIndexOf(".")).equals(ipay);
        if (boo)
          return boo;
      }
      else if (ipArr.contains("-")) {
        ipay = ipArr.substring(ipArr.lastIndexOf(".") + 1);
        String[] ipayArray = ipay.split("-");
        String ips = strIp1.substring(strIp1.lastIndexOf(".") + 1);
        if ((Integer.parseInt(ipayArray[0]) <= Integer.parseInt(ips)) && (Integer.parseInt(ips) <= Integer.parseInt(ipayArray[1]))) {
          boo = true;
          return boo;
        }
      } else {
        boo = strIp1.equals(ipArr);
        if (boo) {
          return boo;
        }
      }
    }
    return boo;
  }

  public static boolean isEmpty(String str) {
    if ((null == str) || (str.trim().length() == 0)) {
      return true;
    }
    return false;
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static String packagingString(String str, String prefix, String suffix)
  {
    if (isEmpty(str))
      str = "";
    if (isEmpty(prefix))
      prefix = "";
    if (isEmpty(suffix))
      suffix = "";
    return new StringBuilder().append(prefix).append(str).append(suffix).toString();
  }

  public static String[] sortArray(String[] array)
  {
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < i; j++) {
        if (array[i].compareTo(array[j]) < 0) {
          String temp = array[j];
          array[j] = array[i];
          array[i] = temp;
        }
      }
    }
    return array;
  }

  public static int bisearch(String[] sourceArray, String seek)
  {
    if ((null == sourceArray) || (sourceArray.length == 0) || (null == seek)) {
      return -1;
    }

    int bottom = 0;
    int top = sourceArray.length - 1;

    while (bottom <= top) {
      int mid = (bottom + top) / 2;
      int result = sourceArray[mid].compareTo(seek);
      if (0 == result)
        return mid;
      if (result > 0)
        top = mid - 1;
      else {
        bottom = mid + 1;
      }
    }
    return -1;
  }

  public static boolean isBlank(String str)
  {
    return (null == str) || (str.trim().length() == 0);
  }

  /*public static boolean isBlank(Object obj) {
    return ObjectUtils.isNull(obj) ? true : isBlank(obj.toString());
  }*/

  /*public static String getModelDate(Date oldDate)
  {
    if (ObjectUtils.isNotNull(oldDate)) {
      Date newDate = new Date();
      long second = (newDate.getTime() - oldDate.getTime()) / 1000L;
      if (second <= 60L)
        return new StringBuilder().append(second).append("秒前").toString();
      if ((60L < second) && (second <= 3600L)) {
        second /= 60L;
        return new StringBuilder().append(second).append("分钟前").toString();
      }if ((3600L < second) && (second <= 86400L)) {
        second = second / 60L / 60L;
        return new StringBuilder().append(second).append("小时前").toString();
      }if ((86400L < second) && (second <= 864000L)) {
        String formatDate = DateUtils.formatDate(oldDate, "HH:mm:ss");
        second = second / 60L / 60L / 24L;
        return new StringBuilder().append(second).append("天前 ").append(formatDate).toString();
      }

      return DateUtils.formatDate(oldDate, "yyyy-MM-dd HH:mm:ss");
    }

    return "";
  }*/

  /**
   * 判断字符穿是否为数字
   * @param str
   * @return
   */
  public static boolean isNumeric(String str){
    if(str==null||str.equals("")){
      return false;
    }
    for (int i = str.length();--i>=0;){
      if (!Character.isDigit(str.charAt(i))){
        return false;
      }
    }
    return true;
  }
}