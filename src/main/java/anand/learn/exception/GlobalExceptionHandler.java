package anand.learn.exception;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response handleUserNotFound(
            UserNotFoundException ex) {

        ErrorMessage error = new ErrorMessage(
                404,
                ex.getMessage()
        );

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }


    @ServerExceptionMapper
    public Response handleInvalidUser(InvalidUserException ex) {

        ErrorMessage error = new ErrorMessage(
                400,
                ex.getMessage()
        );

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }

    @ServerExceptionMapper
    public Response handleGenericException(Exception ex) {

        ErrorMessage error = new ErrorMessage(
                500,
                "Internal server error"
        );

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
