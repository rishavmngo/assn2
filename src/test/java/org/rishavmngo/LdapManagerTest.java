package org.rishavmngo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class LdapManagerTest {

	LdapManager underTest;

	@BeforeEach
	void setup() {

		this.underTest = new LdapManager();

	}

	@Test
	public void testConnection() {
		underTest.getConnection();
	}

	@Test
	public void closeConnection() {

		underTest.getConnection();

	}

}
