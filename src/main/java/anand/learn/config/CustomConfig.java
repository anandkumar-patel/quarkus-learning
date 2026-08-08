package anand.learn.config;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ConfigProperties(prefix = "anand.banking")
public class CustomConfig {
    @ConfigProperty(name = "bank_name", defaultValue = "Anand Bank")
    public String bankName;
    @ConfigProperty(name = "head_office", defaultValue = "India")
    public String headOffice;
    @ConfigProperty(name = "stock_listing")
    public List<String> stockListing;
}
