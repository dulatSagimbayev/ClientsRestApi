package com.example.ClientsApi.model;

import com.example.ClientsApi.validation.RightType;

import javax.persistence.*;

@Entity
@Table(name = "numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RightType
    private String type;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    private String phoneNumber;

    public PhoneNumber(){}

    public PhoneNumber(String type, String phoneNumber) {
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNnumber) {
        this.phoneNumber = phoneNnumber;
    }
}
