package anand.learn;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/user")
public class UserResource {

    private final List<User> users = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int userId) {

        User user = users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User with id " + userId + " not found"
                        )
                );

        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidUserException("Email is required");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuntimeException("Name is required, generic exception");
        }
        users.add(user);

        return Response
                .status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(
            @PathParam("id") int existingUserId,
            User newUser) {

        User existingUser = users.stream()
                .filter(user -> user.getId() == existingUserId)
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User with id " + existingUserId + " not found"
                        )
                );
        existingUser.setName(newUser.getName());
        existingUser.setAge(newUser.getAge());
        existingUser.setEmail(newUser.getEmail());

        return Response.ok(existingUser).build();
    }

    @DELETE
    @Path("/{deleteUserId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(
            @PathParam("deleteUserId") int deleteUserId) {

        User user = users.stream()
                .filter(u -> u.getId() == deleteUserId)
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User with id " + deleteUserId + " not found"
                        )
                );

        users.remove(user);

        return Response.ok("User deleted").build();
    }
}