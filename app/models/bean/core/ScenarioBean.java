package models.bean.core;


import java.util.Date;

import play.data.validation.Constraints.Required;

import models.bean.DefaultBean;

public class ScenarioBean extends DefaultBean {
	public String getName() {
		return name;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public String toString() {
		return "ScenarioBean [name=" + name + ", description=" + description
				+ ", lastUpdate=" + lastUpdate + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

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

	/**
	 * private part
	 */
	@Required
	private String name;
	@Required
	private String description;
	@Required
	private String story;
	private Date lastUpdate;
}
