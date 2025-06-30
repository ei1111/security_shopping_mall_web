package com.web.member.domain;

import com.web.member.form.MemberRequest;
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


    public Address(MemberRequest request) {
        this.city = request.getCity();
        this.street = request.getStreet();
        this.zipcode = request.getZipcode();
    }
}
