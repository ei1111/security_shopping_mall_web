package com.web.member.domain;

import com.web.member.form.MemberRequest;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {

    private String city;

    private String street;

    private String zipcode;


    public Address(MemberRequest request) {
        this.city = request.getCity();
        this.street = request.getStreet();
        this.zipcode = request.getZipcode();
    }
}
