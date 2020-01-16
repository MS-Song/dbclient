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
    DAILY("일간", new Long(24*60*60*1000)),
    HOURLY("시간당", new Long(60*60*1000)),
    MINUTELY("분당", new Long(60*1000)),
    MINUTELY5("5 분당", new Long(5*60*1000)),
    MINUTELY10("10 분당", new Long(10*60*1000));

    private String desc;
    private Long time;

    DownloadLimitType(String desc, Long time) {
        this.desc=desc;
        this.time=time;
    }

    public String getDesc() {
        return desc;
    }

    public Long getTime(){
        return time;
    }
}
