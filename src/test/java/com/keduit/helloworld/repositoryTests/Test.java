package com.keduit.helloworld.repositoryTests;

import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.keduit.helloworld.entity.MemberRole;

@SpringBootTest
public class Test {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    /** 관리자 아이디 만들기 */
    @org.junit.jupiter.api.Test
    public void insertAdmin() {
        Member member = Member.builder().id("admin")
                .name("관리자")
                .pw(passwordEncoder.encode("1111"))
                .nickname("관리자")
                .email("admin@abc.com")
                .purview(false)
                .build();
        member.addMemberRole(MemberRole.ADMIN);
        memberRepository.save(member);
    }
}
