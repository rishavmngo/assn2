package org.rishavmngo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtils {

	public static String hashPasswordWithSalt(String password, String givenSalt) {

		System.out.println("reached here 0");
		byte[] salt = Base64.getDecoder().decode(givenSalt);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			System.out.println("reached here 1");
			md.update(salt);
			byte[] hashedPassword = md.digest(password.getBytes());
			System.out.println("reached here 2");

			// Combine the salt and hashed password and encode in Base64
			byte[] combined = new byte[salt.length + hashedPassword.length];
			System.arraycopy(salt, 0, combined, 0, salt.length);
			System.arraycopy(hashedPassword, 0, combined, salt.length,
					hashedPassword.length);

			return Base64.getEncoder().encodeToString(combined);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
