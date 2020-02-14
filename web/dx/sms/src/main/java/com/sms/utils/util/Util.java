package com.sms.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;

	private static int sequenceSeed = 10000;
	private static Object lock = new Object();


	public static String getSequence()
	{
		synchronized (lock)
		{
			if (sequenceSeed >= 99999)
				sequenceSeed = 10000;

			return getDateTime14String() + (sequenceSeed++);
		}
	}

	private static String getDateTime14String()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

}
