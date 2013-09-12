package service.application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbehaviour.exception.JBehaviourParsingError;
import org.jbehaviour.exception.JBehaviourRuntimeError;
import org.jbehaviour.impl.JBehaviourLauncher;
import org.jbehaviour.xref.IBehaviourXRef;

import play.Configuration;
import play.Logger;
import service.business.IRepoManagerSvc;
import service.business.LocalStorySvc;
import service.business.impl.RepoManagerError;

import models.bean.core.LocalStoryBean;

public class LocalStoryApp {
	/**
	 * spring injection
	 */
	private LocalStorySvc localStorySvc;
	public void setLocalStorySvc(LocalStorySvc LocalStorySvc) {
		this.localStorySvc = LocalStorySvc;
	}
	private IRepoManagerSvc localRepoManagerSvc;
	public void setLocalRepoManagerSvc(IRepoManagerSvc localRepoManagerSvc) {
		this.localRepoManagerSvc = localRepoManagerSvc;
	}

	private String working;
	private Collection<String> ignores;
	private File workingDir;

	/**
	 * initialize application
	 * @param configuration
	 */
	public boolean initialize(Configuration configuration) {
		ignores = new ArrayList<String>();
		Logger.info("Initialize application scmuser : " + configuration.getString("scm.user"));
		Logger.info("Initialize application scmurl  : " + configuration.getString("scm.url"));
		try {
			localRepoManagerSvc.initialize(
					configuration.getString("scm.user"),
					configuration.getString("scm.password"),
					configuration.getString("scm.url"),
					configuration.getString("scm.tmp"),
					ignores);
		} catch (RepoManagerError e) {
			e.printStackTrace();
			return false;
		}
		/**
		 * working directory
		 */
		working = configuration.getString("scm.tmp");
		workingDir = new File(working);
		return true;
	}

	/**
	 * scan working files
	 * @return
	 */
	public Collection<File> scan(){
		Logger.info("finding working files ...");
		Collection<File> all = new ArrayList<File>();
		findFiles(new File(working), all, ignores);
		return all;
	}

	/**
	 * find local files
	 * @param file
	 * @param all
	 */
	private void findFiles(File file, Collection<File> all, Collection<String> ignores) {
	    File[] children = file.listFiles();
	    if (children != null) {
            boolean ignore = false;
            for(String item : ignores) {
	            if(file.getName().compareTo(item)==0) ignore = true;
            }
            if(!ignore) {
		        for (File child : children) {
		            if(child.isFile()) all.add(child);
		            findFiles(child, all, ignores);
		        }
            }
	    }
	}


	/**
	 * create a new LocalStory
	 * @param LocalStoryBean
	 */
	public void create(LocalStoryBean LocalStoryBean) {
		localStorySvc.createElement(LocalStoryBean);
	}
	/**
	 * update
	 * @param LocalStoryBean
	 */
	public void update(LocalStoryBean LocalStoryBean) {
		localStorySvc.updateElement(LocalStoryBean);
	}
	/**
	 * find by its id
	 * @param id
	 * @param b
	 * @return
	 */
	public LocalStoryBean LocalStoryById(Long id) {
		return localStorySvc.findElementById(id, false);
	}
	/**
	 * find all element
	 * @return
	 */
	public List<LocalStoryBean> LocalStorys() {
		return localStorySvc.findAllElement();
	}
	/**
	 * insert a new step
	 * @param id
	 * @param idStep
	 * @param idInsert
	 * @param stepType
	 */
	public void insert(Long id) {
	}
	/**
	 * execute this story
	 * @param id
	 * @return
	 */
	public String executeById(Long id) {
		/**
		 * TODO
		 * execute this story
		 */
        return "";
	}
	/**
	 * execute this story
	 * @param id
	 * @return
	 */
	public IBehaviourXRef execute(String story) {
		File localFile = null;
		try {
			localFile = File.createTempFile("play#", ".story");
			localFile.deleteOnExit();
		} catch (IOException e) {
			/**
			 * TODO
			 * better exception handle ?
			 */
			e.printStackTrace();
		}
		
		Logger.info("Running : " + localFile.getAbsolutePath());
		try {
			FileWriter writer = new FileWriter(localFile);
			writer.write(story);
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			JBehaviourLauncher launcher = new JBehaviourLauncher();
			launcher.registerAndExecute(localFile);
			return launcher.getEnv().getXRef();
		} catch (JBehaviourParsingError e) {
			e.printStackTrace();
		} catch (JBehaviourRuntimeError e) {
			e.printStackTrace();
		}
		return null;
	}

	public File getBaseDir() {
		return workingDir;
	}

	/**
	 * find file
	 * @param path
	 * @param name
	 * @return 
	 * @throws IOException 
	 */
	public String findFile(String path, String name) throws IOException {
		File content = new File(working + path + '/' + name);
		FileReader fw = new FileReader(content);
		char[] cbuf = new char[(int) content.length()];
		fw.read(cbuf);
		fw.close();
		return new String(cbuf);
	}
}
