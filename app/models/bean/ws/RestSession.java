package models.bean.ws;

import models.bean.core.LocalStoryBean;
import models.bean.jstree.JsTreeDataMeta;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestSession {
	@JsonProperty()
	private String rawScript;
	@JsonProperty()
	private int storyId;
	@JsonProperty()
	private String renderedScript;
	@JsonProperty()
	private String renderedStdout;
	@JsonProperty()
	private String renderedStderr;

	/**
	 * data part
	 */
	@JsonProperty()
	private LocalStoryBean LocalStoryBean;
	@JsonProperty()
	private JsTreeDataMeta metaclass;

	@Override
	public String toString() {
		return "RestSession [rawScript=" + rawScript + ", storyId=" + storyId
				+ ", renderedScript=" + renderedScript + ", renderedStdout="
				+ renderedStdout + ", renderedStderr=" + renderedStderr
				+ ", LocalStoryBean=" + LocalStoryBean + ", metaclass="
				+ metaclass + "]";
	}

	public int getStoryId() {
		return storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	public String getRawScript() {
		return rawScript;
	}

	public void setRawScript(String rawScript) {
		this.rawScript = rawScript;
	}

	public String getRenderedScript() {
		return renderedScript;
	}

	public void setRenderedScript(String renderedScript) {
		this.renderedScript = renderedScript;
	}

	public String getRenderedStdout() {
		return renderedStdout;
	}

	public void setRenderedStdout(String renderedStdout) {
		this.renderedStdout = renderedStdout;
	}

	public String getRenderedStderr() {
		return renderedStderr;
	}

	public void setRenderedStderr(String renderedStderr) {
		this.renderedStderr = renderedStderr;
	}

	
	public LocalStoryBean getLocalStoryBean() {
		return LocalStoryBean;
	}

	public void setLocalStoryBean(LocalStoryBean LocalStoryBean) {
		this.LocalStoryBean = LocalStoryBean;
	}

	public JsTreeDataMeta getMetaclass() {
		return metaclass;
	}

	public void setMetaclass(JsTreeDataMeta metaclass) {
		this.metaclass = metaclass;
	}
}
