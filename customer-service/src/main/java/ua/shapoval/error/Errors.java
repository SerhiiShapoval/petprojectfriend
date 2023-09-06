package ua.shapoval.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum Errors {

    UNKNOWN_ERROR(" An unknown error has occurred. Exception : ");

    private final String message;


}
