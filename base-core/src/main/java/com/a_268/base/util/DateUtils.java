package com.a_268.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 日期格式化模式（日期类型数据）
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“2012-10-08”，一般用于日期类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * 日期格式化模式（时间类型数据）
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“2012-10-08 10:10:08”，一般用于时间类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式化模式（时间类型数据），精确到分
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“2012-10-08 10:10”，一般用于时间类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	/**
	 * 日期格式化模式（时间类型数据），精确到时
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“2012-10-08 10”，一般用于时间类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 日期格式化模式（时间类型数据），精确到天
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“20151008”，一般用于时间类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";

	/**
	 * 日期格式化模式（时间类型数据），精确到分钟
	 * <p>
	 * 日期格式化模式，使用此模式将日期格式化为“201510081521”，一般用于时间类型数据格式化
	 * </p>
	 */
	public static final String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";
	/**
	 * 其他时间值转换为毫秒单位：秒
	 */
	public static final long UNIT_SECOND_TIME = 1000;
	/**
	 * 其他时间值转换为毫秒单位：分钟
	 */
	public static final long UNIT_MINUS_TIME = 60 * UNIT_SECOND_TIME;
	/**
	 * 其他时间值转换为毫秒单位：小时
	 */
	public static final long UNIT_HOUR_TIME = 60 * UNIT_MINUS_TIME;
	/**
	 * 其他时间值转换为毫秒单位：天
	 */
	public static final long UNIT_DAY_TIME = 24 * UNIT_HOUR_TIME;
	/**
	 * 时间单位名称：秒
	 */
	public static final String UNIT_SECOND_NAME = "秒";
	/**
	 * 时间单位名称：分钟
	 */
	public static final String UNIT_MINUS_NAME = "分钟";
	/**
	 * 时间单位名称：小时
	 */
	public static final String UNIT_HOUR_NAME = "小时";
	/**
	 * 时间单位名称：天
	 */
	public static final String UNIT_DAY_NAME = "天";

	/**
	 * 根据特定模式，将字符串型日期对象解析成Date对象
	 * 
	 * @param source
	 *            要解析的字符串
	 * @param pattern
	 *            解析模式，默认为{@value #PATTERN_YYYY_MM_DD_HH_MM_SS}
	 * @return 解析结果
	 * @throws ParseException
	 *             如果要解析的字符串格式不匹配，则抛出此异常
	 */
	public static Date parse(final String source, String pattern)
			throws ParseException {
		// 检查value是否为空
		if (source == null || StringUtils.isTrimEmpty(source)) {
			return null;
		}
		// 如果pattern为空
		if (pattern == null) {
			// 设置pattern为PATTERN_YYY_MM_DD_HH_MM_SS
			pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
		}
		// 初始化一个format类
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		// 开始解析
		return format.parse(source);
	}

	/**
	 * 将日期对象根据特定格式格式化成字符串
	 * 
	 * @param source
	 *            要格式化的日期对象
	 * @param pattern
	 *            格式化模式，默认为{@value #PATTERN_YYYY_MM_DD_HH_MM_SS}
	 * @return 格式化后的字符串
	 */
	public static String format(final Date source, String pattern) {
		// 检查value是否为空
		if (source == null) {
			return null;
		}
		// 如果pattern为空
		if (pattern == null) {
			// 设置pattern为PATTERN_YYYY_MM_DD_HH_MM_SS
			pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
		}
		// 初始化一个format类
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(source);
	}

	/**
	 * 格式化到天
	 * 
	 * @param source
	 * @return
	 */
	public static String formatToDay(final Date source) {

		return format(source, PATTERN_YYYY_MM_DD);
	}

	/**
	 * 格式化日期到秒
	 * 
	 * @param source
	 * @return
	 */
	public static String formatToSecond(final Date source) {
		return format(source, PATTERN_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 格式化日期到分钟
	 * 
	 * @param source
	 * @return
	 */
	public static String formatToMinute(final Date source) {
		return format(source, PATTERN_YYYY_MM_DD_HH_MM);
	}
	/**
	 * 格式化日期到小时
	 * 
	 * @param source
	 * @return
	 */
	public static String formatToHour(final Date source){
		return format(source,PATTERN_YYYY_MM_DD_HH);
	}

	/**
	 * 返回服务器时间
	 * 
	 * @return
	 */
	public static Date getServerDate() {
		return new Date();
	}

	/**
	 * 判断特定时间距离当前服务器时间的毫秒数
	 * 
	 *            要比较的时间
	 * @return 比较结果，如果当前日期迟于要比较的时间则返回值小于零，反之大于零
	 */
	private static long millisecondsBeforeNow(final Date source) {
		// 检查日期对象是否存在
		if (source == null) {
			return 0;
		}
		return new Date().getTime() - source.getTime();
	}

	/**
	 * 此方法用于前台项目显示当前相差的时间
	 * <p>
	 * 该方法用于前台项目中使用，一般用于提问时间等，规则如下（xx为时间，数字）：<br/>
	 * （当前时间与发布时间）未超过一分钟：则显示秒，即xx秒前；<br/>
	 * （当前时间与发布时间）未超过一小时：则显示到分钟，即xx分钟前；<br/>
	 * （当前时间与发布时间）未超过一天（24小时）：则显示到小时，即xx小时前；<br/>
	 * （当前时间与发布时间）超过一天（24小时）：则显示实际发布时间，年-月-日 时:分:秒；
	 * </p>
	 * 
	 * @param source
	 *            要格式化的日期
	 * @return 格式化结果
	 */
	public static String formatToFront(final Date source) {
		// 获取相差的毫秒数
		long d = millisecondsBeforeNow(source);
		// 之前描述名称
		final String BERFORE = "前";
		// 一分钟以内
		if (d < UNIT_MINUS_TIME) {
			long unit = d / UNIT_SECOND_TIME;
			return (unit==0?1:unit) + UNIT_SECOND_NAME + BERFORE;
		}
		// 一个小时以内
		else if (d < UNIT_HOUR_TIME) {
			return (d / UNIT_MINUS_TIME) + UNIT_MINUS_NAME + BERFORE;
		}
		// 未超过一天
		else if (d < UNIT_DAY_TIME) {
			return (d / UNIT_HOUR_TIME) + UNIT_HOUR_NAME + BERFORE;
		}
		// 超过一天
		else {
			return format(source, PATTERN_YYYY_MM_DD_HH_MM_SS);
		}
	}

	/**
	 * 获取日期相对于1970/01/01的时间戳
	 * @param source 要比较的时间
	 * @return 时间戳
	 */
	public static long getTime(final Date source){
		if(source == null){
			return -1;
		}
		return source.getTime();
	}
	
	/**
	 * 将秒转成分钟和秒字符串
	 * 
	 * @param second
	 *            秒钟
	 * @return xxm xxs
	 */
	public static String formatToFront(Integer second) {
		long s = second % 60;
		int t = (int) (second / 60);
		return t + "分 " + s + "秒";
	}

	/**
	 * 特定日期和服务器日期相差的天数
	 * 
	 * @param source
	 *            要比较的日期
	 * @return 天数，之后大于零，之前小于零
	 */
	public static int daysBeforeNow(final Date source) {
		// 如果要比较的source不存在
		if (source == null) {
			return 0;
		}
		Calendar now = Calendar.getInstance();
		Calendar old = Calendar.getInstance();
		// 设置时分秒为0:0:0
		old.setTime(source);
		old.set(Calendar.HOUR_OF_DAY, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		// 获取相差描述
		long l = old.getTimeInMillis() - now.getTimeInMillis();
		// 转换程天数
		return BigDecimal
				.valueOf(l)
				.divide(BigDecimal.valueOf(UNIT_DAY_TIME), RoundingMode.HALF_UP)
				.intValue();
	}

	/**
	 * 特定日期1  和  特定日期2  相差的天数
	 * 
	 *            要比较的日期
	 * @return 天数，之后大于零，之前小于零 
	 */
	public static int daysBefore(final Date source1,final Date source2) {
		// 如果要比较的source不存在
		if (source1 == null || source2 == null) {
			return 0;
		}
		Calendar now = Calendar.getInstance();
		Calendar old = Calendar.getInstance();
		// 设置时分秒为0:0:0
		old.setTime(source1);
		old.set(Calendar.HOUR_OF_DAY, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		
		now.setTime(source2);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		// 获取相差描述
		long l = old.getTimeInMillis() - now.getTimeInMillis();
		// 转换程天数
		return BigDecimal
				.valueOf(l)
				.divide(BigDecimal.valueOf(UNIT_DAY_TIME), RoundingMode.HALF_UP)
				.intValue();
	}

	
	/**
	 * 获取在某天的基础上增加或减少N天
	 * 
	 * @param date
	 *            某天
	 *            加上或减少的天数，正数为加，负数为减。
	 * @return
	 * @author ZhangYunHe
	 */
	public static Date getDateAfterDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

	/**
	 * 获取今天是今年中的第几天
	 * 
	 * @return
	 * @author ZhangYunHe
	 * @date 2013-1-8
	 */
	public static int getTodayOfYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取指定日期是今年中的第几天
	 * 
	 * @param dateStr
	 *            日期字符串 格式：yyyy-MM-dd
	 * @return 指定日期是今年中的第几天
	 * @author ZhangYunHe
	 * @note 请不要输入2012-02-31、2012-13-32之类的非法日期字符串
	 */
	public static int getDayOfYear(String dateStr) {
		Calendar calendar = initCalendarByDateString(dateStr);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 使用yyyy-MM-dd格式的日期字符串实例化一个Calendar对象
	 * 
	 * @param dateStr
	 *            日期字符串 格式：yyyy-MM-dd
	 * @return
	 * @author ZhangYunHe
	 * @date 2013-1-8
	 */
	public static Calendar initCalendarByDateString(String dateStr) {
		if (null != dateStr && !"".equals(dateStr.trim())
				&& dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
			String[] date = dateStr.split("-");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
			calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);// 月份需要减1，Calendar从0开始算1月
			calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
			return calendar;
		} else {
			throw new IllegalArgumentException(
					"传入的日期格式字符串非法！接受的日期格式字符串为：yyyy-MM-dd");
		}
	}

	/**
	 * 获取今年总共有多少天
	 * 
	 * @return
	 * @author ZhangYunHe
	 * @date 2013-1-8
	 */
	public static int getDaysOfThisYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 在当前时间的基础上添加特定时间
	 * <p>
	 * 根据type类型来区分添加的时间单位，type等同于Calendar.XXXX 如：小时Calendar.HOUR_OF_DAY。<br/>
	 * 暂只支持，DAY_OF_MONTH,HOUR_OF_DAY，MINUTE
	 * </p>
	 * 
	 * @param type
	 *            类型
	 * @param value
	 *            要新增的值（如果是负数则是减少的值）
	 * @return 处理后的时间
	 */
	public static Date addByCurrent(int type, int value) {
		return addByDate(new Date(), type, value);
	}

	/**
	 * 在特定时间的基础上添加特定时间
	 * <p>
	 * 根据type类型来区分添加的时间单位，type等同于Calendar.XXXX 如：小时Calendar.HOUR_OF_DAY。<br/>
	 * 暂只支持，DAY_OF_MONTH,HOUR_OF_DAY，MINUTE
	 * </p>
	 * 
	 * @param date
	 *            特定时间
	 * @param type
	 *            类型
	 * @param value
	 *            要新增的值（如果是负数则是减少的值）
	 * @return 处理后的时间
	 */
	public static Date addByDate(Date date, int type, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		switch (type) {
		case Calendar.DAY_OF_MONTH:// 月份中的天数
			now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH)
					+ value);
			break;
		case Calendar.HOUR_OF_DAY:// 小时
			now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + value);
			break;
		case Calendar.MINUTE:// 分钟
			now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + value);
			break;
		}
		return now.getTime();
	}

	/**
	 * 格式化观看时间
	 * @param second 秒
	 * @return 0时 00分 00秒
	 */
	public static String formatMediaTime(Integer second) {
		if(second==null){second=0;}
		Integer hour = second / 60 / 60;
		Integer mediaTime = second / 60 - hour * 60;
		Integer mediaSecond = second - hour * 60 * 60 - mediaTime * 60;
		StringBuilder sb = new StringBuilder();
		sb.append(hour < 10 ? hour : hour).append("时 ");
		sb.append(mediaTime < 10 ? "0" + mediaTime : mediaTime).append("分 ");
		sb.append(mediaSecond < 10 ? "0" + mediaSecond : mediaSecond).append("秒");
		return sb.toString();
	}
	/**
	 * 格式化观看时间
	 * @param second 秒
	 * @return 0时 00分 00秒
	 */
	public static String formatMediaTime(BigDecimal second) {
		if(second==null){second=new BigDecimal(0);}
		return formatMediaTime(second.intValue());
	}
	/**
	 * 检查两个日期的某个单位的差值，当前支持小时、天数
	 * @param before 被减数 
	 * @param after 减数
	 * @param type 类型，参见Calendar
	 * @return 值
	 */
	public static int distance(Date before,Date after,int type){
		if(before == null || after == null){
			throw new IllegalArgumentException("参数值错误");
		}
		Calendar start = Calendar.getInstance();
		start.setTime(before);
		Calendar end = Calendar.getInstance();
		end.setTime(after);
		//设置分秒
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MINUTE, 0);
		long unit = -1;
		switch(type){
			case Calendar.DAY_OF_MONTH:
				end.set(Calendar.HOUR_OF_DAY, 0);
				start.set(Calendar.HOUR_OF_DAY, 0);
				unit = UNIT_DAY_TIME;
				break;
			case Calendar.HOUR_OF_DAY:
				unit = UNIT_HOUR_TIME;
				break;
		}
		return BigDecimal
		.valueOf(start.getTimeInMillis() - end.getTimeInMillis())
		.divide(BigDecimal.valueOf(unit), RoundingMode.HALF_UP)
		.intValue();
	}
	
	/**
	 * @Title formatMinuteSecond 
	 * @Description 根据秒数返回M分钟S秒
	 * @param second 秒
	 * @return
	 * String
	 * @author liuqin
	 * @date 2013-3-26 下午5:35:41
	 */
	public static String formatMinuteSecond(Object second){
		if(StringUtils.isEmpty(second)) return "—";
		try{
			int m = Integer.parseInt(second.toString()) / 60;
			int s = Integer.parseInt(second.toString()) % 60;
			return m + "分" + s + "秒";
		}catch (NumberFormatException e) {
			return "—";
		}
	}

	/**
	 * 获取当前日期前一月的
	 * @param date
	 * @return
	 */
	public static Date getAgeinMonth(Date date) {
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期
		calendar.add(Calendar.MONTH, -1);//月份减一
		return calendar.getTime();
	}
}
