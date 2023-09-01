package ua.shapoval.service;

import ua.shapoval.domain.ConfirmationToken;

import java.util.UUID;

public interface ConfirmationTokenService {

    boolean isTokenValid(String token);

    boolean isTokenExpire(String token);

    void deleteToken (ConfirmationToken confirmationToken);
    ConfirmationToken getByByVerificationToken(String token);

    void deleteAllToken();

}
