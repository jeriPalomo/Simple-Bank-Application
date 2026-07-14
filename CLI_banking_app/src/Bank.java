import java.util.ArrayList;
import java.util.List;

/*
 * (Encapsulation) Bank holds the accounts and customers specifically to the bank's database
 * also authenticates customers and admins
 */
public class Bank {
    private List<Customer> customers = new ArrayList<>();
    private List<Accounts> accounts = new ArrayList<>();

    public List<Customer> getCustomers() { return customers; }
    public List<Accounts> getAccounts() { return accounts; }

    public Customer authenticateCustomer(String customerId, String password) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId) && c.getPassword().equals(password)) {
                if(c.isFrozen()) {
                    System.out.println("Account is frozen.");
                    return null;
                }
                return c;
            }
        }
        return null;
    }

    public Admin authenticateAdmin(String adminId, String password) {
        for (Customer c : customers) {
            if (c instanceof Admin) {
                Admin admin = (Admin) c;
                if (admin.getAdminId().equals(adminId) && admin.getAdminPassword().equals(password)) {
                    return admin;
                }
            }
        }
        return null;
    }
}