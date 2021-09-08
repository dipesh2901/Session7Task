package io.getarrays.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userService.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
