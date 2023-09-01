package ua.shapoval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.shapoval.domain.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmailAndLoginAndConfirmedTrue( String email, String login);
    boolean existsByEmailAndLoginAndConfirmedFalse( String email, String login);
    Optional<Customer> findById(Long id);
}
