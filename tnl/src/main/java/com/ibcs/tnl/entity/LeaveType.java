package com.ibcs.tnl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="TNL_LEAVE_APP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveType extends BaseEntity {

    @Column( nullable=false, length=35)
    private String name;


    @Column(name="ALLOWED_NO_LEAVE_Monthly", nullable=false)
    private Long allowedLeaveNoMonthly;

    @Column(name="ALLOWED_NO_LEAVE_Monthly_Yearly", nullable=false)
    private Long allowedLeaveNoYearly;


    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;



}
