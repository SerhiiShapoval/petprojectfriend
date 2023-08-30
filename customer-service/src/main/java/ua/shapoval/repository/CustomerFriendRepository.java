package ua.shapoval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shapoval.domain.CustomerFriend;

public interface CustomerFriendRepository extends JpaRepository<CustomerFriend, Long> {
}
