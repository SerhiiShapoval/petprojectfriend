package ua.shapoval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.shapoval.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmailAndLogin( String email, String login);
}
