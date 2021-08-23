package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

// 컴포넌트 스캔의 대상이 되어, spring bean 으로 등록
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // spring -> entity manager를 만들어 injection 주입
//    @PersistenceContext
//    @Autowired
    private final EntityManager em;

//    public MemberRepository(EntityManager em){
//        this.em = em;
//    }

    // 팩토리를 직접 주입받을 수 있음
//    @PersistenceUnit
//    private EntityManagerFactory ef;

    public void save(Member member){
        em.persist(member);     // db 에 insert 쿼리가 날라가는 것
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    // JPQL : 엔티티를 대상으로 조회 하는 방식 (하단 방식)
    // SQL : 테이블을 대상으로 조회하는 방식
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 파라미터 바인딩
    public List<Member> findByNames(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
