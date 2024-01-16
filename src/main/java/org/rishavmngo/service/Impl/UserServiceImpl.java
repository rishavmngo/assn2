package org.rishavmngo.service.Impl;

import java.util.Optional;

import org.rishavmngo.domain.UserEntity;
import org.rishavmngo.repository.UserRepository;
import org.rishavmngo.service.UserService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	UserRepository userRepository;

	@Override
	public Boolean add(UserEntity user) {
		Optional<UserEntity> u = userRepository.getUserByEmail(user.getEmail());

		if (u.isPresent()) {
			return false;

		} else {

			userRepository.create(user);
			return true;
		}
	}

	@Override
	public Boolean authenticate(UserEntity user) {

		return userRepository.authenticateByEmailAndPassword(user.getEmail(), user.getPassword());
	}

	@Override
	public Boolean delete(Long id) {
		Optional<UserEntity> user = userRepository.getById(id);
		if (user.isEmpty()) {
			return false;
		}

		return userRepository.delete(id);
	}

	@Override
	public Optional<UserEntity> getById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public Boolean update(UserEntity user) {

		return userRepository.update(user);
	}

}
