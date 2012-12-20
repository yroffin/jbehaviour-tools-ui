package controllers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.jbehaviour.report.IBehaviourReportRun;
import org.jbehaviour.xref.IBehaviourXRef;
import org.jbehaviour.xref.IBehaviourXRefSuite;

import play.libs.Json;
import play.mvc.BodyParser;   
import play.mvc.Result;

import models.bean.core.LocalStoryBean;
import models.bean.datatable.JsDataTable;
import models.bean.jstree.JsTreeData;
import models.bean.jstree.JsTreeDataMeta;
import models.bean.ws.RestSession;
import play.Logger;
import play.mvc.*;
import service.Spring;
import service.application.LocalStoryApp;

import views.html.localStory.*;

public class LocalStoryRest extends Controller {
	final static LocalStoryApp localStoryApp = Spring.getBeanOfType(LocalStoryApp.class);

	/**
	 * scan local files
	 * @return
	 */
	public static Result scan(){
		JsonNode result = Json.toJson(new JsDataTable(localStoryApp.getBaseDir(), localStoryApp.scan()));
	    return ok(result);
	}

	static void findFiles(File file, Collection<File> all) {
	    File[] children = file.listFiles();
	    if (children != null) {
	        for (File child : children) {
	            all.add(child);
	            findFiles(child, all);
	        }
	    }
	}

	/**
	 * render all LocalStory
	 * @return
	 */
	public static Result table() {
		JsonNode result = Json.toJson(new JsDataTable(localStoryApp.getBaseDir(), localStoryApp.scan()));
	    return ok(result);
	}
	
	/**
	 * REST api for finding and reading a script
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result get() {
		/**
		 * read json object
		 */
		RestSession klass = Json.fromJson(request().body().asJson(),RestSession.class);
		try {
			String result = localStoryApp.findFile(klass.getPath(),klass.getName());
			klass.setRenderedScript(result);
		} catch (IOException e) {
			e.printStackTrace();
			return badRequest();
		}
		return ok(Json.toJson(klass));
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
		klass.setRenderedScript(klass.getRawScript());
		return ok(Json.toJson(klass));
	}

	/**
	 * REST Api for executing a LocalStory
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

		/**
		 * render from text
		 */
		Logger.info("Execute story from direct text");
		klass.setRenderedScript(klass.getRawScript());
		
		/**
		 * execute this story with jBehave
		 */
		IBehaviourXRef output = localStoryApp.execute(klass.getRenderedScript());
		
		/**
		 * Stdout
		 */
		StringBuilder sbOutput = new StringBuilder();
		for(String key : output.getRunsByScenario().keySet()) {
			IBehaviourXRefSuite local = output.getRunsByScenario().get(key);
			for(IBehaviourReportRun run : local.getRuns()) {
				try {
					sbOutput.append(run.getStdoutAsString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		klass.setRenderedStdout(sbOutput.toString());
		return ok(Json.toJson(klass));
	}

	/**
	 * REST Api for executing a LocalStory
	 * @param id
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result story(Long id) {
		Logger.info("story ["+id+"]");
		LocalStoryBean LocalStory = localStoryApp.LocalStoryById(id);
		/**
		 * find the story
		 */
		ObjectNode result = Json.newObject();
		result.put("Result", "OK");
		result.put("Name", LocalStory.getName());
		result.put("Story", LocalStory.getStory());
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
		List<LocalStoryBean> allLocalStorys = localStoryApp.LocalStorys();

		Set<String> directory = new HashSet<String>();
		/**
		 * find all first letter of all fields
		 */
		for(LocalStoryBean item : allLocalStorys) {
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
			for(LocalStoryBean item : allLocalStorys) {
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
		 * insert this new step in current LocalStory (id)
		 */
		//LocalStoryApp.insert(id);
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
		return ok(all.render(localStoryApp.LocalStorys()));
	}
}