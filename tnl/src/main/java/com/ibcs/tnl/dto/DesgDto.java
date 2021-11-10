package com.ibcs.tnl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DesgDto extends BaseDto {

    private String name;

    private boolean active;
}