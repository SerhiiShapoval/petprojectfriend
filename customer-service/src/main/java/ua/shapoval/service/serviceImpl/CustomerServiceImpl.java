package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.repository.ConfirmationTokenRepository;
import ua.shapoval.repository.CustomerRepository;
import ua.shapoval.service.CustomerService;
import ua.shapoval.service.EmailSenderService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private final ConfirmationTokenServiceImpl confirmationTokenService;

    private final EmailSenderService emailSenderService;



    @Override
    public void registrationCustomer(Customer customer) {

        log.info(" Registration customer login : {}, email: {}", customer.getLogin(), customer.getEmail());
        checkCredential(customer.getLogin(),customer.getEmail());

        emailSenderService.sendMassage(customer.getEmail());
        log.info(" Send on email:{} verification  token .",customer.getEmail());

            Customer validateCustomer = Customer.builder()
                    .login(customer.getLogin())
                    .password(passwordEncoder.encode(customer.getPassword()))
                    .email(customer.getEmail())
                    .confirmed(false)
                    .build();
            customerRepository.save(validateCustomer);
            log.info(" Customer save to DB :{}",validateCustomer);

    }

    private void checkCredential(String login, String email) {
        if (customerRepository.existsByEmailAndLoginAndConfirmedTrue(login,email)){

            log.error(" Error customer login {} or email {} is  busy ", login,email);

            throw new BadCredentialException(" Login or email is busy ");

        }else if (customerRepository.existsByEmailAndLoginAndConfirmedFalse(login,email)){

            log.info(" RE-sent email: {}", email);

            emailSenderService.sendMassage(email);
        }
    }

    @Override
    public void confirmEmail(String token) {

        confirmationTokenService.isTokenValid(token);
        confirmationTokenService.isTokenExpire(token);

        ConfirmationToken confirmationToken = confirmationTokenService.getByByVerificationToken(token);

        Customer customer = confirmationToken.getCustomer();
        customer.setConfirmed(true);
        customerRepository.save(customer);
        confirmationTokenService.deleteToken(confirmationToken);


    }









}
