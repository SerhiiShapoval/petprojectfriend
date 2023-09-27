package ua.shapoval.domain;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ua.shapoval.dto.CustomerDto;
import ua.shapoval.dto.ProjectDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document
@Data
public class CustomerProfile {


    @Id
    private UUID uuid;

    private String firstName;

    private String lastName;


    private LocalDate birthDate;


    private String residence;


    private List<String> progLanguages;

    private String position;

    private String experience;

    private String hobby;


    private String linkedIn;


    private String countProject;


    private Long customerId;
}