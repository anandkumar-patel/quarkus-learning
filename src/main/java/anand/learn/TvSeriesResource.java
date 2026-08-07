package anand.learn;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/tvseries")
public class TvSeriesResource {
    Logger log = LoggerFactory.getLogger(TvSeriesResource.class);

    @Inject
    @RestClient
    TvSeriesProxy proxy;

    @GET
    @Path("/{id}")
    @Fallback(
        fallbackMethod = "getTvSeriesByIdFallback",
            applyOn = {Exception.class},
            skipOn = {}

    )
    @Retry(
        retryOn = {Exception.class},
        maxRetries = 3,
        maxDuration = 10000,
        abortOn = {}, delay = 1000
    )
    @CircuitBreaker(
        requestVolumeThreshold=4,
        failureRatio=0.6,
        successThreshold=2,
        failOn = {Exception.class},
        skipOn = {}
    )
    public Response getTvSeriesById(@PathParam("id") int id) {
        log.info("calling the tv series service from my app...");
        return Response.ok(proxy.getTvSeriesById(id)).build();
    }

    public Response getTvSeriesByIdFallback(int id) {
        // Implement fallback logic here
        return Response.ok("no result found").build(); // Replace with actual fallback implementation
    }
}