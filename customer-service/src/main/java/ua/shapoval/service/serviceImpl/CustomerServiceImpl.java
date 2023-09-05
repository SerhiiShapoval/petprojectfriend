package ua.shapoval.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.shapoval.domain.ConfirmationToken;
import ua.shapoval.domain.Customer;
import ua.shapoval.error.BadCredentialException;
import ua.shapoval.error.EmailVerificationException;
import ua.shapoval.error.Errors;
import ua.shapoval.repository.CustomerRepository;
import ua.shapoval.service.CustomerService;
import ua.shapoval.service.EmailSenderService;

import java.time.LocalDateTime;
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
    public void registration(Customer customer) {

        log.info(" Registration customer  email: {}",  customer.getEmail());

        if (!customerRepository.existsByEmail(customer.getEmail())){

            log.info( " New customer registration with email: {}", customer.getEmail());

            emailSenderService.sendMassage(customer.getEmail()
                    , generationTokenForCustomer(saveCustomerBeforeConfirmationEmail(customer)).getVerificationToken());

        }else {

            reSentConfirmationToken(customer.getEmail());

        }



    }

    private Customer saveCustomerBeforeConfirmationEmail(Customer customer){

     try {

         log.info(" Save customer without confirmation :{} ", customer );
        return customerRepository.save(Customer.builder()
                .password(passwordEncoder.encode(customer.getPassword()))
                .email(customer.getEmail())
                .confirmed(false)
                .build());

    }catch (Exception exception){
         log.error(" An error occurred while saving customer. Exception :{}", exception.getMessage());
         throw new RuntimeException( Errors.UNKNOWN_ERROR.getMessage() );
     }


    }
    private ConfirmationToken generationTokenForCustomer(Customer customer){

        return confirmationTokenService.createTokenForCustomer(customer);

    }
    private void reSentConfirmationToken(String email){

       Customer customer = customerRepository.findCustomerByEmail(email)
                .filter(c -> !c.isConfirmed())
                .orElseThrow(() ->{
                    log.error(" Error registration email is busy ");
                    throw new BadCredentialException(" This email has already registered. Try to login ");
                });


       emailSenderService.sendMassage(email, generationTokenForCustomer(customer).getVerificationToken());

        log.info("Resending verification token to email: {}", email );
        throw new EmailVerificationException(" Email has been registered " +
                ".Verification token sent to the email again ");

    }



    @Transactional
    @Override
    public void saveCustomerWithConfirmation(String token) {

          Customer customer = confirmationTokenService.tokenVerification(token).getCustomer();

          log.info(" The customer has verified his email :{} ", customer.getEmail());

          customer.setConfirmed(true);

          customerRepository.save(customer);
          log.info(" Registration customer completed successfully: {} ", customer);

          confirmationTokenService.deleteTokenAfterConfirmation(token);

      }

    }










