package ua.shapoval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.shapoval.domain.Customer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {


    boolean existsByEmail(String email);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findCustomerByEmail (String email);
}
