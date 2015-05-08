package com.song7749.dl.member.convert;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.entities.MemberAuth;
import com.song7749.dl.member.type.AuthType;
import com.song7749.dl.member.vo.MemberVO;

public class MemberConvertTest {

	@Test
	public void testConvertAddMemberDTO() throws Exception {
		// give
		AddMemberDTO dto = null;

		// when
		Member member = MemberConvert.convert(dto);

		// then
		assertThat(member, nullValue());

		// give
		dto = new AddMemberDTO("id"
				, "password"
				, "email"
				, "passwordQuestion"
				, "passwordAnswer"
				, AuthType.ADMIN);
		// when
		member = MemberConvert.convert(dto);

		// then
		assertThat(dto.getId(), is(member.getId()));
		assertThat(dto.getPassword(), is(member.getPassword()));
		assertThat(dto.getEmail(), is(member.getEmail()));
		assertThat(dto.getPasswordQuestion(), is(member.getPasswordQuestion()));
		assertThat(dto.getPasswordAnswer(), is(member.getPasswordAnswer()));
		assertThat(dto.getAuthType(), is(member.getMemberAuthList().get(0).getAuthType()));
	}

	@Test
	public void testConvertMember() throws Exception {
		// give
		Member member = null;

		// when
		MemberVO vo = MemberConvert.convert(member);

		// then
		assertThat(vo, nullValue());

		// give
		member = new Member("id"
				, "password"
				, "email"
				, "passwordQuestion"
				, "passwordAnswer");
		// when
		vo = MemberConvert.convert(member);

		// then
		assertThat(vo.getId(), is(member.getId()));
		assertThat(vo.getPassword(), is(member.getPassword()));
		assertThat(vo.getEmail(), is(member.getEmail()));
		assertThat(vo.getPasswordQuestion(), is(member.getPasswordQuestion()));
		assertThat(vo.getPasswordAnswer(), is(member.getPasswordAnswer()));
		assertThat(vo.getAuthType(), nullValue());


		// give
		member.addMemberAuthList(new MemberAuth(AuthType.ADMIN));

		// when
		vo = MemberConvert.convert(member);

		// then
		assertThat(vo.getAuthType(), is(member.getMemberAuthList().get(0).getAuthType()));

	}


	@Test
	public void testConvertMemberList() throws Exception {
		// give
		 List<Member> memberList = null;

		// when
		List<MemberVO> voList = MemberConvert.convert(memberList);

		// then
		assertThat(voList, nullValue());


		// give
		memberList = new ArrayList<Member>();
		memberList.add(new Member());
		// when
		voList = MemberConvert.convert(memberList);

		// then
		assertThat(1, is(voList.size()));
	}
}
