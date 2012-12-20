package service.business;

import java.util.Collection;

import service.business.impl.RepoManagerError;

public interface IRepoManagerSvc {

	/**
	 * initialize this repository manager
	 * @param userName
	 * @param password
	 * @param url 
	 * @param ignores 
	 * @throws RepoManagerError 
	 */
	void initialize(String userName, String password, String url, String working, Collection<String> ignores) throws RepoManagerError;
}
