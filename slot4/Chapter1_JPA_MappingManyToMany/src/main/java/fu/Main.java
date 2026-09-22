package fu;

import fu.pojo.Department;
import fu.pojo.Employee;
import fu.pojo.Gender;
import fu.pojo.Project;
import fu.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Department department = new Department();
            department.setName("IT");
            em.persist(department);

            Employee employee1 = new Employee(
                    "Nguyen Van A",
                    "a@gmail.com",
                    Gender.MALE,
                    true,
                    new BigDecimal("1500.00"),
                    LocalDate.of(2022, 1, 10)
            );

            Employee employee2 = new Employee(
                    "Tran Thi B",
                    "b@gmail.com",
                    Gender.FEMALE,
                    true,
                    new BigDecimal("1800.00"),
                    LocalDate.of(2023, 3, 15)
            );

            Employee employee3 = new Employee(
                    "Le Van C",
                    "c@gmail.com",
                    Gender.MALE,
                    true,
                    new BigDecimal("2000.00"),
                    LocalDate.of(2024, 5, 20)
            );

            employee1.setDepartment(department);
            employee2.setDepartment(department);
            employee3.setDepartment(department);

            Project projectA = new Project(
                    "P001",
                    "Project A",
                    new BigDecimal("50000.00"),
                    LocalDate.of(2026, 1, 1),
                    null
            );

            Project projectB = new Project(
                    "P002",
                    "Project B",
                    new BigDecimal("80000.00"),
                    LocalDate.of(2026, 2, 1),
                    null
            );

            em.persist(projectA);
            em.persist(projectB);

            em.persist(employee1);
            em.persist(employee2);
            em.persist(employee3);

            employee1.assignToProject(projectA);
            employee1.assignToProject(projectB);

            employee2.assignToProject(projectB);

            employee3.assignToProject(projectA);

            transaction.commit();

            System.out.println("=== TODO 5.9: UNASSIGN EMPLOYEE FROM PROJECT ===");

            System.out.println("Before unassign:");
            System.out.println("Employee 1 projects: " + employee1.getProjects().size());
            System.out.println("Project B employees: " + projectB.getEmployees().size());

            employee1.unassignFromProject(projectB);

            System.out.println("After unassign:");
            System.out.println("Employee 1 projects: " + employee1.getProjects().size());
            System.out.println("Project B employees: " + projectB.getEmployees().size());

            System.out.println("Employee 1 still exists: " + (employee1.getId() != null));
            System.out.println("Project B still exists: " + (projectB.getId() != null));

            System.out.println("=== TODO 5.8: ACTIVE EMPLOYEES PER PROJECT ===");

            String jpql = """
                    SELECT p.projectName, COUNT(e), SUM(e.salary)
                    FROM Project p JOIN p.employees e
                    WHERE e.active = true
                    GROUP BY p.projectName
                    """;

            var results = em.createQuery(jpql, Object[].class)
                    .getResultList();

            for (Object[] row : results) {
                System.out.println(
                        "Project: " + row[0]
                                + " | Active employees: " + row[1]
                                + " | Total salary: " + row[2]
                );
            }

            System.out.println("=== PROJECTS OF EACH EMPLOYEE ===");

            System.out.println("Employee: " + employee1.getFullName());
            for (Project project : employee1.getProjects()) {
                System.out.println(
                        "  - " + project.getProjectCode()
                                + " - " + project.getProjectName()
                );
            }

            System.out.println("Employee: " + employee2.getFullName());
            for (Project project : employee2.getProjects()) {
                System.out.println(
                        "  - " + project.getProjectCode()
                                + " - " + project.getProjectName()
                );
            }

            System.out.println("Employee: " + employee3.getFullName());
            for (Project project : employee3.getProjects()) {
                System.out.println(
                        "  - " + project.getProjectCode()
                                + " - " + project.getProjectName()
                );
            }

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}