package ua.shapoval.error.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.error.EmailVerificationException;

import java.util.stream.Collectors;

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

    @ExceptionHandler(EmailVerificationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?>  handlerEmailVerificationException(EmailVerificationException exception){
        return new ResponseEntity<>(exception.getMassage(),HttpStatusCode.valueOf(409));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlerMethodArgumentNotValidException (MethodArgumentNotValidException exception){

        return new ResponseEntity<>(exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList()),

                HttpStatusCode.valueOf(400));
    }


}
