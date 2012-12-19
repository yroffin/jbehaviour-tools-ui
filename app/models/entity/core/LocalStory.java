package models.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;

import models.entity.DefaultModel;

@Entity
public class LocalStory extends DefaultModel {
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = -6637319009847077217L;

	@Column(name = "name", nullable = false, length = 64)
	public String name;
	@Column(name = "description", nullable = false, length = 1024)
	public String description;
	@Column(name = "story", nullable = false, length = 65536)
	public String story;

	@Override
	public String toString() {
		return "LocalStory [name=" + name + ", description=" + description
				+ ", story=" + story + "]";
	}
}
