package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class Name : SrDataRequestRemoveDto
 * Description : 삭제 DTO 객체
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  13/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 13/11/2019
 */
@ApiModel("SR DATA REQUEST 삭제")
public class SrDataRequestRemoveDto extends AbstractDto {
    private static final long serialVersionUID = 2852613154038983882L;

    @NotNull
    @ApiModelProperty(value="ID", position = 1, dataType = "INT")
    private Long id;

    @NotNull
    @ApiModelProperty(value="삭제자 회원 ID", position = 2, dataType = "INT")
    private Long removeMemberId;

    public SrDataRequestRemoveDto() {
    }

    public SrDataRequestRemoveDto(@NotNull Long id, @NotNull Long removeMemberId) {
        this.id = id;
        this.removeMemberId = removeMemberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoveMemberId() {
        return removeMemberId;
    }

    public void setRemoveMemberId(Long removeMemberId) {
        this.removeMemberId = removeMemberId;
    }
}