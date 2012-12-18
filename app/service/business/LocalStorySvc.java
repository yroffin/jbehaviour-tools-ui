package service.business;

import java.util.Date;

import play.db.ebean.Model.Finder;

import models.bean.core.LocalStoryBean;
import models.entity.core.LocalStory;
import service.CrudService;

public class LocalStorySvc extends CrudService<LocalStory, LocalStoryBean> {
	/**
	 * create the default finder
	 */
	public LocalStorySvc() {
		find = new Finder<Long, LocalStory>(Long.class,LocalStory.class);
		persistent = LocalStory.class;
		bean = LocalStoryBean.class;
	}
	@Override
	public LocalStoryBean updateVo(LocalStoryBean vo, LocalStory entity) {
		vo.setName(entity.name);
		vo.setLastUpdate(new Date());
		vo.setDescription(entity.description);
		vo.setStory(entity.story);
		return vo;
	}
	@Override
	public LocalStory updateEntity(LocalStoryBean vo, LocalStory entity) {
		entity.name = vo.getName();
		entity.description = vo.getDescription();
		entity.lastUpdate = vo.getLastUpdate();
		entity.story = vo.getStory();
		return entity;
	}
}
