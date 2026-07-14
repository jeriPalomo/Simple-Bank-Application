import java.util.ArrayList;
import java.util.List;

/*
 * Customer class representing a bank customer
*/
public class Customer {
    private String customerId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String branchLocation;
    private int postalCode;
    private boolean isFrozen;

    private List<Accounts> accounts = new ArrayList<>(); // Links customer to their account(s) -- able to use Account.java class methods

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

    public void interAccountTransfer(String fromAccountNumber, String toAccountNumber, double amount, Bank bankDatabase) {
        Accounts fromAcc = getAccount(fromAccountNumber);
        if (fromAcc == null) {
            System.out.println("Transfer failed: You don't have an account with number " + fromAccountNumber + ".");
            return;
        }

        Accounts toAcc = null;
        for (Accounts a : bankDatabase.getAccounts()) {
            if (a.getAccountNumber().equals(toAccountNumber)) {
                toAcc = a;
                break;
            }
        }

        if (toAcc == null) {
            System.out.println("Transfer failed: Destination account not found.");
            return;
        }

        if (fromAcc.transferTo(toAcc, amount)) {
            System.out.println("Success! Transferred $" + amount + " to account " + toAccountNumber);
        } else {
            System.out.println("Transfer failed: Insufficient funds or invalid amount.");
        }
    }
}