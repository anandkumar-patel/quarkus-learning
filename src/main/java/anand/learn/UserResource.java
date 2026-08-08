package anand.learn;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/user")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        return Response.ok(User.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long userId) {
        return User.findByIdOptional(userId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        user.persist();
        return Response.ok(user).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") long existingUserId,
                               User newUser) {
        Optional<User> existingUserOptional = User.findByIdOptional(existingUserId);
        if(existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update existing user with new values
            existingUser.setName(newUser.getName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setAge(newUser.getAge());
            existingUser.persist();
            if (existingUser.isPersistent()) {
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
        boolean isDeleted = User.deleteById(deleteUserId);
        if (isDeleted) {
            return Response.ok("User deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
