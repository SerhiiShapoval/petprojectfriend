package ua.shapoval.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.shapoval.domain.CustomerProfile;

import java.util.UUID;

@Repository
public interface CustomerProfileRepository extends MongoRepository<CustomerProfile, UUID> {
}
