package com.song7749.dbclient.value;

import com.song7749.base.AbstractVo;

/**
 * <pre>
 * Class Name : DatabaseDdlVO.java
 * Description : Database Table Create VO
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 7. 5.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 7. 5.
*/
public class DatabaseDdlVo  extends AbstractVo {

	private static final long serialVersionUID = -6668465538753365479L;

	private String showCreateTable;

	public DatabaseDdlVo() {}

	public DatabaseDdlVo(String showCreateTable) {
		this.showCreateTable = showCreateTable;
	}

	public String getShowCreateTable() {
		return showCreateTable;
	}

	public void setShowCreateTable(String showCreateTable) {
		this.showCreateTable = showCreateTable;
	}
}