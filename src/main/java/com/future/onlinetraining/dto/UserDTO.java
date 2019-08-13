package com.future.onlinetraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty
    @Size(min = 6, max = 32)
    String name;

    String role;

    @NotEmpty
    @Size(min = 6, max = 64)
    String email;

    @NotEmpty
    @Size(min = 10, max = 32)
    String phone;
}
