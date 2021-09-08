package io.getarrays.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userService.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}