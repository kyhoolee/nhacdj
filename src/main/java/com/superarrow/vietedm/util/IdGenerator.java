package com.superarrow.vietedm.util;



public class IdGenerator {
	
	private static final String ID_SPACE = "edm"; 
	private static final String CATEGORY_ID = "categoryId";
	private static final String TOPIC_ID = "topicId";
	private static final String AUDIO_ID = "audioId";
	
	public static long genCategoryId() {
		return RedisStore.incrAndGet(ID_SPACE, CATEGORY_ID);
	}
	
	public static long genTopicId() {
		return RedisStore.incrAndGet(ID_SPACE, TOPIC_ID);
	}
	
	public static long genAudioId() {
		return RedisStore.incrAndGet(ID_SPACE, AUDIO_ID);
	}
	


	

}
