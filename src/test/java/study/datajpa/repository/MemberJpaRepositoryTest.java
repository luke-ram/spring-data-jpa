package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member userA = new Member("userA");
        memberJpaRepository.save(userA);

        Member findMember = memberJpaRepository.find(userA.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(userA.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(userA.getUserName());
    }
}