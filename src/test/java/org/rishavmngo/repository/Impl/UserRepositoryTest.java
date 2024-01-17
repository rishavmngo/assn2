package org.rishavmngo.repository.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
	void testRegisterFailed() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);
		UserEntity u1 = underTest.create(user);

		assertNull(u1);
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
	void testGetByEmail() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);

		Optional<UserEntity> u1 = underTest.getUserByEmail(user.getEmail());

		assertTrue(u1.isPresent());
	}

	@Test
	void testGetByEmailIfNotPresent() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		// UserEntity u = underTest.create(user);

		Optional<UserEntity> u1 = underTest.getUserByEmail(user.getEmail());

		assertFalse(u1.isPresent());
	}

	@Test
	void testUpdationOfUser() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);
		Optional<UserEntity> u1 = underTest.getUserByEmail(user.getEmail());

		UserEntity userUpdated = UserEntity.builder().id(1L).firstName("newFirstname").lastName("newLastname")
				.email("rishavmngo@gmail.com").build();
		userUpdated.setId(u1.get().getId());

		Boolean updated = underTest.update(userUpdated);

		assertTrue(updated);

	}

	@Test
	void testUpdationOfUserFailWhenNotProvidedCorrectId() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);
		Optional<UserEntity> u1 = underTest.getUserByEmail(user.getEmail());

		UserEntity userUpdated = UserEntity.builder().id(1L).firstName("newFirstname").lastName("newLastname")
				.email("rishavmngo@gmail.com").build();

		// userUpdated.setId(u1.get().getId());

		Boolean updated = underTest.update(userUpdated);

		assertFalse(updated);

	}

	@Test
	void testDeleteionOfUser() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);
		Boolean deleted = underTest.delete(u.getId());

		assertTrue(deleted);

	}

	@Test
	void testDeleteionOfUserFailCase() {

		UserEntity user = UserEntity.builder().id(1L).firstName("rishav").lastName("raj").email("rishavmngo@gmail.com")
				.password("1234567890").build();

		UserEntity u = underTest.create(user);
		Boolean deleted = underTest.delete(1232L);

		assertFalse(deleted);

	}

}
