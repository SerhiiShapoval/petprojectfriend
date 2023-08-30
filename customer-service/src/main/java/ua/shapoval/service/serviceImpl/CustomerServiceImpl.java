package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.repository.CustomerRepository;
import ua.shapoval.service.CustomerService;
import ua.shapoval.service.EmailSenderService;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private final EmailSenderService emailSenderService;



    @Override
    public void registrationCustomer(Customer customer) {
        log.info(" Registration customer login : {}, email: {}", customer.getLogin(), customer.getEmail());
        checkCredential(customer.getLogin(),customer.getEmail());
        log.info(" Send ");
        emailSenderService.confirmEmail(customer.getEmail());

        customerRepository.save(customer);
    }




    private void checkCredential(String login, String email) {
        if (customerRepository.existsByEmailAndLogin(login, email)){
            log.error(" Error customer login {} or email {} is  busy ", login,email);
            throw new BadCredentialException(" Login or email is busy",
                    HttpStatus.IM_USED,
                    HttpStatusCode.valueOf(226));
        }
    }




}
