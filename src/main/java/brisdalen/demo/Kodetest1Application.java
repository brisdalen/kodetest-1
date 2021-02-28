package brisdalen.demo;

import domain.Booking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

@SpringBootApplication
public class Kodetest1Application {

    public static void main(String[] args) {
        RoomInformationSystem roomInformationSystem = new RoomInformationSystem();
        SpringApplication.run(Kodetest1Application.class, args);
    }

    @Bean
    @Scope("singleton")
    public RoomManager roomManagerSingleton() {
        return new RoomManager();
    }

    @Bean
    @Scope("singleton")
    public RoomBookingManager roomBookingManagerSingleton() {
        return new RoomBookingManager(roomManagerSingleton(), new ArrayList<>());
    }

}
