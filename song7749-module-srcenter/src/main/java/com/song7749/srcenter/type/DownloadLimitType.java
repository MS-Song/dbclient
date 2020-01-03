package com.song7749.srcenter.type;

/**
 * <pre>
 * Class Name : DownloadLimitType
 * Description : 다운로드 제한 타입 (월/주/일/시간)
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  12/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 12/11/2019
 */
public enum DownloadLimitType {
    MONTHLY("월간"),
    WEEKLY("주간"),
    DAILY("일간"),
    HOURLY("시간당"),
    MINUTELY("분당"),
    MINUTELY5("5 분당"),
    MINUTELY10("10 분당");

    private String desc;

    DownloadLimitType(String desc) {
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }
}
