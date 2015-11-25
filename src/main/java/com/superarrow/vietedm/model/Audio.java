package com.superarrow.vietedm.model;

import com.superarrow.vietedm.util.Utils;

public class Audio {
	public int audioId;
	public String name;
	public String description;
	public String coverImage;
	public String subscription;
	public long timeStamp;
	
	public String dataURL;
	public String siteURL;
	
	
	public String toString() {
		String result = "";
		try {
			result = Utils.toString(this);
		} catch (Exception e) {
			
		}
		return result;
	}
}
