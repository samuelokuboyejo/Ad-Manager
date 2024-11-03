package com.project.SamServices.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegisterRequest {
    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;



}
