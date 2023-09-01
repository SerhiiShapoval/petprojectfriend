package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.repository.ConfirmationTokenRepository;
import ua.shapoval.service.ConfirmationTokenService;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public boolean isTokenValid(String token) {
        if (token == null || !confirmationTokenRepository.existsByVerificationToken(token) ) {
            log.error(" Token not valid :{} ", token);
            throw new ConfirmationTokenException(" The token is not validated. ");
        }

        return true;
    }

    @Override
    public boolean isTokenExpire(String token) {

        ConfirmationToken confirmationToken = getByByVerificationToken(token);
        if ( LocalDateTime.now().isAfter(confirmationToken.getExpireToken())){
           log.error(" Error token expired :{}", confirmationToken);
            throw new ConfirmationTokenException(" The token expired ");

        }
        return true;
    }

    @Override
    public ConfirmationToken getByByVerificationToken(String token) {
        return confirmationTokenRepository.findByVerificationToken(token);
    }

    @Override
    public void deleteAllToken() {
        confirmationTokenRepository.deleteAllBySentToCustomerFalse();
    }

    @Override
    public void deleteToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.delete(confirmationToken);

    }
}
