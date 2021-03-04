package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CosmosController{
   private static final Logger LOGGER = LoggerFactory.getLogger(CosmosController.class);

   @Autowired
   private UserRepository repository;

   @GetMapping("/cosmosdb")
   public void handleMessage(String message) {
this.repository.deleteAll().block();
        LOGGER.info("Deleted all data in container.");

        final User testUser = new User("testId", "testFirstName", "testLastName", "test address line one");

        // Save the User class to Azure Cosmos DB database.
        final Mono<User> saveUserMono = repository.save(testUser);

        final Flux<User> firstNameUserFlux = repository.findByFirstName("testFirstName");

        //  Nothing happens until we subscribe to these Monos.
        //  findById will not return the user as user is not present.
        final Mono<User> findByIdMono = repository.findById(testUser.getId());
        final User findByIdUser = findByIdMono.block();
        Assert.isNull(findByIdUser, "User must be null");

        final User savedUser = saveUserMono.block();
        Assert.state(savedUser != null, "Saved user must not be null");
        Assert.state(savedUser.getFirstName().equals(testUser.getFirstName()), "Saved user first name doesn't match");

        firstNameUserFlux.collectList().block();

        final Optional<User> optionalUserResult = repository.findById(testUser.getId()).blockOptional();
        Assert.isTrue(optionalUserResult.isPresent(), "Cannot find user.");

        final User result = optionalUserResult.get();
        Assert.state(result.getFirstName().equals(testUser.getFirstName()), "query result firstName doesn't match!");
        Assert.state(result.getLastName().equals(testUser.getLastName()), "query result lastName doesn't match!");

        LOGGER.info("findOne in User collection get result: {}", result.toString());
   }
}
