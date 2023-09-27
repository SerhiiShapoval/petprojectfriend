package ua.shapoval.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.shapoval.dto.RegistrationCustomerDto;
import ua.shapoval.service.CustomerService;

@RestController
@RequestMapping("/api/v1/confirm-account")
@RequiredArgsConstructor
@Tag(name = " Api for confirm email ")
public class ConfirmCustomerController {

    private final CustomerService customerService;

    @Operation(summary = " Confirm email for end registration  "
            , description = " Return message email verified successfully!")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = " 200 " ,
                    description = " Email verified successfully! ") ,
            @ApiResponse(
                    responseCode = " 400 " ,
                    description = " Verification token has expire, null or empty " ) ,
            @ApiResponse(
                    responseCode = " 500 " ,
                    description = " Unknown error " ) })
    @GetMapping
    public ResponseEntity<String> confirmEmail(
            @Parameter(description = " The token what was sent to the email" ,
            example = " b601d004-22ba-45d1-97c9-689ee2a44b2a ", required = true)
            @RequestParam("token") String confirmationToken ){

        customerService.saveCustomerWithConfirmation(confirmationToken);

            return ResponseEntity.
                    ok(" Email verified successfully! ");

    }

}
