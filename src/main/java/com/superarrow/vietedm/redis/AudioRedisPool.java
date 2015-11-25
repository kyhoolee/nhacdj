package com.superarrow.vietedm.redis;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.superarrow.vietedm.util.LoggerUtil;

import redis.clients.jedis.JedisPool;

public class AudioRedisPool {
	private static String configFile = "redis_audio_info.properties";
	private static JedisPool _instance = null;
	private static final String defaultHost = "42.112.31.182";
	private static final int defaultPort = 9701;
	


	public static JedisPool getJedisPool() {
		try {
			if (_instance == null) {
				_instance = BaseRedisPool.getInstance(configFile, defaultHost, defaultPort);
			}
			return _instance;
			
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
			LoggerUtil.getRootLogger().info(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

}
