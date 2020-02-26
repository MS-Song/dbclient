package com.song7749;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * Class Name : UnitTest.java
 * Description : repository, Service 등의 유닛 테스트는 반드시 UnitTest 를 상속 받아야 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 9. 5.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 9. 5.
*/

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=ModuleCommonApplicationTests.class)
@DataJpaTest
@ComponentScan(
	excludeFilters = @ComponentScan.Filter(
		type=FilterType.REGEX,pattern="com\\.song7749\\.web")
)
@Ignore
public class UnitTest {

}
