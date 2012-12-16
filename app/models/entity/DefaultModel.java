package models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class DefaultModel extends Model {

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 2174455648625762926L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@Column(name = "lastUpdate", nullable = false)
	public Date lastUpdate;
}
