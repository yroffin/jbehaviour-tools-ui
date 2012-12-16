package models.bean.core;


import java.util.Date;
import java.util.Set;

import play.data.validation.Constraints.Required;

import models.bean.DefaultBean;

public class ObjectEntityBean extends DefaultBean {
	public String getName() {
		return name;
	}

	public Date getLastUpdate() {
		return lastUpdate;
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

	public Long getFather() {
		return father;
	}

	public void setFather(Long father) {
		this.father = father;
	}

	public Set<Long> getChildren() {
		return children;
	}

	public void setChildren(Set<Long> children) {
		this.children = children;
	}

	public Set<Long> getValues() {
		return values;
	}

	public void setValues(Set<Long> values) {
		this.values = values;
	}

	/**
	 * private part
	 */
	@Required
	private String name;
	@Required
	private String description;
	private Date lastUpdate;
	private Long father;
	private Set<Long> children;
	private Set<Long> values;

	@Override
	public String toString() {
		return "ObjectEntityBean [name=" + name + ", description="
				+ description + ", lastUpdate=" + lastUpdate + ", father="
				+ father + ", children=" + children + ", values=" + values
				+ "]";
	}
}
