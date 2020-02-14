package com.a_268.base.redis;

import com.a_268.base.serializer.SerializationUtils;
import com.a_268.base.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public abstract class RedisManager {
	private Logger logger = LoggerFactory.getLogger(RedisManager.class);
	/**读取配置文件*/
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
	
	private static  String REDIS_REGION = "dx_redis_px_";
	
	private String redisSwitch = "OFF";

	private static JedisPool jedisPool = null;
	
	/**
	 * 初始化方法
	 */
	public void init(){
		logger.info("-------------------------init redis----------------------");
		try{
			redisSwitch = propertyUtil.getProperty("redis.switch");
		}catch (Exception e){
			logger.info("-----------------redis redis.switch is null,redis init fail,redis not open-------");
			return;
		}
		logger.info("-------------------------redisSwitch:"+redisSwitch);
		//redis 开关配置不是NO则不进行初始化
		if(!redisSwitch.trim().equals("NO")){
			logger.info("-------------------------redis not init----------------------");
			return;
		}
		String host = propertyUtil.getProperty("redis.host");

		int port =  Integer.parseInt(propertyUtil.getProperty("redis.port"));

		//timeout for jedis try to connect to redis server, not expire time! In milliseconds
		int timeout = Integer.parseInt(propertyUtil.getProperty("redis.timeout"));

		String password = propertyUtil.getProperty("redis.password");
		if(jedisPool == null){
			if(password != null && !"".equals(password)){
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
				logger.info("-------------------------init redis success----------------------");
			}else if(timeout != 0){
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port,timeout);
				logger.info("-------------------------init redis success----------------------");
			}else{
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
				logger.info("-------------------------init redis success----------------------");
			}
			
		}
	}
	
	/**
	 * 添加缓存
	 * @param key 缓存中的Key
	 * @param value 要缓存的数据对象
	 * @param seconds 过时间（单位/秒）
	 */
	public void set(String key,Object value,int seconds){
		this._set(null, key, value, seconds);
	}
	/**
	 * 通过Key获取缓存中的Value
	 * @param key 缓存中存在的Key
	 * @return 返回缓存中的数据对象
	 */
	public Object get(String key){
		return this._get(null, key);
	}

	/**
	 * 把数据缓存到指定的区域中
	 * @param key 
	 * @param value
	 */
	public void regionSet(String key,Object value){
		this._set(REDIS_REGION, key, value, 0);
	}
	
	/**
	 * 获取指定区域中的Key的Value
	 * @param key
	 * @return 返回指定区域中的Key的Value
	 */
	public Object regiontGet(String key){
		return this._get(REDIS_REGION, key);
	}
	
	/**
	 * 通过Key删除缓存记录
	 * @param key 缓存存在的Key
	 */
	public void remove(String key){
		this._remove(key);
	}
	
	/**
	 * 删除指定区域中的Key的缓存
	 * @param key 缓存存在的Key
	 */
	public void regiontRemove(Object key){
		this._remove(REDIS_REGION,key);
	}
	//===============================================================
	/**
	 * 添加缓存
	 * @param region 指定区域关键字
	 * @param key 缓存标识Key
	 * @param value 要缓存的数据对象
	 * @param seconds (当region为null时，seconds要大于0，region不为空时seconds不起作用)
	 */
	private void _set(String region,String key,Object value,int seconds) throws RuntimeException{
		logger.info("-------------------------set redis data : region="+region+"  key="+key+"  value="+value+"  seconds:"+seconds);
		try{
			Jedis cache = jedisPool.getResource(); 
			Throwable localThrowable2 = null;
			try {
				if(region==null || region.trim().length()<=0){
					cache.setex(key.getBytes(), seconds, SerializationUtils.serialize(value));
				}else{
					cache.hset(region.getBytes(), key.getBytes(), SerializationUtils.serialize(value));
				}
			}catch(Throwable localThrowable1){
				localThrowable2 = localThrowable1;
				throw localThrowable1;
			}finally {
				finallyCache(cache,localThrowable2);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通过的获取缓存方法
	 * @param region 指定区域关键字
	 * @param key 缓存中存在的Key
	 * @return 返回缓存中的数据对象
	 */
	private Object _get(String region,String key) throws RuntimeException{
		logger.info("-------------------------get redis data : region="+region+"  key="+key);
		Object obj = null;
		if(key==null){
			return null;
		}
		try{
			Jedis cache = jedisPool.getResource();
			Throwable localThrowable2 = null;
			try{
				byte[] b;
				if(region==null || region.trim().length()<=0){
					b = cache.get(key.getBytes());
					obj = SerializationUtils.deserialize(b);
				}else{
					b = cache.hget(region.getBytes(), key.getBytes());
					obj = SerializationUtils.deserialize(b);
				}
			}catch(Throwable localThrowable1){
				localThrowable2 = localThrowable1;
				throw localThrowable1; 
			}finally {
				finallyCache(cache,localThrowable2);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
	
	/**
	 * 删除缓存
	 * @param region 指定区域的关键字
	 * @param key 缓存中存在的Key
	 * @throws RuntimeException
	 */
	private void _remove(String region,Object key) throws RuntimeException{
		logger.info("-------------------------remove redis data : region="+region+"  key="+key);
		if(key==null){
			return;
		}
		if(region==null){
			_remove(key);
		}else{
			if(key instanceof List){
				@SuppressWarnings("unchecked")
				List<Object> keys = (List<Object>) key;
				try {
					Jedis cache = jedisPool.getResource();
					Throwable localThrowable3 = null;
					try {
						int size = keys.size();
						byte[][] okeys = new byte[size][];
						for (int i = 0; i < size; i++) {
							String _key = keys.get(i).toString();
							okeys[i] = _key.getBytes();
						}
						cache.del(okeys);
					} catch (Throwable localThrowable1) {
						localThrowable3 = localThrowable1;
						throw localThrowable1;
					} finally {
						finallyCache(cache,localThrowable3);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}else{
				try {
					Jedis cache = jedisPool.getResource();
					Throwable e = null;
					try {
						cache.hdel(region.getBytes(), new byte[][] { key.toString().getBytes()});
					} catch (Throwable localThrowable4) {
						e = localThrowable4;
						throw localThrowable4;
					} finally {
						finallyCache(cache,e);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 通过Key删除不是保存在缓存中的数据
	 * @param key 缓存中Key
	 * @throws RuntimeException
	 */
	private void _remove(Object key) throws RuntimeException {
		logger.info("-------------------------remove redis data : region=null  key="+key);
		if (key == null)
			return;
		String _key = key.toString();
		try {
			Jedis cache = jedisPool.getResource();
			Throwable localThrowable2 = null;
			try {
				cache.del(_key.getBytes());
			} catch (Throwable localThrowable1) {
				localThrowable2 = localThrowable1;
				throw localThrowable1;
			} finally {
				finallyCache(cache,localThrowable2);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private  void finallyCache(Jedis cache,Throwable localThrowable3){
		if (cache != null){
			if (localThrowable3 != null){
				try {
					cache.close();
				} catch (Throwable x2) {
					localThrowable3.addSuppressed(x2);
				}
			}else{
				cache.close();
			}
		}
	}

}
