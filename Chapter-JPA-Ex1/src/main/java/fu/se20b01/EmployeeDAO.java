package fu.se20b01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmployeeDAO {

    private final EntityManagerFactory emf;

    public EmployeeDAO() {
        emf = Persistence.createEntityManagerFactory("defaultPU");
    }

    public void save(Employee e) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw ex;

        } finally {

            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}