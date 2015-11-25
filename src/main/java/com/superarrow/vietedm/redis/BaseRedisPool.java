package com.superarrow.vietedm.redis;

import java.util.Properties;

import com.superarrow.vietedm.util.Utils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class BaseRedisPool {

	public static JedisPool getInstance(String configFile, String defaultHost, int defaultPort) {

		Properties properties = Utils.loadPropertiesResource(configFile);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		int max_pool = Integer.valueOf(properties.getProperty("max_pool", "1000"));
		String host = properties.getProperty("host", defaultHost);
		int port = Integer.valueOf(properties.getProperty("port", defaultPort + ""));
		int time_out = Integer.valueOf(properties.getProperty("time_out", "1000"));
		jedisPoolConfig.setMaxTotal(max_pool);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		jedisPoolConfig.setTestWhileIdle(true);
		JedisPool instance = new JedisPool(jedisPoolConfig, host, port, time_out);

		return instance;

	}

}
