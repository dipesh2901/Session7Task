package io.getarrays.userService.userServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.getarrays.userService.domain.Role;
import io.getarrays.userService.domain.User;
import io.getarrays.userService.repository.RoleRepository;
import io.getarrays.userService.repository.UserRepository;
import io.getarrays.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		log.info("Saving new User {} to the database", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new Role {} to the database", role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to user {}", roleName, username);
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);

	}

	@Override
	public User getUser(String username) {
		log.info("getting username {}", username);
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("getting all Users");
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("Username Not Found!");
			throw new UsernameNotFoundException("Username not Found!!");
		} else {
			log.info("Username found in the database {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
