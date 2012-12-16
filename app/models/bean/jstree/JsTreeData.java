package models.bean.jstree;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsTreeData {
	@JsonProperty()
	private JsTreeDataElement data;
	@JsonProperty()
	private JsTreeDataAttr attr;
	@JsonProperty()
	private JsTreeDataMeta metadata;
	@JsonProperty()
	private JsTreeData[] children;

	/**
	 * constructor
	 * @param _id
	 * @param _title
	 */
	public JsTreeData(String _id, String _title, JsTreeDataMeta _metadata, JsTreeData[] _arrayOfChildKlass) {
		data = new JsTreeDataElement(_title,new JsTreeDataAttr(_title),"/assets/images/"+_metadata.getKlass()+".png");
		attr = data.getAttr();
		metadata = _metadata;
		children = _arrayOfChildKlass;
	}

	/**
	 * add a new child, and increse as necessary
	 * this array
	 * @param jsTreeData
	 */
	public void addChild(JsTreeData jsTreeData) {
		if(children == null) {
			children = new JsTreeData[1];
			children[0] = jsTreeData;
		} else {
			JsTreeData[] old = children;
			children = new JsTreeData[children.length+1];
			int i;
			for(i=0;i<old.length;i++) {
				children[i] = old[i];
			}
			children[i] = jsTreeData;
		}
	}
}
