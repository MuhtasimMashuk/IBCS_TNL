package com.ibcs.tnl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDto extends  BaseDto {

    private String photo;
    private String code;
    private String name;
    private String fatherName;
    private LocalDate dob;
    private LocalDate doj;
    private String nid;
    private String gender;
    private String email;
    private String mobileNo;
    private boolean active;
    private Long userId;
    private Long deptId;
    private Long desgId;
    private Long supervisorId;

}
