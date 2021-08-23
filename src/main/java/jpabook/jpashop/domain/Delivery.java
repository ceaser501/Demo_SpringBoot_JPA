package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)   // default 가 EnumType.ORDINAL인데 이건 숫자로 들어감
    // 이거 문제는 중간에 항목이 추가되어 READY, XXXX, COMP 가 되면 숫자가 밀려서 꼬일 수 있음
    // 꼭 STRING 으로 쓸 것!
    private DeliveryStatus status;  // READY, COMP
}
