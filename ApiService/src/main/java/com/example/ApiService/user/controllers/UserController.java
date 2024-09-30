package com.example.ApiService.user.controllers;

import com.example.ApiService.user.dtos.UpdateUserDto;
import com.example.ApiService.user.dtos.UserDto;
import com.example.ApiService.user.exceptions.UserAlreadyExistException;
import com.example.ApiService.user.exceptions.UserIncorrectPasswordException;
import com.example.ApiService.user.exceptions.UserNotFoundException;
import com.example.ApiService.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@Tag(name = "Users", description = "User controller to manage usernames, passwords and roles")
@Validated
@RestController
@RequestMapping("/V1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Rename user and reset password to newer one",
            description = "Update password and username ",
            tags = {"Users"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202",
                    description = "Username, password changed",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "oldUsername not found or newUsername alreadyExists or oldPassword doesn't matches with existing"
            ),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @Parameter(description = "Object to update username and password", required = true)
            @Valid @RequestBody UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserAlreadyExistException, UserIncorrectPasswordException {
        try {
            return ResponseEntity.ok(userService.updateUser(updateUserDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}