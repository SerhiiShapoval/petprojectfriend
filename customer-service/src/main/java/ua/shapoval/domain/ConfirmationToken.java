package ua.shapoval.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "customers")
@Builder
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @CreationTimestamp
    private LocalDate createDate;

    public ConfirmationToken(){

        this.confirmationToken = UUID.randomUUID().toString();
    }
}
