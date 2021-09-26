package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    // 조회조건을 넣고 전체 조회하는 로직 들어갈 예정
    public List<Order> findAll(OrderSearch orderSearch) {

        // 동적SQL이 아닌 명확한 파라미터가 들어오는 것을 알고 있을 때 -> 정적SQL
        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status " +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)        // 최대 1000건
                .getResultList();


    }

// 동적SQL로 처리 할때
// 1. if문으로 있는지 없는지 판단해서 where을 붙일지 and를 붙일지 결정해서 JPQL로 처리하는 방법 (완전 비추)
// 2. JPA Criteria 를 사용해서 JPA 내부적으로 정의한 형태로 동적SQL 처리하는 방법 (완전 비추)
// 3. QueryDSL로 해결하는 방법 (추천)
//
//    public List<Order> findAll(OrderSearch orderSearch){
//
//        QOrder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return query
//                .select(order)
//                .from(order)
//                .join(order.member, member)
//                .where(statusEq(orderSearch.getOrderStatus()),
//                        nameLike(orderSearch.getMemberName()))
//                .limit(1000)
//                .fetch();
//    }
//
//    private BooleanExpression statusEq(OrderSearch orderSearch){
//        if(statusCond == null){
//            return null;
//        }
//
//        return order.status.eq(statusCond);
//    }
//
//    private BooleanExpression nameLike(String nameCond){
//        if(!StringUtils.hasText(nameCond)){
//            return null;
//        }
//
//        return order.name.like(nameCond);
//    }
}
