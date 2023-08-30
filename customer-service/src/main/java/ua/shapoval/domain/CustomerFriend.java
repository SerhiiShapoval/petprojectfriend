package ua.shapoval.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString(exclude = "id")
@Table(name = "customerFriends")
public class CustomerFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(name = "nameFriends")
    private String nameFriend;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
