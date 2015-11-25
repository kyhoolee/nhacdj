package com.superarrow.vietedm.redis;

public class RedisKeyPattern {
	// Category Topic Audio
	
	public static final String categorySet = "category";
	public static final String categoryHash = "category:%s";
	
	public static final String categoryTopicZSet = "cat:%s:topic";
	public static final String topicHash = "topic:%s";
	
	public static final String topicAudioZSet = "topic:%s:audio";
	public static final String audioHash = "audio:%s";
	
	
}
