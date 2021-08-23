package jpabook.jpashop.MemberService;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)    // JUNIT 실행할때 spring과 엮어서 실행할 때
@SpringBootTest                 // Spring Boot를 띄워서 실행할때 꼭 있어야함, AUTOWIRED 에러남
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false)
    // transactional 이 기본적으로 rollback을 하기 때문에, insert query문이 찍히지 않음... (persist flush 되어야 insert문이 나감)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();

        // 같은 Transactional 안에서의 행위는 같은 엔티티 pk id는 동일한 영속성 컨텍스트로 관리가 됨
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        try{
            memberService.join(member2);            // 에러 발생해야 한다!!! (동일 name 중복 에러)
        }catch(IllegalStateException e){
            return;
        }

        //then
        fail("예외가 발생해야 한다.");             // fail이 떨어지면 assert fail 구문이 실행 된다
    }
}
