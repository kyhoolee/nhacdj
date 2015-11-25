package com.superarrow.vietedm.redis;

public class RedisKeyField {
	/**
	 * string separator in redis key
	 */
	public static final String separatorKey = "::";

	/**
	 * string separator in field ID
	 */
	public static final String separatorID = "_";
	/**
	 * videoId-trunk
	 */
	public static final String separatorCacheID = "-";

	public static final double LONG_MAX_VALUE = Long.MAX_VALUE;
	public static final byte MAX_USER_VIEW_IN_VIDEO = 5;
	public static final String title = "title";
	public static final String id = "id";
	public static final String userId = "user_id";
	public static final String audioId = "audio_id";
	public static final String videoId = "video_id";
	public static final String parentId = "parent_id";
	public static final String timestamp = "timestamp";
	public static final String name = "name";
	public static final String trunks = "trunks";
	public static final String length = "length";
	public static final String likeCount = "like_count";
	public static final String videoCount = "video_count";
	public static final String storeId = "store_id";
	public static final String messageTags = "message_tags";
	public static final String osId = "os_id";
	public static final String registerId = "register_id";
	public static final String data = "data";
	public static final String status = "status";
	public static final String appStatus = "app_status";
	public static final String totalSent = "total_sent";
	public static final String totalReceived = "total_received";
	public static final String totalError = "total_error";

	public static final String videosOfAudio = "db::audio::%s::videos::%s";
	public static final String usersUsingAudio = "db::audio::%s::users";
	public static final String titleAudio = "db::audio::%s::title::%s";
	public static final String uploadedVideoOfUser = "db::users::%s::videos::upload";
	public static final String likedVideosByUser = "db::users::%s::videos::like";
	public static final String followers = "db::users::%s::followers";
	public static final String followingUsers = "db::users::%s::following";
	public static final String likedAudiosByUser = "db::users::%s::audios::like";
	public static final String commentsOfVideo = "db::videos::%s::comments::time";
	public static final String commentInfo = "db::comments::{comment_id}";
	public static final String image = "image";
	public static final String audio = "audio";
	public static final String sub = "sub";
	public static final String md5 = "md5";
	public static final String level = "level";
	public static final String message = "message";
	public static final String hotValue = "1";
	public static final String like_count = "like_count";
	public static final String text = "text";
	public static final String priority = "priority";
	public static final String video = "video";
	public static final String description = "description";
	public static final String type = "type";
	public static final String trunksInfo = "trunks_info";
	public static final String statusNotify = "status_notify";
	public static final String lastGetNotify = "last_get_notify";
	public static final String avatar = "avatar";
	public static final String gender = "gender";
	public static final String googleApiKey = "googleApiKey";

	public static final double firstGet = -1.5;

	public static final double endData = -4;

	public static final long increaseDefault = 1;
	public static final long decreaseDefault = -1;

	public static final String followValue = "1";
	public static final String unfollowValue = "-1";

	public static final int likeValue = 1;
	public static final int unlikeValue = 0;

	public static final long objectTypeCommentOfVideoValue = 1;
	public static final long objectTypeReplyOfCommentValue = 0;

	public static final String newNotifyValue = "1";

	public static final int TIME_EXPIRE_VIDEO_FOLLOWING = 5 * 60;

}
