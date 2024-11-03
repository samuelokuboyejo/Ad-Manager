package com.project.SamServices.auth.service.auth;

import com.project.SamServices.auth.utils.*;
import com.project.SamServices.entities.Company;
import com.project.SamServices.entities.User;
import com.project.SamServices.entities.UserRole;
import com.project.SamServices.repository.CompanyRepository;
import com.project.SamServices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(ClientRegisterRequest clientRegisterRequest){
        var user = User.builder()
                .firstName(clientRegisterRequest.getFirstName())
                .lastName(clientRegisterRequest.getLastName())
                .gender(clientRegisterRequest.getGender())
                .phoneNumber(clientRegisterRequest.getPhoneNumber())
                .email(clientRegisterRequest.getEmail())
                .username(clientRegisterRequest.getUsername())
                .password(passwordEncoder.encode(clientRegisterRequest.getPassword()))
                .role(UserRole.CLIENT)
                .build();
        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse registerCompany(CompanyRegisterRequest companyRegisterRequest){
        var user = Company.builder()
                .name(companyRegisterRequest.getName())
                .email(companyRegisterRequest.getEmail())
                .password(passwordEncoder.encode(companyRegisterRequest.getPassword()))
                .phone(companyRegisterRequest.getPhone())
                .address(companyRegisterRequest.getAddress())
                .role(UserRole.COMPANY)
                .build();
        Company savedUser = companyRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse loginCompany(CompanyLoginRequest companyLoginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        companyLoginRequest.getEmail(),
                        companyLoginRequest.getPassword())
        );

        var user = companyRepository.findByEmail(companyLoginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(companyLoginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


}
