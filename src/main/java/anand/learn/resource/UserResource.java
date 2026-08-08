package anand.learn.resource;

import anand.learn.entity.User;
import anand.learn.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/user")
public class UserResource {

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        return Response.ok(userRepository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long userId) {
        return userRepository.findByIdOptional(userId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        userRepository.persist(user);
        if (userRepository.isPersistent(user)) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") long existingUserId,
                               User newUser) {
        Optional<User> existingUserOptional = userRepository.findByIdOptional(existingUserId);
        if(existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update existing user with new values
            existingUser.setName(newUser.getName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setAge(newUser.getAge());
            userRepository.persist(existingUser);
            if (userRepository.isPersistent(existingUser)) {
                return Response.ok(existingUser).build();
            }
            return Response.ok(existingUser).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Transactional
    @Path("/{deleteUserId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("deleteUserId") long deleteUserId) {
        boolean isDeleted = userRepository.deleteById(deleteUserId);
        if (isDeleted) {
            return Response.ok("User deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
