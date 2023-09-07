package ua.shapoval.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.shapoval.domain.Customer;
import ua.shapoval.dto.RegistrationCustomerDto;
import ua.shapoval.service.CustomerService;


@Tag(name = " Customer API ")
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = " Registration new customer without confirm email confirmation "
            , description = " Return a mail confirmation message ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = " 200 " ,
                    description = " Verify  email by the link sent on your email address " ) ,
            @ApiResponse(
                    responseCode = " 400 " ,
                    description = " Bad credential. Invalid email or password " ) ,
            @ApiResponse(
                    responseCode = "409" ,
                    description = "Conflict: Email already confirmed. Resent confirmation . " ) ,
            @ApiResponse(
                    responseCode = " 500 " ,
                    description = " Unknown error " ) })

    @PostMapping("/registration")
    public ResponseEntity<String> add(@RequestBody @Valid RegistrationCustomerDto customerDto){

        customerDto.checkPasswordEquality(customerDto.getPassword(), customerDto.getConfirmPassword());
        Customer customer = Customer.builder()
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .build();
        customerService.registration(customer);

        return ResponseEntity.ok(" Verify  email by the link sent on your email address ");
    }


}
