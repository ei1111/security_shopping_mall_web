package com.web.member.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {
    @Comment("도시")
    private String city;

    @Comment("도로")
    private String street;

    @Comment("우편번호")
    private String zipcode;


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
