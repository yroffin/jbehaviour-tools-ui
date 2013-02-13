package service.business.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import play.Logger;

import service.business.IRepoManagerSvc;

public class RepoManagerSvcSvn implements IRepoManagerSvc {

	SVNClientManager clientManager = null;
	SVNURL url = null;
	File working = null;

	@Override
	public void initialize(String _userName, String _password, String _url, String _working, Collection<String> ignores) throws RepoManagerError {
		/**
		 * initialize with default option this svn manager
		 */
		DefaultSVNOptions myOptions = new DefaultSVNOptions();
		boolean storeAuth = myOptions == null ? true : myOptions.isAuthStorageEnabled();
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(null, _userName, _password, storeAuth);
		clientManager = SVNClientManager.newInstance(myOptions, authManager);
		ignores.add(".svn");

		/**
		 * now initialize SVN url
		 * with SVNURL
		 */
		try {
			url = SVNURL.parseURIEncoded(_url);
		} catch (SVNException e) {
			throw new RepoManagerError(e);
		}
		
		/**
		 * checkout a fresh working path
		 */
		try {
			if(_working == null) {
				working = File.createTempFile(".tmp", ".svn");
				working.delete();
			} else {
				working = new File(_working);
				if(working.exists()) {
					/**
					 * TODO
					 * destroy all data ...
					 */
				}
			}
		} catch (IOException e) {
			throw new RepoManagerError(e);
		}
		working.mkdirs();
		
		/**
		 * checkout it
		 */
		SVNUpdateClient updateClient = clientManager.getUpdateClient( );
		updateClient.setIgnoreExternals( false );
		try {
			updateClient.doCheckout( this.url , working , SVNRevision.HEAD , SVNRevision.HEAD , SVNDepth.INFINITY, true);
		} catch (SVNException e) {
			throw new RepoManagerError(e);
		}
		Logger.info("Working area : " + working);
	}
}
