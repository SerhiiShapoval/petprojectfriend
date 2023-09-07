package ua.shapoval.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import ua.shapoval.error.BadCredentialException;

import java.util.Objects;

@Data

@Schema(description = " Request credential for registration ")
public class RegistrationCustomerDto {

    @Schema(description = " The password must contain at least one upper or lower case letter " +
           ", at least one number, can contain special characters , length at 4 to 15 characters ",
            example = "12345678A!" )
    @NotBlank(message = " Password con`t be empty ")
    @Pattern( regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$#_!]{4,15}$",
    message = "The password must contain at least one upper or lower case letter\" +\n" +
            "        \", at least one number, can contain special characters , length at 4 to 15 characters")
    private String password;

    @Schema(description = "Confirm password must be same ",
            example = "12345678A!" )
    @NotBlank(message = " Confirm password con`t be empty   ")
    private String confirmPassword;

    @Schema(description = "User email address validate regexp ",
                example = "user@example.com")
    @NotBlank(message = " Email can`t be empty " )
    @Pattern(regexp ="^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,6})$",
            message = " Input correct email address for registration ")
    private String email;

    public void checkPasswordEquality(String password, String confirmPassword){
        if (!Objects.equals(password,confirmPassword)){
            throw new BadCredentialException(" Passwords must be the same ");
        }
    }

}
