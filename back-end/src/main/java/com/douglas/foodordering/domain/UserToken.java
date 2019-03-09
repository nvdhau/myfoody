package com.douglas.foodordering.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, updatable = false)
	private @NonNull String token;
	
	@Column(nullable = false, updatable = false)
	private @NonNull String email;
	
	@Column(nullable = false, updatable = false)
	private @NonNull Timestamp created;
	
	@Column(nullable = false)
	private @NonNull Timestamp expired;
}
