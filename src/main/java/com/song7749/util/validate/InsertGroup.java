package com.song7749.util.validate;

import javax.validation.groups.Default;

/**
 * <pre>
 * Class Name : InsertGroup.java
 * Description : 인서트 Validate Group
 * Default 는 하이버네이트 Validate 에서의 기본 insert 검증과 같다. insert 에 추가해준다.
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 21.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 21.
 */

public interface InsertGroup extends BaseGroup, Default {}
