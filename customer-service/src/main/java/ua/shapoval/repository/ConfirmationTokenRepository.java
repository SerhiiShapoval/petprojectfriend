package ua.shapoval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.shapoval.domain.ConfirmationToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {


    Optional<ConfirmationToken> getByVerificationTokenAndAndExpireTokenBefore (String token, LocalDateTime time);
    void deleteAllBySentToCustomerFalse();
    void deleteByVerificationToken(String token);


}