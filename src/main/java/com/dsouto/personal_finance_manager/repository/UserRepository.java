package com.dsouto.personal_finance_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsouto.personal_finance_manager.model.User;


public interface UserRepository extends JpaRepository<User, Long> {	
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
}
