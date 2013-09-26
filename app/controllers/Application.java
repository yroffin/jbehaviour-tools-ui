package controllers;

import java.util.List;
import java.util.Map;

import models.bean.ws.DefaultMessage;
import models.params.GlobalParams;
import play.libs.Json;
import play.libs.Yaml;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.Spring;
import service.application.LocalStoryApp;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Application extends Controller {
	final static LocalStoryApp localStoryApp = Spring
			.getBeanOfType(LocalStoryApp.class);

	/**
	 * default index
	 * 
	 * @return
	 */
	public static Result index() {
		return ok(index.render("Your new application is ready.",
				GlobalParams.instance()));
	}

	/**
	 * only for test reload data from test yaml
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result reload() {
		reinit("initial-data.yml");
		return ok(Json.toJson(new DefaultMessage()));
	}

	@SuppressWarnings("unchecked")
	public static void reinit(String yaml) {
		Map<String, List<Object>> all = ((Map<String, List<Object>>) Yaml
				.load(yaml));
		/**
		 * destroy data
		 */
		for (Object ids : Ebean.find(models.entity.core.LocalStory.class)
				.findIds()) {
			Ebean.find(models.entity.core.LocalStory.class, ids).delete();
		}
		/**
		 * saving data
		 */
		Ebean.save(all.get("story"));
	}
}