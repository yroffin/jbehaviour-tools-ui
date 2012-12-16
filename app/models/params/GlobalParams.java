package models.params;

import java.util.HashMap;
import java.util.Map;

import models.bean.jstree.JsTree;

public class GlobalParams {
	/**
	 * singleton pattern
	 */
	private static GlobalParams _instance = instance();
	public static GlobalParams instance() {
		if(_instance == null) {
			_instance = new GlobalParams();
		}
		return _instance;
	}

	/**
	 * global structure
	 */
	private Map<String,JsTree> jsTreeParams = new HashMap<String,JsTree>();
	public JsTree getJsTreeParams(String name) {
		return jsTreeParams.get(name);
	}
	/**
	 * define global parameters for this application
	 */
	private GlobalParams() {
		jsTreeParams.put("Entities",
				new JsTree(
						"TreeContainerEntities",
						"Entities",
						"Entities",
						"jsOnEntityNode",
						"/rest/entity/create",
						"/rest/entity/update",
						"/rest/entity/delete",
						"/rest/entity/tree"));
		jsTreeParams.put("Fields",
				new JsTree(
						"TreeContainerFields",
						"Fields",
						"Fields",
						"jsOnFieldNode",
						"/rest/field/create",
						"/rest/field/update",
						"/rest/field/delete",
						"/rest/field/tree"));
		jsTreeParams.put("Scenarios",
				new JsTree(
						"TreeContainerScenarios",
						"Scenarios",
						"Scenarios",
						"jsOnScenarioNode",
						"/rest/scenario/create",
						"/rest/scenario/update",
						"/rest/scenario/delete",
						"/rest/scenario/tree"));
	}
}
