package com.song7749.dl.base;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * <pre>
 * Class Name : AbstractVo.java
 * Description : 모든 VO 는 AbstractVO 를 구현해야 한다.
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 4. 28.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 4. 28.
 */
@ApiModel("BASE VO")
public abstract class AbstractVo extends BaseObject implements Vo {
	private static final long serialVersionUID = -8005676524782732033L;
}