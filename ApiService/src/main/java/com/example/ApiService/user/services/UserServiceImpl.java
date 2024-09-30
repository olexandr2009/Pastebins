package com.example.ApiService.user.services;

import com.example.ApiService.security.UserDetailsImpl;
import com.example.ApiService.user.dtos.UpdateUserDto;
import com.example.ApiService.user.dtos.UserDto;
import com.example.ApiService.user.entities.UserEntity;
import com.example.ApiService.user.exceptions.UserAlreadyExistException;
import com.example.ApiService.user.exceptions.UserIncorrectPasswordException;
import com.example.ApiService.user.exceptions.UserNotFoundException;
import com.example.ApiService.user.mapper.UserMapper;
import com.example.ApiService.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        userRepository.save(user);

        return UserDetailsImpl.build(user);
    }

    @Override
    @Transactional
    public void registerUser(String username, String email ,String password) throws UserAlreadyExistException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(username);
        }
        UserEntity user = new UserEntity(username, email, encoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserIncorrectPasswordException, UserAlreadyExistException {
        UserEntity user = userRepository.findByUsername(updateUserDto.getOldUsername())
                .orElseThrow(() -> new UserNotFoundException(updateUserDto.getOldUsername()));
        if (userRepository.existsByUsername(updateUserDto.getNewUsername())) {
            throw new UserAlreadyExistException(updateUserDto.getNewUsername());
        }
        if (encoder.matches(updateUserDto.getOldPassword(), user.getPassword())) {
            user.setUsername(updateUserDto.getNewUsername());
            user.setPassword(encoder.encode(updateUserDto.getNewPassword()));
            return userMapper.toUserDto(userRepository.save(user));
        } else {
            throw new UserIncorrectPasswordException(updateUserDto.getOldUsername());
        }
    }
}