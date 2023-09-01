package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@Builder
public class ConfirmationTokenException extends RuntimeException {


    private String message;

}
