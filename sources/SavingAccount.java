public class SavingAccount extends BankAccount {

    private double interestRate;

    private static final double MIN_BALANCE = 50000.0;

    public SavingAccount(
            String accountNumber,
            String holderName,
            double balance,
            double interestRate
    ) throws InvalidAmountException {

        super(accountNumber, holderName, balance);

        if (balance < MIN_BALANCE) {
            throw new InvalidAmountException(
                    "Tai khoan tiet kiem phai co so du toi thieu 50,000 VND.");
        }

        if (interestRate < 0) {
            throw new InvalidAmountException(
                    "Lai suat khong duoc am.");
        }

        this.interestRate = interestRate;
        checkSavingInvariant();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate)
            throws InvalidAmountException {

        if (interestRate < 0) {
            throw new InvalidAmountException(
                    "Lai suat khong duoc am.");
        }

        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException {

        // Pre-condition
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "So tien rut phai lon hon 0.");
        }

        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientBalanceException(
                    "Khong the rut. So du sau khi rut phai >= 50,000 VND.");
        }

        double balanceOld = getBalance();

        decreaseBalance(amount);

        // Post-condition
        if (getBalance() != balanceOld - amount) {
            throw new IllegalStateException(
                    "Vi pham post-condition cua withdraw().");
        }

        // Invariant
        checkSavingInvariant();
    }

    private void checkSavingInvariant() {
        if (getBalance() < MIN_BALANCE) {
            throw new IllegalStateException(
                    "Vi pham invariant tai khoan tiet kiem: "
                    + "balance phai >= 50,000 VND.");
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Lai suat: " + String.format("%.2f%%", interestRate);
    }
}
