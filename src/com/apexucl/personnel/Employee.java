package com.apexucl.personnel;

/**
 * Base class for every ApexUCL staff member on the payroll.
 *
 * Employee is abstract: nobody is "just an Employee", each person belongs to
 * exactly one category (Faculty, Staff or ResearchAssistant). The category-
 * specific rules for gross and net salary are declared here as abstract
 * methods and OVERRIDDEN in each subclass.
 */
public abstract class Employee {

    /** Shared rule for Faculty and Staff: medical allowance cap per year. */
    public static final double MAX_MEDICAL_ALLOWANCE = 115_000;

    private final int empNo;
    private final String name;
    private final String address;
    private final String city;
    private final double salary;
    private final String jobTitle;

    protected Employee(int empNo, String name, String address, String city,
                       double salary, String jobTitle) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.empNo = empNo;
        this.name = name;
        this.address = address;
        this.city = city;
        this.salary = salary;
        this.jobTitle = jobTitle;
    }

    // ---- Methods every subclass MUST override (runtime polymorphism) ----

    /** Category label printed in the report, e.g. "Faculty". */
    public abstract String getCategory();

    /** Salary plus every allowance this category earns. */
    public abstract double calculateGrossSalary();

    /** Gross salary minus every deduction this category pays. */
    public abstract double calculateNetSalary();

    /** Prints the allowance lines that appear before Gross Salary. */
    protected abstract void displayAllowances();

    /** Prints the deduction lines that appear after Net Salary. */
    protected abstract void displayDeductions();

    // ---- Shared behaviour ----

    /**
     * Template for the report block. The common lines are printed here; the
     * category-specific lines come from the overridden hooks above, so the
     * same call prints a different block for each subclass.
     */
    public void display() {
        System.out.println("Employee name: " + name);
        System.out.println("Emp No: " + empNo);
        System.out.println("Job Title: " + jobTitle);
        System.out.println("City: " + city);
        printAmount("Salary", salary);
        displayAllowances();
        printAmount("Gross Salary", calculateGrossSalary());
        printAmount("Net Salary", calculateNetSalary());
        displayDeductions();
    }

    /** Overloaded display: optionally prints the category header first. */
    public void display(boolean withCategoryHeader) {
        if (withCategoryHeader) {
            System.out.println("Category: " + getCategory());
        }
        display();
    }

    protected static void printAmount(String label, double amount) {
        System.out.printf("%s: %,.0f%n", label, amount);
    }

    protected static double requireNonNegative(double value, String field) {
        if (value < 0) {
            throw new IllegalArgumentException(field + " cannot be negative.");
        }
        return value;
    }

    protected static double requireMedicalWithinCap(double medical) {
        requireNonNegative(medical, "Medical allowance");
        if (medical > MAX_MEDICAL_ALLOWANCE) {
            throw new IllegalArgumentException(String.format(
                    "Medical allowance %,.0f exceeds the yearly maximum of %,.0f.",
                    medical, MAX_MEDICAL_ALLOWANCE));
        }
        return medical;
    }

    public int getEmpNo() { return empNo; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public double getSalary() { return salary; }
    public String getJobTitle() { return jobTitle; }
}
