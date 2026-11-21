package com.apexucl.personnel;

/**
 * Staff: tax, medical allowance (max 115,000 per year), pension and
 * bonus (max 40,000 per year).
 *
 * Gross = salary + medical allowance + bonus
 * Net   = gross - tax - pension
 */
public class Staff extends Employee {

    public static final double MAX_BONUS = 40_000;

    private final double tax;
    private final double medicalAllowance;
    private final double pension;
    private final double bonus;

    public Staff(int empNo, String name, String address, String city,
                 double salary, String jobTitle,
                 double tax, double medicalAllowance,
                 double pension, double bonus) {
        super(empNo, name, address, city, salary, jobTitle);
        this.tax = requireNonNegative(tax, "Tax");
        this.medicalAllowance = requireMedicalWithinCap(medicalAllowance);
        this.pension = requireNonNegative(pension, "Pension");
        requireNonNegative(bonus, "Bonus");
        if (bonus > MAX_BONUS) {
            throw new IllegalArgumentException(String.format(
                    "Bonus %,.0f exceeds the yearly maximum of %,.0f.", bonus, MAX_BONUS));
        }
        this.bonus = bonus;
    }

    @Override
    public String getCategory() {
        return "Staff";
    }

    @Override
    public double calculateGrossSalary() {
        return getSalary() + medicalAllowance + bonus;
    }

    @Override
    public double calculateNetSalary() {
        return calculateGrossSalary() - tax - pension;
    }

    @Override
    protected void displayAllowances() {
        printAmount("Medical Allowance", medicalAllowance);
        printAmount("Bonus", bonus);
    }

    @Override
    protected void displayDeductions() {
        printAmount("Tax", tax);
        printAmount("Pension", pension);
    }

    public double getTax() { return tax; }
    public double getMedicalAllowance() { return medicalAllowance; }
    public double getPension() { return pension; }
    public double getBonus() { return bonus; }
}
