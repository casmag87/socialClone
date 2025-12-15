import entities.Role;
import entities.User;
import errorhandling.AuthenticationFailedException;
import facade.UserFacade;
import org.junit.jupiter.api.*;
import util.EMF_Creator;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class userFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    @BeforeAll
    public static void beforeAll(){
        emf = EMF_Creator.getEMF();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        // Optional: close EMF after tests
        emf.close();
    }

    @BeforeEach
    public void setUp() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Clear old data
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();

            // Setup test role and user
            Role userRole = new Role("user");
            em.persist(userRole);

            User testUser = new User("testuser", "password");
            testUser.getRoleList().add(userRole);
            em.persist(testUser);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        // Optional cleanup after each test
    }

    @Test
    public void testGetVerifiedUser_InvalidPassword() {
        assertThrows(AuthenticationFailedException.class, () ->
                facade.getVeryfiedUser("testuser", "wrongpassword"));
    }

    @Test
    public void testCreateUser_Success() {
        User newUser = new User("newuser", "newpass");
        User createdUser = facade.createUser(newUser);

        assertNotNull(createdUser.getName());
        assertEquals("newuser", createdUser.getName());
        assertTrue(createdUser.getRoleList().stream()
                .anyMatch(r -> r.getRoleName().equals("user")));
    }



}
