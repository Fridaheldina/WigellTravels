package com.lundberg.wigelltravels.config;

import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.entity.Destination;
import com.lundberg.wigelltravels.repository.CustomerRepository;
import com.lundberg.wigelltravels.repository.DestinationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public org.springframework.boot.CommandLineRunner init(
            CustomerRepository customerRepo,
            DestinationRepository destinationRepo,
            PasswordEncoder encoder) {

        return args -> {
            if (customerRepo.count() == 0) {
                Customer admin = new Customer();
                admin.setName("Admin");
                admin.setUsername("a");
                admin.setPassword(encoder.encode("1234"));
                admin.setRole("ROLE_ADMIN");
                customerRepo.save(admin);

                Customer user = new Customer();
                user.setName("User");
                user.setUsername("u");
                user.setPassword(encoder.encode("1234"));
                user.setRole("ROLE_USER");
                customerRepo.save(user);

                Customer c1 = new Customer();
                c1.setName("Frida Lundberg");
                c1.setUsername("frida");
                c1.setPassword(encoder.encode("1234"));
                c1.setRole("ROLE_USER");
                customerRepo.save(c1);

                Customer c2 = new Customer();
                c2.setName("Gustav Vinqvist");
                c2.setUsername("gustav");
                c2.setPassword(encoder.encode("1234"));
                c2.setRole("ROLE_USER");
                customerRepo.save(c2);

                Customer c3 = new Customer();
                c3.setName("Anna Andersson");
                c3.setUsername("anna");
                c3.setPassword(encoder.encode("1234"));
                c3.setRole("ROLE_USER");
                customerRepo.save(c3);

                Customer c4 = new Customer();
                c4.setName("Erik Karlsson");
                c4.setUsername("erik");
                c4.setPassword(encoder.encode("1234"));
                c4.setRole("ROLE_USER");
                customerRepo.save(c4);

                Customer c5 = new Customer();
                c5.setName("Lisa Nilsson");
                c5.setUsername("lisa");
                c5.setPassword(encoder.encode("1234"));
                c5.setRole("ROLE_USER");
                customerRepo.save(c5);

                System.out.println("Customers + Admin created!");
            }

            if (destinationRepo.count() == 0) {
                destinationRepo.save(new Destination("Paris", "France", "Hilton", 5000));
                destinationRepo.save(new Destination("Rome", "Italy", "City Hotel", 4500));
                destinationRepo.save(new Destination("Berlin", "Germany", "Central Stay", 3500));
                destinationRepo.save(new Destination("Barcelona", "Spain", "Beach Resort", 4000));
                destinationRepo.save(new Destination("Warsaw", "Poland", "Budget Inn", 3000));

                System.out.println("Destinations created!");
            }
        };
    }
}
