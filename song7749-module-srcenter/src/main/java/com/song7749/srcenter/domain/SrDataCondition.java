package com.song7749.srcenter.domain;

import com.song7749.common.Entities;
import com.song7749.common.YN;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.value.SrDataConditionVo;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class Name : SrDataCondition
 * Description : Sr Data Request 내에 SQL 조건을 기술한다.
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

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
// 유니크키 셋팅 ID+KEY 로 조합
public class SrDataCondition extends Entities {

    private static final long serialVersionUID = 3497980567736745915L;

    @Id
    @Column(name="sr_data_condition_id", nullable=false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 2000)
    @Column(nullable = false)
    private String whereSql;

    @NotBlank
    @Length(max = 200)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Length(max = 200)
    @Column(nullable = false)
    private String key;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataType type;

    @Length(max = 200)
    @Column(nullable = false)
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YN required;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="sr_data_request_id",nullable = false, insertable = true, updatable = true)
    private SrDataRequest srDataRequest;

    /**
     * 기본 생성자
     */
    public SrDataCondition() {   }

    public SrDataCondition(@NotBlank @Length(max = 2000) String whereSql, @NotBlank @Length(max = 200) String name, @NotBlank @Length(max = 200) String key, @NotBlank DataType type, @Length(max = 200) String value, YN required, SrDataRequest srDataRequest) {
        this.whereSql = whereSql;
        this.name = name;
        this.key = key;
        this.type = type;
        this.value = value;
        this.required = required;
        this.srDataRequest = srDataRequest;
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

    public SrDataRequest getSrDataRequest() {
        return srDataRequest;
    }

    public void setSrDataRequest(SrDataRequest srDataRequest) {
        this.srDataRequest = srDataRequest;
    }

    public SrDataConditionVo getSrDataConditionVo(ModelMapper mapper){
        return mapper.map(this, SrDataConditionVo.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SrDataCondition other = (SrDataCondition) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}