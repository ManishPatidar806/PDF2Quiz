package com.backend.internalhackthon.Service;

import com.backend.internalhackthon.DTO.LoginDto;
import com.backend.internalhackthon.DTO.UserDTO;
import com.backend.internalhackthon.DTO.UserUpdateDto;
import com.backend.internalhackthon.Exception.CommonException;
import com.backend.internalhackthon.Exception.UserNotFoundException;
import com.backend.internalhackthon.Model.User;
import com.backend.internalhackthon.Repository.AuthRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ModelMapper mapper;

    @SneakyThrows
    @Override
    public UserDTO signup(UserDTO userDTO) {
        User user = authRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new CommonException("User is Already exist!");
        }
        User user1 = mapper.map(userDTO, User.class);
        return mapper.map(authRepository.save(user1), UserDTO.class);
    }

    @SneakyThrows
    @Override
    public UserDTO login(LoginDto loginDto) {
        User user = authRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User is not found!");
        }
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new CommonException("Password is Incorrect");
        }
        return mapper.map(user, UserDTO.class);
    }

    @SneakyThrows
    public UserUpdateDto updateProfile(UserUpdateDto updateDto, String email) {
        User user = authRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User is not found!");
        }
        mapper.map(updateDto, user);
        authRepository.save(user);
        return updateDto;
    }


}
