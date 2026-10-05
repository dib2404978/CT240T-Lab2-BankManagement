import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BankManager manager = new BankManager();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            printMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addSavingAccount();
                    case 2 -> findAccount();
                    case 3 -> deposit();
                    case 4 -> withdraw();
                    case 5 -> transfer();
                    case 6 -> manager.displayAllAccounts();
                    case 7 -> calculateTotalBalance();
                    case 0 -> {
                        running = false;
                        System.out.println("Da thoat chuong trinh.");
                    }
                    default -> System.out.println(
                            "Lua chon khong hop le.");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Loi: Vui long nhap so hop le.");

            } catch (InvalidAmountException |
                     InsufficientBalanceException e) {

                System.out.println("Loi nghiep vu: " + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println("Loi: " + e.getMessage());

            } catch (Exception e) {

                System.out.println(
                        "Loi khong xac dinh: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {

        System.out.println("========================================");
        System.out.println("   BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Them tai khoan tiet kiem");
        System.out.println("2. Tim tai khoan");
        System.out.println("3. Nap tien");
        System.out.println("4. Rut tien");
        System.out.println("5. Chuyen tien");
        System.out.println("6. Hien thi tat ca tai khoan");
        System.out.println("7. Tinh tong so du");
        System.out.println("0. Thoat");
        System.out.println("========================================");
        System.out.print("Nhap lua chon: ");
    }

    private static void addSavingAccount()
            throws InvalidAmountException {

        System.out.print("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Nhap ten chu tai khoan: ");
        String holderName = scanner.nextLine();

        System.out.print("Nhap so du ban dau (>= 50000): ");
        double balance = Double.parseDouble(scanner.nextLine());

        System.out.print("Nhap lai suat (%): ");
        double interestRate = Double.parseDouble(scanner.nextLine());

        SavingAccount account = new SavingAccount(
                accountNumber,
                holderName,
                balance,
                interestRate
        );

        manager.addAccount(account);

        System.out.println("Them tai khoan thanh cong!");
    }

    private static void findAccount() {

        System.out.print("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = manager.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Khong tim thay tai khoan.");
        } else {
            System.out.println(account);
        }
    }

    private static void deposit()
            throws InvalidAmountException {

        System.out.print("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = manager.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Khong tim thay tai khoan.");
            return;
        }

        System.out.print("Nhap so tien nap: ");
        double amount = Double.parseDouble(scanner.nextLine());

        account.deposit(amount);

        System.out.println("Nap tien thanh cong.");
        System.out.println("So du moi: " + account.getBalance());
    }

    private static void withdraw()
            throws InvalidAmountException,
                   InsufficientBalanceException {

        System.out.print("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = manager.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Khong tim thay tai khoan.");
            return;
        }

        System.out.print("Nhap so tien rut: ");
        double amount = Double.parseDouble(scanner.nextLine());

        account.withdraw(amount);

        System.out.println("Rut tien thanh cong.");
        System.out.println("So du moi: " + account.getBalance());
    }

    private static void transfer()
            throws InvalidAmountException,
                   InsufficientBalanceException {

        System.out.print("Nhap tai khoan nguon: ");
        String fromAcc = scanner.nextLine();

        System.out.print("Nhap tai khoan dich: ");
        String toAcc = scanner.nextLine();

        System.out.print("Nhap so tien chuyen: ");
        double amount = Double.parseDouble(scanner.nextLine());

        manager.transferMoney(fromAcc, toAcc, amount);

        System.out.println("Chuyen tien thanh cong.");
    }

    private static void calculateTotalBalance() {

        List<BankAccount> accounts = manager.getAllAccounts();

        double total = manager.calculateTotalBalance(accounts);

        System.out.printf(
                "Tong so du toan he thong: %.2f%n",
                total
        );
    }
}
