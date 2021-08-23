package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")            // 실제 db 컬럼 명
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계
    @JoinColumn(name="member_id")       // join mapping column
    private Member member;

    // JPQL select o from order o; 로 가져오면 sql로 그대로 번역 -> SQL select * from order;
    // order 100개가 있는데, @ManyToOne은 기본이 FetchType fetch() default EAGER; 로 되어 있으므로 member를 즉시로딩 한다 n+1(order) 문제
    // 나는 order를 한번 날렸는데, 관련된 member 100건을 같이 조회하므로 100 (member) + 1 (order) 번 쿼리가 날라간다

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // persist(orderItemA)
    // persist(orderItemB)
    // persist(orderItemC)
    // persist(order);

    // 이렇게 할 것을 Cascade 옵션이 있으면, persist(order)만 해도 됌 -> 전파해서 orderItems도 자동으로 persist함
    // delete도 동일하게 적용

    // Delivery의 order와 일대일 관계이므로 @OneToOne을 써줬는데, 이때 FK는 어디에 두어도 상관없다
    // 하지만 access가 좀더 일어나는 Order에 선언 해 두는 것을 추천한다
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 자바8에서는 하이버네이트가 자동 지원. 시분초

    private OrderStatus status;         // 주문상태 enum 타입 [ORDER, CANCEL]
}
