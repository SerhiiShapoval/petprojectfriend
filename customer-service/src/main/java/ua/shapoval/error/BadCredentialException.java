package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class BadCredentialException extends RuntimeException {

    private  String massage;
    private HttpStatus httpStatus;
    private HttpStatusCode httpStatusCode;

}
