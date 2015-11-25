package com.superarrow.vietedm.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.superarrow.vietedm.api.DataAPI;
import com.superarrow.vietedm.model.Audio;
import com.superarrow.vietedm.model.Category;
import com.superarrow.vietedm.model.Topic;
import com.superarrow.vietedm.util.IdGenerator;

public class NhacdjCrawl {
	public static final String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36";

	/////////////////////////////////////////////////////////////////////
	// PARSE CATEGORY
	/////////////////////////////////////////////////////////////////////

	public static Category crawlCategory() {
		Category category = new Category();
		category.categoryId = (int)IdGenerator.genCategoryId();
		category.coverImage = "";
		category.name = "Nhạc sàn";
		category.dataURL = "http://nhacdj.7viet.com/Music-cat1.html";

		return category;
	}

	/////////////////////////////////////////////////////////////////////
	// PARSE TOPIC
	/////////////////////////////////////////////////////////////////////

	public static List<Topic> crawlTopic(int categoryId, String dataURL) {
		List<Topic> result = new ArrayList<Topic>();
		
		try {
			Document doc = Jsoup.connect(dataURL).userAgent(userAgent).get();
			Element topics = doc.select("ul#child-cat-list").first();
			Elements topicList = topics.select("li a.white-title");
			for(Element topic : topicList) {
				Topic data = new Topic();
				data.topicId = (int)IdGenerator.genTopicId();
				data.name = topic.attr("title");
				data.dataURL = topic.attr("href");
				System.out.println(data);
				
				DataAPI.insertTopic(categoryId, data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/////////////////////////////////////////////////////////////////////
	// PARSE AUDIO
	/////////////////////////////////////////////////////////////////////

	public static List<Audio> crawlAudio(int topicId, String topicURL) {
		List<Audio> result = new ArrayList<Audio>();

		return result;
	}
	
	
	public static void main(String[] args) {
		crawlTopic(1, "http://nhacdj.7viet.com/Music-cat1.html");
	}
}
