package com.song7749.base;


/**
 * <pre>
 * Class Name : Profiles.java
 * Description : 프로파일 변수
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
     * 로컬
     */
    LOCAL(Constants.LOCAL),

    /**
     * 개발
     */
    DEVELOPMENT(Constants.DEVELOPMENT),

    /**
     * Open Source 배포
     */
    OPEN(Constants.OPEN),

    /**
     * 마리아DB 용
     */
    MARIADB(Constants.MARIADB),

    /**
     * 운영
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

        /** The Constant LOCAL. */
        public static final String LOCAL = "local";

        /** The Constant DEVELOPMENT. */
        public static final String DEVELOPMENT = "dev";

        /** The Constant Open Source. */
        public static final String OPEN = "open";

        /** The Constant Mariadb. */
        public static final String MARIADB = "mariadb";

        /** The Constant PRODUCTION. */
        public static final String PRODUCTION = "production";
    }
}