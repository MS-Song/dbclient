package com.song7749.common.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <pre>
 * Class Name : Entities.java
 * Description : 모든 Entity 는 Entities 를 상송해야 한다.
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 28.		song7749	NEW
*
* </pre>
*
* @author song7749
* @since 2015. 4. 28.
*
*
*/
public abstract class Entities extends BaseObject{

	private static final long serialVersionUID = 8467296254940035942L;

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}