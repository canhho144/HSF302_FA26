package fu.dao;

import fu.pojo.Department;
import fu.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class DepartmentDAO {

    public void save(Department department) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(department);
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

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM Department d",
                    Department.class
            ).getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Department.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    public Department update(Department department) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            department = em.merge(department);
            transaction.commit();
            return department;
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

            Department department = em.find(Department.class, id);

            if (department != null) {
                em.remove(department);
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