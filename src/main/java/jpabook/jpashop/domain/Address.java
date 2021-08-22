package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable             // 어딘가에 포함될 수 있다는 JPA 내장타입 어노테이션
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
