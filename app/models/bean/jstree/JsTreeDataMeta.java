package models.bean.jstree;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsTreeDataMeta {
	@JsonProperty()
	private String id;
	@JsonProperty()
	private String father;
	@JsonProperty()
	private String name;
	@JsonProperty()
	private String klass;
	@JsonProperty()
	private String description;
	@JsonProperty()
	private Set<Long> children;
	@JsonProperty()
	private Set<Long> values;

	public JsTreeDataMeta(Long _id, Long _father, String _name,
			String _klass, String _description, Set<Long> _children,
			Set<Long> _values) {
		if(_id != null) setId(_id+"");
		else setId(null);
		setName(_name);
		setKlass(_klass);
		setDescription(_description);
		if(_father != null) setFather(_father+"");
		else setFather(null);
		setChildren(_children);
		setValues(_values);
	}

	@Override
	public String toString() {
		return "JsTreeDataMeta [id=" + id + ", father=" + father + ", name="
				+ name + ", klass=" + klass + ", description=" + description
				+ ", children=" + children + ", values=" + values + "]";
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

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKlass() {
		return klass;
	}

	public void setKlass(String klass) {
		this.klass = klass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
