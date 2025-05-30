package com.backend.internalhackthon.Service;

import com.backend.internalhackthon.DTO.LoginDto;
import com.backend.internalhackthon.DTO.UserDTO;
import com.backend.internalhackthon.DTO.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
public UserDTO signup(UserDTO userDTO );

public UserDTO login(LoginDto loginDto);

    public UserUpdateDto updateProfile(UserUpdateDto updateDto, String email);


}
