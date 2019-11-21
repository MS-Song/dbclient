package com.song7749.srcenter.value;

import com.song7749.common.AbstractVo;
import com.song7749.common.YN;
import com.song7749.srcenter.type.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class Name : SrDataConditionVo
 * Description : 검색 조건 Value 객체
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

@ApiModel("SR Data Request 검색 조건")
public class SrDataConditionVo extends AbstractVo {
    private static final long serialVersionUID = 5819461541350812217L;

    @ApiModelProperty(dataType = "Long", value="검색 조건 ID")
    private Long id;

    @ApiModelProperty(dataType = "String", value="where")
    private String whereSql;

    @ApiModelProperty(dataType = "String", value="검색 조건 명")
    private String name;

    @ApiModelProperty(dataType = "String", value="검색 조건 변수 명")
    private String key;

    @ApiModelProperty(dataType = "DataType", value="데이터 타입")
    private DataType type;

    @ApiModelProperty(dataType = "String", value="검색 조건 기본 값")
    private String value;

    @ApiModelProperty(dataType = "String", value="필수값 여부")
    private YN required;

    public SrDataConditionVo() {
    }

    public SrDataConditionVo(Long id, String whereSql, String name, String key, DataType type, String value, YN required) {
        this.id = id;
        this.whereSql = whereSql;
        this.name = name;
        this.key = key;
        this.type = type;
        this.value = value;
        this.required = required;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public YN getRequired() {
        return required;
    }

    public void setRequired(YN required) {
        this.required = required;
    }
}