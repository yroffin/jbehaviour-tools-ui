package models.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.entity.DefaultModel;

@Entity
public class ObjectFieldValue extends DefaultModel {
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = -6837319009887077217L;

	@Column(name = "value", nullable = false, length = 1024)
	public String value;
	@ManyToOne
	public ObjectField field;
	@ManyToOne
	public ObjectEntity entity;
}
