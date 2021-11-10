package com.ibcs.tnl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="TNL_LEAVE_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApp extends BaseEntity{


    @Column(name = "APPLICATION_DATE", nullable=false)
    private LocalDate appDate;

    @Column(name = "LEAVE_FROM_DATE", nullable=false)
    private LocalDate fromDate;

    @Column(name = "LEAVE_UPTO_DATE", nullable=false)
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private EntryType entry;
    @Column()
    private String reason;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Long employeeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "APPLICATION_LEAVE_TYPE_ID", nullable = false)
    private LeaveType  leaveTypeId;

    @Column(name = "ON_LEAVE_CONTACT_NO",nullable=false, length=13)
    private String onLeaveContactNo;

    @Column(name = "RESPONSIBLE_PERSON_ID", nullable = false,  length=13)
    private Long responsiblePersonId;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @Column(length=254)
    private String remark;




}
