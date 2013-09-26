package service;

import java.util.List;
import java.util.Map;

import models.bean.DefaultBean;
import play.db.ebean.Model;

public interface ICrudService<E extends Model, V extends DefaultBean> {
	/**
	 * create a new element
	 * 
	 * @param vo
	 * @return V
	 */
	public abstract V createElement(V vo);

	/**
	 * update an element
	 * 
	 * @param id
	 * @param vo
	 * @return V
	 */
	public V updateElement(V vo);

	/**
	 * delete an element
	 * 
	 * @param id
	 * @return
	 */
	public void deleteElement(long id);

	/**
	 * return all element
	 * 
	 * @return List<V>
	 */
	public List<V> findAllElement(boolean recursive);

	/**
	 * find entity
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	List<V> findElement(boolean recursive, String query,
			Map<String, Object> params);

	/**
	 * find element by name
	 * 
	 * @param name
	 * @return
	 */
	List<V> findElementByName(String name, boolean recursive);

	/**
	 * find element by id
	 * 
	 * @param name
	 * @return
	 */
	V findElementById(long id, boolean recursive);

	V findElementById(long id);

	List<V> findAllElement();
}
