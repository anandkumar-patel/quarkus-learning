package anand.learn.lifecycle;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Startup
public class CarService {
    @PostConstruct
    public void startEngine(){
        System.out.println("@Startup->@PostConstruct :: CarService is starting the engine....");
    }

    @PreDestroy
    public void stopEngine(){
        System.out.println("@Startup->@PreDestroy :: CarService is stopping the engine....");
    }
}
