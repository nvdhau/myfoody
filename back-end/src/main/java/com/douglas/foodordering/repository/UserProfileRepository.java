package com.douglas.foodordering.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.douglas.foodordering.domain.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
	Optional<UserProfile> findByEmail(String email);
}
