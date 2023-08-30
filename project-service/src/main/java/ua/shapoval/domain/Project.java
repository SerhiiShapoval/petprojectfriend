package ua.shapoval.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "project")
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdProject;

    @Column(nullable = false)
    private String description;

    @Size(min = 1, max = 5, message = " Rating can be from 1 to 5 ")
    private int rating;

    @ElementCollection
    private List<String> languages;

    @Enumerated(EnumType.STRING)
    private Status status;
}
