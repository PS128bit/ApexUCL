package com.apexucl;

import com.apexucl.personnel.PersonnelModule;

import java.util.Scanner;

/**
 * ApexUCL admin console entry point.
 *
 * Run with --sample to print the Part 3 payroll report straight away:
 *   java -cp out com.apexucl.ApexUCLApp --sample
 */
public class ApexUCLApp {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PersonnelModule personnel = new PersonnelModule(in);

        if (args.length > 0 && args[0].equals("--sample")) {
            personnel.loadSampleData();
            personnel.printReport();
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("========== ApexUCL Admin Console ==========");
            System.out.println("1. Personnel & Payroll");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            if (!in.hasNextLine()) {
                return;
            }
            String choice = in.nextLine().trim();
            if (choice.equals("1")) {
                personnel.run();
            } else if (choice.equals("0")) {
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Enter 1 or 0.");
            }
        }
    }
}
