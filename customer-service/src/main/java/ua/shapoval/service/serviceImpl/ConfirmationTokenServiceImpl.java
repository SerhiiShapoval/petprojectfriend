package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.error.Errors;
import ua.shapoval.repository.ConfirmationTokenRepository;
import ua.shapoval.service.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    @Override
    public ConfirmationToken tokenVerification(String token) {

        if (token == null || token.isEmpty()) {
            throw new ConfirmationTokenException(" Verification token is null or empty.");
        }

        return confirmationTokenRepository.getByVerificationTokenAndAndExpireTokenBefore(token, LocalDateTime.now())
                .orElseThrow(() -> new ConfirmationTokenException(" Verification token has expire "));
    }



    @Override
    public ConfirmationToken createToken() {
        try {
            log.info(" Creating a verification token ");
            return confirmationTokenRepository.save(ConfirmationToken.builder()
                    .verificationToken(UUID.randomUUID().toString())
                            .sentToCustomer(false)
                    .build());
        }catch (Exception exception){
            log.error(" An error occurred while generating the token. Exception :{} ", exception.getMessage());
            throw new RuntimeException(Errors.UNKNOWN_ERROR.getMessage());
        }
    }

    @Override
    public void updateToken(ConfirmationToken confirmationToken) {

        try {
            confirmationTokenRepository.save(confirmationToken);
        }catch (Exception exception){

            log.error( "Error while update token status sent_to_customer" );
            throw new RuntimeException(Errors.UNKNOWN_ERROR.getMessage());
        }
    }

    @Override
    public void deleteAllTokenTask() {

        log.info(" Delete verification token where sent_to_customer = false ");
        confirmationTokenRepository.deleteAllBySentToCustomerFalse();

    }

    @Override
    public void deleteTokenAfterConfirmation (String confirmationToken) {

        log.info(" Delete verification token after successful successful email verification :{}", confirmationToken);
        confirmationTokenRepository.deleteByVerificationToken(confirmationToken);

    }
}
