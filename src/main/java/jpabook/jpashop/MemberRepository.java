package jpabook.jpashop;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
// 내부에 @Component로 지정되어 있어, 스캔 시점에 자동으로 Spring bean에 등록
public class MemberRepository {

    // Spring boot가 entity manager를 자동으로 주입해주는 annotation
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
        // member를 반환하는게 아니라 id를 반환하는 이유 : command와 query를 분리해라...
        // return은 id만 보내주면 재조회 가능하므로 간단하게 return
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
