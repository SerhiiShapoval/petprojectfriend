package ua.shapoval.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.shapoval.service.CustomerService;
import ua.shapoval.service.EmailSenderService;
import ua.shapoval.service.serviceImpl.CustomerServiceImpl;

@RestController
@RequestMapping("/api/v1/confirm-account")
@RequiredArgsConstructor
public class ConfirmCustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String confirmationToken ){

        customerService.confirmEmail(confirmationToken);

            return ResponseEntity.
                    ok(" Email verified successfully! ");

    }

}
