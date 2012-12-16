package service.business;


import java.util.Date;

import play.db.ebean.Model.Finder;

import models.bean.core.ObjectFieldValueBean;
import models.entity.core.ObjectFieldValue;
import service.CrudService;

public class ObjectFieldValueSvc extends CrudService<ObjectFieldValue, ObjectFieldValueBean> {
	/**
	 * create the default finder
	 */
	public ObjectFieldValueSvc() {
		find = new Finder<Long, ObjectFieldValue>(Long.class,ObjectFieldValue.class);
		persistent = ObjectFieldValue.class;
		bean = ObjectFieldValueBean.class;
	}
	@Override
	public ObjectFieldValueBean updateVo(ObjectFieldValueBean vo, ObjectFieldValue entity) {
		vo.setValue(entity.value);
		vo.setLastUpdate(new Date());
		vo.setField(entity.field.id);
		vo.setEntity(entity.entity.id);
		return vo;
	}
	@Override
	public ObjectFieldValue updateEntity(ObjectFieldValueBean vo, ObjectFieldValue entity) {
		entity.value = vo.getValue();
		entity.lastUpdate = vo.getLastUpdate();
		return entity;
	}
}
