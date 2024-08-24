import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Account abstract class
abstract class Account {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public abstract String getAccountType();
}

// SavingsAccount class
class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    //@Override
    public String getAccountType() {
        return "Savings";
    }
}

// CheckingAccount class
class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    //@Override
    public String getAccountType() {
        return "Checking";
    }
}

// Transaction class
class Transaction {
    private String accountNumber;
    private double amount;
    private String type;

    public Transaction(String accountNumber, double amount, String type) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
    }

    public String getTransactionDetails() {
        return type + ": " + amount + " on Account " + accountNumber;
    }
}

// Loan class
class Loan {
    private String accountNumber;
    private double loanAmount;
    private double interestRate;
    private int termMonths;

    public Loan(String accountNumber, double loanAmount, double interestRate, int termMonths) {
        this.accountNumber = accountNumber;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = interestRate / 12 / 100;
        double denominator = Math.pow(1 + monthlyRate, termMonths) - 1;
        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) / denominator;
    }

    public void displayLoanDetails() {
        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("Monthly Payment: " + calculateMonthlyPayment());
    }
}

// BankingSystem class to manage accounts, transfers, and balance inquiry
class BankingSystem {
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    // Create Account
    public Account createAccount(String type, String accountNumber, double initialBalance) {
        Account account = null;
        if (type.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accountNumber, initialBalance);
        } else if (type.equalsIgnoreCase("Checking")) {
            account = new CheckingAccount(accountNumber, initialBalance);
        }
        if (account != null) {
            accounts.add(account);
        }
        return account;
    }

    // Transfer Funds
    public void transferFunds(String fromAccount, String toAccount, double amount) {
        Account from = findAccount(fromAccount);
        Account to = findAccount(toAccount);
        if (from != null && to != null && from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
            transactions.add(new Transaction(fromAccount, amount, "Transfer to " + toAccount));
            transactions.add(new Transaction(toAccount, amount, "Transfer from " + fromAccount));
            System.out.println("Transfer Successful.");
        } else {
            System.out.println("Transfer failed. Check account details or balance.");
        }
    }

    // Find Account
    private Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Get Balance
    public double getBalance(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            return account.getBalance();
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    // Display Transactions
    public void displayTransactions() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getTransactionDetails());
        }
    }
}

// Main class with a user-choice menu
public class account_management_system {
    public static void main(String[] args) {
        BankingSystem system = new BankingSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Online Banking System Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. View Balance");
            System.out.println("3. Transfer Funds");
            System.out.println("4. View Transaction History");
            System.out.println("5. Apply for Loan");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account type (Savings/Checking): ");
                    String accountType = scanner.next();
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter initial deposit: ");
                    double initialBalance = scanner.nextDouble();
                    system.createAccount(accountType, accountNumber, initialBalance);
                    System.out.println(accountType + " account created successfully.");
                    break;

                case 2:
                    System.out.print("Enter account number to check balance: ");
                    String balanceAccount = scanner.next();
                    double balance = system.getBalance(balanceAccount);
                    if (balance != -1) {
                        System.out.println("Balance for account " + balanceAccount + ": " + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter source account number: ");
                    String fromAccount = scanner.next();
                    System.out.print("Enter target account number: ");
                    String toAccount = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double amount = scanner.nextDouble();
                    system.transferFunds(fromAccount, toAccount, amount);
                    break;

                case 4:
                    system.displayTransactions();
                    break;

                case 5:
                    System.out.print("Enter account number to apply for a loan: ");
                    String loanAccount = scanner.next();
                    System.out.print("Enter loan amount: ");
                    double loanAmount = scanner.nextDouble();
                    System.out.print("Enter interest rate: ");
                    double interestRate = scanner.nextDouble();
                    System.out.print("Enter loan term in months: ");
                    int termMonths = scanner.nextInt();

                    Loan loan = new Loan(loanAccount, loanAmount, interestRate, termMonths);
                    loan.displayLoanDetails();
                    break;

                case 6:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}
