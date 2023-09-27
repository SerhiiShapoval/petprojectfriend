package ua.shapoval.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "confirmation_token")
@Builder
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="verification_token", unique = true, nullable = false)
    private String verificationToken;

    @CreationTimestamp
    private LocalDateTime createToken;


    @Column(name = "expire")
    private LocalDateTime expireToken;


    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customer customer;



}
