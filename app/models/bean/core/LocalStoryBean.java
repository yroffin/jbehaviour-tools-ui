package models.bean.core;

import java.util.Date;

import models.bean.DefaultBean;
import play.data.validation.Constraints.Required;

public class LocalStoryBean extends DefaultBean {
	public String getName() {
		return name;
	}

	@Override
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String encode) {
		this.hash = encode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "LocalStoryBean [name=" + name + ", description=" + description
				+ ", lastUpdate=" + lastUpdate + "]";
	}

	/**
	 * private part
	 */
	@Required
	private String name;
	@Required
	private String description;
	@Required
	private String path;
	@Required
	private String story;
	@Required
	private Date lastUpdate;
	@Required
	private String hash;
}
