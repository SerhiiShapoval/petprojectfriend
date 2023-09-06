package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ConfirmationTokenException extends RuntimeException {


    private String message;

}
