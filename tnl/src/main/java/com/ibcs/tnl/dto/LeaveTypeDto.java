package com.ibcs.tnl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeDto extends BaseDto{

    private String name;


    private Long allowedLeaveNoMonthly;


    private Long allowedLeaveNoYearly;


    private boolean active;

   // private ResponseDto responseDto;

}
