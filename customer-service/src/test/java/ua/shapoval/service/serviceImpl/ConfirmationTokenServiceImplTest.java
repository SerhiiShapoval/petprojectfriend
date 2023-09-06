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
import java.util.Optional;
import java.util.UUID;
;

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
                .createToken(LocalDateTime.now())
                .expireToken(LocalDateTime.now().plusHours(12))
                .build();
        ConfirmationToken notValidToken = ConfirmationToken.builder()
                .id(UUID.fromString("a0c1a49e-b6d3-4db6-9e9b-ef220f593a82"))
                .verificationToken(" not valid token ")
                .createToken(LocalDateTime.now())
                .expireToken(LocalDateTime.now().plusHours(12))
                .build();

        return List.of(validToken, notValidToken);
    }

    @Test
    void createTokenForCustomerTest() {

       ConfirmationToken validToken = getTokens().get(0);

       Customer customer = new Customer();

        when(confirmationTokenRepository.save(any(ConfirmationToken.class)))
                .thenReturn(validToken);

        ConfirmationToken result = confirmationTokenService.createTokenForCustomer(customer);

        assertNotNull(result);

        assertEquals(validToken, result);

        verify(confirmationTokenRepository, times(1))
                .save(argThat(token -> token instanceof ConfirmationToken));


        when(confirmationTokenRepository.save(any(ConfirmationToken.class)))
                .thenThrow(new RuntimeException(" Test "));

        assertThrows(RuntimeException.class, () -> confirmationTokenService.createTokenForCustomer(customer));

    }



    @Test
    public void tokenVerificationTest_ReturnValidToken() {

        String validToken = " valid token ";


        when(confirmationTokenRepository
                .getByVerificationTokenAndAndExpireTokenIsAfter(eq(validToken),any(LocalDateTime.class)))
                    .thenReturn(Optional.of(getTokens().get(0)));

        ConfirmationToken result = confirmationTokenService.tokenVerification(validToken);

        assertNotNull(result);

        assertNotNull(result.getVerificationToken());

        assertEquals(validToken, result.getVerificationToken());

        verify(confirmationTokenRepository)
                .getByVerificationTokenAndAndExpireTokenIsAfter(eq(validToken),any(LocalDateTime.class));


    }
    @Test
    public void tokenVerificationTest_ExpireToken(){

        ConfirmationToken expireToken = getTokens().get(1);
        LocalDateTime dateVerification = LocalDateTime.now().plusHours(13);

        when(confirmationTokenRepository
                .getByVerificationTokenAndAndExpireTokenIsAfter(eq(expireToken.getVerificationToken()), any(LocalDateTime.class)))
                    .thenThrow(new ConfirmationTokenException(" test "));

        assertTrue(expireToken.getExpireToken().isBefore(dateVerification));

            assertThrows(ConfirmationTokenException.class,
                    () -> confirmationTokenService.tokenVerification(expireToken.getVerificationToken()));
            verify(confirmationTokenRepository)
                    .getByVerificationTokenAndAndExpireTokenIsAfter(eq(expireToken.getVerificationToken()), any(LocalDateTime.class));

    }

    @Test
    public void tokenVerificationTest_NullOrEmptyToken() {

        String emptyToken = "";

        assertNotNull(emptyToken);

        assertThrows(ConfirmationTokenException.class, () -> confirmationTokenService.tokenVerification(emptyToken));

        assertThrows(ConfirmationTokenException.class, () -> confirmationTokenService.tokenVerification(null));

    }


    @Test
    public void deleteAllTokenExpiredTest() {


        List<ConfirmationToken> validList = getTokens().stream()
                .filter(confirmationToken -> confirmationToken.getVerificationToken().equals(" not valid token "))
                    .toList();

       doNothing().when(confirmationTokenRepository).deleteAllByExpireTokenIsBefore(any(LocalDateTime.class));

        confirmationTokenService.deleteAllTokenExpiredTask();

        assertNotEquals(validList.size(), getTokens().size());

        assertTrue(validList.stream()
                .anyMatch(confirmationToken -> confirmationToken.getVerificationToken().equals(" not valid token ")));

        assertEquals(1, validList.size());

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