package models.bean.node;

import java.util.HashMap;
import java.util.Map;

import models.bean.core.ObjectEntityBean;

public class NodeFinder {

	Map<String,Object> fields = new HashMap<String,Object>();

	/**
	 * constructor
	 * @param vo
	 */
	public NodeFinder(ObjectEntityBean vo) {
		fields.put("id", vo.getId());
		fields.put("name", vo.getName());
		fields.put("description", vo.getDescription());
	}

	/**
	 * retrieve field by name
	 * @param name
	 * @return
	 */
	public Object field(String name) {
		return fields.get(name);
	}
}
