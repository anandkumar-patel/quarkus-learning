package anand.learn;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;

@Path("/tvseries")
public class TvSeriesResource {
    Logger log = LoggerFactory.getLogger(TvSeriesResource.class);

    @Inject
    @RestClient
    TvSeriesProxy proxy;

    @GET
    @Path("/{id}")
    @Timeout(unit = ChronoUnit.SECONDS, value = 2)
    @Retry(delayUnit = ChronoUnit.SECONDS, maxRetries = 2, delay = 1)
    @Fallback(fallbackMethod = "getTvSeriesByIdFallback")
    @CircuitBreaker(delayUnit = ChronoUnit.SECONDS, requestVolumeThreshold = 4,
            failureRatio = .75, delay = 3,
            successThreshold = 2
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