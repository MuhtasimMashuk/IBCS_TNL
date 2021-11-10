package com.ibcs.hr.dto;

import com.ibcs.hr.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptDto extends BaseEntityDto {

    private String code;

    private String name;

    private Long headOfId;

    private boolean active;

    //private ResponseDto responseDto;
}