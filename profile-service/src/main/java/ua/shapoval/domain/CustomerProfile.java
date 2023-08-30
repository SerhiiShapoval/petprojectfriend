package ua.shapoval.domain;


import jakarta.persistence.*;
import lombok.*;
import ua.shapoval.dto.CustomerDto;
import ua.shapoval.dto.ProjectDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "profile")
@Builder
public class CustomerProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String residence;

    @Column(name = "programming_language", nullable = false)
    @ElementCollection
    private List<String> progLanguages;

    private String position;

    private String experience;

    private String hobby;

    @Column(nullable = false)
    private String linkedIn;

    @Column(nullable = false)
    private String countProject;

    @Column(nullable = false, unique = true)
    private Long customerId;
}