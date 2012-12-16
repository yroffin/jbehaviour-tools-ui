package service.business;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import play.db.ebean.Model.Finder;

import models.bean.core.ObjectEntityBean;
import models.entity.core.ObjectEntity;
import models.entity.core.ObjectFieldValue;
import service.CrudService;

public class ObjectEntitySvc extends CrudService<ObjectEntity, ObjectEntityBean> {
	/**
	 * create the default finder
	 */
	public ObjectEntitySvc() {
		find = new Finder<Long, ObjectEntity>(Long.class,ObjectEntity.class);
		persistent = ObjectEntity.class;
		bean = ObjectEntityBean.class;
	}
	@Override
	public ObjectEntityBean updateVo(ObjectEntityBean vo, ObjectEntity entity) {
		vo.setName(entity.name);
		vo.setLastUpdate(new Date());
		vo.setDescription(entity.description);
		if(entity.father != null) {
			vo.setFather(entity.father.id);
		}
		if(entity.children != null) {
			Set<Long> children = new HashSet<Long>();
			for(ObjectEntity child : entity.children) {
				children.add(child.id);
			}
			vo.setChildren(children);
		}
		if(entity.values != null) {
			Set<Long> values = new HashSet<Long>();
			for(ObjectFieldValue child : entity.values) {
				values.add(child.id);
			}
			vo.setValues(values);
		}
		return vo;
	}
	@Override
	public ObjectEntity updateEntity(ObjectEntityBean vo, ObjectEntity entity) {
		entity.name = vo.getName();
		entity.description = vo.getDescription();
		entity.lastUpdate = new Date();
		if(vo.getFather() != null) {
			Long fatherId = vo.getFather();
			System.err.println("YRO:"+fatherId);
			entity.father = find.byId(fatherId);
			System.err.println("YRO:"+find.byId(fatherId));
		}
		/**
		 * TODO
		 */
		//entity.father = vo.getFather();
		//entity.children = vo.getChildren();
		return entity;
	}
}
