package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
public enum Errors {

    UNKNOWN_ERROR(" An unknown error has occurred. Try later ");

    private final String message;


}
