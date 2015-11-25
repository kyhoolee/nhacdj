package com.superarrow.vietedm.redis;



public class RedisKey {

	// Audio Info
	public static String categoryHash(int categoryId) {
		return String.format(RedisKeyPattern.categoryHash, categoryId);
	}
	
	public static String categorySet() {
		return String.format(RedisKeyPattern.categorySet);
	}
	
	public static String categoryTopicZSet(int categoryId) {
		return String.format(RedisKeyPattern.categoryTopicZSet, categoryId);
	}
	
	public static String topicHash(int topicId) {
		return String.format(RedisKeyPattern.topicHash, topicId);
	}
	
	public static String topicAudioZSet(int topicId) {
		return String.format(RedisKeyPattern.topicAudioZSet, topicId);
	}
	
	public static String audioHash(int audioId) {
		return String.format(RedisKeyPattern.audioHash, audioId);
	}
}

