package com.douglas.foodordering.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.douglas.foodordering.domain.User;

public interface UserRepositoty extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
