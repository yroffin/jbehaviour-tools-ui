package service.application;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

import play.Logger;

import models.bean.core.ObjectEntityBean;
import models.bean.node.NodeFinder;
import service.business.ObjectEntitySvc;
import service.business.ObjectFieldSvc;
import service.business.ScenarioSvc;

public class ObjectFinderApp implements LogChute {
	/**
	 * spring injection
	 */
	private ObjectEntitySvc objectEntitySvc;
	public void setObjectEntitySvc(ObjectEntitySvc _objectEntitySvc) {
		objectEntitySvc = _objectEntitySvc;
	}
	private ObjectFieldSvc objectFieldSvc;
	public void setObjectFieldSvc(ObjectFieldSvc _objectFieldSvc) {
		objectFieldSvc = _objectFieldSvc;
	}
	private ScenarioSvc scenarioSvc;
	public void setScenarioSvc(ScenarioSvc scenarioSvc) {
		this.scenarioSvc = scenarioSvc;
	}
	
	private VelocityEngine ve;

	/**
	 * constructor
	 */
	public ObjectFinderApp() {
		ve = new VelocityEngine();
		ve.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, this);
		ve.init();
	}

	/**
	 * render this script
	 * @param script
	 * @param ctx
	 * @return
	 */
	public String render(String script, Object ctx) {
		Logger.info("render ["+script+"]");

		VelocityContext context = new VelocityContext();
		context.put( "context", ctx );
		StringWriter w = new StringWriter();
		if(ve.evaluate( context, w, "render", script )) {
			try {
				w.close();
			} catch (IOException e) {
				/**
				 * TODO
				 * dump stack trace to logger
				 */
				e.printStackTrace();
			}
			return w.getBuffer().toString();
		} else {
			Logger.warn("Unable to render this script");
		}
		return null;
	}

	/**
	 * render story by id
	 * @param storyId
	 * @param ctx
	 * @return
	 */
	public String render(int storyId, Object ctx) {
		Logger.info("render ["+storyId+"]");

		VelocityContext context = new VelocityContext();
		context.put( "context", ctx );
		StringWriter w = new StringWriter();
		if(ve.evaluate( context, w, "render", scenarioSvc.findElementById(storyId, false).getStory() )) {
			try {
				w.close();
			} catch (IOException e) {
				/**
				 * TODO
				 * dump stack trace to logger
				 */
				e.printStackTrace();
			}
			return w.getBuffer().toString();
		} else {
			Logger.warn("Unable to render this script");
		}
		return null;
	}

	/**
	 * find by its id
	 * @param id
	 * @param b
	 * @return
	 */
	public NodeFinder byId(int id) {
		return new NodeFinder(objectEntitySvc.findElementById(id, false));
	}

	/**
	 * find by name
	 * @param name
	 * @return
	 */
	public List<NodeFinder> byName(String name) {
		List<NodeFinder> result = new ArrayList<NodeFinder>();
		for(ObjectEntityBean item : objectEntitySvc.findElementByName(name, false)) {
			result.add(new NodeFinder(item));
		}
		return result;
	}

	/**
	 * retrieve all node
	 * @return
	 */
	public List<NodeFinder> all() {
		List<NodeFinder> result = new ArrayList<NodeFinder>();
		for(ObjectEntityBean item : objectEntitySvc.findAllElement()) {
			result.add(new NodeFinder(item));
		}
		return result;
	}

	/**
	 * retrieve first node
	 * @param list
	 * @return
	 */
	public NodeFinder first(List<NodeFinder> list) {
		return list.get(0);
	}

	/**
	 * retrieve last node
	 * @param list
	 * @return
	 */
	public NodeFinder last(List<NodeFinder> list) {
		return list.get(list.size()-1);
	}

	/**
	 * logger
	 */
	@Override
	public boolean isLevelEnabled(int arg0) {
		return true;
	}

	/**
	 * logger
	 */
	@Override
	public void log(int arg0, String arg1) {
		Logger.info(arg1);
	}

	/**
	 * logger
	 */
	@Override
	public void log(int arg0, String arg1, Throwable arg2) {
		Logger.warn(arg1 + ":" + arg2.getLocalizedMessage());
	}

	/**
	 * initialize
	 */
	@Override
	public void init(RuntimeServices arg0) throws Exception {
	}
}
