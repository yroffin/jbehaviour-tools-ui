package models.bean;

import java.util.Date;

/**
 * all beans have an id
 */
public class DefaultBean {
	/**
	 * members
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * private part
	 */
	protected long id;
	protected Date lastUpdate;
}
