package ua.shapoval.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.error.Errors;
import ua.shapoval.repository.CustomerRepository;
import ua.shapoval.service.CustomerService;
import ua.shapoval.service.EmailSenderService;



@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private final ConfirmationTokenServiceImpl confirmationTokenService;

    private final EmailSenderService emailSenderService;

    @Override
    public void registration(Customer customer) {

        log.info(" Registration customer  email: {}",  customer.getEmail());
        processEmailVerification(customer.getEmail());

        processEmailVerification(customer.getEmail());
        log.info(" Verification token sent successfully to the email : {} ", customer.getEmail());

        saveCustomerBeforeConfirmationEmail(customer);
        log.info(" Save customer: {} ", customer);

    }

    private void saveCustomerBeforeConfirmationEmail(Customer customer){

     try {
        customerRepository.save(Customer.builder()
                .password(passwordEncoder.encode(customer.getPassword()))
                .email(customer.getEmail())
                .confirmed(false)
                .build());
        log.info(" Save customer without confirmation ");
    }catch (Exception exception){
         log.error(" An error occurred while saving customer. Exception :{}", exception.getMessage());
         throw new RuntimeException( Errors.UNKNOWN_ERROR.getMessage() );
     }


    }

    private void processEmailVerification(String email) {

         customerRepository.findCustomerByEmail(email).ifPresentOrElse(
                        customer -> {
                       if (customer.isConfirmed()){
                           log.error(" Registration failed, email is busy : {}", email);
                           throw new BadCredentialException("Email is busy");
                            }else {
                                log.info("Resending verification token to email: {}", email);
                                emailSenderService.sendMassage(email);
                                throw new BadCredentialException(" Verification token sent to the email again ");
                             }
                         },
                        () -> {
                            log.info( " New registration for email: {}", email);
                            emailSenderService.sendMassage(email);
                        }
         );
    }


    @Override
    public void saveCustomerWithConfirmation(String token) {

       Customer customer = confirmationTokenService.tokenVerification(token).getCustomer();

       log.info(" The customer has verified his email :{} ", customer.getEmail());
       customer.setConfirmed(true);

      try {
          customerRepository.save(customer);
          log.info(" Registration customer completed successfully: {} ", customer);
      }catch (Exception exception){
          log.error(" An error occurred while saving a user with a verified email. Exception :{} ", exception.getMessage() );
          throw new RuntimeException( Errors.UNKNOWN_ERROR.getMessage());
      }


        confirmationTokenService.deleteTokenAfterConfirmation(token);

    }









}
