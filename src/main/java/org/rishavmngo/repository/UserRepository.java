package org.rishavmngo.repository;

import java.util.Optional;

import org.rishavmngo.domain.UserEntity;

public interface UserRepository {

	UserEntity create(UserEntity user);

	Optional<UserEntity> getById(Long id);

	Boolean delete(Long id);

	Boolean authenticateByEmailAndPassword(String email, String password);

	Optional<UserEntity> getUserByEmail(String email);

	Boolean update(UserEntity user);

}
