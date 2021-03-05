package com.ilyha.secsite.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ilyha.secsite.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("from User where username=:username")
	User findByUsername(@Param("username") String username);
	
	@Query("select userId from User where username=:username")
	Integer checkIfRegistered(@Param("username") String username);
}