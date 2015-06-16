package com.example.httpdemo.model;

import java.io.Serializable;

public class HttpResult implements Serializable {

	private static final long serialVersionUID = 4452952194575293740L;

	private String id;
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "HttpResult [id=" + id + ", title=" + title + "]";
	}

}
