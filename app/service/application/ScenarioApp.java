package service.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jbehaviour.exception.JBehaviourParsingError;
import org.jbehaviour.exception.JBehaviourRuntimeError;
import org.jbehaviour.impl.JBehaviourLauncher;
import org.jbehaviour.xref.IBehaviourXRef;

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
	public IBehaviourXRef execute(String story) {
		File localFile = null;
		try {
			localFile = File.createTempFile("play#", ".story");
			localFile.deleteOnExit();
		} catch (IOException e) {
			/**
			 * TODO
			 * better exception handle ?
			 */
			e.printStackTrace();
		}
		
		Logger.info("Running : " + localFile.getAbsolutePath());
		try {
			FileWriter writer = new FileWriter(localFile);
			writer.write(story);
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			JBehaviourLauncher launcher = new JBehaviourLauncher();
			launcher.registerAndExecute(localFile);
			return launcher.getEnv().getXRef();
		} catch (JBehaviourParsingError e) {
			e.printStackTrace();
		} catch (JBehaviourRuntimeError e) {
			e.printStackTrace();
		}
		return null;
	}
}
