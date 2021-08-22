package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

//    @Autowired MemberRepository memberRepository;
//    @Transactional
//    @Test
//    @Rollback(false)
//    public void testMember() throws Exception {
//        // test에서 transactional 어노테이션을 쓰면, 실제로 테스트가 끝나고 roll back을 수행한다
//        // @Rollback(false)를 주면 데이터를 디비에서 확인할 수 있다 roll back 안함
//
//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long savedId = memberRepository.save(member);      // Ctrl + Alt + V : 변수 뽑아오는 단축키
//        Member findMember = memberRepository.find(savedId);
//
//        //then (검증)
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        System.out.println("findMember == member : " + (findMember == member));
//        // 같은 transaction 안에서 저장, 조회하는 것이기 때문에 영속성 컨텍스트가 동일함
//        // 같은 영속성 컨텍스트 내에서는 id가 같으면 같은 엔티티로 식별하기 때문에, 1차캐시에서 꺼냄
//        // console select 쿼리가 안찍힘 -> 1차 캐시에서 꺼내오기 때문에 SELECT 쿼리를 날리지 않음
//    }
}
