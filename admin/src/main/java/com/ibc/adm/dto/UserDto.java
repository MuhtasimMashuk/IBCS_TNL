package com.ibc.adm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {


    private String photo;

    private String code;

    private String name;

    private String email;

    private String mobileNo;
    
    private String password;

    private boolean active;

     private String roles;


   // private ResponseDto responseDto;

   
}