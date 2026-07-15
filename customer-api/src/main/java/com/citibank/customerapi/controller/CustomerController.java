package com.citibank.customerapi.controller;

import com.citibank.customerapi.dto.CustomerResponse;
import com.citibank.customerapi.model.Customer;
import com.citibank.customerapi.repository.CustomerRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/*
 * @RestController = @Controller + @ResponseBody: every method's return value is
 * serialized straight to the HTTP response body (as JSON), instead of resolving
 * to an HTML view like a traditional MVC controller would.
 *
 * @RequestMapping sets the base path all methods in this class share.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    // Constructor injection: Spring sees this class needs a CustomerRepository and
    // hands it the auto-generated Spring Data MongoDB repository implementation.
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // GET /api/customers -> list of every customer
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::new)
                .toList();
    }

    // GET /api/customers/{id} -> a single customer, or 404 if not found
    @GetMapping("/{customerId}")
    public CustomerResponse getCustomer(@PathVariable String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + customerId + " not found"));
        return new CustomerResponse(customer);
    }

    // POST /api/customers -> creates a new customer, 409 if the id is already taken
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody Customer customer) {
        if (customerRepository.existsById(customer.getCustomerId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer " + customer.getCustomerId() + " already exists");
        }
        Customer saved = customerRepository.save(customer);
        return new CustomerResponse(saved);
    }

    // DELETE /api/customers/{id} -> removes a customer, or 404 if it doesn't exist
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + customerId + " not found");
        }
        customerRepository.deleteById(customerId);
    }
}
