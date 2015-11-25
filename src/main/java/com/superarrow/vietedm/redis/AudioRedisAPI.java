package com.superarrow.vietedm.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.superarrow.vietedm.model.Audio;
import com.superarrow.vietedm.model.Category;
import com.superarrow.vietedm.model.Topic;
import com.superarrow.vietedm.util.Utils;

import redis.clients.jedis.Jedis;

public class AudioRedisAPI {

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Category
	// ZSet<CategoryId, TimeStamp>
	// CategoryId: Hash<Feature, Value>
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public static List<Integer> getCategoryIds() {
		List<Integer> result = new ArrayList<Integer>();
		
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Set<String> categoryIds = jedis.zrevrange(RedisKey.categorySet(), 0, -1);
		for(String catId : categoryIds) {
			try {
				Integer categoryId = Integer.parseInt(catId);
				result.add(categoryId);
			} catch (Exception e) {}
		}
		
		jedis.close();
		
		return result;
	}
	
	public static List<Category> getCategoryList() {
		List<Category> result = new ArrayList<Category>();
		
		List<Integer> categoryIds = getCategoryIds();
		for(Integer id : categoryIds) {
			Category category = getCategoryById(id);
			result.add(category);
		}
		
		return result;
	}
	
	
	public static void setCategory(Category category) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// Add categoryId
		jedis.zadd(RedisKey.categorySet(), (double)Utils.timeStamp(), String.valueOf(category.categoryId));
		
		// Add feature
		jedis.hset(RedisKey.categoryHash(category.categoryId), "name", category.name);
		jedis.hset(RedisKey.categoryHash(category.categoryId), "coverImage", category.coverImage);
		
		jedis.close();
	}
	
	public static Category getCategoryById(int categoryId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Category result = new Category();
		
		result.categoryId = categoryId;
		result.name = jedis.hget(RedisKey.categoryHash(categoryId), "name");
		result.coverImage = jedis.hget(RedisKey.categoryHash(categoryId), "coverImage");
		
		jedis.close();
		
		return result;
	}
	
	public static void removeCategory(int categoryId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// remove categoryId
		jedis.zrem(RedisKey.categorySet(), String.valueOf(categoryId));
		
		// remove hash-key
		jedis.del(RedisKey.categoryHash(categoryId));
		
		jedis.close();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Topic
	// ZSet:CategoryId<TopicId, TimeStamp>
	// CategoryId: Hash<Feature, Value>
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<Integer> getTopicIds(int categoryId, long startIndex, long end) {
		List<Integer> result = new ArrayList<Integer>();
		
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Set<String> topicIds = jedis.zrevrange(RedisKey.categoryTopicZSet(categoryId), startIndex, end);
		for(String id : topicIds) {
			try {
				Integer topicId = Integer.parseInt(id);
				result.add(topicId);
			} catch (Exception e) {}
		}
		
		jedis.close();
		
		return result;
	}
	
	public static List<Topic> getTopicList(int categoryId, long startIndex, long end) {
		List<Topic> result = new ArrayList<Topic>();
		
		List<Integer> topicIds = getTopicIds(categoryId, startIndex, end);
		for(Integer id : topicIds) {
			Topic topic = getTopicById(id);
			result.add(topic);
		}
		
		return result;
	}
	
	
	public static void setTopic(int categoryId, Topic topic) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// Add topicId
		jedis.zadd(RedisKey.categoryTopicZSet(categoryId), (double)Utils.timeStamp(), String.valueOf(topic.topicId));
		
		// Add feature
		jedis.hset(RedisKey.topicHash(topic.topicId), "name", topic.name);
		jedis.hset(RedisKey.topicHash(topic.topicId), "coverImage", topic.coverImage);
		
		jedis.close();
	}
	
	public static Topic getTopicById(int topicId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Topic result = new Topic();
		
		result.topicId = topicId;
		result.name = jedis.hget(RedisKey.topicHash(topicId), "name");
		result.coverImage = jedis.hget(RedisKey.topicHash(topicId), "coverImage");
		
		jedis.close();
		
		return result;
	}
	
	public static void removeTopic(int categoryId, int topicId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// remove topicId
		jedis.zrem(RedisKey.categoryTopicZSet(categoryId), String.valueOf(topicId));
		
		// remove hash-key
		jedis.del(RedisKey.topicHash(topicId));
		
		jedis.close();
	}
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Audio-Base-Info
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<Integer> getAudioIds(int topicId, long startIndex, long end) {
		List<Integer> result = new ArrayList<Integer>();
		
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Set<String> audioIds = jedis.zrevrange(RedisKey.topicAudioZSet(topicId), startIndex, end);
		for(String id : audioIds) {
			try {
				Integer audioId = Integer.parseInt(id);
				result.add(audioId);
			} catch (Exception e) {}
		}
		
		jedis.close();
		
		return result;
	}
	
	public static List<Audio> getAudioList(int topicId, long startIndex, long end) {
		List<Audio> result = new ArrayList<Audio>();
		
		List<Integer> topicIds = getTopicIds(topicId, startIndex, end);
		for(Integer id : topicIds) {
			Audio topic = getAudioById(id);
			result.add(topic);
		}
		
		return result;
	}
	
	
	public static void setAudio(int topicId, Audio audio) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// Add topicId
		jedis.zadd(RedisKey.topicAudioZSet(topicId), (double)Utils.timeStamp(), String.valueOf(audio.audioId));
		
		// Add feature
		jedis.hset(RedisKey.audioHash(audio.audioId), "name", audio.name);
		jedis.hset(RedisKey.audioHash(audio.audioId), "description", audio.description);
		jedis.hset(RedisKey.audioHash(audio.audioId), "coverImage", audio.coverImage);
		jedis.hset(RedisKey.audioHash(audio.audioId), "subscription", audio.subscription);
		jedis.hset(RedisKey.audioHash(audio.audioId), "timeStamp", String.valueOf(audio.timeStamp));
		jedis.hset(RedisKey.audioHash(audio.audioId), "dataURL", audio.dataURL);
		
		jedis.close();
	}
	
	public static Audio getAudioById(int audioId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		Audio audio = new Audio();
		
		audio.name = jedis.hget(RedisKey.audioHash(audioId), "name");
		audio.description = jedis.hget(RedisKey.audioHash(audioId), "description");
		audio.coverImage = jedis.hget(RedisKey.audioHash(audioId), "coverImage");
		audio.subscription = jedis.hget(RedisKey.audioHash(audioId), "subscription");
		audio.timeStamp = Long.parseLong(jedis.hget(RedisKey.audioHash(audioId), "timeStamp"));
		audio.dataURL = jedis.hget(RedisKey.audioHash(audioId), "dataURL");
		
		jedis.close(); 
		
		return audio;
	}
	
	public static void removeAudio(int topicId, int audioId) {
		Jedis jedis = AudioRedisPool.getJedisPool().getResource();
		
		// Add topicId
		jedis.zrem(RedisKey.topicAudioZSet(topicId), String.valueOf(audioId));
		
		// Add feature
		jedis.del(RedisKey.audioHash(audioId));
		
		jedis.close();
	}
	
	
	

}
