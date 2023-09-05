package ua.shapoval.service;

import org.springframework.mail.SimpleMailMessage;
import ua.shapoval.domain.ConfirmationToken;

public interface EmailSenderService {

    void sendMassage (String email, String token);

}
