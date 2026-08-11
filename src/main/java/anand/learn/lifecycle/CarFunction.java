package anand.learn.lifecycle;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;

public class CarFunction {
    public void setRace(@Observes StartupEvent startupEvent){
        System.out.println("@Observes -> StartupEvent :: CarFunction is setting the race....");
    }

    public void setBreak(@Observes ShutdownEvent shutdownEvent){
        System.out.println("@Observes -> ShutdownEvent :: CarFunction is breaking the car....");
    }
}
