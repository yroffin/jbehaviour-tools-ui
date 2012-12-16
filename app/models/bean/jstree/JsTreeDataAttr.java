package models.bean.jstree;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsTreeDataAttr {
	@JsonProperty()
	private String title;
	@JsonProperty()
	private String rel;

	public JsTreeDataAttr(String _title) {
		setTitle(_title);
	}

	public void setTitle(String _title) {
		title = _title;
	}

	public void setRel(String _rel) {
		rel = _rel;		
	}
}
