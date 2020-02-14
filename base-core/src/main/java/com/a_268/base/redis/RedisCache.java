package com.a_268.base.redis;

/**
 * Redis对外接口
 * @author s.li
 */
public class RedisCache extends RedisManager{
	
	private RedisCache(){
		init();
	}
	
	public static RedisCache redisCache = new RedisCache();
	
	public static RedisCache getInstance(){
		return redisCache;
	}
	
}
