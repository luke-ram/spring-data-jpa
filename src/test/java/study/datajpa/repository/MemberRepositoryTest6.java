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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest6 {

    @Autowired
    MemberRepository memberRepository;

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
}
