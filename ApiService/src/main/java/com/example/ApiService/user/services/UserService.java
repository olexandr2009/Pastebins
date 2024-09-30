package com.example.ApiService.user.services;

import com.example.ApiService.user.dtos.UpdateUserDto;
import com.example.ApiService.user.dtos.UserDto;
import com.example.ApiService.user.exceptions.UserAlreadyExistException;
import com.example.ApiService.user.exceptions.UserIncorrectPasswordException;
import com.example.ApiService.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Collection;

public interface UserService {
    @Transactional
    void registerUser(String username, String email,String password)
            throws UserAlreadyExistException;

    @Transactional
    UserDto updateUser(UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserIncorrectPasswordException, UserAlreadyExistException;
}
