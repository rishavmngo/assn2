package org.rishavmngo.controller;

import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;

import com.unboundid.ldap.sdk.LDAPConnection;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
public class UserController {

    private static final String LDAP_SERVER_HOST = "localhost";
    private static final int LDAP_SERVER_PORT = 3389;
    private static final String LDAP_BIND_DN = "cn=Directory Manager";
    private static final String LDAP_BIND_PASSWORD = "ldap@803101";
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
            userRepository.delete(id);

            return ResponseBuilder.ok("User has been deleted")
                    .build();
        } else {

            return ResponseBuilder.ok("Failed to Delete!")
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
