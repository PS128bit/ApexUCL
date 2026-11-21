package com.apexucl.personnel;

/**
 * Faculty: tax, medical allowance (max 115,000 per year), pension and
 * research allowance.
 *
 * Gross = salary + medical allowance + research allowance
 * Net   = gross - tax - pension
 */
public class Faculty extends Employee {

    private final double tax;
    private final double medicalAllowance;
    private final double pension;
    private final double researchAllowance;

    public Faculty(int empNo, String name, String address, String city,
                   double salary, String jobTitle,
                   double tax, double medicalAllowance,
                   double pension, double researchAllowance) {
        super(empNo, name, address, city, salary, jobTitle);
        this.tax = requireNonNegative(tax, "Tax");
        this.medicalAllowance = requireMedicalWithinCap(medicalAllowance);
        this.pension = requireNonNegative(pension, "Pension");
        this.researchAllowance = requireNonNegative(researchAllowance, "Research allowance");
    }

    @Override
    public String getCategory() {
        return "Faculty";
    }

    @Override
    public double calculateGrossSalary() {
        return getSalary() + medicalAllowance + researchAllowance;
    }

    @Override
    public double calculateNetSalary() {
        return calculateGrossSalary() - tax - pension;
    }

    @Override
    protected void displayAllowances() {
        printAmount("Medical Allowance", medicalAllowance);
        printAmount("Research Allowance", researchAllowance);
    }

    @Override
    protected void displayDeductions() {
        printAmount("Tax", tax);
        printAmount("Pension", pension);
    }

    public double getTax() { return tax; }
    public double getMedicalAllowance() { return medicalAllowance; }
    public double getPension() { return pension; }
    public double getResearchAllowance() { return researchAllowance; }
}
