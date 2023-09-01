package ua.shapoval.service;

import ua.shapoval.domain.Customer;

public interface CustomerService {

    void registrationCustomer(Customer customer);
    void confirmEmail(String token);

}
