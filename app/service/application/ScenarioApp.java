package service.application;

import java.util.List;

import play.Logger;

import models.bean.core.ScenarioBean;
import service.business.ScenarioSvc;

public class ScenarioApp {
	/**
	 * spring injection
	 */
	private ScenarioSvc scenarioSvc;
	public void setScenarioSvc(ScenarioSvc scenarioSvc) {
		this.scenarioSvc = scenarioSvc;
	}
	/**
	 * create a new scenario
	 * @param scenarioBean
	 */
	public void create(ScenarioBean scenarioBean) {
		scenarioSvc.createElement(scenarioBean);
	}
	/**
	 * update
	 * @param scenarioBean
	 */
	public void update(ScenarioBean scenarioBean) {
		scenarioSvc.updateElement(scenarioBean);
	}
	/**
	 * find by its id
	 * @param id
	 * @param b
	 * @return
	 */
	public ScenarioBean scenarioById(Long id) {
		return scenarioSvc.findElementById(id, false);
	}
	/**
	 * find all element
	 * @return
	 */
	public List<ScenarioBean> scenarios() {
		return scenarioSvc.findAllElement();
	}
	/**
	 * insert a new step
	 * @param id
	 * @param idStep
	 * @param idInsert
	 * @param stepType
	 */
	public void insert(Long id) {
	}
	/**
	 * execute this story
	 * @param id
	 * @return
	 */
	public String executeById(Long id) {
		/**
		 * TODO
		 * execute this story
		 */
        return "";
	}
	/**
	 * execute this story
	 * @param id
	 * @return
	 */
	public Object execute(String story) {
		/**
		 * TODO
		 * execute this story
		 */
		return null;
	}
}
