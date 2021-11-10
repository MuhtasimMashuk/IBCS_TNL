package com.ibcs.tnl.dto;

import com.ibcs.tnl.entity.EntryType;
import com.ibcs.tnl.entity.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LeaveAppDto extends BaseDto {

    private LocalDate appDate;

    private LocalDate fromDate;

    private LocalDate toDate;

    private EntryType entry;

    private String reason;

    private Long employeeId;

    private Long leaveTypeId; //object to long

    private String onLeaveContactNo;

    private Long responsiblePersonId;

    private boolean active;

    private String remarks;

 //  private ResponseDto responseDto;
}
