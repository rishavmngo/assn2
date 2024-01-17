package org.rishavmngo;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.unboundid.ldap.sdk.LDAPConnection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LdapManager {

	private LDAPConnection ldapConnection;

	@Inject
	@ConfigProperty(name = "ldap.server.host")
	String server_host;

	@Inject
	@ConfigProperty(name = "ldap.server.port")
	int server_port;

	@Inject
	@ConfigProperty(name = "ldap.bind.dn")
	String bind_dn;

	@Inject
	@ConfigProperty(name = "ldap.bind.password")
	String bind_password;

	public LDAPConnection getConnection() {

		try {

			this.ldapConnection = new LDAPConnection(this.server_host, this.server_port,
					this.bind_dn,
					this.bind_password);
			System.out.println("connection created");
			return this.ldapConnection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void closeConnection() {
		System.out.println("connection closed");
		this.ldapConnection.close();
	}
}
