package models.bean.datatable;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsDataTable {
	@JsonProperty()
	private String aaData[][];

	public JsDataTable(File baseDir, Collection<File> scan) {
		aaData = new String[scan.size()][4];
		int line = 0;
		for(File file : scan) {
			aaData[line][0] = file.getParent().replace(baseDir.getAbsolutePath(), "").replace("\\", "/");
			aaData[line][1] = file.getName();
			aaData[line][2] = file.length()+"";
			aaData[line][3] = (new Date(file.lastModified()))+"";
			line++;
		}
	}
}
