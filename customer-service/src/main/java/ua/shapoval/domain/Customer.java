package ua.shapoval.domain;


import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "customers")
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private boolean confirmed;


    @OneToMany(mappedBy = "customer")
    private List<CustomerFriend> friends = new ArrayList<>();
}
