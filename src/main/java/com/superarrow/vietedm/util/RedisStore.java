package com.superarrow.vietedm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisStore {

	public static Logger logger = LoggerUtil.getDailyLogger("RedisStore_log");

	private static HashMap<String, String> keyValue = new HashMap<String, String>();

	
	private static String jedisGet(String key) {
		if (keyValue.containsKey(key))
			return keyValue.get(key);
		else {
			String result = null;
			Jedis jedis = RedisPool.getJedis();
			try {
				result = jedis.get(key);
				if (result != null) {
					keyValue.put(key, result);
				}

				return result;
			} catch (JedisConnectionException ex) {
				jedis.close();

			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();

			}

			return result;
		}
	}

	private static void jedisSet(String key, String value) {
		keyValue.put(key, value);
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.set(key, value);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

	}
	
	private static long jedisDel(String key) {
		keyValue.remove(key);
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.del(key);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
		return 0;
	}

	public static long getLongValue(String key) {
		long result = 0;
		Jedis jedis = RedisPool.getJedis();

		try {
			String data = jedisGet(key);
			if (data != null)
				result = Long.parseLong(data);

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static void setLongValue(String key, long value) {

		Jedis jedis = RedisPool.getJedis();

		try {
			jedisSet(key, String.valueOf(value));

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	public static long delLongValue(String key) {
		long result = 0;
		Jedis jedis = RedisPool.getJedis();

		try {
			result = (long) (jedisDel(key));

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static int getIntValue(String key) {
		int result = 0;
		Jedis jedis = RedisPool.getJedis();

		try {
			String data = jedis.get(key);
			if (data != null)
				result = Integer.parseInt(data);

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static void setIntValue(String key, int value) {

		Jedis jedis = RedisPool.getJedis();

		try {
			jedisSet(key, String.valueOf(value));

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	public static int delIntValue(String key) {
		int result = 0;
		Jedis jedis = RedisPool.getJedis();

		try {
			result = (int) (long) (jedisDel(key));

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static String getStringValue(String key) {
		String result = "";
		Jedis jedis = RedisPool.getJedis();

		try {
			result = jedisGet(key);

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static void setStringValue(String key, String value) {
		Jedis jedis = RedisPool.getJedis();

		try {
			jedisSet(key, value);
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	public static void delStringValue(String key) {
		Jedis jedis = RedisPool.getJedis();

		try {
			jedisDel(key);
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// stringValue-set-stored
	// ////////////////////////////////////////////////////////////////////////////////////////

	private static HashMap<String, Set<String>> keySet = new HashMap<String, Set<String>>();
	
	private static Set<String> jedisSmembers(String key) {
		if (keySet.containsKey(key))
			return keySet.get(key);
		else {
			Set<String> result = null;
			Jedis jedis = RedisPool.getJedis();
			try {
				result = jedis.smembers(key);
				if (result != null) {
					keySet.put(key, result);
				}

				return result;
			} catch (JedisConnectionException ex) {
				jedis.close();

			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();

			}

			return result;
		}
	}
	
	private static void jedisSadd(String key, String value) {
		if(keySet.containsKey(key)) {
			Set<String> setValue = keySet.get(key);
			setValue.add(value);
			keySet.put(key, setValue);
		} else {
			Set<String> setValue = new HashSet<String>();
			setValue.add(value);
			keySet.put(key, setValue);
		}
		
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(key, value);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

	}
	
	private static void jedisSrem(String key, String value) {
		if(keySet.containsKey(key)) {
			Set<String> setValue = keySet.get(key);
			setValue.remove(value);
			keySet.put(key, setValue);
		} 
		
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.srem(key, value);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		
	}
	
	
	public static List<String> getMemberList(String key) {
		List<String> logicalList = new ArrayList<String>();

		Jedis jedis = RedisPool.getJedis();

		try {
			Set<String> result = jedisSmembers(key);
			logicalList.addAll(result);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return logicalList;
	}

	public static void insertMember(String key, String value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisSadd(key, value);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	public static void removeMember(String key, String value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisSrem(key, value);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// stringValue-hash-stored
	// ////////////////////////////////////////////////////////////////////////////////////////

	private static HashMap<String, Map<String, String>> mapString = new HashMap<String, Map<String, String>>();
	
	private static boolean jedisHexists(String hashKey, String fieldKey) {
		if(mapString.containsKey(hashKey)) {
			Map<String, String> mapValue = mapString.get(hashKey);
			if(mapValue.containsKey(fieldKey))
				return true;
		} else {
			Jedis jedis = RedisPool.getJedis();
			try {
				return jedis.hexists(hashKey, fieldKey);
			} catch (JedisConnectionException ex) {
				jedis.close();

			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
			}
		}
		
		
		return false;
	}
	
	private static Set<String> jedisHkeys(String hashKey) {
		
		Set<String> result = new HashSet<String>();
		
		if(mapString.containsKey(hashKey)) {
			result = mapString.get(hashKey).keySet();
			if(result != null)
				return result;
			else 
				return new HashSet<String>();
						
		} else {
		
			Jedis jedis = RedisPool.getJedis();
			try {
				Set<String> keys = jedis.hkeys(hashKey);
				
				Map<String, String> keyValue = jedis.hgetAll(hashKey);
				mapString.put(hashKey, keyValue);
				
				result.addAll(keys);
				return result;
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		}
		return result;
	}
	
	private static void jedisHset(String hashKey, String fieldKey, String value) {
		if(mapString.containsKey(hashKey)) {
			Map<String, String> mapValue = mapString.get(hashKey);
			mapValue.put(fieldKey, value);
			mapString.put(hashKey, mapValue);
						
		} else {
			Map<String, String> mapValue = new HashMap<String, String>();
			mapValue.put(fieldKey, value);
			mapString.put(hashKey, mapValue);
			
		}
			Jedis jedis = RedisPool.getJedis();
			try {
				jedis.hset(hashKey, fieldKey, value);
				
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		
	}
	
	private static String jedisHget(String hashKey, String fieldKey) {
		String value = null;
		
		if(mapString.containsKey(hashKey)) {
			value = mapString.get(hashKey).get(fieldKey);
			return value;
						
		} else {
		
			Jedis jedis = RedisPool.getJedis();
			try {
				value = jedis.hget(hashKey, fieldKey);
				if(value != null) {
					Map<String, String> keyValue = jedis.hgetAll(hashKey);
					mapString.put(hashKey, keyValue);
				}
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		}
		
		return value;
	}
	
	private static void jedisHdel(String hashKey, String fieldKey) {
		
		
		if(mapString.containsKey(hashKey)) {
			Map<String, String> mapValue =  mapString.get(hashKey);
			if(mapValue != null) {
				mapValue.remove(fieldKey);
				mapString.put(hashKey, mapValue);
			}
						
		} else {
		
			Jedis jedis = RedisPool.getJedis();
			try {
				jedis.hdel(hashKey, fieldKey);
				
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		}
		
		
	}
	
	
	public static boolean checkField(String hashKey, String fieldKey) {
		boolean stored = false;
		Jedis jedis = RedisPool.getJedis();
		try {
			stored = jedisHexists(hashKey, fieldKey);
			return stored;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();
		}

		return stored;
	}

	public static List<String> listField(String hashKey) {
		List<String> result = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> keys = jedisHkeys(hashKey);
			result.addAll(keys);
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static String setValue(String hashKey, String fieldKey, String value) {
		String result = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisHset(hashKey, fieldKey, value);
			result = value;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static String getStringValue(String hashKey, String fieldKey) {
		String stored = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			stored = jedisHget(hashKey, fieldKey);
			if (stored == null) {
				return "";
			}
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return stored;
	}

	public static void removeStringValue(String hashKey, String fieldKey) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisHdel(hashKey, fieldKey);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// longValue-hash-stored
	// ////////////////////////////////////////////////////////////////////////////////////////
	
	private static HashMap<String, Map<String, Long>> mapLong = new HashMap<String, Map<String, Long>>();
	
	
	private static long jedisLHincrBy(String hashKey, String fieldKey, long value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			value = jedis.hincrBy(hashKey, fieldKey, value);
			
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
		
		if(mapLong.containsKey(hashKey)) {
			Map<String, Long> mapValue = mapLong.get(hashKey);
			mapValue.put(fieldKey, value);
			mapLong.put(hashKey, mapValue);
		} else {
			Map<String, Long> mapValue = new HashMap<String, Long>();
			mapValue.put(fieldKey, value);
			mapLong.put(hashKey, mapValue);
		}
		return value;
	}
	
	private static long jedisLHset(String hashKey, String fieldKey, long value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(hashKey, fieldKey, String.valueOf(value));
			
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
		
		if(mapLong.containsKey(hashKey)) {
			Map<String, Long> mapValue = mapLong.get(hashKey);
			mapValue.put(fieldKey, value);
			mapLong.put(hashKey, mapValue);
						
		} else {
			Map<String, Long> mapValue = new HashMap<String, Long>();
			mapValue.put(fieldKey, value);
			mapLong.put(hashKey, mapValue);
			
		}
			
			
		return value;
	}
	
	private static long jedisLHget(String hashKey, String fieldKey) {
		long value = 0;
		
		if(mapLong.containsKey(hashKey) && mapLong.get(hashKey).containsKey(fieldKey)) {
			value = mapLong.get(hashKey).get(fieldKey);
			return value;
						
		} else {
		
			Jedis jedis = RedisPool.getJedis();
			try {
				String data = jedis.hget(hashKey, fieldKey);
				
				if(data != null) {
					value = Long.parseLong(data);
					
					Map<String, Long> longValue = mapLong.get(hashKey);
					if(!mapLong.containsKey(hashKey))
						longValue =	new HashMap<String, Long>();
					longValue.put(fieldKey, value);
					mapLong.put(hashKey, longValue);
					
				}
				
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		}
		
		return value;
	}

	public static long addLongValue(String hashKey, String fieldKey, long value) {
		long result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			Long val = jedisLHincrBy(hashKey, fieldKey, value);
			result = val;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static long setLongValue(String hashKey, String fieldKey, long value) {
		long result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisLHset(hashKey, fieldKey, value);
			result = value;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static long getLongValue(String hashKey, String fieldKey) {
		long stored = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			stored = jedisLHget(hashKey, fieldKey);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return stored;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// intValue-hash-stored
	// ////////////////////////////////////////////////////////////////////////////////////////
	
	private static HashMap<String, Map<String, Integer>> mapInt = new HashMap<String, Map<String, Integer>>();
	
	
	private static int jedisIHincrBy(String hashKey, String fieldKey, int value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			value = (int)(long)jedis.hincrBy(hashKey, fieldKey, value);
			
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
		
		if(mapInt.containsKey(hashKey)) {
			Map<String, Integer> mapValue = mapInt.get(hashKey);
			mapValue.put(fieldKey, value);
			mapInt.put(hashKey, mapValue);
		} else {
			Map<String, Integer> mapValue = new HashMap<String, Integer>();
			mapValue.put(fieldKey, value);
			mapInt.put(hashKey, mapValue);
		}
		return value;
	}
	
	private static int jedisIHset(String hashKey, String fieldKey, int value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(hashKey, fieldKey, String.valueOf(value));
			
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
		
		if(mapInt.containsKey(hashKey)) {
			Map<String, Integer> mapValue = mapInt.get(hashKey);
			mapValue.put(fieldKey, value);
			mapInt.put(hashKey, mapValue);
						
		} else {
			Map<String, Integer> mapValue = new HashMap<String, Integer>();
			mapValue.put(fieldKey, value);
			mapInt.put(hashKey, mapValue);
			
		}
			
			
		return value;
	}
	
	private static int jedisIHget(String hashKey, String fieldKey) {
		int value = 0;
		
		if(mapInt.containsKey(hashKey) && mapInt.get(hashKey).containsKey(fieldKey)) {
			value = mapInt.get(hashKey).get(fieldKey);
			return value;
						
		} else {
		
			Jedis jedis = RedisPool.getJedis();
			try {
				String data = jedis.hget(hashKey, fieldKey);
				
				if(data != null) {
					value = Integer.parseInt(data);
					
					Map<String, Integer> longValue = mapInt.get(hashKey);
					if(!mapInt.containsKey(hashKey))
						longValue =	new HashMap<String, Integer>();
					longValue.put(fieldKey, value);
					mapInt.put(hashKey, longValue);
					
				}
				
			} catch (JedisConnectionException ex) {
				jedis.close();
	
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				jedis.close();
	
			}
		}
		
		return value;
	}

	public static int addValue(String hashKey, String fieldKey, int value) {
		int result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			result = jedisIHincrBy(hashKey, fieldKey, value);
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static int setValue(String hashKey, String fieldKey, int value) {
		int result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			jedisIHset(hashKey, fieldKey, value);
			result = value;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static int getIntValue(String hashKey, String fieldKey) {
		int stored = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			stored = jedisIHget(hashKey, fieldKey);
			

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return stored;
	}

	public static void removeValue(String hashKey, String fieldKey) {
		if(mapString.containsKey(hashKey)) {
			mapString.get(hashKey).remove(fieldKey);
		} else if(mapInt.containsKey(hashKey)) {
			mapInt.get(hashKey).remove(fieldKey);
		} else if(mapLong.containsKey(hashKey)) {
			mapLong.get(hashKey).remove(fieldKey);
		}
		
		
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(hashKey, fieldKey);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// intValue-id-generated
	// ////////////////////////////////////////////////////////////////////////////////////////

	public static synchronized long incrAndGet(String hashKey, String fieldKey) {
		long result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			long val = jedis.hincrBy(hashKey, fieldKey, 1);
			result = val;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// intValue-sorted-set
	// ////////////////////////////////////////////////////////////////////////////////////////

	public static double zincrValue(String sortedKey, String field, double value) {
		double result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			result = jedis.zincrby(sortedKey, value, field);

			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static double zsetValue(String hashKey, String fieldKey, double value) {
		double result = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(hashKey, value, fieldKey);
			result = value;
			return result;
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return result;
	}

	public static String zheadField(String zKey) {
		String field = null;
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> fields = jedis.zrange(zKey, 0, 0);
			if (fields != null) {
				if (fields.size() == 1) {
					for (String e : fields) {
						field = e;
						break;
					}
				}
			}

			jedis.zrem(zKey, field);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return field;
	}

	public static Set<String> zheadFields(String hashKey, long number) {
		Set<String> keys = new TreeSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			keys = jedis.zrange(hashKey, 0, number - 1);
			for (String key : keys) {
				jedis.zrem(hashKey, key);
			}
		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return keys;
	}

	public static Set<String> zgetFields(String hashKey, long start, long end) {
		Set<String> keys = new TreeSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			keys = jedis.zrange(hashKey, start, end);

		} catch (JedisConnectionException ex) {
			jedis.close();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			jedis.close();

		}

		return keys;
	}
}
