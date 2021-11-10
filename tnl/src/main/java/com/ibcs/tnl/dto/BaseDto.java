package com.ibcs.tnl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
