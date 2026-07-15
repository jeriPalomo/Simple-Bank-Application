package com.citibank.customerapi.config;

import com.citibank.customerapi.model.Customer;
import com.citibank.customerapi.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
 * Seeds demo customers into MongoDB on startup, but only if the
 * collection is empty - so restarts don't keep duplicating data.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataSeeder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        seedIfMissing("C001", "pass123", "Sonia Jain", "sonia@example.com", "555-0101", "New York", 10001);
        seedIfMissing("C002", "pass123", "Nevil Johnson", "nevil@example.com", "555-0102", "Chicago", 60601);
        seedIfMissing("C003", "pass123", "Carla Gomez", "carla@example.com", "555-0103", "Los Angeles", 90001);
        seedIfMissing("C004", "pass123", "Priya Shah", "priya@example.com", "555-0104", "Toronto", 10002);
    }

    private void seedIfMissing(String customerId, String password, String name, String email,
                               String phoneNumber, String branchLocation, int postalCode) {
        if (customerRepository.existsById(customerId)) {
            return;
        }

        customerRepository.save(new Customer(customerId, password, name, email, phoneNumber, branchLocation, postalCode));
    }
}