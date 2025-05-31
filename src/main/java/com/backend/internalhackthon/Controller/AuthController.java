package com.backend.internalhackthon.Controller;

import com.backend.internalhackthon.Service.UserDetail;
import com.backend.internalhackthon.Util.Security;
import com.backend.internalhackthon.DTO.LoginDto;
import com.backend.internalhackthon.DTO.UserDTO;
import com.backend.internalhackthon.DTO.UserUpdateDto;
import com.backend.internalhackthon.Model.Response.AuthResponse;
import com.backend.internalhackthon.Model.Response.CommonResponse;
import com.backend.internalhackthon.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Security security;

    private final AuthService authService;

    public AuthController(Security security, AuthService authService) {
        this.security = security;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid UserDTO userDTO) {
        UserDTO userDTO1 = authService.signup(userDTO);
        String token = security.generateToken(userDTO1.getEmail(), userDTO1.getRole());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Registration Successfully!");
        authResponse.setStatus(true);
        authResponse.setToken(token);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDto loginDto) {
        UserDTO userDTO = authService.login(loginDto);
        String token = security.generateToken(userDTO.getEmail(), userDTO.getRole());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setStatus(true);
        authResponse.setMessage("Login Successfully!");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PutMapping("/upadteProfile")
    public ResponseEntity<CommonResponse> login(@RequestBody @Valid UserUpdateDto updateDto,
                                                @AuthenticationPrincipal UserDetail userDetail) throws Exception {

        UserUpdateDto userDTO = authService.updateProfile(updateDto, userDetail.getUsername());
        CommonResponse authResponse = new CommonResponse();
        authResponse.setStatus(true);
        authResponse.setMessage("Profile Updated Successfully!");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


}
