package rest;

import DTO.PostDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.PostFacade;
import facade.UserFacade;
import util.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("posts")
public class PostResource {

    private static final EntityManagerFactory EMF = EMF_Creator.getEMF();
    private final PostFacade postFacade = PostFacade.getPostFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

   @POST
   @RolesAllowed("user")
   @Produces(MediaType.APPLICATION_JSON)
   public Response createPost(PostDto postDto) {
       String username = securityContext.getUserPrincipal().getName();
       PostDto created = postFacade.createUserPost(username, postDto);
       return Response.status(Response.Status.CREATED).entity(created).build();
   }


}
