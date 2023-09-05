package ua.shapoval.service;

import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.domain.Customer;

public interface ConfirmationTokenService {


    ConfirmationToken tokenVerification (String token);


    void deleteTokenAfterConfirmation (String token);

    void deleteAllTokenExpiredTask();

    ConfirmationToken createTokenForCustomer(Customer customer);




}
