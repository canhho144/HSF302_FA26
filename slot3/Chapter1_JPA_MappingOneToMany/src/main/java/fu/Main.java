package fu;

import fu.dao.DepartmentDAO;
import fu.pojo.Department;
import fu.pojo.Employee;
import fu.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Department dept = new Department("IT", "Ha Noi");

        Employee emp = new Employee(
                "Test",
                "test@company.com",
                Gender.OTHER,
                true,
                new BigDecimal("1000"),
                LocalDate.now()
        );

        dept.addEmployee(emp);

        DepartmentDAO departmentDAO = new DepartmentDAO();

        departmentDAO.save(dept);

        Department result =
                departmentDAO.findByIdWithEmployees(dept.getId());

        System.out.println("Department: " + result.getName());
        System.out.println("Number of employees: "
                + result.getEmployees().size());
    }
}