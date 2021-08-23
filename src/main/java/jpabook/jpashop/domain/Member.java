package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    // 연관관계의 주인은 Order 이고, 주인이 아닌 쪽에서 mappedBy 주인쪽 컬럼을 지정해 준다
    // 변경 사항에 대해서 Member가 변해도 Order는 변경되지 않는다는 뜻 (읽기전용)

    private List<Order> orders = new ArrayList<>();
}
