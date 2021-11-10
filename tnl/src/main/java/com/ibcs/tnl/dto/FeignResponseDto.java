package com.ibcs.tnl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeignResponseDto {


    private LeaveAppDto leaveAppDto;
    private EmpDto empDto;
    private ResponseDto responseDto;
}
