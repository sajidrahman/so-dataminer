package edu.kstate.so.SOMiner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {
	private String title;
	private String body;
	private int postTypeId;

	public Post() {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = sanitizeBodyText(body);
	}

	public String getTitle() {
		return this.title != null ? this.title.trim() : "";
	}

	public String getBody() {
		return this.body != null ? this.body : "";
	}

	public String sanitizeBodyText(String body) {
		String REGEX1 = "<code>.+?</code>";
		String REGEX2 = "<(.|\n)+?>";
		String REPLACE = " ";
		Pattern p1 = Pattern.compile(REGEX1, Pattern.DOTALL);
		Pattern p2 = Pattern.compile(REGEX2, Pattern.DOTALL);
		// get a matcher object
		Matcher m1 = p1.matcher(body);

		StringBuffer sb1 = new StringBuffer();
		while (m1.find()) {
			m1.appendReplacement(sb1, REPLACE);
		}
		m1.appendTail(sb1);
		// System.out.println(sb1.toString());
		String tempBody = sb1.toString().trim();
		Matcher m2 = p2.matcher(tempBody);
		StringBuffer sb2 = new StringBuffer();
		while (m2.find()) {
			m2.appendReplacement(sb2, REPLACE);
		}
		m2.appendTail(sb2);
		
		tempBody = sb2.toString().replaceAll("\\s+$", "");
		tempBody = tempBody.replaceAll("\\n", "");
		return tempBody;
	}

	public void setPostTypeId(String postTypeId) {
		// TODO Auto-generated method stub
		this.postTypeId = Integer.parseInt(postTypeId);
	}
	
	public int getPostTypeId(){
		return this.postTypeId;
	}
}


