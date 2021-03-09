package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Arrays;
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
        Member member2 = new Member("memberA");
        memberRepository.save(member1);
        memberRepository.save(member2);

        memberRepository.findByNames(Arrays.asList("memberA", "memberB"));


    }
}
