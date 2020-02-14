package com.a_268.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Web工具类型
 * @author s.li
 *
 */
public class WebUtils {
	/**系统主域名*/
	public static String MYDOMAIN = "";

	/**
	 * 设置Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie的值
	 * @param minuts 存放时间（单位/分）
	 */
	public static void setCookieMinute(HttpServletResponse response, String key, String value, int minuts) {
		setCookieMinuteDomain(response, key, value, minuts, MYDOMAIN);
	}

	/**
	 * 设置Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie的值
	 * @param minuts 存放时间（单位/分）
	 * @param domain Cookie存放域
	 */
	public static void setCookieMinuteDomain(HttpServletResponse response, String key, String value, int minuts,
			String domain) {
		if ((key != null) && (value != null)) {
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(minuts * 60);
			cookie.setPath("/");
			if (!StringUtils.isEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		}
	}

	/**
	 * 设置Session类型的Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie值
	 */
	public static void setCookieSessionTime(HttpServletResponse response, String key, String value) {
		setCookieSessionTime(response, key, value, MYDOMAIN);
	}

	/**
	 * 设置Session类型的Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie值
	 * @param domain Cookie存放域
	 */
	public static void setCookieSessionTime(HttpServletResponse response, String key, String value, String domain) {
		if ((key != null) && (value != null)) {
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			if (!StringUtils.isEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		}
	}

	/**
	 * 设置普通Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie值
	 * @param days 存放天数据
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, int days) {
		setCookie(response, key, value, days, MYDOMAIN);
	}

	/**
	 * 设置普通Cookie
	 * @param response HttpServletResponse
	 * @param key Cookie键
	 * @param value Cookie值
	 * @param days 存放天数据
	 * @param domain Cookie存放域
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, int days, String domain) {
		if ((key != null) && (value != null)) {
			Cookie cookie = new Cookie(key, value);

			cookie.setMaxAge(days * 24 * 60 * 60);

			cookie.setPath("/");
			if (!StringUtils.isEmpty(domain)) {
				cookie.setDomain(domain);
			}

			response.addCookie(cookie);
		}
	}

	/**
	 * 获取Cookie
	 * @param request HttpServletRequest
	 * @param key Cookie键
	 * @return 返回中的值
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		String resValue = "";
		if ((cookies != null) && (cookies.length > 0)) {
			for (int i = 0; i < cookies.length; i++) {
				if ((!key.equalsIgnoreCase(cookies[i].getName())) || (StringUtils.isEmpty(cookies[i].getValue())))
					continue;
				resValue = cookies[i].getValue();
			}

		}
		return resValue;
	}

	/**
	 * 删除Cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name 要删除的Cookie名
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		deleteCookieDomain(request, response, name, MYDOMAIN);
	}

	/**
	 * 删除指定域下的Cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name 要删除的Cookie名
	 * @param domain Cookie存放域
	 */
	public static void deleteCookieDomain(HttpServletRequest request, HttpServletResponse response, String name,String domain) {
		Cookie[] cookies = request.getCookies();
		if ((cookies != null) && (cookies.length > 0)){
			for (int i = 0; i < cookies.length; i++) {
				if (!name.equalsIgnoreCase(cookies[i].getName()))
					continue;
				Cookie ck = new Cookie(cookies[i].getName(), null);
				ck.setPath("/");
				if (!StringUtils.isEmpty(domain)) {
					ck.setDomain(domain);
				}
				ck.setMaxAge(0);
				response.addCookie(ck);
				return;
			}
		}
	}

	/**
	 * 创建CookieMap
	 * @param response HttpServletResponse
	 * @param nameValues 存放Cookie键=值的 Hashtable
	 * @param days 存放天数据
	 */
	public static void createCookieFromMap(HttpServletResponse response, Hashtable<String, String> nameValues,int days) {
		createCookieFromMapDomain(response, nameValues, days, MYDOMAIN);
	}

	/**
	 * 创建CookieMap
	 * @param response HttpServletResponse
	 * @param nameValues 存放Cookie键=值的 Hashtable
	 * @param days 存放天数据
	 * @param domain Cookie存放域
	 */
	public static void createCookieFromMapDomain(HttpServletResponse response, Hashtable<String, String> nameValues,int days, String domain) {
		Set<String> set = nameValues.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			String value = (String) nameValues.get(name);
			Cookie cookie = new Cookie(name, value);
			if (!StringUtils.isEmpty(domain)) {
				cookie.setDomain(domain);
			}
			cookie.setSecure(false);
			cookie.setMaxAge(days * 24 * 60 * 60);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	/**
	 * 获取CookieMap
	 * @param request HttpServletRequest
	 * @return 返回存放Cookie的Hashtable
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Hashtable<String, String> getCookiesForMap(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Hashtable cookieHt = new Hashtable();
		if (cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				cookieHt.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieHt;
	}

	/**
	 * 修改Cookie
	 * @param request HttpServletRequest
	 * @param name Cookie键
	 * @param value Cookie值
	 */
	public static void updateCookie(HttpServletRequest request, String name, String value) {
		Cookie[] cookies = request.getCookies();
		if (cookies.length > 0)
			for (int i = 0; i < cookies.length; i++)
				if (name.equalsIgnoreCase(cookies[i].getName())) {
					cookies[i].setValue(value);
					return;
				}
	}

	/**
	 * 删除所有Cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static void deleteAllCookie(HttpServletRequest request, HttpServletResponse response) {
		deleteAllCookieDomain(request, response, MYDOMAIN);
	}

	/**
	 * 删除所有Cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param domain Cookie指定域
	 */ 
	public static void deleteAllCookieDomain(HttpServletRequest request, HttpServletResponse response, String domain) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				
				Cookie ck = new Cookie(cookie.getName(), null);
				ck.setPath("/");
				if (!StringUtils.isEmpty(domain)) {
					ck.setDomain(domain);
				}
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
		}
	}

	/**
	 * 获取IP地址
	 * @param request HttpServletRequest
	 * @return 返回 IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}

		if ((ipAddress != null) && (ipAddress.length() > 15)) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取操作浏览器类型
	 * @param request HttpServletRequest
	 * @return 返回浏览类型
	 */
	public static String getUserAgent(HttpServletRequest request) {
		String uabrow = request.getHeader("User-Agent");
		System.out.println(new StringBuilder().append("+++ uabrow:").append(uabrow).toString());
		uabrow = uabrow.toLowerCase();
		String result = "";
		if (uabrow.indexOf("firefox") > 0) {
			result = "firefox";
		}
		if (uabrow.indexOf("opera") > 0) {
			result = "opera";
		}
		if (uabrow.indexOf("msie") > 0) {
			result = uabrow.split(";")[1].trim();
		}
		if (uabrow.indexOf("chrome") > 0) {
			result = "chrome";
		}
		if (uabrow.indexOf("android") > 0) {
			result = "android";
		}
		if (uabrow.indexOf("mac os") > 0) {
			result = "mac";
		}
		if (uabrow.indexOf("ios") > 0) {
			result = "ios";
		}
		return result.toLowerCase();
	}

	/**
	 * 判断是不是Ajax请求
	 * @param request HttpServletRequest
	 * @return true是，false不是
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String her = request.getHeader("x-requested-with");
		return StringUtils.isEmpty(her);
	}

	/**
	 * 判断非Ajax请求
	 * @param request HttpServletRequest
	 * @return true是，false不是
	 */
	public static boolean isNotAjaxRequest(HttpServletRequest request) {
		return !isAjaxRequest(request);
	}

	/**
	 * 获取系统根目录
	 * @return 返回根目录 
	 */
	public String getWebRootPath() {
		String s = System.getProperty("user.dir");
		if (s.indexOf("classes") > 0) {
			s = s.replace("WEB-INF", "").replace("classes", "").replace(
					new StringBuilder().append(File.separator).append(File.separator).toString(), File.separator);
		}
		return s;
	}

}
