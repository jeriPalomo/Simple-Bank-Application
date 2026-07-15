package com.citibank.customerapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/*
 * Customer class representing a bank customer
*/
@Document(collection = "customers")
public class Customer {
    @Id
    private String customerId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String branchLocation;
    private int postalCode;
    private boolean isFrozen;

    private List<Accounts> accounts = new ArrayList<>();


    @PersistenceCreator // helps Spring Data create object from MongoDB
    public Customer(String customerId, String password, String name, String email, String phoneNumber, String branchLocation, int postalCode) {
        this.customerId = customerId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.branchLocation = branchLocation;
        this.postalCode = postalCode;
        this.isFrozen = false;
    }

    // Getters + Setters
    public String getCustomerId() { return customerId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getBranchLocation() { return branchLocation; }
    public void setBranchLocation(String branchLocation) { this.branchLocation = branchLocation; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public boolean isFrozen() { return isFrozen; }
    public List<Accounts> getAccounts() { return accounts; }

    // Methods
    public void freezeAccount() { isFrozen = true; }
    public void unfreezeAccount() { isFrozen = false; }

    public void addAccount(Accounts account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    public void removeAccount(Accounts account) {
        accounts.remove(account);
    }

    public Accounts getAccount(String accountNumber) {
        for (Accounts a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) {
                return a;
            }
        }
        return null;
    }
}
