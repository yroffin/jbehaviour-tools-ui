package models.bean.jstree;

public class JsTree {
	private String name;
	private String onNodeAction;
	private String jsonData;
	private String domid;
	private String label;
	private String jsonCreateData;
	private String jsonUpdateData;
	private String jsonDeleteData;

	/**
	 * constructor
	 * @param _domid
	 * @param _label
	 * @param _name
	 * @param _jsFunction
	 * @param _jsonData
	 */
	public JsTree(
			String _domid,
			String _label,
			String _name,
			String _jsFunction,
			String _jsonCreateData,
			String _jsonUpdateData,
			String _jsonDeleteData,
			String _jsonData) {
		domid = _domid;
		label = _label;
		name = _name;
		onNodeAction = _jsFunction;
		jsonData = _jsonData;
		jsonCreateData = _jsonCreateData;
		jsonUpdateData = _jsonUpdateData;
		jsonDeleteData = _jsonDeleteData;
	}
	
	
	public String getDomid() {
		return domid;
	}


	public String getLabel() {
		return label;
	}


	/**
	 * retrieve jsonData service name
	 * @return
	 */
	public String getJsonData() {
		return jsonData;
	}
	public String getJsonCreate() {
		return jsonCreateData;
	}
	public String getJsonUpdate() {
		return jsonUpdateData;
	}
	public String getJsonDelete() {
		return jsonDeleteData;
	}
	/**
	 * get basename
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * on select node javascript function
	 * @return
	 */
	public String getOnSelectNode() {
		return onNodeAction+"Select(event, data)";
	}
	public String getOnCreateNode() {
		return onNodeAction+"Create(event, data)";
	}
	public String getOnUpdateNode() {
		return onNodeAction+"Update(event, data)";
	}
	public String getOnDeleteNode() {
		return onNodeAction+"Delete(event, data)";
	}
	public String getTransform() {
		return onNodeAction+"Transform(data)";
	}
	public String toObjectEntityBean() {
		return onNodeAction+"toObjectEntityBean(data)";
	}
	public String updateData() {
		return onNodeAction+"UpdateData(data)";
	}
}
