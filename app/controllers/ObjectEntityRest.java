package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;   
import play.mvc.Result;

import models.bean.core.ObjectEntityBean;
import models.bean.jstree.JsTreeData;
import models.bean.jstree.JsTreeDataMeta;
import models.bean.ws.RestSession;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import service.Spring;
import service.application.ObjectEntityApp;

public class ObjectEntityRest extends Controller {
	final static ObjectEntityApp objectEntityApp = Spring.getBeanOfType(ObjectEntityApp.class);

	/**
	 * render all ObjectEntity
	 * @return
	 */
	public static Result allEntities(Long jtStartIndex, Long jtPageSize, String jtSorting) {
		ObjectNode result = Json.newObject();
		result.put("Result", "OK");
		ArrayNode array = result.putArray("Records");
		for(ObjectEntityBean item : objectEntityApp.ObjectEntitys()) {
			ObjectNode sub = Json.newObject();
			sub.put("Id", item.getId());
			sub.put("Name", item.getName());
			sub.put("Description", item.getDescription());
			sub.put("Father", item.getFather());
			StringBuilder sb = new StringBuilder();
			for(Long child : item.getChildren()) {
				if(sb.length()>1) sb.append(", ");
				sb.append(child.toString());
			}
			sub.put("Children", sb.toString());
			array.add(sub);
		}
	    return ok(result);
	}

	/**
	 * edit steps of this ObjectEntity
	 * @param id
	 * @return
	 */
	public static Result tree(String operation) {
		return treeById(null);
	}

	/**
	 * edit steps of this ObjectEntity
	 * @param id
	 * @return
	 */
	public static Result treeById(Long id) {
		List<ObjectEntityBean> allEntities = objectEntityApp.ObjectEntitys();
		
		int index = 0;
		JsTreeData[] arrayOfKlass = new JsTreeData[allEntities.size()];
		for(ObjectEntityBean item : allEntities) {
			if(item.getFather() == null) {
				arrayOfKlass[index++] =
						new JsTreeData(
								item.getId()+"",
								item.getName(),
								new JsTreeDataMeta(
										item.getId(),
										item.getFather(),
										item.getName(),
										"entity",
										item.getDescription(),
										item.getChildren(),
										item.getValues()),
								(JsTreeData[]) findChild(item.getId(), item.getValues(), allEntities, new ArrayList<JsTreeData>())
						);
			}
		}

		return ok(Json.toJson(arrayOfKlass));
	}

	/**
	 * find all child
	 * @param id
	 * @param values 
	 * @param allEntities
	 * @param result
	 * @return
	 */
	private static JsTreeData[] findChild(long id, Set<Long> values, List<ObjectEntityBean> allEntities, List<JsTreeData> result) {
		/**
		 * find values
		 */
		for(Long value : values) {
			result.add(new JsTreeData(
					value+"",
					value+"",
					new JsTreeDataMeta(
							value,
							null,
							"TODO",
							"value",
							"N/A",
							null,
							null),
					null));
		}

		for(ObjectEntityBean item : allEntities) {
			/**
			 * find sub-entities
			 */
			if( item.getFather() != null && item.getFather() == id) {
				result.add(new JsTreeData(
						item.getId()+"",
						item.getName(),
						new JsTreeDataMeta(
								item.getId(),
								item.getFather(),
								item.getName(),
								"entity",
								item.getDescription(),
								item.getChildren(),
								item.getValues()),
						findChild(item.getId(), item.getValues(), allEntities, new ArrayList<JsTreeData>())));
			}
		}
		
		if(result.size() == 0) return null;
		int index = 0;
		JsTreeData[] asArray = new JsTreeData[result.size()];
		for(JsTreeData item : result) {
			asArray[index++] = item;
		}
		return asArray;
	}

	/**
	 * REST create
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result create() {
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		/**
		 * get bean from REST session
		 */
		ObjectEntityBean fromSession = klass.getEntityBean();
		Logger.info("create bean from session: "+fromSession);
		ObjectEntityBean bean = new ObjectEntityBean();
		bean.setName(fromSession.getName());
		bean.setDescription(fromSession.getDescription());
		objectEntityApp.create(bean);
		klass.setEntityBean(bean);
		/**
		 * return REST session
		 */
		return ok(Json.toJson(klass));
	}

	/**
	 * REST update
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result update() {
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		/**
		 * get bean from REST session
		 */
		ObjectEntityBean fromSession = klass.getEntityBean();
		Logger.info("update bean from session: "+fromSession);
		ObjectEntityBean bean = objectEntityApp.ObjectEntityById(fromSession.getId());
		bean.setName(fromSession.getName());
		bean.setDescription(fromSession.getDescription());
		objectEntityApp.update(bean);
		/**
		 * return REST session
		 */
		return ok(Json.toJson(klass));
	}

	/**
	 * REST delete
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result delete() {
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		return ok("");
	}
}