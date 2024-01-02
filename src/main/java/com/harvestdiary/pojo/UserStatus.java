package com.harvestdiary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    private String UserId;
    private Boolean localLogin;
    private Boolean autoLogin;
    private Boolean rememberPw;
}
