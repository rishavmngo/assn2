package org.rishavmngo;

import org.eclipse.microprofile.config.ConfigProvider;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class LDAPConnectionProducer {

	@Produces
	public LDAPConnection createConnection() throws LDAPException {
		System.out.println("insted234832098490238");
		// Load properties from application.properties
		String host = ConfigProvider.getConfig().getValue("ldap.server.host", String.class);
		int port = ConfigProvider.getConfig().getValue("ldap.server.port", Integer.class);
		String bindDN = ConfigProvider.getConfig().getValue("ldap.bind.dn", String.class);
		String password = ConfigProvider.getConfig().getValue("ldap.bind.password", String.class);

		System.out.println("insted234832098490238");
		return new LDAPConnection(host, port, bindDN, password);
	}
}
