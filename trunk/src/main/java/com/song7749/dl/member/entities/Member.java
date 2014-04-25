package com.song7749.dl.member.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;

import com.song7749.dl.base.Entities;

/**
 * <pre>
 * Class Name : Member.java
 * Description : 회원정보
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 21.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 21.
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id",name="UK_id")})
public class Member extends Entities {

	private static final long serialVersionUID = 2498909624760963269L;

	@Id
	@Column
	private String id;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	private String passwordQuestion;

	@Column
	private String passwordAnswer;

	@OneToMany(mappedBy="member",fetch=FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval=true)
	@BatchSize(size=10)
	private List<MemberAuth> memberAuthList = new ArrayList<MemberAuth>();

	public Member() {}

	public Member(String id) {
		this.id = id;
	}

	public Member(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public Member(String id,String passwordQuestion, String passwordAnswer) {
		this.id = id;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

	public Member(String id, String password, String email,
			String passwordQuestion, String passwordAnswer) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public String getPasswordAnswer() {
		return passwordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}

	public List<MemberAuth> getMemberAuthList() {
		return memberAuthList;
	}

	public void addMemberAuthList(MemberAuth memberAuth) {
		if(null==memberAuthList){
			memberAuthList = new ArrayList<MemberAuth>();
		}
		memberAuth.setMember(this);
		this.memberAuthList.add(memberAuth);
	}

	public void setMemberAuthList(List<MemberAuth> memberAuthList) {
		this.memberAuthList = memberAuthList;
	}
}