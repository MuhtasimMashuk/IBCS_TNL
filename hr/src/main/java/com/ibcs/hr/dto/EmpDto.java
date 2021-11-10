package com.ibcs.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDto extends BaseEntityDto {

    private String photo;

    private String code;

    private String name;

    private String fatherName;

	private LocalDate dob;

	private LocalDate doj;

    private String nid;

    private String gender;

    //@Email
    private String email;

    private String mobileNo;

    private boolean active;

    private Long userId;

    private Long deptId;

    private Long desgId;

    private Long supervisorId;

    //private ResponseDto responseDto;
}