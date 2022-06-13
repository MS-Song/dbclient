package com.song7749.common.base;


/**
 * <pre>
 * Class Name : Profiles.java
 * Description : 프로파일 설정
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 8. 28.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 8. 28.
*/
public enum Profiles {
    /**
     * 테스트 코드
     */
    TEST(Constants.TEST),

	/**
     * 로컬
     */
    LOCAL(Constants.LOCAL),

    /**
     * 개발
     */
    DEVELOPMENT(Constants.DEVELOPMENT),

    /**
     * 스테이지 배포용
     */
    STAGE(Constants.STAGE),

    /**
     * 운영 배포용
     */
    PRODUCTION(Constants.PRODUCTION);


    private final String name;

    private Profiles(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public static class Constants {

        /** The Constant TEST-CODE. */
        public static final String TEST = "test";

    	/** The Constant LOCAL. */
        public static final String LOCAL = "local";

        /** The Constant DEVELOPMENT. */
        public static final String DEVELOPMENT = "dev";

        /** The Constant STAGE. */
        public static final String STAGE = "stage";

        /** The Constant PRODUCTION. */
        public static final String PRODUCTION = "production";
    }
}