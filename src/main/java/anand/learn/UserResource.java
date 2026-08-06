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
        return users.stream().filter(user-> user.getId() == userId)
                .findFirst()
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        users.add(user);
        return Response.ok(user).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int existingUserId,
                               User newUser) {
        return users.stream()
                .filter(user -> user.getId() == existingUserId)
                .findFirst()
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setAge(newUser.getAge());
                    user.setEmail(newUser.getEmail());

                    return Response.ok(user).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{deleteUserId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("deleteUserId") int deleteUserId) {
            //use stream
            return users.stream()
                    .filter(user -> user.getId() == deleteUserId)
                    .findFirst()
                    .map(user -> {
                        users.remove(user);
                        return Response.ok("User deleted").build();
                    })
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }
}
