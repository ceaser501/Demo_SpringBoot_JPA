package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable             // 어딘가에 포함될 수 있다는 JPA 내장타입 어노테이션
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // public, protected (좀더 안전) 둘다 사용 가능
    // JPA 리플렉션, 프록시와 같은 기술을 사용할 수 있도록 기본 생성자 타입을 제한함
    protected Address(){
    }

    // setter 를 두지 않고, 생성할 때 값을 초기화 하여 immutable하게 만드는 것이 좋음
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
