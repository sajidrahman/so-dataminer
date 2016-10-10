package edu.kstate.so.SOMiner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {
	private String postID;
	private String title;
	private String body;
	private String postTypeId;
	private String acceptedAnswerId;
	private String creationDate;
	private String score;
	private String viewCount;
	private String tags;

	public String getTags() {
		return this.tags != null ? this.tags : "n/a";
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Post() {
	}







	public String getCreationDate() {
		return this.creationDate != null ? this.creationDate : "n/a";
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}





	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = sanitizeBodyText(body);
	}

	public String getTitle() {
		return this.title != null ? this.title.trim() : "n/a";
	}

	public String getBody() {
		return this.body != null ? this.body : "n/a";
	}

	public String sanitizeBodyText(String body) {
		String REGEX1 = "<code>.+?</code>";
		String REGEX2 = "<(.|\n)+?>";
		String REPLACE = "";
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
		tempBody = tempBody.replaceAll("\n", "");
		return tempBody;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public String getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getAcceptedAnswerId() {
		return  this.acceptedAnswerId != null ? this.acceptedAnswerId : "n/a";
	}

	public void setAcceptedAnswerId(String acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public String getScore() {
		return  this.score != null ? this.score : "n/a";
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getViewCount() {
		return this.viewCount != null ? this.viewCount : "n/a";
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}


}


