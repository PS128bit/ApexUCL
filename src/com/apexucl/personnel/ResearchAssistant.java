package com.apexucl.personnel;

/**
 * Research Assistant: research fund and overtime. The problem statement
 * gives Research Assistants no tax or pension, so net equals gross.
 *
 * Gross = salary + research fund + overtime
 * Net   = gross
 */
public class ResearchAssistant extends Employee {

    private final double researchFund;
    private final double overtime;

    public ResearchAssistant(int empNo, String name, String address, String city,
                             double salary, String jobTitle,
                             double researchFund, double overtime) {
        super(empNo, name, address, city, salary, jobTitle);
        this.researchFund = requireNonNegative(researchFund, "Research fund");
        this.overtime = requireNonNegative(overtime, "Overtime");
    }

    /** Overloaded constructor: a Research Assistant with no overtime yet. */
    public ResearchAssistant(int empNo, String name, String address, String city,
                             double salary, String jobTitle, double researchFund) {
        this(empNo, name, address, city, salary, jobTitle, researchFund, 0);
    }

    @Override
    public String getCategory() {
        return "Research Assistant";
    }

    @Override
    public double calculateGrossSalary() {
        return getSalary() + researchFund + overtime;
    }

    @Override
    public double calculateNetSalary() {
        return calculateGrossSalary();
    }

    @Override
    protected void displayAllowances() {
        printAmount("Research Fund", researchFund);
        printAmount("Overtime", overtime);
    }

    @Override
    protected void displayDeductions() {
        // No deductions defined for Research Assistants.
    }

    public double getResearchFund() { return researchFund; }
    public double getOvertime() { return overtime; }
}
