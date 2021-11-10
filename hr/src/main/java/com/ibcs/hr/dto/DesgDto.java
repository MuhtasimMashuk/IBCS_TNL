package com.ibcs.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesgDto extends BaseEntityDto {

    private String name;

    private boolean active;

  //  private ResponseDto responseDto;
}