package models.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;

import models.entity.DefaultModel;

@Entity
public class ObjectField extends DefaultModel {
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = -6837319009887077217L;

	@Column(name = "name", nullable = false, length = 64)
	public String name;
	@Column(name = "description", nullable = false, length = 1024)
	public String description;
	@Column(name = "type", nullable = false, length = 16)
	public String type;
	@Column(name = "size", nullable = false, length = 16)
	public Long size;
}
