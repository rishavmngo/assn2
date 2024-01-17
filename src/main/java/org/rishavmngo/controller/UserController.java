package org.rishavmngo.controller;

import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/register")
    public RestResponse<String> register(UserEntity user) {

        Optional<UserEntity> u = userRepository.getUserByEmail(user.getEmail());

        if (u.isPresent()) {
            return ResponseBuilder.ok("Email already taken!")
                    .status(Response.Status.BAD_REQUEST).build();

        } else {

            userRepository.create(user);
            return ResponseBuilder.ok("User registration successful")
                    .build();
        }

    }

    @POST
    @Path("/login")
    public RestResponse<String> login(UserEntity user) {

        if (userRepository.authenticateByEmailAndPassword(user.getEmail(),
                user.getPassword())) {

            return ResponseBuilder.ok("User authenticated")
                    .build();
        } else {

            return ResponseBuilder.ok("Unauthenticated!")
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    @PUT
    @Path("/update/{id}")
    public RestResponse<String> FullUpdate(@PathParam("id") Long id, UserEntity user) {

        user.setId(id);

        if (userRepository.update(user)) {

            return ResponseBuilder.ok("User has been updated")
                    .build();
        } else {

            return ResponseBuilder.ok("Failed to update!")
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public RestResponse<String> Delete(@PathParam("id") Long id) {

        if (userRepository.getById(id).isEmpty()) {

            return ResponseBuilder.ok("Failed to Delete no user exist!")
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        } else {

            userRepository.delete(id);
            return ResponseBuilder.ok("User has been deleted")
                    .build();
        }
    }
}
