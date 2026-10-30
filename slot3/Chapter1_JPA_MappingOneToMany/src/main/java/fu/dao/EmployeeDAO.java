package fu.dao;

import fu.pojo.Employee;
import fu.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Employee e",
                    Employee.class
            ).getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Employee.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public Employee update(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            employee = em.merge(employee);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Employee employee = em.find(Employee.class, id);

            if (employee != null) {
                em.remove(employee);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}