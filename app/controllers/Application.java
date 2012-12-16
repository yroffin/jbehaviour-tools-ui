package controllers;

import java.util.List;
import java.util.Map;

import models.bean.ws.RestSession;
import models.entity.core.ObjectEntity;
import models.entity.core.ObjectFieldValue;
import models.params.GlobalParams;

import com.avaje.ebean.Ebean;

import play.libs.Json;
import play.libs.Yaml;
import play.mvc.*;
import service.Spring;
import service.application.ScenarioApp;

import views.html.index;

public class Application extends Controller {
	final static ScenarioApp scenarioApp = Spring.getBeanOfType(ScenarioApp.class);

	/**
	 * default index
	 * @return
	 */
	public static Result index() {
		return ok(index.render("Your new application is ready.",GlobalParams.instance()));
	}

	/**
	 * only for test reload data from test yaml
	 * @return
	 */
	@BodyParser.Of(BodyParser.TolerantJson.class)
	public static Result reload() {
		reinit("initial-data.yml");
		return ok(Json.toJson(new RestSession()));
	}

	/**
	 * save association
	 * @param obj
	 */
	static void saveAssociation(ObjectEntity obj) {
		/**
		 * crossref are not supported by snakeyaml so force this manualy by
		 * code
		 */
		ObjectEntity objectEntity = ((models.entity.core.ObjectEntity) obj);
		objectEntity.save();
		for (ObjectEntity child : objectEntity.children) {
			child.father = objectEntity;
		}
		for (ObjectFieldValue child : objectEntity.values) {
			child.entity = objectEntity;
		}
		Ebean.saveAssociation(objectEntity, "children");
		Ebean.saveAssociation(objectEntity, "values");
	}

	/**
	 * save association
	 * @param obj
	 */
	static void saveValues(ObjectEntity obj) {
		/**
		 * crossref are not supported by snakeyaml so force this manualy by
		 * code
		 */
		ObjectEntity objectEntity = ((models.entity.core.ObjectEntity) obj);
		objectEntity.save();
		for (ObjectFieldValue child : objectEntity.values) {
			child.entity = objectEntity;
		}
		Ebean.saveAssociation(objectEntity, "values");
	}

	@SuppressWarnings("unchecked")
	public static void reinit(String yaml) {
		Map<String, List<Object>> all = ((Map<String, List<Object>>) Yaml
				.load(yaml));
		/**
		 * destroy data
		 */
		for (Object ids : Ebean.find(models.entity.core.ObjectFieldValue.class).findIds()) {
			Ebean.find(models.entity.core.ObjectFieldValue.class, ids).delete();
		}
		for (Object ids : Ebean.find(models.entity.core.ObjectEntity.class).findIds()) {
			Ebean.find(models.entity.core.ObjectEntity.class, ids).delete();
		}
		for (Object ids : Ebean.find(models.entity.core.ObjectField.class).findIds()) {
			Ebean.find(models.entity.core.ObjectField.class, ids).delete();
		}
		for (Object ids : Ebean.find(models.entity.core.Scenario.class).findIds()) {
			Ebean.find(models.entity.core.Scenario.class, ids).delete();
		}
		/**
		 * saving data
		 */
		Ebean.save(all.get("scenarios"));
		Ebean.save(all.get("fields"));
		for (Object obj : all.get("root")) saveAssociation((ObjectEntity) obj);
		for (Object obj : all.get("level10")) saveAssociation((ObjectEntity) obj);
		for (Object obj : all.get("level20")) saveAssociation((ObjectEntity) obj);
		for (Object obj : all.get("level30")) saveAssociation((ObjectEntity) obj);
		for (Object obj : all.get("level301")) saveAssociation((ObjectEntity) obj);
		Ebean.save(all.get("values"));
		for (Object obj : all.get("root")) saveValues((ObjectEntity) obj);
		for (Object obj : all.get("level10")) saveValues((ObjectEntity) obj);
		for (Object obj : all.get("level20")) saveValues((ObjectEntity) obj);
		for (Object obj : all.get("level30")) saveValues((ObjectEntity) obj);
		for (Object obj : all.get("level301")) saveValues((ObjectEntity) obj);
	}
}