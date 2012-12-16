package service.application;


import java.util.List;

import models.bean.core.ObjectEntityBean;
import models.bean.core.ObjectFieldBean;
import service.business.ObjectEntitySvc;
import service.business.ObjectFieldSvc;

public class ObjectEntityApp {
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
	/**
	 * create a new ObjectEntity
	 * @param ObjectEntityBean
	 */
	public void create(ObjectEntityBean ObjectEntityBean) {
		objectEntitySvc.createElement(ObjectEntityBean);
	}
	/**
	 * update
	 * @param ObjectEntityBean
	 */
	public void update(ObjectEntityBean ObjectEntityBean) {
		objectEntitySvc.updateElement(ObjectEntityBean);
	}
	/**
	 * find by its id
	 * @param id
	 * @param b
	 * @return
	 */
	public ObjectEntityBean ObjectEntityById(Long id) {
		return objectEntitySvc.findElementById(id, false);
	}
	/**
	 * find all element
	 * @return
	 */
	public List<ObjectEntityBean> ObjectEntitys() {
		return objectEntitySvc.findAllElement();
	}
	/**
	 * insert new entity
	 * @param id
	 */
	public void insertEntity(Long id) {
	}
	/**
	 * create a new ObjectEntity
	 * @param ObjectEntityBean
	 */
	public void create(ObjectFieldBean bean) {
		objectFieldSvc.createElement(bean);
	}
	/**
	 * update
	 * @param ObjectEntityBean
	 */
	public void update(ObjectFieldBean bean) {
		objectFieldSvc.updateElement(bean);
	}
	/**
	 * find by its id
	 * @param id
	 * @param b
	 * @return
	 */
	public ObjectFieldBean ObjectFieldById(Long id) {
		return objectFieldSvc.findElementById(id, false);
	}
	/**
	 * find all element
	 * @return
	 */
	public List<ObjectFieldBean> ObjectFields() {
		return objectFieldSvc.findAllElement();
	}
	/**
	 * insert new entity
	 * @param id
	 */
	public void insertField(Long id) {
	}
}
