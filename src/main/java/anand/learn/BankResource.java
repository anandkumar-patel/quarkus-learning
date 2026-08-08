package anand.learn;

import anand.learn.config.CustomConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/bank")
public class BankResource {

    @Inject
    @ConfigProperties(prefix = "anand.banking")
    CustomConfig customConfig;

    @ConfigProperty(name = "ceo", defaultValue = "Anand Patel")
    String ceo;

    @GET
    @Path("/ceo")
    public Response getCEO() {
        return Response.ok(ceo).build();
    }

    @GET
    @Path("/branch/{branch}")
    public Response getBranchIfExist(@PathParam("branch") String branch) {
        List<String> branchList = ConfigProvider.getConfig()
                .getValues("branch_list", String.class);

        String branchName = branchList.stream()
                .filter(b -> b.equalsIgnoreCase(branch))
                .findFirst()
                .orElse("branch not found");

        return Response.ok(branchName).build();
    }

    @GET
    @Path("/interest/{branch}/{amount}")
    public Response getInterestRate(@PathParam("branch") String branch, @PathParam("amount") Double amount) {
        // Implementation for getting interest rate based on branch and amount
        // example : jaipur_interest_rate=13
        Integer interestRateByBranch = ConfigProvider.getConfig()
                .getValue(branch.toLowerCase() + "_interest_rate", Integer.class);
        return Response.ok(amount* interestRateByBranch /100).build();
    }

    @GET
    @Path("/custom-config")
    public Response getCustomConfig() {
        return Response.ok(customConfig.stockListing).build();
    }
}
