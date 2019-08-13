package com.future.onlinetraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @NotEmpty
    @Size(min = 6, max = 32)
    private String currentPassword;

    @NotEmpty
    @Size(min = 6, max = 32)
    private String newPassword;

    @NotEmpty
    @Size(min = 6, max = 32)
    private String confirmPassword;
}
