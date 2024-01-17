package org.rishavmngo;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

public class TestConfiguration {

	public LdapManager provideLdapManager() {
		String testMode = System.getProperty("quarkus.test.mode");
		if ("test".equals(testMode)) {
			System.out.println("geeting the TestLdapmanager");
			return new TestLdapManager();
		} else {
			System.out.println("geeting the SimpleLdapmanager");
			return new LdapManager();
		}
	}
}
