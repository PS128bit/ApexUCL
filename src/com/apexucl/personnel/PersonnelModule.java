package com.apexucl.personnel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ApexUCL "Personnel & Payroll" module (Deliverable 4, Part 3).
 *
 * Every employee, whatever its category, is stored in ONE List<Employee>.
 * The report and the payroll totals never check the concrete type: they call
 * getCategory(), display(), calculateGrossSalary() and calculateNetSalary()
 * on an Employee reference and Java picks the overriding method at runtime.
 */
public class PersonnelModule {

    private static final String SEPARATOR = "------------------------------------";
    private static final String[] CATEGORY_ORDER = {"Faculty", "Staff", "Research Assistant"};

    private final List<Employee> employees = new ArrayList<>();
    private final Scanner in;
    private int nextEmpNo = 1001;

    public PersonnelModule(Scanner in) {
        this.in = in;
    }

    /** Module menu shown from the ApexUCL admin console. */
    public void run() {
        while (true) {
            System.out.println();
            System.out.println("=== ApexUCL > Personnel & Payroll ===");
            System.out.println("1. Load sample personnel (4 Faculty, 2 Staff, 3 Research Assistants)");
            System.out.println("2. Enter personnel manually");
            System.out.println("3. Show payroll report");
            System.out.println("0. Back to main menu");
            int choice = readInt("Choose: ", 0, 3);
            switch (choice) {
            case 1:
                loadSampleData();
                System.out.println(employees.size() + " employees on record.");
                break;
            case 2:
                enterEmployees();
                break;
            case 3:
                printReport();
                break;
            case 0:
                return;
            default:
                break;
        }
        }
    }

    // ------------------------------------------------------------------ data

    public void addEmployee(Employee e) {
        employees.add(e); // upcast: a Faculty/Staff/RA stored as an Employee
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    /** Sample set from the problem statement: 4 Faculty, 2 Staff, 3 Research Assistants. */
    public void loadSampleData() {
        employees.clear();
        nextEmpNo = 1001;
        addEmployee(new Faculty(nextEmpNo++, "Umar Ali", "House 12, Street 4, F-7/2", "Islamabad",
                450_000, "Professor", 40_000, 95_000, 10_000, 30_000));
        addEmployee(new Faculty(nextEmpNo++, "Muhammad Ahmad", "Flat 3, Block B, G-11/3", "Islamabad",
                380_000, "Associate Professor", 32_000, 85_000, 9_000, 25_000));
        addEmployee(new Faculty(nextEmpNo++, "Ayesha Khan", "45 Mall Road", "Lahore",
                300_000, "Assistant Professor", 25_000, 70_000, 8_000, 20_000));
        addEmployee(new Faculty(nextEmpNo++, "Hassan Raza", "House 7, Jinnah Town", "Abbottabad",
                200_000, "Lecturer", 15_000, 60_000, 6_000, 10_000));

        addEmployee(new Staff(nextEmpNo++, "Bilal Ahmed", "House 22, Satellite Town", "Rawalpindi",
                150_000, "Fixture Operations Officer", 10_000, 50_000, 5_000, 40_000));
        addEmployee(new Staff(nextEmpNo++, "Sana Malik", "Plot 9, DHA Phase 2", "Karachi",
                120_000, "Administrative Officer", 8_000, 45_000, 4_000, 30_000));

        addEmployee(new ResearchAssistant(nextEmpNo++, "Zainab Tariq", "House 5, University Town", "Peshawar",
                90_000, "Research Assistant (Tie-Breaker Analytics)", 25_000, 8_000));
        addEmployee(new ResearchAssistant(nextEmpNo++, "Usman Shah", "Street 10, Model Town", "Lahore",
                85_000, "Research Assistant (Fixture Scheduling)", 20_000, 12_000));
        // Overloaded constructor: no overtime recorded yet.
        addEmployee(new ResearchAssistant(nextEmpNo++, "Fatima Noor", "House 31, I-8/4", "Islamabad",
                80_000, "Research Assistant (Data Quality)", 18_000));
    }

    // ----------------------------------------------------------------- input

    private void enterEmployees() {
        int faculty = readInt("How many Faculty? ", 0, 100);
        int staff = readInt("How many Staff? ", 0, 100);
        int ra = readInt("How many Research Assistants? ", 0, 100);

        for (int i = 1; i <= faculty; i++) {
            System.out.println("\n-- Faculty " + i + " of " + faculty + " --");
            String[] c = readCommonFields();
            double salary = readAmount("Salary: ", Double.MAX_VALUE);
            double tax = readAmount("Tax: ", Double.MAX_VALUE);
            double medical = readAmount("Medical allowance (max 115,000): ", Employee.MAX_MEDICAL_ALLOWANCE);
            double pension = readAmount("Pension: ", Double.MAX_VALUE);
            double research = readAmount("Research allowance: ", Double.MAX_VALUE);
            addEmployee(new Faculty(nextEmpNo++, c[0], c[1], c[2], salary, c[3],
                    tax, medical, pension, research));
        }
        for (int i = 1; i <= staff; i++) {
            System.out.println("\n-- Staff " + i + " of " + staff + " --");
            String[] c = readCommonFields();
            double salary = readAmount("Salary: ", Double.MAX_VALUE);
            double tax = readAmount("Tax: ", Double.MAX_VALUE);
            double medical = readAmount("Medical allowance (max 115,000): ", Employee.MAX_MEDICAL_ALLOWANCE);
            double pension = readAmount("Pension: ", Double.MAX_VALUE);
            double bonus = readAmount("Bonus (max 40,000): ", Staff.MAX_BONUS);
            addEmployee(new Staff(nextEmpNo++, c[0], c[1], c[2], salary, c[3],
                    tax, medical, pension, bonus));
        }
        for (int i = 1; i <= ra; i++) {
            System.out.println("\n-- Research Assistant " + i + " of " + ra + " --");
            String[] c = readCommonFields();
            double salary = readAmount("Salary: ", Double.MAX_VALUE);
            double fund = readAmount("Research fund: ", Double.MAX_VALUE);
            double overtime = readAmount("Overtime: ", Double.MAX_VALUE);
            addEmployee(new ResearchAssistant(nextEmpNo++, c[0], c[1], c[2], salary, c[3],
                    fund, overtime));
        }
        System.out.println(employees.size() + " employees on record.");
    }

    /** Returns {name, address, city, jobTitle}. */
    private String[] readCommonFields() {
        String name = readText("Name: ");
        String address = readText("Address: ");
        String city = readText("City: ");
        String jobTitle = readText("Job title: ");
        return new String[] {name, address, city, jobTitle};
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("  Please enter a value.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the message below
            }
            System.out.println("  Enter a whole number from " + min + " to " + max + ".");
        }
    }

    private double readAmount(String prompt, double max) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim().replace(",", "");
            try {
                double value = Double.parseDouble(line);
                if (value < 0) {
                    System.out.println("  Amount cannot be negative.");
                } else if (value > max) {
                    System.out.printf("  Amount cannot exceed %,.0f per year.%n", max);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Enter a number, e.g. 95000 or 95,000.");
            }
        }
    }

    // ---------------------------------------------------------------- report

    /** Prints every employee grouped by category, then polymorphic payroll totals. */
    public void printReport() {
        if (employees.isEmpty()) {
            System.out.println("No employees on record. Load the sample or enter personnel first.");
            return;
        }
        System.out.println();
        for (String category : CATEGORY_ORDER) {
            boolean headerPrinted = false;
            for (Employee e : employees) {
                if (!e.getCategory().equals(category)) {
                    continue;
                }
                if (!headerPrinted) {
                    System.out.println("Category: " + category);
                    headerPrinted = true;
                }
                e.display();              // dynamic dispatch picks the subclass's version
                System.out.println(SEPARATOR);
            }
        }

        double totalGross = 0;
        double totalNet = 0;
        for (Employee e : employees) {   // same loop, no instanceof, three different rules
            totalGross += e.calculateGrossSalary();
            totalNet += e.calculateNetSalary();
        }
        System.out.println("Payroll summary (" + employees.size() + " employees)");
        System.out.printf("Total Gross Salary: %,.0f%n", totalGross);
        System.out.printf("Total Net Salary: %,.0f%n", totalNet);
    }
}
