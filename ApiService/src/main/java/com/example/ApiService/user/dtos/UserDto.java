package com.example.ApiService.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User model")
@EqualsAndHashCode
@ToString
public class UserDto {
    @Schema(description = "Unique user id")
    private UUID id;
    @Schema(description = "Unique username")
    private String username;
    @Schema(description = "Unique email")
    private String email;
}