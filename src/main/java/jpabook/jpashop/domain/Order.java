package jpabook.jpashop.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 자바8에서는 하이버네이트가 자동 지원. 시분초

    private OrderStatus status;         // 주문상태 enum 타입 [ORDER, CANCEL]


    //== 연관관계 편의 메서드 ==//
    // 양방향의 경우 원자적으로 하나의 set으로 만들어 버림
    // 어느쪽에 만드는지는 컨트롤이 많은 곳에 만들면 됌
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    // 원래는 member가 추가되거나, order가 추가되면 아래와 같이 main에서 양방향 모두에게 set하고 add 해야 함
    // 편의 메서드를 Order에 해놓으면, main에서는 order.setMember(member); 한줄만으로 양쪽 모두에게 영향을 줄 수 있다다
//   public static void main(String[] args){
//        Member member = new Member();
//        Order order = new Order();
//
//        order.setMember(member);
//        member.getOrders().add(order);
//    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    /**
     * 주문 생성
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... OrderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : OrderItems){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //== 비지니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다");
        }

        this.setStatus(OrderStatus.CANCEL);

        // order가 cancel이 되면, order에 묶인 orderItems에도 각각 cancel 날려줘야 함
        for(OrderItem orderItem : this.orderItems){
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for(OrderItem orderItem : this.orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//
//        return totalPrice;

        // java8
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
