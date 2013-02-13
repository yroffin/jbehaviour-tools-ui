package service.business.impl;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

public class RepoManagerError extends Exception {

	private static final long serialVersionUID = 4408699962637129776L;

	public RepoManagerError(SVNException e) {
		super(e);
	}

	public RepoManagerError(IOException e) {
		super(e);
	}

}
