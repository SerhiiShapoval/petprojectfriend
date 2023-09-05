package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmailVerificationException extends RuntimeException {

    private String massage;

}
