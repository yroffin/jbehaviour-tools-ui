package controllers;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.BodyParser;   

import models.bean.core.ScenarioBean;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import service.Spring;
import service.application.ScenarioApp;

import views.html.scenario.*;

public class Scenario extends Controller {
	final static Form<ScenarioBean> scenarioForm = form(ScenarioBean.class);
	final static ScenarioApp scenarioApp = Spring.getBeanOfType(ScenarioApp.class);

	/**
	 * render all scenario
	 * @return
	 */
	public static Result all() {
		return ok(all.render(scenarioApp.scenarios()));
	}

	/**
	 * edit steps of this scenario
	 * @param id
	 * @return
	 */
	public static Result edit(Long id) {
		return null;
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
		 * insert this new step in current scenario (id)
		 */
		scenarioApp.insert(id);
		ObjectNode result = Json.newObject();
		result.put("text", "essai");
		return ok(result);
	}

	/**
	 * update
	 * @param id
	 * @return
	 */
	public static Result update(Long id) {
		ScenarioBean myBean = scenarioApp.scenarioById(id);
		scenarioForm.data().put("id", ""+id);
		scenarioForm.data().put("name", myBean.getName());
		scenarioForm.data().put("description", myBean.getDescription());
		return ok(manage.render(scenarioForm));
	}

	/**
	 * create
	 * @return
	 */
	public static Result create() {
		scenarioForm.data().clear();
		scenarioForm.data().put("id", "-1");
		return ok(manage.render(scenarioForm));
	}

	/**
	 * crud validation
	 * @return
	 */
	public static Result submit() {
		Form<ScenarioBean> vo = scenarioForm.bindFromRequest();
		if("-1".compareTo(vo.field("id").value())==0) {
			scenarioApp.create(vo.get());
		} else {
			scenarioApp.update(vo.get());
		}
		return all();
	}

	/**
	 * delete
	 * @param id
	 * @return
	 */
	public static Result delete(Long id) {
		return ok(all.render(scenarioApp.scenarios()));
	}

	/**
	 * add new step
	 * @param id
	 * @return
	 */
	public static Result addStep(Long id) {
		return edit(id);
	}
}