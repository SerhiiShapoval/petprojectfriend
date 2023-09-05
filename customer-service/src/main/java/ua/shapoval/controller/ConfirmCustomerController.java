package ua.shapoval.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.shapoval.error.ConfirmationTokenException;
import ua.shapoval.service.CustomerService;

@RestController
@RequestMapping("/api/v1/confirm-account")
@RequiredArgsConstructor
public class ConfirmCustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String confirmationToken ){

        customerService.saveCustomerWithConfirmation(confirmationToken);

            return ResponseEntity.
                    ok(" Email verified successfully! ");

    }

}
