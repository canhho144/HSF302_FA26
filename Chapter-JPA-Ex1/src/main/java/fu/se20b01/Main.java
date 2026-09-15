package fu.se20b01;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("=== CREATE ===");

        Employee employee = new Employee(
                "Nguyen Van A",
                "nguyenvana@gmail.com",
                new BigDecimal("1500"),
                Gender.MALE,
                LocalDate.of(2024, 1, 15)
        );

        dao.save(employee);

        System.out.println("Created employee:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Salary: " + employee.getSalary());


        System.out.println("\n=== READ ===");

        Employee found = dao.findById(employee.getId());

        if (found != null) {
            System.out.println("Employee found:");
            System.out.println("ID: " + found.getId());
            System.out.println("Name: " + found.getFullName());
            System.out.println("Email: " + found.getEmail());
            System.out.println("Salary: " + found.getSalary());
        } else {
            System.out.println("Employee not found");
        }


        System.out.println("\n=== UPDATE ===");

        found.setSalary(new BigDecimal("2000"));

        dao.update(found);

        System.out.println("Employee salary updated to: "
                + found.getSalary());


        System.out.println("\n=== READ AFTER UPDATE ===");

        Employee updated = dao.findById(employee.getId());

        if (updated != null) {
            System.out.println("Employee after update:");
            System.out.println("ID: " + updated.getId());
            System.out.println("Name: " + updated.getFullName());
            System.out.println("Email: " + updated.getEmail());
            System.out.println("Salary: " + updated.getSalary());
        } else {
            System.out.println("Employee not found");
        }


        System.out.println("\n=== DELETE ===");

        Long deleteId = employee.getId();

        dao.delete(deleteId);

        System.out.println("Employee with ID "
                + deleteId + " has been deleted.");


        System.out.println("\n=== READ AFTER DELETE ===");

        Employee deleted = dao.findById(deleteId);

        if (deleted == null) {
            System.out.println("Employee not found. Delete successful.");
        } else {
            System.out.println("Employee still exists.");
        }


        System.out.println("\n=== TODO 0.9 UNIQUE EMAIL ===");

        Employee employee1 = new Employee(
                "Nguyen Van B",
                "duplicate@gmail.com",
                new BigDecimal("1800"),
                Gender.MALE,
                LocalDate.of(2024, 2, 1)
        );

        Employee employee2 = new Employee(
                "Tran Thi C",
                "duplicate@gmail.com",
                new BigDecimal("1900"),
                Gender.FEMALE,
                LocalDate.of(2024, 3, 1)
        );

        try {
            dao.save(employee1);

            System.out.println("First employee saved successfully.");
            System.out.println("Email: " + employee1.getEmail());

            dao.save(employee2);

            System.out.println("Second employee saved successfully.");
        } catch (Exception ex) {
            System.out.println("ERROR: Email already exists.");
            System.out.println("The second employee could not be saved.");
            System.out.println("Exception: " + ex.getClass().getSimpleName());
        }

        System.out.println("\n=== CRUD DEMO COMPLETED ===");
    }
}

