package models.bean.jstree;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsTreeDataElement {
	@JsonProperty()
	private String title;
	@JsonProperty()
	private JsTreeDataAttr attr;
	@JsonProperty()
	private String icon;

	public JsTreeDataElement(String _title, JsTreeDataAttr _attr, String _icon) {
		title = _title;
		attr=_attr;
		attr.setRel(_icon);
		icon = _icon;
	}

	public JsTreeDataAttr getAttr() {
		return attr;
	}
}
