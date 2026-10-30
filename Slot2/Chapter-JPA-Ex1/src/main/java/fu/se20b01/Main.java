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

        // Entity đang ở trạng thái New/Transient trước khi save()
        dao.save(employee);

        // Sau save(), EntityManager đã đóng nên entity trở thành Detached
        System.out.println("Created employee:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Salary: " + employee.getSalary());

        System.out.println("\n=== READ ===");

        Employee found = dao.findById(employee.getId());

        // Entity được em.find() lấy ra và đang ở trạng thái Managed trong EntityManager
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

        // Entity found từ findById() đã trở thành Detached sau khi EntityManager đóng
        found.setSalary(new BigDecimal("2000"));

        dao.update(found);

        // Object cũ vẫn Detached; object được merge() trả về là Managed trong transaction
        System.out.println("Employee salary updated to: "
                + found.getSalary());

        System.out.println("\n=== READ AFTER UPDATE ===");

        Employee updated = dao.findById(employee.getId());

        // Entity được tìm thấy bởi find() là Managed trong EntityManager của findById()
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

        // Entity được em.remove() chuyển sang Removed trong transaction và bị xóa sau commit()
        System.out.println("Employee with ID "
                + deleteId + " has been deleted.");

        System.out.println("\n=== READ AFTER DELETE ===");

        Employee deleted = dao.findById(deleteId);

        if (deleted == null) {
            System.out.println("Employee not found. Delete successful.");
        } else {
            System.out.println("Employee still exists:");
            System.out.println(deleted);
        }

        System.out.println("\n=== CRUD DEMO COMPLETED ===");
    }
}