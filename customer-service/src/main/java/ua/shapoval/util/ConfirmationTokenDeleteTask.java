package ua.shapoval.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.shapoval.service.ConfirmationTokenService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenDeleteTask {

    private final ConfirmationTokenService confirmationTokenService;



    @Scheduled(cron = " 0 0 0 * * ?")
    public void deleteExpiredTokens(){

        log.info(" Start delete confirmation token task at : {}", LocalDateTime.now());

        confirmationTokenService.deleteAllToken();

        log.info(" End delete confirmation token task at : {}", LocalDateTime.now());

    }



}
