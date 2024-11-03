package com.project.SamServices.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String gender;

    private long phoneNumber;

    private String username;






}
