package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.bean.core.LocalStoryBean;
import models.bean.jstree.JsTreeData;
import models.bean.jstree.JsTreeDataMeta;
import models.bean.ws.DefaultMessage;

import org.codehaus.jackson.node.ObjectNode;
import org.jbehaviour.report.IBehaviourReportRun;
import org.jbehaviour.xref.IBehaviourXRef;
import org.jbehaviour.xref.IBehaviourXRefSuite;

import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.Spring;
import service.application.LocalStoryApp;
import service.technic.Md5;

public class LocalStoryRest extends Controller {
	final static LocalStoryApp localStoryApp = Spring
			.getBeanOfType(LocalStoryApp.class);

	/**
	 * retrieve all story
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result stories() {
		/**
		 * retrieve all stories from repository
		 */
		List<LocalStoryBean> klass = null;
		try {
			klass = findStories(localStoryApp.getBaseDir(),
					localStoryApp.scan());
		} catch (Exception e) {
			e.printStackTrace();
			return badRequest();
		}
		return ok(Json.toJson(klass));
	}

	/**
	 * REST api for finding and reading a script
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result story() {
		DefaultMessage msg = extractMessage();

		Logger.debug("story: " + msg);

		try {
			if (msg.getId() != null) { return storyById(msg.getId()); }
			if (msg.getHash() != null) { return storyByHash(msg.getHash()); }
		} catch (Exception e) {
			e.printStackTrace();
			return badRequest();
		}
		return badRequest();
	}

	/**
	 * REST api for finding and reading a script
	 * 
	 * @return
	 */
	public static Result storyById(Long id) {
		/**
		 * find by id
		 */
		for (LocalStoryBean klass : findStories(localStoryApp.getBaseDir(),
				localStoryApp.scan())) {
			if (id == klass.getId()) {
				try {
					Logger.debug("" + klass.getPath() + " / " + klass.getName());
					klass.setStory(localStoryApp.findFile(klass.getPath(),
							klass.getName()));
				} catch (IOException e) {
					e.printStackTrace();
					return badRequest();
				}
				return ok(Json.toJson(klass));
			}
		}
		return badRequest();
	}

	/**
	 * REST api for finding and reading a script
	 * 
	 * @return
	 */
	public static Result storyByHash(String hash) {
		/**
		 * find by id
		 */
		for (LocalStoryBean klass : findStories(localStoryApp.getBaseDir(),
				localStoryApp.scan())) {
			if (hash.compareTo(klass.getHash()) == 0) {
				try {
					klass.setStory(localStoryApp.findFile(klass.getPath(),
							klass.getName()));
				} catch (IOException e) {
					e.printStackTrace();
					return badRequest();
				}
				return ok(Json.toJson(klass));
			}
		}
		return badRequest();
	}

	/**
	 * REST Api for executing a LocalStory
	 * 
	 * @param id
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result execute() {
		/**
		 * read json object and render script
		 */
		DefaultMessage klass = Json.fromJson(request().body().asJson(),
				DefaultMessage.class);

		/**
		 * render from text
		 */
		Logger.info("Execute story from direct text");
		klass.setRenderedScript(klass.getRawScript());

		try {
			/**
			 * execute this story with jBehave
			 */
			IBehaviourXRef output = localStoryApp.execute(klass
					.getRenderedScript());

			/**
			 * Stdout
			 */
			StringBuilder sbOutput = new StringBuilder();
			for (String key : output.getRunsByScenario().keySet()) {
				IBehaviourXRefSuite local = output.getRunsByScenario().get(key);
				sbOutput.append("Running " + local);
				for (IBehaviourReportRun run : local.getRuns()) {
					try {
						sbOutput.append(run.getStdoutAsString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			klass.setRenderedStdout(sbOutput.toString());
			return ok(Json.toJson(klass));
		} catch (Exception e) {
			klass.setRenderedStdout(e.toString());
			return ok(Json.toJson(klass));
		}
	}

	/**
	 * edit steps of this ObjectField
	 * 
	 * @param id
	 * @return
	 */
	public static Result tree(String operation) {
		return treeById(null);
	}

	/**
	 * edit steps of this ObjectField
	 * 
	 * @param id
	 * @return
	 */
	public static Result treeById(Long id) {
		List<LocalStoryBean> allLocalStorys = localStoryApp.LocalStorys();

		Set<String> directory = new HashSet<String>();
		/**
		 * find all first letter of all fields
		 */
		for (LocalStoryBean item : allLocalStorys) {
			directory.add(item.getName().substring(0, 1));
		}

		int index = 0;
		JsTreeData[] arrayOfKlass = new JsTreeData[directory.size()];
		for (String item : directory) {
			arrayOfKlass[index++] = new JsTreeData("N/A", item + "...",
					new JsTreeDataMeta(null, null, item + "...", "help_index",
							"", null, null), null);
		}

		index = 0;
		for (String prefix : directory) {
			for (LocalStoryBean item : allLocalStorys) {
				if (item.getName().startsWith(prefix)) {
					arrayOfKlass[index].addChild(new JsTreeData(item.getId()
							+ "", item.getName(), new JsTreeDataMeta(item
							.getId(), null, item.getName(), "field", item
							.getDescription(), null, null), null));
				}
			}
			index++;
		}

		return ok(Json.toJson(arrayOfKlass));
	}

	/**
	 * REST create
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result create() {
		DefaultMessage klass = Json.fromJson(request().body().asJson(),
				DefaultMessage.class);
		Logger.info("create : " + klass);
		/**
		 * insert this new step in current LocalStory (id)
		 */
		// LocalStoryApp.insert(id);
		ObjectNode result = Json.newObject();
		result.put("text", "essai");
		return ok(result);
	}

	/**
	 * REST update
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result update() {
		DefaultMessage klass = Json.fromJson(request().body().asJson(),
				DefaultMessage.class);
		return ok("");
	}

	/**
	 * REST delete
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result delete() {
		DefaultMessage klass = Json.fromJson(request().body().asJson(),
				DefaultMessage.class);
		return ok("");
	}

	/**
	 * extract message from body
	 * 
	 * @return
	 */
	public static DefaultMessage extractMessage() {
		/**
		 * retrieve json message
		 */
		return Json.fromJson(request().body().asJson(), DefaultMessage.class);
	}

	/**
	 * retrieve local stories
	 * 
	 * @param baseDir
	 * @param scan
	 * @return
	 */
	private static List<LocalStoryBean> findStories(File baseDir,
			Collection<File> scan) {
		List<LocalStoryBean> list = new ArrayList<LocalStoryBean>();
		long id = 0;
		String uri = null;
		for (File file : scan) {
			uri = file.getParent().replace(baseDir.getAbsolutePath(), "")
					.replace("\\", "/");
			LocalStoryBean bean = new LocalStoryBean();
			bean.setDescription(uri);
			bean.setPath(uri);
			bean.setLastUpdate(new Date(file.lastModified()));
			bean.setId(id++);
			bean.setHash(Md5.encode(uri));
			bean.setName(file.getName());
			list.add(bean);
		}
		return list;
	}

}