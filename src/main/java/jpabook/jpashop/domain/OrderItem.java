package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;         // 주문당시 주문가격
    private int count;              // 주문당시 주문수량

    //== 생성 메서드 ==//
    /**
     * 주문아이템 생성
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);            // 주문아이템 생성 시, 해당 count는 item의 재고를 까줘야 함
        return orderItem;
    }


    //== 비지니스 로직 ==//
    /**
     * 주문아이템 취소
     */
    public void cancel(){
//        item.addStock(count); 이건 안되나???
        getItem().addStock(count);
    }

    //== 조회 로직 ==//
    /**
     * 주문아이템 총금액 조회
     */
    public int getTotalPrice(){
        return orderPrice * count;
    }
}
