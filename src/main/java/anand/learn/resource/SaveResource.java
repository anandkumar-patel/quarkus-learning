package anand.learn.resource;

import anand.learn.entity.Aadhaar;
import anand.learn.entity.User;
import anand.learn.repository.AadhaarRepository;
import anand.learn.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/person")
public class SaveResource {

    @Inject
    UserRepository userRepository;
    @Inject
    AadhaarRepository aadhaarRepository;

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response save() {
        User user = new User();
        user.setName("anand");
        user.setAge(35);
        user.setEmail("anand@gmail.com");

        Aadhaar aadhaar = new Aadhaar();
        aadhaar.setAadhaarNumber(123456789012L);
        aadhaar.setAddress("123 Main Street");
        aadhaar.setUser(user);

        userRepository.persist(user);
        aadhaarRepository.persist(aadhaar);
        if (userRepository.isPersistent(user) && aadhaarRepository.isPersistent(aadhaar)) {
            return Response.ok("user and aadhaar created successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/aadhaar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAadhaarById(@PathParam("id") Long id) {
        Aadhaar aadhaar = aadhaarRepository.findById(id);
        if (aadhaar != null) {
            return Response.ok(aadhaar).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
