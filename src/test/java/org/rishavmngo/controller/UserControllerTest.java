package org.rishavmngo.controller;

import org.apache.http.entity.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.rishavmngo.TestLdapManager;
import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;
import org.rishavmngo.repository.Impl.UserRepositoryImpl;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import io.quarkus.test.junit.mockito.InjectMock;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

@QuarkusTest
public class UserControllerTest {

	@Inject
	UserController underTest;

	@InjectMock
	UserRepository userRepository;

	@BeforeEach
	void setup() {

	}

	@Test
	public void testRegisterUser() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.getUserByEmail(user.getEmail()))
				.thenReturn(Optional.ofNullable(null));

		// Mockito.doNothing().when(userRepository).create(user);

		RestResponse<String> response = underTest.register(user);

		assertEquals(200, response.getStatus());
	}

	@Test
	public void testRegisterUserUnsuccessful() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.getUserByEmail(user.getEmail()))
				.thenReturn(Optional.ofNullable(user));

		RestResponse<String> response = underTest.register(user);
		assertEquals(400, response.getStatus());
	}

	@Test
	public void testUserAuthenticationRoute() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();

		Mockito.when(userRepository.authenticateByEmailAndPassword(user.getEmail(), user.getPassword()))
				.thenReturn(true);

		RestResponse<String> response = underTest.login(user);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testUserAuthenticationRouteForUnAuthenticatedUser() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();

		Mockito.when(userRepository.authenticateByEmailAndPassword(user.getEmail(), "WrongPassword"))
				.thenReturn(false);

		RestResponse<String> response = underTest.login(user);
		assertEquals(401, response.getStatus());
	}

	@Test
	public void testFullUpdate() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.update(user))
				.thenReturn(true);

		RestResponse<String> response = underTest.FullUpdate(1L, user);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testFullUpdateFailCase() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.update(user))
				.thenReturn(false);

		RestResponse<String> response = underTest.FullUpdate(1L, user);
		assertEquals(400, response.getStatus());
	}

	@Test
	public void testDeletion() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.getById(1L))
				.thenReturn(Optional.of(user));

		RestResponse<String> response = underTest.Delete(1L);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testDeletionFailCase() {

		UserEntity user = UserEntity.builder().email("rishavmngo@gmail.com")
				.password("1234567890").build();
		Mockito.when(userRepository.getById(1L))
				.thenReturn(Optional.ofNullable(null));

		RestResponse<String> response = underTest.Delete(1L);
		assertEquals(400, response.getStatus());
	}
}
