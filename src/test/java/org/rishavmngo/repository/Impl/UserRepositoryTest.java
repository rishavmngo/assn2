package org.rishavmngo.repository.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rishavmngo.TestLdapManager;
import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserRepositoryTest {
	static {
		System.setProperty("quarkus.test.mode", "test");
	}

	// @Inject
	UserRepository underTest;

	@BeforeEach
	void setup() throws Exception {

		this.underTest = new UserRepositoryImpl(new TestLdapManager());
	}

	@AfterEach
	void setupAfter() {
	}

	@Test
	void testRegister() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);

		assertEquals(u.getEmail(), user.getEmail());
	}

	@Test
	void testAuthentication() {

		Boolean authenticate = underTest.authenticateByEmailAndPassword("john.doe@gmail.com", "1234567890");

		assertTrue(authenticate);

	}

	@Test
	void testAuthenticationForUnauthenticatedUser() {

		Boolean authenticate = underTest.authenticateByEmailAndPassword("rishav.doe@gmail.com", "1234567890");

		assertFalse(authenticate);

	}

	@Test
	void testDeletionOfUser() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);

		Optional<UserEntity> u1 = underTest.getUserByEmail(user.getEmail());

		// assertEquals(u.getEmail(), user.getEmail());
	}
}
