package ua.shapoval.service.serviceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    private String email;
    private String token;

    @BeforeEach
    void setUp(){

        this.email = " Test@email.com ";

        this.token = " Token test ";

    }


    @Test
    void sendMassageTest() {

        String expectedSubject = " Hi! Complete Registration! ";

        String expectedMessage = "To confirm your account, please click here : " +
                    "http://localhost:8484/api/v1/confirm-account?token=" + token;

        emailSenderService.sendMassage(email, token);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

        ArgumentCaptor<SimpleMailMessage> messageArgumentCaptor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageArgumentCaptor.capture());


        assertEquals(email, messageArgumentCaptor.getValue().getTo()[0]);

       assertEquals(expectedSubject, messageArgumentCaptor.getValue().getSubject());

        assertEquals(expectedMessage, messageArgumentCaptor.getValue().getText());

    }

    @Test
    public void sendMail_ReturnException(){


        doThrow(new RuntimeException("test")).when(mailSender).send(any(SimpleMailMessage.class));

        assertThrows(RuntimeException.class, () -> emailSenderService.sendMassage(email,token));

    }
}