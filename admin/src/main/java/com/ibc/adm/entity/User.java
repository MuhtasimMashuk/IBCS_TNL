package com.ibc.adm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "User")
//@MappedSuperclass
@Table(name = "ADM_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column()//255
    private String photo;

    @Column(unique = true, nullable = false, length = 6)
    private String code;

    @Column(nullable = false, length = 35)
    private String name;

    @Column(unique = true, length = 50)
    private String email;

    @Column(name = "MOBILE_NO", nullable = false, unique = true, length = 14)
    private String mobileNo;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Role roles;

}