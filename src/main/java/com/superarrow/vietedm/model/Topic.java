package com.superarrow.vietedm.model;

import com.superarrow.vietedm.util.Utils;

public class Topic {
	public int topicId;
	public String name;
	public String coverImage;
	
	public String dataURL;
	
	public String toString() {
		String result = "";
		try {
			result = Utils.toString(this);
		} catch (Exception e) {
			
		}
		return result;
	}
}
