package com.song7749.traffic.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import com.song7749.common.Entities;

/**
 * <pre>
 * Class Name : Category.java
 * Description : 트래픽 가드 카테고리
 * seft join 형태로 구성하려고 하나 잘 되지 않음 ㅎㅎ...
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 11. 30.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 11. 30.
 */
@Entity
@DynamicUpdate(true)
public class Category extends Entities {

	private static final long serialVersionUID = -6171427675558924867L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="category_id", nullable=false, updatable = false)
	private Long id;

	@Column(nullable = false, updatable = true)
	@NotBlank
	private String name;

	@Column(nullable = true, updatable = true)
	private String desc;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name="parent_category_id")
	private Category parentCategory;

	@OneToMany(mappedBy="parentCategory", fetch= FetchType.LAZY)
	private List<Category> childCategories = new ArrayList<Category>();;

	public Category() {
	}

	public Category(Long id) {
		this.id = id;
	}

	/**
	 * @param name
	 * @param desc
	 */
	public Category(@NotBlank String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
		if(null!=this.parentCategory) {
			this.parentCategory.childCategories.add(this);
		}
	}

	public List<Category> getChildCategories() {
		return childCategories;
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
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}