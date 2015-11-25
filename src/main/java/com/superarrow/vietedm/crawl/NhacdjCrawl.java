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
		int pageSize = 2;
		for(int page = 1 ; page < pageSize ; page++) {
			try {
				Document doc = Jsoup.connect(topicURL + "?p=" + page).userAgent(userAgent).get();
				Element audios = doc.select("div#list-video-cat").first();
				Elements audioList = audios.select("div.song-bit div.song-name a");
				for(Element audio : audioList) {
					//System.out.println(audio);
					
					Audio data = new Audio();
					data.audioId = (int)IdGenerator.genAudioId();
					data.name = audio.attr("title");
					data.siteURL = audio.attr("href");
					data.siteURL = data.siteURL.replaceAll("-s([0-9]+).html", "-zo$1.xml");
					data.dataURL = crawlPlayURL(data.siteURL);
					System.out.println(data);
					DataAPI.insertAudio(topicId, data);
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static String crawlPlayURL(String dataURL) {
		try {
			Document doc = Jsoup.connect(dataURL).userAgent(userAgent).get();
			String playURL = doc.select("media|content").first().attr("url");
			System.out.println(playURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public static void main(String[] args) {
		//crawlTopic(1, "http://nhacdj.7viet.com/Music-cat1.html");
		crawlAudio(1, "http://nhacdj.7viet.com/Nonstop-c1.html");
		//crawlPlayURL("http://nhacdj.7viet.com/Nonstop-EDM-Is-The-Best-Of-My-Life-DJ-Duy-G-M-Mix-zo2491.xml");
	}
}
