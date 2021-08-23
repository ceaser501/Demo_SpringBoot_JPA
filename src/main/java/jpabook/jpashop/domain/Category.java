package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "categoriy_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),            // 중간테이블의 category_id
        inverseJoinColumns = @JoinColumn(name = "item_id")          // 중간테이블의 item_id
    )
    // 중간테이블 매핑을 해줘야 함
    // 객체는 컬렉션 관계를 양쪽에 가져서 상관없는데, 관계형 DB는 그런게 없어서 중간 테이블이 각각 FK를 갖고 연결해줘야 함
    private List<Item> items = new ArrayList<>();

    // == 부모자식 관계 계층구조 만들기 (부모/자식) ==
    @ManyToOne
    @JoinColumn(name = "parent")
    private Category parent;

    // 동일 엔티티내에서 다른 엔티티를 여러개 갖는 것 처럼 mapped by 하면 됨
    // 자식 엔티티는 여러개 가질 수 있으므로 OneToMany
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    //== 연관관계 편의 메서드 ==/
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
