package anand.learn.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidUserExceptionMapper
        implements ExceptionMapper<InvalidUserException> {
    @Override
    public Response toResponse(InvalidUserException exception) {
        ErrorMessage errorMessage = new ErrorMessage(400, exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
