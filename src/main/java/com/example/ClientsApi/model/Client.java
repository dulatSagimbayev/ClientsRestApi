package com.example.ClientsApi.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String secondName;

    public Client(){
    }

    public Client(String name, String lastName, String secondName) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.secondName = secondName;
    }

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JoinColumn(name = "cn_id",referencedColumnName = "id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    List<PhoneNumber> numbers=new ArrayList<>();

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<PhoneNumber> numbers) {
        this.numbers = numbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
