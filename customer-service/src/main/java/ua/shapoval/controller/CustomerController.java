package ua.shapoval.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.shapoval.domain.Customer;
import ua.shapoval.dto.RegistrationCustomerDto;
import ua.shapoval.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    public ResponseEntity<String> add(@RequestBody @Valid RegistrationCustomerDto customerDto){

        customerDto.checkPasswordEquality(customerDto.getPassword(), customerDto.getConfirmPassword());
        Customer customer = Customer.builder()
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .build();
       // customerService.addCustomerBeforeConfirmation(customer);

        return ResponseEntity.ok(" Verify  email by the link sent on your email address ");
    }


}
