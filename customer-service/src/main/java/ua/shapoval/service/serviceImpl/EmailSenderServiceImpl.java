package ua.shapoval.service.serviceImpl;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.shapoval.error.Errors;
import ua.shapoval.service.ConfirmationTokenService;
import ua.shapoval.service.EmailSenderService;



@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    private final ConfirmationTokenService confirmationTokenService;

    @Value("${spring.mail.username}")
    private String senderEmail;


    @Override
    public void sendMassage(String email, String token) {


        try {



            SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(" Hi! Complete Registration! ");
        message.setFrom(senderEmail);
        message.setText("To confirm your account, please click here : "
                + "http://localhost:8484/api/v1/confirm-account?token="
                + token);

        javaMailSender.send(message);


        } catch (Exception exception) {

            log.error(" Message sending error. Exception : {} ", exception.getMessage() );
            throw new RuntimeException(Errors.UNKNOWN_ERROR.getMessage() + exception.getMessage());
        }
    }
}
