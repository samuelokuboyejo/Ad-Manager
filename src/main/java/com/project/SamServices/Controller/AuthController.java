package com.project.SamServices.Controller;

import com.project.SamServices.auth.service.auth.AuthService;
import com.project.SamServices.auth.service.auth.JwtService;
import com.project.SamServices.auth.service.auth.RefreshTokenService;
import com.project.SamServices.auth.utils.*;
import com.project.SamServices.entities.RefreshToken;
import com.project.SamServices.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/client/register")
    public ResponseEntity<AuthResponse> registerClient(@RequestBody ClientRegisterRequest clientRegisterRequest){
        return ResponseEntity.ok(authService.register(clientRegisterRequest));
    }

    @PostMapping("/company/register")
    public ResponseEntity<AuthResponse> registerCompany(@RequestBody CompanyRegisterRequest companyRegisterRequest){
        return ResponseEntity.ok(authService.registerCompany(companyRegisterRequest));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/company/login")
    public ResponseEntity<AuthResponse> loginCompany(@RequestBody CompanyLoginRequest companyLoginRequest){
        return ResponseEntity.ok(authService.loginCompany(companyLoginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}
