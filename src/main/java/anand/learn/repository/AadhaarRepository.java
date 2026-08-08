package anand.learn.repository;

import anand.learn.entity.Aadhaar;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AadhaarRepository implements PanacheRepository<Aadhaar> {
}
