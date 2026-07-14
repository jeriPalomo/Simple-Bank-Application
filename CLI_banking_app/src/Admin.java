import java.util.Iterator;

/*
 *  Admin class representing a bank administrator handling CRUD operations
*/

// Inerhitance: Admin extends Customer
// Polymorphism: Admin can perform actions on Customer and Accounts
public class Admin extends Customer {
    private String adminId;
    private String adminPassword;
    private Bank bank; // Links the admin to the bank's database

    public Admin(String customerId, String password, String name, String email, String phoneNumber, String branchLocation, int postalCode, String adminId, String adminPassword, Bank bank) {
        super(customerId, password, name, email, phoneNumber, branchLocation, postalCode);
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.bank = bank;
    }
    
    // Getters + Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
    
    // Methods
    public void registerCustomer(Customer customer) {
        bank.getCustomers().add(customer);
        System.out.println("Registered customer " + customer.getName());
    }

    public void createAccount(Accounts newAccount) {
        bank.getAccounts().add(newAccount);
        System.out.println("Created account " + newAccount.getAccountNumber());
    }

    public void assignAccountToCustomer(String customerId, Accounts account) {
        for (Customer c : bank.getCustomers()) {
            if (c.getCustomerId().equals(customerId)) {
                c.addAccount(account);

                if (!bank.getAccounts().contains(account)) {
                    bank.getAccounts().add(account);
                }

                System.out.println("Account " + account.getAccountNumber() + " assigned to " + c.getName());
                return;
            }
        }
        System.out.println("Customer ID not found.");
    }

    public void seeAllCustomers() {
        System.out.println("\n--- All Customers ---");
        boolean found = false;
        for (Customer c : bank.getCustomers()) {
            if (c instanceof Admin) {
                continue;
            }
            System.out.println("ID: " + c.getCustomerId() + " | Name: " + c.getName() + " | Branch: " + c.getBranchLocation());
            found = true;
        }
        if (!found) {
            System.out.println("No customers found.");
        }
    }

    public void seeAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        if (bank.getAccounts().isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        for (Accounts a : bank.getAccounts()) {
            System.out.println("Acc No: " + a.getAccountNumber() +
                               " | Owner: " + a.getPrimaryOwner() +
                               " | Balance: $" + a.getBalance());
        }
    }

    public void deleteCustomer(String customerId) {
        Customer customerToRemove = null;
        for (Customer c : bank.getCustomers()) {
            if (c.getCustomerId().equals(customerId)) {
                customerToRemove = c;
                break;
            }
        }

        if (customerToRemove != null) {
            for (Accounts a : customerToRemove.getAccounts()) {
                String accountNum = a.getAccountNumber();

                // Iterator = safely remove elements from a collection (only goes forward)
                Iterator<Accounts> accountToRemove = bank.getAccounts().iterator();
                while (accountToRemove.hasNext()) {
                    Accounts current = accountToRemove.next();
                    if (current.getAccountNumber().equals(accountNum)) {
                        accountToRemove.remove();
                    }
                }

                System.out.println("Admin: Account " + accountNum + " also removed for customer " + customerId);
            }

            bank.getCustomers().remove(customerToRemove);
            System.out.println("Admin: Customer " + customerId + " deleted successfully.");
        } else {
            System.out.println("Admin: Customer not found.");
        }
    }

    public void deleteAccount(String accountNumber) {
        // Removes from customer's account list
        for (Customer c : bank.getCustomers()) {
            Accounts owned = c.getAccount(accountNumber);
            if (owned != null) {
                c.removeAccount(owned);
                break;
            }
        }

        // Removes from bank's account list
        boolean removed = false;
        Iterator<Accounts> it = bank.getAccounts().iterator();
        while (it.hasNext()) {
            Accounts current = it.next();
            if (current.getAccountNumber().equals(accountNumber)) {
                it.remove();
                removed = true;
            }
        }

        if (removed) {
            System.out.println("Account " + accountNumber + " deleted successfully.");
        } else {
            System.out.println("Account number not found.");
        }
    }
}