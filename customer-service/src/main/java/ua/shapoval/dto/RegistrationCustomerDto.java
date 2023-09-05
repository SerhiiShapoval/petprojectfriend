package ua.shapoval.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import ua.shapoval.error.BadCredentialException;

import java.util.Objects;

@Data

public class RegistrationCustomerDto {


    @NotBlank(message = " Password con`t be empty ")
    @Pattern( regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$#_!]{4,15}$",
    message = "The password must contain at least one upper or lower case letter (A-Z or a-z)." +
            "The password must contain at least one number (0-9)." +
            "The password can contain special characters: !@#$%^&*()-_+={}\";:,<.>?[]|." +
            " Password can be length at 4 to 15 characters ")
    private String password;


    @NotBlank(message = " Confirm password con`t be empty   ")
    private String confirmPassword;


    @NotBlank(message = " Email can`t be empty ")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$",message = " Input correct email address for registration ")
    private String email;

    public void checkPasswordEquality(String password, String confirmPassword){
        if (!Objects.equals(password,confirmPassword)){
            throw new BadCredentialException(" Passwords must be the same ");
        }
    }

}
