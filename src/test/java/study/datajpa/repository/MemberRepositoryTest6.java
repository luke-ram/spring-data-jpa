package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.MemberDto;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest6 {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 50));
        memberRepository.save(new Member("member6", 60));
        memberRepository.save(new Member("member7", 70));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        //map으로 변환
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUserName(), member.getAge()));

        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        System.out.println(totalElements);

        for (Member member : content) {
            System.out.println("member = " + member);
        }


    }

    @Test
    public void slice() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 50));
        memberRepository.save(new Member("member6", 60));
        memberRepository.save(new Member("member7", 70));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);

        List<Member> content = page.getContent();
        page.hasNext();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 50));
        memberRepository.save(new Member("member6", 60));
        memberRepository.save(new Member("member7", 70));

        int i = memberRepository.bulkAgePlus(20);


        Member member1 = memberRepository.findMemberByUserName("member1");
        System.out.println("age" + member1.getAge()); //10이 출력됨,

        em.clear(); // 꼭 벌크후에는 클리어 해줘야함
        Member member2 = memberRepository.findMemberByUserName("member1");
        System.out.println("age" + member1.getAge()); //11이 출력됨,


    }

    @Test
    public void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 10, teamB));

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println(member.getUserName());
            System.out.println(member.getTeam().getName());
        }


    }
}
