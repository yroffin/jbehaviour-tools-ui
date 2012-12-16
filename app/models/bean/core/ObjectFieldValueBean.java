package models.bean.core;


import java.util.Date;

import play.data.validation.Constraints.Required;

import models.bean.DefaultBean;

public class ObjectFieldValueBean extends DefaultBean {
	public String getValue() {
		return value;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setValue(String _value) {
		this.value = _value;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setField(Long id) {
		field = id;
	}

	public void setEntity(Long id) {
		entity = id;
	}

	@Override
	public String toString() {
		return "ObjectFieldValueBean [value=" + value + ", field=" + field
				+ ", entity=" + entity + ", lastUpdate=" + lastUpdate + "]";
	}

	/**
	 * private part
	 */
	@Required
	private String value;
	@Required
	private Long field;
	@Required
	private Long entity;
	private Date lastUpdate;
}
