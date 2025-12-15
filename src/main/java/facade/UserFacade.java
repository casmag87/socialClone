package facade;

import entities.Role;
import entities.User;
import errorhandling.AuthenticationFailedException;

import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationFailedException{
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (!user.verifyPassword(password)) {
                throw new AuthenticationFailedException("Invalid username or password");
            }
            return user;
        } catch (javax.persistence.NoResultException e) {
            throw new AuthenticationFailedException("Invalid username or password");
        } finally {
            em.close();
        }
    }

    public User createUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Find existing "user" role by primary key
            Role userRole = em.find(Role.class, "user"); // assuming 'name' is the PK
            if (userRole == null) {
                userRole = new Role("user");
                em.persist(userRole);
            }

            // Assign role to user
            user.getRoleList().add(userRole);

            // Persist the user
            em.persist(user);

            em.getTransaction().commit();
            return user;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // propagate exception
        } finally {
            em.close();
        }
    }











}
