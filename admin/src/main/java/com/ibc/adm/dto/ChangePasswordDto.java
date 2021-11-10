package com.ibc.adm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    private Long userId;

    private String oldPassword;
    private String newPassword;
    private String retypePassword;

}