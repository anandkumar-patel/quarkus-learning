package anand.learn;

import anand.learn.entity.User;
import anand.learn.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        return Response.ok(userService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long userId) {
        User user = userService.findById(userId)
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
        validateUser(user);
        userService.create(user);

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
            @PathParam("id") Long existingUserId,
            User newUser) {
        validateUser(newUser);
        User updatedUser = userService.update(existingUserId, newUser)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User with id " + existingUserId + " not found"
                        )
                );

        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{deleteUserId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(
            @PathParam("deleteUserId") Long deleteUserId) {

        if (!userService.delete(deleteUserId)) {
            throw new UserNotFoundException(
                    "User with id " + deleteUserId + " not found"
            );
        }

        return Response.ok("User deleted").build();
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User body is required");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidUserException("Email is required");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new InvalidUserException("Name is required");
        }
    }
}
