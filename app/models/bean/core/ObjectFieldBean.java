package models.bean.core;


import java.util.Date;

import play.data.validation.Constraints.Required;

import models.bean.DefaultBean;

public class ObjectFieldBean extends DefaultBean {
	public String getName() {
		return name;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public String toString() {
		return "ObjectFieldBean [name=" + name + ", description=" + description
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * private part
	 */
	@Required
	private String name;
	@Required
	private String description;
	@Required
	private String type;
	@Required
	private Long size;
	private Date lastUpdate;
}
