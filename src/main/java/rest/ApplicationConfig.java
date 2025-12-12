package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api") // Base URL for the REST API
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserController.class);
        resources.add(ProductionController.class);
        resources.add(CountryController.class);

        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);

        return resources;
    }
}

