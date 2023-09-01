package ua.shapoval.error.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.error.ConfirmationTokenException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdviceHandler{


    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlerBadCredentialException(BadCredentialException exception){

        return new ResponseEntity<>(exception.getMassage(), HttpStatusCode.valueOf(400));

    }

    @ExceptionHandler(ConfirmationTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlerConfirmationTokenException(ConfirmationTokenException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(400));

    }


}
