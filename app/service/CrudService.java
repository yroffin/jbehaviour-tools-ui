package service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.db.ebean.Model.Finder;

import models.bean.DefaultBean;
import models.entity.DefaultModel;

public abstract class CrudService<E extends DefaultModel, V extends DefaultBean> implements ICrudService<E, V> {
	/**
	 * default finder for this entity/vo service
	 */
	public Finder<Long, E> find = null;
	public Class<V> bean = null;
	public Class<E> persistent = null;
	/**
	 * create a new instance of vo
	 * @param sessionClass
	 * @return
	 */
	public V createBean(Class<V> objectClass) {
        try {
			return objectClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}

    }
	public E createEntity(Class<E> objectClass) {
        try {
			return objectClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}

    }
	/**
	 * convert an entity to value object
	 * @param entity
	 * @param recursive
	 * @return
	 */
	protected V entityToVo(E entity, boolean recursive) {
		V vo = createBean(bean);
		vo.setId(entity.id);
		updateVo(vo, entity);
		return vo;
	}
	/**
	 * without recursive loading
	 * @param entity
	 * @return
	 */
	public V entityToVo(E entity) {
		return entityToVo(entity,false);
	}
	/**
	 * convert an value object to entity
	 * @param vo
	 * @return E
	 */
	public E voToEntity(V vo) {
		E entity = createEntity(persistent);
		updateEntity(vo, entity);
		return entity;
	}
	/**
	 * update this bean
	 * @param vo
	 * @param entity
	 * @return
	 */
	public abstract V updateVo(V vo, E entity);
	/**
	 * update this entity
	 * @param vo
	 * @param entity
	 * @return
	 */
	public abstract E updateEntity(V vo, E entity);
	/**
	 * retrieve finder
	 * @return
	 */
	protected Finder<Long, E> finder() {
		return find;
	}
	/**
	 * find all
	 * @param query
	 * @param params
	 * @return
	 */
	protected List<E> findEntity(String query, Map<String,Object> params) {
		return find.all();
	}
	/**
	 * delete it
	 * @param id
	 */
	protected void deleteEntity(Long id) {
		find.ref(id).delete();
	}
	/**
	 * find by id
	 * @param id
	 * @return
	 */
	protected E findEntityById(long id) {
		return find.byId(id);
	}
	/**
	 * find all entity
	 * @return
	 */
	protected List<E> findAllEntity() {
		return find.all();
	}
	@Override
	public V createElement(V vo) {
		E entity = voToEntity(vo);
		entity.save();
		/**
		 * retrieve created id
		 * and store it to bean
		 */
		vo.setId(entity.id);
		return vo;
	}
	@Override
	public V updateElement(V vo) {
		E entity = findEntityById(vo.getId());
		updateEntity(vo, entity);
		entity.save();
		return vo;
	}
	@Override
	public List<V> findAllElement(boolean recursive) {
		List<V> list = new ArrayList<V>();
		for(E entity : findAllEntity()) {
			list.add(entityToVo(entity, recursive));
		}
		return list;
	}
	@Override
	public List<V> findAllElement() {
		return findAllElement(false);
	}
	@Override
	public void deleteElement(long id) {
		deleteEntity(id);
	}
	@Override
	public List<V> findElement(boolean recursive, String query, Map<String,Object> params) {
		List<V> list = new ArrayList<V>();
		for(E entity : findEntity(query, params)) {
			list.add(entityToVo(entity, recursive));
		}
		return list;
	}
	@Override
	public List<V> findElementByName(String name, boolean recursive) {
		List<V> list = new ArrayList<V>();
		for(E entity : find.where().eq("name", name).findList()) {
			list.add(entityToVo(entity, recursive));
		}
		return list;
	}
	@Override
	public V findElementById(long id, boolean recursive) {
		return this.entityToVo(find.byId(id),recursive);
	}
	@Override
	public V findElementById(long id) {
		return findElementById(id, false);
	}
}
