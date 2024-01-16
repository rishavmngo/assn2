package org.rishavmngo.service;

import java.util.Optional;

import org.rishavmngo.domain.UserEntity;

public interface UserService {
	Boolean add(UserEntity user);

	Boolean authenticate(UserEntity user);

	Optional<UserEntity> getById(Long id);

	Boolean delete(Long id);

	Boolean update(UserEntity user);
}
