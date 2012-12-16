package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;   
import play.mvc.Result;

import models.bean.core.ObjectEntityBean;
import models.bean.core.ObjectFieldBean;
import models.bean.jstree.JsTreeData;
import models.bean.jstree.JsTreeDataMeta;
import play.Logger;
import play.mvc.*;
import service.Spring;
import service.application.ObjectEntityApp;

public class ObjectFieldRest extends Controller {
	final static ObjectEntityApp ObjectEntityApp = Spring.getBeanOfType(ObjectEntityApp.class);

	/**
	 * retrieve all fields
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 * @return
	 */
	public static Result allFields(Long jtStartIndex, Long jtPageSize, String jtSorting) {
		ObjectNode result = Json.newObject();
		result.put("Result", "OK");
		ArrayNode array = result.putArray("Records");
		for(ObjectFieldBean item : ObjectEntityApp.ObjectFields()) {
			ObjectNode sub = Json.newObject();
			sub.put("Id", item.getId());
			sub.put("Name", item.getName());
			sub.put("Description", item.getDescription());
			sub.put("Type", item.getType());
			sub.put("Size", item.getSize());
			array.add(sub);
		}
	    return ok(result);
	}

	/**
	 * edit steps of this ObjectField
	 * @param id
	 * @return
	 */
	public static Result tree(String operation) {
		return treeById(null);
	}

	/**
	 * edit steps of this ObjectField
	 * @param id
	 * @return
	 */
	public static Result treeById(Long id) {
		List<ObjectFieldBean> allFields = ObjectEntityApp.ObjectFields();

		Set<String> directory = new HashSet<String>();
		/**
		 * find all first letter of all fields
		 */
		for(ObjectFieldBean item : allFields) {
			directory.add(item.getName().substring(0,1));
		}

		int index = 0;
		JsTreeData[] arrayOfKlass = new JsTreeData[directory.size()];
		for(String item : directory) {
			arrayOfKlass[index++] =
					new JsTreeData(
							"N/A",
							item + "...",
							new JsTreeDataMeta(
									null,
									null,
									item + "...",
									"help_index",
									"",
									null,
									null),
							null);
		}

		index = 0;
		for(String prefix : directory) {
			for(ObjectFieldBean item : allFields) {
				if(item.getName().startsWith(prefix)) {
					arrayOfKlass[index].addChild(
							new JsTreeData(
									item.getId()+"",
									item.getName(),
									new JsTreeDataMeta(
											item.getId(),
											null,
											item.getName(),
											"field",
											item.getDescription(),
											null,
											null),
									null)
							);
				}
			}
			index++;
		}

		return ok(Json.toJson(arrayOfKlass));
	}

	/**
	 * REST service for insert a new step before idStep
	 * @param id
	 * @param idStep
	 * @param idInsert
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result insert(Long id, Long idStep, Long idInsert, String stepType) {
		Logger.info("insert ["+id+","+idStep+","+idInsert+","+stepType+"]");
		/**
		 * insert this new step in current ObjectField (id)
		 */
		ObjectEntityApp.insertField(id);
		ObjectNode result = Json.newObject();
		result.put("text", "essai");
		return ok(result);
	}

	/**
	 * update
	 * @param id
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result update() {
		Logger.info(1+"");
		JsonNode json = request().body().asJson();
		Logger.info(request().body().asText());
		//String name = json.findPath("name").getTextValue();
		return null;
	}

	/**
	 * create
	 * @return
	 */
	public static Result create() {
		return null;
	}

	/**
	 * crud validation
	 * @return
	 */
	public static Result submit() {
		return null;
	}

	/**
	 * delete
	 * @param id
	 * @return
	 */
	public static Result delete(Long id) {
		return null;
	}
}