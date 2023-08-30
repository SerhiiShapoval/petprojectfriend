package ua.shapoval.service.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.repository.ConfirmationTokenRepository;
import ua.shapoval.service.EmailSenderService;

@Service
@RequiredArgsConstructor

public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository tokenRepository;
    @Value("${spring.mail.username}")
    private final String senderEmail;


    private void sendEmail(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void confirmEmail(String email) {
        ConfirmationToken token = new ConfirmationToken();
        tokenRepository.save(token);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(" Hi! Complete Registration! ");
        message.setFrom(senderEmail);
        message.setText("To confirm your account, please click here : "
                +"http://localhost:8484/confirm-account?token=" + token.getConfirmationToken());
        sendEmail(message);
    }

}
