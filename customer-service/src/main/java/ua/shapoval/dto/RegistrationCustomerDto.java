package ua.shapoval.dto;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import ua.shapoval.error.BadCredentialException;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
public class RegistrationCustomerDto {

    @NotNull
    @NotBlank(message = " Login can`t be empty")
    private String login;

    @NotNull
    @NotBlank(message = " Password con`t be empty ")
    @Pattern( regexp = " ^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d!@#$%^&*()\\\\-_+={};:,<.>?[\\\\]|\\\\\\\\]$ ",
    message = " Input correct password for registration ")
    @Size(min = 4, max = 12, message = " Password can be length at 4 to 8 characters ")
    private String password;

    @NotNull
    @NotBlank(message = " Confirm password can`t be empty ")
    private String confirmPassword;

    @NotNull
    @NotBlank(message = " Email can`t be empty ")
    @Email(message = " Input correct email address for registration ")
    private String email;

    public void checkPasswordEquality(String password, String confirmPassword){
        if (!Objects.equals(password,confirmPassword)){
            throw new BadCredentialException(" Passwords must be the same ");
        }
    }

}
