package com.song7749.dbclient.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import springfox.documentation.annotations.ApiIgnore;
/**
 * <pre>
 * Class Name : IndexController.java
 * Description : / 로 들어오는 request 에 대해서 index.html 로 돌려 보낸다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 4. 10.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 4. 10.
*/
@ApiIgnore
@Controller
public class IndexController {
	@GetMapping("/")
	public RedirectView method() {
        return new RedirectView("/index.html");
	}
}
