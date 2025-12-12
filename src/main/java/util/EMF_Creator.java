package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public class EMF_Creator {
    private static EntityManagerFactory emf;
    private static final Object LOCK = new Object();

    public static EntityManagerFactory getEMF() {
        synchronized (LOCK) {
            if (emf == null || !emf.isOpen()) {
                try {
                    // First try loading from persistence.xml
                    emf = Persistence.createEntityManagerFactory("pu");
                    System.out.println("EMF created from persistence.xml");
                } catch (PersistenceException e1) {
                    System.err.println("Failed to load EMF from persistence.xml: " + e1.getMessage());
                    try {
                        // Fallback to hardcoded config
                        Map<String, String> properties = new HashMap<>();
                        properties.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/postgres");
                        properties.put("javax.persistence.jdbc.user", "postgres");
                        properties.put("javax.persistence.jdbc.password", "2585");
                        properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
                        properties.put("eclipselink.logging.level", "WARNING");

                        emf = Persistence.createEntityManagerFactory("pu", properties);
                        System.out.println("EMF created with hardcoded config");
                    } catch (Exception e2) {
                        System.err.println("Failed to create EMF with hardcoded config: " + e2.getMessage());
                        e2.printStackTrace(); // Log the full stack trace for debugging
                        throw new RuntimeException("Failed to create EMF (tried both persistence.xml and hardcoded)", e2);
                    }
                }
            }
            return emf;
        }
    }
}

