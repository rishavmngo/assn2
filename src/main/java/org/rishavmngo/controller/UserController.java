package org.rishavmngo.controller;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.service.UserService;

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

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public RestResponse<String> register(@Valid UserEntity user) {

        Boolean created = userService.add(user);

        if (created) {
            return ResponseBuilder.ok("User registration successful")
                    .build();
        } else {
            return ResponseBuilder.ok("Email already taken!")
                    .status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/login")
    public RestResponse<String> login(UserEntity user) {

        if (userService.authenticate(user)) {

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

        if (userService.update(user)) {

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

        if (userService.delete(id)) {

            return ResponseBuilder.ok("User has been deleted")
                    .build();
        } else {

            return ResponseBuilder.ok("Failed to Delete!")
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
