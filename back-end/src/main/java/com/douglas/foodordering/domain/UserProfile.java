package com.douglas.foodordering.domain;

import java.sql.Date;
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
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, updatable = false, unique = true)
	private @NonNull String email;
	
	@Column
	private String firstname, lastname;
	
	@Column
	private String address, phonenumbers;
	
	@Column
	private Date dateofbirth;
	
	@Column(nullable = false, updatable = false)
	private @NonNull Timestamp joindate;
}