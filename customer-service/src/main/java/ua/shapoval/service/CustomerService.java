package ua.shapoval.service;

import ua.shapoval.domain.Customer;

public interface CustomerService {

    void registration(Customer customer);
    void saveCustomerWithConfirmation(String token);


}
