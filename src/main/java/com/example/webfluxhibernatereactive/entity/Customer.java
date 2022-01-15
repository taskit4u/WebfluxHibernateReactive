package com.example.webfluxhibernatereactive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @Pattern(regexp = "male|female", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String sex;

    @Min(value = 18)
    @Max(value = 70)
    private Integer age;

    public Customer() {
    }

    public Customer(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }
}
