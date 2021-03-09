package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member save = memberRepository.save(member);

        Member byId = memberRepository.findById(save.getId()).get();
        Assertions.assertThat(member.getUserName()).isEqualTo(byId.getUserName());
        Assertions.assertThat(member.getId()).isEqualTo(byId.getId());
    }

    @Test
    public void findByNames() {
        Member member1 = new Member("memberA");
        Member member2 = new Member("memberB");
        memberRepository.save(member1);
        memberRepository.save(member2);

        memberRepository.findByNames(Arrays.asList("memberA", "memberB"));


    }

    @Test
    public void returnType() {

        Member member1 = new Member("memberA");
        Member member2 = new Member("memberB");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> memberA = memberRepository.findListByUserName("memberA");
        System.out.println(memberA.get(0).getUserName());

        Optional<Member> memberA1 = memberRepository.findOptionalByUserName("memberA");
        System.out.println("memberA1.get().getUserName() = " + memberA1.get().getUserName());

        Member memberA2 = memberRepository.findMemberByUserName("memberA");
        System.out.println(memberA2.getUserName());


    }
}
