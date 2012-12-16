package models.entity.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.entity.DefaultModel;

@Entity
public class ObjectEntity extends DefaultModel {
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = -6837319009847077217L;

	@Column(name = "name", nullable = false, length = 64)
	public String name;
	@Column(name = "description", nullable = false, length = 1024)
	public String description;
	
	@ManyToOne
	public ObjectEntity father;
	@OneToMany(mappedBy = "father", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public Set<ObjectEntity> children = new HashSet<ObjectEntity>();

	@ManyToOne
	public ObjectFieldValue value;
	@OneToMany(mappedBy = "entity", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public Set<ObjectFieldValue> values = new HashSet<ObjectFieldValue>();
}
