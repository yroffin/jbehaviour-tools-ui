package models.bean.datatable;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsDataTable {
	@JsonProperty()
	private String aaData[][];
	public JsDataTable() {
		aaData = new String[100][5];
		for(int line = 0;line < 100;line ++) {
			for(int row = 0;row < 5;row ++) {
				aaData[line][row] = "a";
			}
		}
	}
}
