package facade;

import DTO.PostDto;
import entities.Post;
import entities.Post;
import entities.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;

public class PostFacade {

    private static EntityManagerFactory emf;
    private static PostFacade instance;

    private PostFacade() {
    }

    public static PostFacade getPostFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PostFacade();
        }
        return instance;
    }


    public PostDto createUserPost(String userName, PostDto postDto) {
        if (postDto.getText() == null) {
            throw new WebApplicationException("Post id and text are required", 400);
        }

        EntityManager em = emf.createEntityManager();


        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.name = :name", User.class
            );
            query.setParameter("name", userName);

            User user;
            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                throw new WebApplicationException("User not found", 404);
            }

            // Begin transaction
            em.getTransaction().begin();

            // Create a new Post entity from the DTO
            Post post = new Post();
            post.setId(postDto.getId());
            post.setText(postDto.getText());
            post.setUser(user);  // associate post with user

            // Persist the post
            em.persist(post);

            // Add post to user's list if bidirectional
            if (user.getPosts() != null) {
                user.getPosts().add(post);
            }

            em.getTransaction().commit();

            // Convert back to DTO to return
            PostDto resultDto = new PostDto();
            resultDto.setId(post.getId());
            resultDto.setText(post.getText());
            resultDto.setUsername(user.getName());

            return resultDto;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }





}
