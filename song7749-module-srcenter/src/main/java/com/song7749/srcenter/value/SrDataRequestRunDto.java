package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestRunDto
 * Description : 요청된 SR Data 를 실행 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  20/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 20/11/2019
 */
@ApiModel("SR Data Request 실행 조건")
public class SrDataRequestRunDto extends AbstractDto {
    private static final long serialVersionUID = -948753918456264455L;

    @NotNull
    @ApiModelProperty(required = true, position = 1, value="SrDataRequestID",  dataType = "INT")
    private Long id;

    @ApiModelProperty(required=false, position=2, hidden = true, value="실행자")
    private Long runMemberId;

    @ApiModelProperty(required=false, position=2, hidden = true, value="실행자 IP ")
    private String remoteAddress;

    @ApiModelProperty(value="최대 조회 개수", hidden = true)
    private Long limit = 100L;

    @ApiModelProperty(value="조회 시작 Offset", hidden = true)
    private Long offset = 0L;

    @ApiModelProperty(value="Result 수를 지정할 것인가?", hidden = true)
    private boolean useLimit = true;

    @ApiModelProperty(required=false, position=3, value="엑셀다운로드")
    private boolean excel;

    @ApiModelProperty(value="DEBUG MODE", hidden = true)
    private boolean debug = false;

    public SrDataRequestRunDto() {}

    public SrDataRequestRunDto(@NotNull Long id, Long runMemberId, String remoteAddress, Long limit, Long offset, boolean useLimit, boolean excel, boolean debug) {
        this.id = id;
        this.runMemberId = runMemberId;
        this.remoteAddress = remoteAddress;
        this.limit = limit;
        this.offset = offset;
        this.useLimit = useLimit;
        this.excel = excel;
        this.debug = debug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunMemberId() {
        return runMemberId;
    }

    public void setRunMemberId(Long runMemberId) {
        this.runMemberId = runMemberId;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public boolean isUseLimit() {
        return useLimit;
    }

    public void setUseLimit(boolean useLimit) {
        this.useLimit = useLimit;
    }

    public boolean isExcel() {
        return excel;
    }

    public void setExcel(boolean excel) {
        this.excel = excel;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}