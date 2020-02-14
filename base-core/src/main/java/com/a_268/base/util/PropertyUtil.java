package com.a_268.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 获取properties属性文件的工具类
 * @author s.li
 *
 */
public class PropertyUtil {
	/**属性文件获取工类的Map，Key=文件名，value=PropertyUtil*/
	private static Map<String, PropertyUtil> instance = Collections.synchronizedMap(new HashMap<String, PropertyUtil>());
	private String sourceUrl;
	private ResourceBundle resourceBundle;
	private static Map<String, String> convert = Collections.synchronizedMap(new HashMap<String, String>());

	protected PropertyUtil(String sourceUrl) {
		this.sourceUrl = sourceUrl;
		load();
	}

	/**
	 * 初始化读取
	 * @param sourceUrl 属性文件的名字，不包含后缀
	 * @return
	 */
	public static PropertyUtil getInstance(String sourceUrl) {
		synchronized (PropertyUtil.class) {
			PropertyUtil manager = (PropertyUtil) instance.get(sourceUrl);
			if (manager == null) {
				manager = new PropertyUtil(sourceUrl);
				instance.put(sourceUrl, manager);
			}
			return manager;
		}
	}

	/**
	 * 加载属性文件内容（key=value）
	 */
	private synchronized void load() {
		try {
			this.resourceBundle = ResourceBundle.getBundle(this.sourceUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sourceUrl = " + this.sourceUrl + " file load error!");
		}
	}

	/**
	 * 通过key获取属性文件value
	 * @param key 属性文件的
	 * @return 返回属性key对应的value
	 */
	public String getProperty(String key) {
		try {
			return new String(this.resourceBundle.getString(key).getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return this.resourceBundle.getString(key);
	}

	/**
	 * 读取所有属性中的所有的key=value并设置到Map中
	 * @return 返回设置好的key=value的Map，这个Map包含了所有属性文件中所有的key=value
	 */
	public Map<String, String> readyConvert() {
		Enumeration<String> enu = this.resourceBundle.getKeys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = this.resourceBundle.getString(key);
			convert.put(value, key);
		}
		return convert;
	}

	/**
	 * 能过ResourceBundle读取某个属性文件的key=value，并设置Map中
	 * @param resourcebundle ResourceBundle
	 * @return 返回设置好key=value的Map,其中这个Map包含了某个属性文件的key=value
	 */
	public Map<String, String> readyConvert(ResourceBundle resourcebundle) {
		Enumeration<String> enu = resourcebundle.getKeys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = resourcebundle.getString(key);
			convert.put(value, key);
		}
		return convert;
	}
	
	/**
     * 将文件中配置信息填充到properties对象中
     * @return  Properties
     */
    public Properties fillProperties() throws MissingResourceException {
        Properties properties = new Properties();

        final ResourceBundle res = this.resourceBundle;
        Enumeration<String> en = res.getKeys();
        String key = null;
        String value = null;
        while (en.hasMoreElements()) {
            key = en.nextElement().trim();
            value = res.getString(key);
            properties.setProperty(key, value.trim());
        }
        return properties;
    }
}
