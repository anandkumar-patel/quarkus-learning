package anand.learn;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/show")
@RegisterRestClient(baseUri = "https://api.tvmaze.com")
public interface TvSeriesProxy {
    @GET
    @Path("/{id}")
    TvSeries getTvSeriesById(@PathParam("id") int id);
}
