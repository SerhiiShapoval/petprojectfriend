package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.error.Errors;
import ua.shapoval.repository.ConfirmationTokenRepository;
import ua.shapoval.service.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    @Override
    public ConfirmationToken tokenVerification(String token) {

        return confirmationTokenRepository.getByVerificationTokenAndAndExpireTokenIsAfter(token, LocalDateTime.now())
                .orElseThrow(() ->{
                    log.error( " The token is expired or not valid " );
                    throw   new ConfirmationTokenException(" Verification token has expire ");
                });
    }


    @Override
    public ConfirmationToken createTokenForCustomer(Customer customer) {
        try {
            log.info(" Creating a verification token for customer . " );

            return confirmationTokenRepository.save(ConfirmationToken.builder()
                    .verificationToken(UUID.randomUUID().toString())
                    .customer(customer)
                    .expireToken(LocalDateTime.now().plusHours(12))
                    .build());
        }catch (Exception exception){
            log.error(" An error occurred while generating the token. Exception :{} ", exception.getMessage());
            throw new RuntimeException(Errors.UNKNOWN_ERROR.getMessage() + exception.getMessage());
        }
    }


    @Override
    public void deleteAllTokenExpiredTask() {

        log.info(" Delete verification token that have expired " );

        confirmationTokenRepository.deleteAllByExpireTokenIsBefore(LocalDateTime.now());

    }

    @Override
    public void deleteTokenAfterConfirmation (String confirmationToken) {

        log.info(" Delete verification token after successful successful email verification :{}", confirmationToken);
        confirmationTokenRepository.deleteByVerificationToken(confirmationToken);

    }
}
