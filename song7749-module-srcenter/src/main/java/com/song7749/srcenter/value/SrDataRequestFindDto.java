package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.srcenter.domain.SrDataRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * <pre>
 * Class Name : SrDataRequestFindDto
 * Description : 리스트 검색 기능
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
public class SrDataRequestFindDto extends AbstractDto implements Specification<SrDataRequest> {


    @Override
    public Predicate toPredicate(Root<SrDataRequest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
