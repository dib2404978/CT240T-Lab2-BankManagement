public abstract class BankAccount {

    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance)
            throws InvalidAmountException {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("So tai khoan khong duoc rong.");
        }

        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten chu tai khoan khong duoc rong.");
        }

        if (balance < 0) {
            throw new InvalidAmountException("So du ban dau khong duoc am.");
        }

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;

        checkInvariant();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("So tai khoan khong duoc rong.");
        }
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten chu tai khoan khong duoc rong.");
        }
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) throws InvalidAmountException {
        if (balance < 0) {
            throw new InvalidAmountException("So du khong duoc am.");
        }
        this.balance = balance;
        checkInvariant();
    }

    /**
     * Nap tien.
     *
     * @param amount so tien can nap
     * @throws InvalidAmountException neu amount <= 0
     */
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "So tien nap phai lon hon 0.");
        }

        double balanceOld = balance;

        balance += amount;

        if (balance != balanceOld + amount) {
            throw new IllegalStateException(
                    "Vi pham post-condition cua deposit().");
        }

        checkInvariant();
    }

    /**
     * Rut tien khoi tai khoan.
     *
     * @param amount so tien can rut
     * @throws InsufficientBalanceException neu so du khong du
     * @throws InvalidAmountException neu amount <= 0
     */
    public abstract void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException;

    protected void decreaseBalance(double amount) {
        balance -= amount;
        checkInvariant();
    }

    protected void checkInvariant() {
        if (balance < 0) {
            throw new IllegalStateException(
                    "Vi pham invariant: balance phai >= 0.");
        }
    }

    @Override
    public String toString() {
        return "So TK: " + accountNumber
                + " | Chu TK: " + holderName
                + " | So du: " + String.format("%.2f", balance);
    }
}
