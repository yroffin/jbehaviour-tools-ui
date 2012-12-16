package service.business;


import java.util.Date;

import play.db.ebean.Model.Finder;

import models.bean.core.ObjectFieldBean;
import models.entity.core.ObjectField;
import service.CrudService;

public class ObjectFieldSvc extends CrudService<ObjectField, ObjectFieldBean> {
	/**
	 * create the default finder
	 */
	public ObjectFieldSvc() {
		find = new Finder<Long, ObjectField>(Long.class,ObjectField.class);
		persistent = ObjectField.class;
		bean = ObjectFieldBean.class;
	}
	@Override
	public ObjectFieldBean updateVo(ObjectFieldBean vo, ObjectField entity) {
		vo.setName(entity.name);
		vo.setLastUpdate(new Date());
		vo.setDescription(entity.description);
		vo.setType(entity.type);
		vo.setSize(entity.size);
		return vo;
	}
	@Override
	public ObjectField updateEntity(ObjectFieldBean vo, ObjectField entity) {
		entity.name = vo.getName();
		entity.description = vo.getDescription();
		entity.lastUpdate = vo.getLastUpdate();
		entity.type = vo.getType();
		entity.size = vo.getSize();
		return entity;
	}
}
