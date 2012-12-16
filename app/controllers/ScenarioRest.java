package controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;   
import play.mvc.Result;

import models.bean.core.ObjectFieldBean;
import models.bean.core.ScenarioBean;
import models.bean.jstree.JsTreeData;
import models.bean.jstree.JsTreeDataMeta;
import models.bean.ws.RestSession;
import play.Logger;
import play.mvc.*;
import service.Spring;
import service.application.ObjectFinderApp;
import service.application.ScenarioApp;

import views.html.scenario.*;

public class ScenarioRest extends Controller {
	final static ScenarioApp scenarioApp = Spring.getBeanOfType(ScenarioApp.class);
	final static ObjectFinderApp objectFinderApp = Spring.getBeanOfType(ObjectFinderApp.class);

	/**
	 * render all scenario
	 * @return
	 */
	public static Result all(Long jtStartIndex, Long jtPageSize, String jtSorting) {
		ObjectNode result = Json.newObject();
		result.put("Result", "OK");
		ArrayNode array = result.putArray("Records");
		for(ScenarioBean item : scenarioApp.scenarios()) {
			ObjectNode sub = Json.newObject();
			sub.put("Id", item.getId());
			sub.put("Name", item.getName());
			sub.put("Description", item.getDescription());
			array.add(sub);
		}
	    return ok(result);
	}
	
	/**
	 * REST api for validating a script
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result render() {
		/**
		 * read json object and
		 * render script
		 */
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		klass.setRenderedScript(objectFinderApp.render(klass.getRawScript(),objectFinderApp));
		return ok(Json.toJson(klass));
	}

	/**
	 * REST Api for executing a scenario
	 * @param id
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result execute() {
		/**
		 * read json object and
		 * render script
		 */
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		if(klass.getStoryId() != -1) {
			/**
			 * get story from scenario id
			 */
			Logger.info("Execute story from id "+klass.getStoryId());
			klass.setRenderedScript(objectFinderApp.render(klass.getStoryId(),objectFinderApp));
		} else {
			/**
			 * render from text
			 */
			Logger.info("Execute story from direct text");
			klass.setRenderedScript(objectFinderApp.render(klass.getRawScript(),objectFinderApp));
		}
		/**
		 * execute this story with jBehave
		 */
		Object output = scenarioApp.execute(klass.getRenderedScript());
		/**
		 * TODO
		 * klass.setRenderedStdout(((Object) output).getConsoleOutput());
		 */
		return ok(Json.toJson(klass));
	}

	/**
	 * REST Api for executing a scenario
	 * @param id
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result story(Long id) {
		Logger.info("story ["+id+"]");
		ScenarioBean scenario = scenarioApp.scenarioById(id);
		/**
		 * find the story
		 */
		ObjectNode result = Json.newObject();
		result.put("Result", "OK");
		result.put("Name", scenario.getName());
		result.put("Story", scenario.getStory());
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
		List<ScenarioBean> allScenarios = scenarioApp.scenarios();

		Set<String> directory = new HashSet<String>();
		/**
		 * find all first letter of all fields
		 */
		for(ScenarioBean item : allScenarios) {
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
			for(ScenarioBean item : allScenarios) {
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
	 * REST create
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result create() {
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		Logger.info("create : "+klass);
		/**
		 * insert this new step in current scenario (id)
		 */
		//scenarioApp.insert(id);
		ObjectNode result = Json.newObject();
		result.put("text", "essai");
		return ok(result);
	}

	/**
	 * REST update
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result update() {
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		return ok("");
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

	/**
	 * delete
	 * @param id
	 * @return
	 */
	public static Result delete(Long id) {
		return ok(all.render(scenarioApp.scenarios()));
	}
}