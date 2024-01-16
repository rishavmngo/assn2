package org.rishavmngo.repository.Impl;

import java.util.Optional;
import java.util.Random;

import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

	private static final String LDAP_SERVER_HOST = "localhost";
	private static final int LDAP_SERVER_PORT = 3389;
	private static final String LDAP_BIND_DN = "cn=Directory Manager";
	private static final String LDAP_BIND_PASSWORD = "ldap@803101";

	private Long getUID() {
		Random rand = new Random();
		Long id = rand.nextLong(1000000);
		return id;
	}

	@Override
	public UserEntity create(UserEntity user) {

		user.setId(getUID());

		LDAPConnection ldapConnection = null;
		try {
			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT,
					LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);
			String dn = "mail= " + user.getEmail() + ",ou=users,dc=todo_app,dc=com";
			Entry entry = new Entry(
					dn,
					new Attribute("objectClass", "top", "person", "organizationalPerson",
							"inetOrgPerson"),
					new Attribute("cn", user.getId().toString()),
					new Attribute("uid", user.getId().toString()),
					new Attribute("givenName", user.getFirstName()),
					new Attribute("sn", user.getLastName()),
					new Attribute("mail", user.getEmail()),
					new Attribute("userPassword", user.getPassword()));

			ldapConnection.add(entry);

			System.out.println("User created");
		} catch (LDAPException e) {
			System.out.println(e);
		} finally {
			ldapConnection.close();
		}
		return user;
	}

	@Override
	public Boolean delete(Long id) {

		Optional<UserEntity> user = getById(id);

		if (user.isEmpty()) {
			return false;
		}

		UserEntity deletedUser = user.get();

		LDAPConnection ldapConnection = null;
		try {
			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT,
					LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);
			String dn = "mail= " + deletedUser.getEmail() + ",ou=users,dc=todo_app,dc=com";

			ldapConnection.delete(dn);

			return true;
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;
		} finally {
			ldapConnection.close();
		}

	}

	@Override
	public Optional<UserEntity> getById(Long id) {
		UserEntity user = new UserEntity();

		user.setId(id);
		LDAPConnection ldapConnection = null;
		try {

			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT, LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);

			String searchBase = "ou=users,dc=todo_app,dc=com";
			String filter = String.format("(&(uid=%d)(objectclass=person))", id);

			SearchRequest searchRequest = new SearchRequest(
					searchBase,
					SearchScope.SUB,
					filter);
			SearchResult result = ldapConnection.search(searchRequest);
			if (!result.getSearchEntries().isEmpty()) {

				SearchResultEntry entry = result.getSearchEntries().get(0);

				user.setEmail(entry.getAttributeValue("mail"));
				user.setId(Long.parseLong(entry.getAttributeValue("uid")));
				user.setLastName(entry.getAttributeValue("sn"));
				user.setFirstName(entry.getAttributeValue("givenName"));
				user.setPassword(entry.getAttributeValue("userPassword"));
				return Optional.of((user));
			} else {

				return Optional.ofNullable(null);
			}

		} catch (LDAPException e) {
			e.printStackTrace();
			return Optional.ofNullable(null);
		} finally {
			ldapConnection.close();
		}
	}

	@Override
	public Boolean authenticateByEmailAndPassword(String email, String password) {
		LDAPConnection ldapConnection = null;
		try {

			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT, LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);

			String userDn = String.format("mail=%s,ou=users,dc=todo_app,dc=com", email);

			ldapConnection.bind(userDn, password);
			System.out.println("authenicated");
			return true;
		} catch (LDAPException e) {
			return false;
		} finally {
			ldapConnection.close();
		}
	}

	@Override
	public Optional<UserEntity> getUserByEmail(String email) {
		UserEntity user = new UserEntity();
		user.setEmail(email);
		LDAPConnection ldapConnection = null;
		try {

			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT,
					LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);

			String searchBase = "ou=users,dc=todo_app,dc=com";
			String filter = String.format("(&(mail=%s)(objectclass=person))",
					user.getEmail());

			SearchRequest searchRequest = new SearchRequest(
					searchBase,
					SearchScope.SUB,
					filter);
			SearchResult result = ldapConnection.search(searchRequest);

			if (!result.getSearchEntries().isEmpty()) {
				SearchResultEntry entry = result.getSearchEntries().get(0);

				user.setId(Long.parseLong(entry.getAttributeValue("uid")));
				user.setLastName(entry.getAttributeValue("sn"));
				user.setFirstName(entry.getAttributeValue("givenName"));
				return Optional.of(user);
			} else {
				return Optional.ofNullable(null);
			}

		} catch (LDAPException e) {
			return Optional.ofNullable(null);
		} finally {
			ldapConnection.close();
		}
	}

	@Override
	public Boolean update(UserEntity user) {

		LDAPConnection ldapConnection = null;
		Optional<UserEntity> usera = getById(user.getId());
		if (usera.isEmpty()) {
			return false;
		}

		UserEntity userc = usera.get();

		user.setEmail(userc.getEmail());
		try {
			ldapConnection = new LDAPConnection(LDAP_SERVER_HOST, LDAP_SERVER_PORT,
					LDAP_BIND_DN,
					LDAP_BIND_PASSWORD);
			String dn = "mail= " + user.getEmail().toString() +
					",ou=users,dc=todo_app,dc=com";
			Modification modification1 = new Modification(ModificationType.REPLACE,
					"givenName", user.getFirstName());
			Modification modification2 = new Modification(ModificationType.REPLACE, "sn",
					user.getLastName());
			ldapConnection.modify(dn, modification1, modification2);
			return true;
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;

		} finally {
			ldapConnection.close();
		}
	}

}
