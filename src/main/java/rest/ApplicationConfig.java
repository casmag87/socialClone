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
        resources.add(cors.CorsFilter.class);
        resources.add(errorhandling.AuthenticationFailedException.class);
        resources.add(errorhandling.API_Exception.class);
        resources.add(UserResource.class);
        resources.add(PostResource.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.LoginEndpoint.class);


        return resources;
    }
}

