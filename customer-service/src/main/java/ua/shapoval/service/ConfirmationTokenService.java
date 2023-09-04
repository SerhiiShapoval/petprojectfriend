package ua.shapoval.service;

import ua.shapoval.domain.ConfirmationToken;

public interface ConfirmationTokenService {


    ConfirmationToken tokenVerification (String token);

    void updateToken(ConfirmationToken confirmationToken);

    void deleteTokenAfterConfirmation (String token);

    void deleteAllTokenTask();

    ConfirmationToken createToken();

}
