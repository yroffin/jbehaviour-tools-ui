package models.bean.ws;

import models.bean.core.LocalStoryBean;
import models.bean.jstree.JsTreeDataMeta;

import org.codehaus.jackson.annotate.JsonProperty;

public class DefaultMessage {
	@JsonProperty()
	private Long id;
	@JsonProperty()
	private String hash;
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
	@JsonProperty()
	private String path;
	@JsonProperty()
	private String name;
	@JsonProperty()
	private String filesize;

	/**
	 * data part
	 */
	@JsonProperty()
	private LocalStoryBean LocalStoryBean;
	@JsonProperty()
	private JsTreeDataMeta metaclass;

	@Override
	public String toString() {
		return "DefaultMessage [id=" + id + ", hash=" + hash + ", rawScript="
				+ rawScript + ", storyId=" + storyId + ", renderedScript="
				+ renderedScript + ", renderedStdout=" + renderedStdout
				+ ", renderedStderr=" + renderedStderr + ", path=" + path
				+ ", name=" + name + ", filesize=" + filesize
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

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
