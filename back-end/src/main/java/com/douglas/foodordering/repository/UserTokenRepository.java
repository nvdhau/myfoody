package com.douglas.foodordering.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.douglas.foodordering.domain.UserToken;

public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

	Optional<UserToken> findByTokenAndEmail(String token, String email);
}
