package ua.shapoval.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendMassage (String email);

}
