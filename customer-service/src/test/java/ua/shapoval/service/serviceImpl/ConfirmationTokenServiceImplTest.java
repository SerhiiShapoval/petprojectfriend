package ua.shapoval.service.serviceImpl;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceImplTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenServiceImpl confirmationTokenService;

    private List<ConfirmationToken> getTokens() {
        ConfirmationToken validToken = ConfirmationToken.builder()
                .id(UUID.fromString("a0c9a69e-b6d3-4db6-9e9b-ef220f593a82"))
                .verificationToken(" valid token ")
                .sentToCustomer(true)
                .createToken(LocalDateTime.now())
                .expireToken(LocalDateTime.now().plusHours(11))
                .build();
        ConfirmationToken notValidToken = ConfirmationToken.builder()
                .id(UUID.fromString("a0c1a49e-b6d3-4db6-9e9b-ef220f593a82"))
                .verificationToken(" not valid token ")
                .sentToCustomer(false)
                .createToken(LocalDateTime.now())
                .expireToken(LocalDateTime.now().plusHours(13))
                .build();

        return List.of(validToken, notValidToken);
    }

    @Test
    public void createTokenTest() {

        ConfirmationToken validToken = getTokens().get(1);


        when(confirmationTokenRepository.save(any(ConfirmationToken.class)))
                .thenReturn(validToken)
                    .thenThrow(new RuntimeException(" Test exception"));

        ConfirmationToken result = confirmationTokenService.createToken();

        verify(confirmationTokenRepository, times(1))
                .save(argThat(token -> token instanceof ConfirmationToken));

        assertNotNull(result);

        assertEquals(validToken, result);

        assertThrows(RuntimeException.class, () -> confirmationTokenService.createToken());

    }


    @Test
    public void tokenVerification() {

        when(confirmationTokenRepository.getByVerificationTokenAndAndExpireTokenBefore())
                .thenReturn()
                    .thenThrow(new ConfirmationTokenException(" test "));


    }

    @Test
    public void deleteAllTokenWhenSentOnCustomerFalseTest() {

        List<ConfirmationToken> validListTokens = getTokens().stream()
                .filter(ConfirmationToken::isSentToCustomer)
                .toList();


        doNothing().when(confirmationTokenRepository).deleteAllBySentToCustomerFalse();

        confirmationTokenService.deleteAllTokenTask();

        verify(confirmationTokenRepository, times(1)).deleteAllBySentToCustomerFalse();

        assertNotEquals(validListTokens.size(), getTokens().size());

        assertTrue(validListTokens.get(0).isSentToCustomer());

        assertFalse(validListTokens.stream().anyMatch(confirmationToken -> !confirmationToken.isSentToCustomer()));

        assertEquals(1, validListTokens.size());

    }

    @Test
    public void deleteTokenAfterConfirmationTest() {

        String deleteVerificationToken = " not valid token ";


        List<ConfirmationToken> validListTokens = getTokens().stream()
                .filter(t -> !(t.getVerificationToken().equals(deleteVerificationToken)))
                .toList();



        doNothing().when(confirmationTokenRepository).deleteByVerificationToken(any(String.class));

        confirmationTokenService.deleteTokenAfterConfirmation(deleteVerificationToken);

        verify(confirmationTokenRepository,times(1))
                .deleteByVerificationToken(argThat(token -> token.getClass().equals(deleteVerificationToken.getClass())));

        assertNotEquals(validListTokens.size(), getTokens().size());

        assertNotEquals(deleteVerificationToken, validListTokens.get(0).getVerificationToken());

        assertFalse(validListTokens.stream()
                .anyMatch(token -> token.getVerificationToken().equals(deleteVerificationToken)));

        assertEquals(1, validListTokens.size());


    }


}