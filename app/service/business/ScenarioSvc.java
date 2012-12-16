package service.business;

import java.util.Date;

import play.db.ebean.Model.Finder;

import models.bean.core.ScenarioBean;
import models.entity.core.Scenario;
import service.CrudService;

public class ScenarioSvc extends CrudService<Scenario, ScenarioBean> {
	/**
	 * create the default finder
	 */
	public ScenarioSvc() {
		find = new Finder<Long, Scenario>(Long.class,Scenario.class);
		persistent = Scenario.class;
		bean = ScenarioBean.class;
	}
	@Override
	public ScenarioBean updateVo(ScenarioBean vo, Scenario entity) {
		vo.setName(entity.name);
		vo.setLastUpdate(new Date());
		vo.setDescription(entity.description);
		vo.setStory(entity.story);
		return vo;
	}
	@Override
	public Scenario updateEntity(ScenarioBean vo, Scenario entity) {
		entity.name = vo.getName();
		entity.description = vo.getDescription();
		entity.lastUpdate = vo.getLastUpdate();
		entity.story = vo.getStory();
		return entity;
	}
}
