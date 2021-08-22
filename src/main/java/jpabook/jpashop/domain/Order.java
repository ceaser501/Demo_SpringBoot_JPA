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

    @ManyToOne                          // 다대일 관계
    @JoinColumn(name="member_id")       // join mapping column
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // Delivery의 order와 일대일 관계이므로 @OneToOne을 써줬는데, 이때 FK는 어디에 두어도 상관없다
    // 하지만 access가 좀더 일어나는 Order에 선언 해 두는 것을 추천한다
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 자바8에서는 하이버네이트가 자동 지원. 시분초

    private OrderStatus status;         // 주문상태 enum 타입 [ORDER, CANCEL]

}
