package org.rishavmngo;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.sdk.LDAPConnection;

import jakarta.inject.Inject;

public class TestLdapManager extends LdapManager {

	private LDAPConnection ldapConnection;

	// @Inject
	// @ConfigProperty(name = "ldap.server.host")
	// String server_host;
	//
	// @Inject
	// @ConfigProperty(name = "ldap.server.port")
	// int server_port;
	//
	// @Inject
	// @ConfigProperty(name = "ldap.bind.dn")
	// String bind_dn;
	//
	// @Inject
	// @ConfigProperty(name = "ldap.bind.password")
	// String bind_password;

	@Override
	public LDAPConnection getConnection() {

		String bind_dn = "cn=Directory Manager";
		String bind_password = "ldap@803101";
		try {

			InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=user_crud,dc=com");

			config.addAdditionalBindCredentials(bind_dn, bind_password);
			InMemoryDirectoryServer inMemoryServer = new InMemoryDirectoryServer(config);
			inMemoryServer.importFromLDIF(true, "schemas/user_crud.ldif");

			inMemoryServer.startListening();

			this.ldapConnection = inMemoryServer.getConnection();
			System.out.println("In-memory Connection created");
			return this.ldapConnection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void closeConnection() {
		System.out.println("connection closed");
	}

}
